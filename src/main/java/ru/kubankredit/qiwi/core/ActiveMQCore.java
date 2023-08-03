package ru.kubankredit.qiwi.core;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import ru.kubankredit.qiwi.core.enums.EnumQueueType;
import ru.kubankredit.qiwi.methods.main.getStatus.GetPaymentStatusProcess;

import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * <h2>Класс работы с брокером сообщений ActiveMQ</h2>
 * Модуль позволяет отправлять и получать информацию из очередей и топиков ActiveMQ
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-31
 */
public class ActiveMQCore {

    private static final long CONSUME_TIMEOUT = 3000;
    private static ActiveMQCore instance;
    /**
     * Наименование очереди для получения информацию о текущем статусе платежа в Киви
     */
    final protected String thisClassName = this.getClass().getName();
    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private ConnectionFactory activeMQConnectionFactory;

    private ActiveMQCore() {
        try {
            String url = Core.settings.getActiveMQSettings().getUrl();
            String username = Core.settings.getActiveMQSettings().getUsername();
            String password = Core.settings.getActiveMQSettings().getPassword();
            if (url != null && username != null && password != null) {
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, url);
                Core.lm.debug(thisClassName, ActiveMQCore.class.getName(), "ActiveMQConnectionFactory created successfully");

                ((ActiveMQConnectionFactory) connectionFactory).setUseAsyncSend(true);

                RedeliveryPolicy redeliveryPolicy = ((ActiveMQConnectionFactory) connectionFactory).getRedeliveryPolicy();
                redeliveryPolicy.setInitialRedeliveryDelay(1000);
                redeliveryPolicy.setBackOffMultiplier(2);
                redeliveryPolicy.setUseExponentialBackOff(true);
                redeliveryPolicy.setMaximumRedeliveries(2);

                ActiveMQPrefetchPolicy prefetchPolicy = ((ActiveMQConnectionFactory) connectionFactory).getPrefetchPolicy();
                prefetchPolicy.setQueuePrefetch(3);

                this.activeMQConnectionFactory = connectionFactory;
            }

        } catch (Exception e) {
            Core.lm.error(thisClassName, ActiveMQCore.class.getName(), "ActiveMQConnectionFactory error: " + e.getMessage());
        }
    }

    public static synchronized ActiveMQCore getInstance() {
        if (instance == null) {
            instance = new ActiveMQCore();
        }
        return instance;
    }

    /**
     * Отправить сообщение в очередь
     *
     * @param xmlRequest XML запрос. который полетит в очередь
     * @param queueType  Тип сообщения
     */
    public void addToQueue(String xmlRequest, EnumQueueType queueType) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        if (activeMQConnectionFactory == null) {
            Core.lm.error(thisClassName, methodName, "ActiveMQConnectionFactory is null");
            return;
        }
        try {
            // Create the connection and session
            Connection connection = activeMQConnectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a new queues
            Queue queue = session.createQueue(queueType.getQueueName());
            Core.lm.debug(thisClassName, methodName, "ActiveMQ Queue name: " + queue.getQueueName());

            // Create a producer for the queue
            MessageProducer producer = session.createProducer(queue);
            producer.setPriority(9);

            // Prepare the XML request message
            TextMessage requestMessage = session.createTextMessage(xmlRequest);
            requestMessage.setJMSType(queueType.getXmlType());

            // Send the message to the queue
            producer.send(requestMessage);
            Core.lm.debug(thisClassName, methodName, "Enqueued:\r\n" + xmlRequest);

            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            Core.lm.error(thisClassName, methodName, "Exception during enqueue: " + e.getMessage());
        }

    }

    /**
     * Включить потребитель сообщений брокера ActiveMQ (запускать по таймеру)
     */
    public void listenGetPaymentStatusQueue() {
        // Submit the long-running task to the ExecutorService
        //executor.submit(() -> {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        //Core.lm.debug(thisClassName, methodName, "ListenGetPaymentStatusQueue started");
        if (activeMQConnectionFactory == null) {
            Core.lm.error(thisClassName, methodName, "ActiveMQConnectionFactory is null");
            return;
        }
        try {
            // Create the connection and session
            Connection connection = activeMQConnectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a new queues
            Queue queue = session.createQueue(Constants.QIWI_GET_PAYMENT_STATUS_QUEUE);

            // Create a consumer for the queue
            MessageConsumer consumer = session.createConsumer(queue);
            // Create a producer for the queue
            MessageProducer producer = session.createProducer(queue);

            connection.start();

            Integer consumeCount = Core.settings.getActiveMQSettings().getConsumeMessagesPerCall();

            GetPaymentStatusProcess getPaymentStatusProcess = new GetPaymentStatusProcess();

            // Read and process incoming messages
            while (consumeCount > 0) {
                TextMessage receivedMessage = (TextMessage) consumer.receive(CONSUME_TIMEOUT);
                if (receivedMessage == null) {
                    break;
                }
                String MessageID = receivedMessage.getJMSMessageID();
                // Parse the message
                try {
                    String xmlContent = receivedMessage.getText();
                    consumeCount--;
                    Core.lm.debug(thisClassName, methodName, "[" + MessageID + "] Consumed Message: \r\n" + xmlContent);
                    if (receivedMessage.getJMSType().equalsIgnoreCase(EnumQueueType.GET_PAYMENT_STATUS.getXmlType())) {
                        getPaymentStatusProcess.process(xmlContent);
                    }
                } catch (Exception e) {
                    Core.lm.error(thisClassName, methodName, e.getMessage());
                    // If error occurs, send message back to the queue for retry
                    if (receivedMessage.getJMSPriority() > 1) {
                        producer.setPriority(receivedMessage.getJMSPriority() - 1);
                    }
                    producer.send(receivedMessage);
                }
            }
            consumer.close();
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            Core.lm.error(thisClassName, methodName, "Exception during enqueue: " + e.getMessage());
        }
        //Core.lm.debug(thisClassName, methodName, "ListenGetPaymentStatusQueue stopped");
        //executor.shutdown();
        //});
    }

    /*public static void main(String[] args) {
        Core.activeMQCore.listenGetPaymentStatusQueue();
    }*/

    /*public static void main(String[] args) {
        ActiveMQCore activeMQCore = new ActiveMQCore();

        String xmlRequest = "<GetPaymentStatus><pmnt_id>986235</pmnt_id><qiwi_id>1111111111111</qiwi_id></GetPaymentStatus>";

        activeMQCore.addToQueue(XMLBeautifier.IndentXML(xmlRequest),EnumQueueType.GET_PAYMENT_STATUS);
    }*/
}
