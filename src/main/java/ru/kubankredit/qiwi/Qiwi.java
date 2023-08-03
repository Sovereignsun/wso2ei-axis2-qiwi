package ru.kubankredit.qiwi;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.ServiceLifeCycle;
import ru.kubankredit.qiwi.core.Constants;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.core.http.HTTPModule;
import ru.kubankredit.qiwi.emulators.QiwiHTTPEmulator;
import ru.kubankredit.qiwi.methods.main.addPayment.AddPaymentProcess;
import ru.kubankredit.qiwi.methods.main.getCharges.GetChargesProcess;
import ru.kubankredit.qiwi.methods.main.possPayment.PossPaymentProcess;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalanceParser;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalancePayload;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalanceRequest;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalanceResponse;
import ru.kubankredit.qiwi.methods.qiwi.getUIProviders.GetUIProvidersPayload;
import ru.kubankredit.qiwi.methods.qiwi.getUIProviders.GetUIProvidersRequest;
import ru.kubankredit.qiwi.methods.qiwi.getUIProviders.GetUIProvidersResponse;
import ru.kubankredit.qiwi.methods.qiwi.setPublicKey.*;
import ru.kubankredit.qiwi.responses.UniformResponse;

import java.io.ByteArrayInputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>Главный класс по интеграции с QIWI</h1>
 * Модуль содержит методы для работы с QIWI в рамках интеграции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-06-20
 */
public class Qiwi implements ServiceLifeCycle {

    /**
     * Событие по таймеру, которое запускает чтение из сообщений из брокера сообщений ActiveMQ
     */
    public static final TimerTask consumeMQ = new TimerTask() {
        @Override
        public void run() {

            if (Core.settings.getActiveMQSettings().getConsumeActive()) {
                String methodName = new Object() {
                }.getClass().getEnclosingMethod().getName();
                Core.lm.generateGUID();
                //Core.lm.debug(Core.class.getName(), methodName, "TimerTask");
                Core.activeMQCore.listenGetPaymentStatusQueue();
            }
        }
    };
    protected final String thisClassName = this.getClass().getName();
    private final Core core = new Core();
    private final UniformResponse uniformResponse = new UniformResponse();
    /**
     * Таймер, который выполняется по интервалу времени, настройки в settings.yml (consumeInterval)
     */
    Timer timer = new Timer(true);
    private String methodName;

    /**
     * Событие, которое запускается при deploy сервиса
     *
     * @param configurationContext Контекст конфигурации сервиса и его жизнедеятельности
     * @param axisService          Экземпляр сервиса Axis2
     */
    @Override
    public void startUp(ConfigurationContext configurationContext, AxisService axisService) {

        String messageID = (String) configurationContext.getProperty("MESSAGE_ID");
        System.out.println("messageID=" + messageID);
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Core.lm.generateGUID();
        Core.lm.debug(Core.class.getName(), methodName, "Service startUp");
        timer.scheduleAtFixedRate(consumeMQ, 0, Core.settings.getActiveMQSettings().getConsumeInterval() * 1000);
    }

    /**
     * Событие, которое запускается при undeploy сервиса, при восвобождении ресурсов
     *
     * @param configurationContext Контекст конфигурации сервиса и его жизнедеятельности
     * @param axisService          Экземпляр сервиса Axis2
     */
    @Override
    public void shutDown(ConfigurationContext configurationContext, AxisService axisService) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Core.lm.debug(Core.class.getName(), methodName, "Service shutDown");
        // Выключает таймер
        timer.cancel();
    }

    /**
     * Запрос начислений из ПЦ в QIWI
     *
     * @param requestData Запрос в XML формате в виде OMElement из ПЦ
     * @return Ответ в ПЦ в XML виде
     * @apiNote Пример запроса в пакете <i><b>Examples -> Requests -> GetChargesRequest</b></i>
     */
    public OMElement GetCharges(OMElement requestData) {
        methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        MessageContext messageContext = MessageContext.getCurrentMessageContext();

        final String QiwiServiceId = core.getQueryParam("id", messageContext);

        if (QiwiServiceId == null || QiwiServiceId.isEmpty()) {
            return uniformResponse.respond(UniformResponse.OperationName.GetCharges, Constants.NO_SERVICE_ID, Constants.NO_SERVICE_ID_DETAIL, false);
        }

        Core.lm.debug(thisClassName, methodName, "QiwiServiceId: " + QiwiServiceId);

        final String QiwiTerminalId = core.getQueryParam("terminal", messageContext);

        Core.lm.debug(thisClassName, methodName, "QiwiTerminalId: " + QiwiTerminalId);

        // Инициализируем обьект Process для текущей операции
        GetChargesProcess getChargesProcess = new GetChargesProcess();

        OMElement processResponse = null;
        try {
            // Вызываем метод для исполнения логики для текущей операции
            processResponse = getChargesProcess.process(requestData, QiwiServiceId, QiwiTerminalId);
        } catch (AbstractException e) {
            return uniformResponse.respond(UniformResponse.OperationName.GetCharges, e.getMessage(), e.getDetailMessage(), false);
        }

        if (processResponse != null) {
            return uniformResponse.respond(UniformResponse.OperationName.GetCharges, null, null, true, processResponse);
        } else {
            return uniformResponse.respond(UniformResponse.OperationName.GetCharges, null, null, true);
        }
    }

    /**
     * Запрос последконтроля перед созданием платежа из ПЦ в QIWI
     *
     * @param requestData Запрос в XML формате в виде OMElement из ПЦ
     * @return Ответ в ПЦ в XML виде
     * @apiNote Пример запроса в пакете <i><b>Examples -> Requests -> PossPaymentRequest</b></i>
     */
    public OMElement PossPayment(OMElement requestData) {
        methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        MessageContext messageContext = MessageContext.getCurrentMessageContext();

        final String QiwiServiceId = core.getQueryParam("id", messageContext);

        if (QiwiServiceId == null || QiwiServiceId.isEmpty()) {
            return uniformResponse.respond(UniformResponse.OperationName.PossPayment, Constants.NO_SERVICE_ID, Constants.NO_SERVICE_ID_DETAIL, false);
        }

        Core.lm.debug(thisClassName, methodName, "QiwiServiceId: " + QiwiServiceId);

        final String QiwiTerminalId = core.getQueryParam("terminal", messageContext);

        Core.lm.debug(thisClassName, methodName, "QiwiTerminalId: " + QiwiTerminalId);

        // Инициализируем обьект Process для текущей операции
        PossPaymentProcess possPaymentProcess = new PossPaymentProcess();

        OMElement processResponse = null;
        try {
            // Вызываем метод для исполнения логики для текущей операции
            processResponse = possPaymentProcess.process(requestData, QiwiServiceId, QiwiTerminalId);
        } catch (AbstractException e) {
            return uniformResponse.respond(UniformResponse.OperationName.PossPayment, e.getMessage(), e.getDetailMessage(), false);
        }

        if (processResponse != null) {
            return uniformResponse.respond(UniformResponse.OperationName.PossPayment, null, null, true, processResponse);
        } else {
            return uniformResponse.respond(UniformResponse.OperationName.PossPayment, null, null, true);
        }
    }

    /**
     * Запрос добавления платежа из ПЦ в QIWI
     *
     * @param requestData Запрос в XML формате в виде OMElement из ПЦ
     * @return Ответ в ПЦ в XML виде
     * @apiNote Пример запроса в пакете <i><b>Examples -> Requests -> AddPaymentRequest</b></i>
     */
    public OMElement AddPayment(OMElement requestData) {
        methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        MessageContext messageContext = MessageContext.getCurrentMessageContext();

        final String QiwiServiceId = core.getQueryParam("id", messageContext);

        if (QiwiServiceId == null || QiwiServiceId.isEmpty()) {
            return uniformResponse.respond(UniformResponse.OperationName.AddPayment, Constants.NO_SERVICE_ID, Constants.NO_SERVICE_ID_DETAIL, false);
        }

        Core.lm.debug(thisClassName, methodName, "QiwiServiceId: " + QiwiServiceId);

        final String QiwiTerminalId = core.getQueryParam("terminal", messageContext);

        Core.lm.debug(thisClassName, methodName, "QiwiTerminalId: " + QiwiTerminalId);

        // Инициализируем обьект Process для текущей операции
        AddPaymentProcess addPaymentProcess = new AddPaymentProcess();

        try {
            // Вызываем метод для исполнения логики для текущей операции
            addPaymentProcess.process(requestData, QiwiServiceId, QiwiTerminalId);
        } catch (AbstractException e) {
            return uniformResponse.respond(UniformResponse.OperationName.AddPayment, e.getMessage(), e.getDetailMessage(), false);
        }

        // Return the whole SOAP envelope
        return uniformResponse.respond(UniformResponse.OperationName.AddPayment, null, null, true);
    }

    /**
     * Получение данных о балансе на счёту терминала
     *
     * @return Ответ от QIWI по запросу о состоянии баланса
     */
    public OMElement GetBalance(OMElement requestData) {
        methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        GetBalanceRequest getBalanceRequest = new GetBalanceRequest();
        GetBalancePayload getBalancePayload = new GetBalancePayload();
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        GetBalanceParser getBalanceParser = new GetBalanceParser();
        HTTPModule httpModule = new HTTPModule();

        MessageContext messageContext = MessageContext.getCurrentMessageContext();

        String QiwiTerminalId = core.getQueryParam("terminal", messageContext);

        if (QiwiTerminalId == null) {
            QiwiTerminalId = Core.settings.getQiwiSettings().getTerminalId();
        }

        Core.lm.debug(thisClassName, methodName, "QiwiTerminalId: " + QiwiTerminalId);

        getBalanceRequest.setTerminal(QiwiTerminalId);

        String PayloadXMLString = getBalancePayload.buildXMLPayload(getBalanceRequest);

        try {
            String QiwiResponse;
            if (!Core.DeveloperMode) {
                QiwiResponse = httpModule.HTTPPost(PayloadXMLString, true);
            } else {
                QiwiResponse = new QiwiHTTPEmulator().getBalance();
            }
            getBalanceResponse = getBalanceParser.ParseResponse(QiwiResponse);
        } catch (Exception e) {
            getBalanceResponse.setResultCode(-1);
            getBalanceResponse.setResultMessage(e.getMessage());
        }

        OMFactory responseFactory = OMAbstractFactory.getOMFactory();

        return OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(getBalanceResponse.toString().getBytes())).getDocumentElement();
    }

    /**
     * Получение информации о поставщиках услуг в КИВИ
     * @return Ответ от QIWI по запросу информации о поставщиках услуг
     */
    public OMElement GetUIProviders(OMElement requestData) {
        methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        GetUIProvidersRequest getUIProvidersRequest = new GetUIProvidersRequest();
        GetUIProvidersPayload getUIProvidersPayload = new GetUIProvidersPayload();
        GetUIProvidersResponse getUIProvidersResponse = new GetUIProvidersResponse();
        HTTPModule httpModule = new HTTPModule();

        MessageContext messageContext = MessageContext.getCurrentMessageContext();

        String QiwiTerminalId = core.getQueryParam("terminal", messageContext);

        if (QiwiTerminalId == null) {
            QiwiTerminalId = Core.settings.getQiwiSettings().getTerminalId();
        }

        Core.lm.debug(thisClassName, methodName, "QiwiTerminalId: " + QiwiTerminalId);

        getUIProvidersRequest.setTerminal(QiwiTerminalId);

        String PayloadXMLString = getUIProvidersPayload.buildXMLPayload(getUIProvidersRequest);

        try {
            String QiwiResponse;
            if (!Core.DeveloperMode) {
                QiwiResponse = httpModule.HTTPPost(PayloadXMLString, true);
            } else {
                QiwiResponse = new QiwiHTTPEmulator().getUIProviders();
            }
            OMFactory responseFactory = OMAbstractFactory.getOMFactory();
            return OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(QiwiResponse.getBytes())).getDocumentElement();
        } catch (Exception e) {
            getUIProvidersResponse.setResultCode(-1);
            getUIProvidersResponse.setResultMessage(e.getMessage());
        }

        OMFactory responseFactory = OMAbstractFactory.getOMFactory();
        return OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(getUIProvidersResponse.toString().getBytes())).getDocumentElement();
    }

    /**
     * Подписывание данных с помощью закрытого ключа при помощи SHA1
     * и кодируем в BASE64
     * @param requestData Объект содержащий данные для регистрации публичного ключа в QIWI
     * @return Ответ от QIWI по запросу регистрации публичного ключа
     * @throws AbstractException Исключение обёртка разного рода исключений
     */
    public OMElement SetPublicKey(OMElement requestData) throws AbstractException {
        methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        SetPublicKeySerializer setPublicKeySerializer = new SetPublicKeySerializer();
        SetPublicKeyRequest setPublicKeyRequest = setPublicKeySerializer.serialize(requestData);
        SetPublicKeyPayload setPublicKeyPayload = new SetPublicKeyPayload();
        SetPublicKeyResponse setPublicKeyResponse = new SetPublicKeyResponse();
        SetPublicKeyParser setPublicKeyParser = new SetPublicKeyParser();
        HTTPModule httpModule = new HTTPModule();

        String PayloadXMLString = setPublicKeyPayload.buildXMLPayload(setPublicKeyRequest);

        try {
            String QiwiResponse;
            if (!Core.DeveloperMode) {
                QiwiResponse = httpModule.HTTPPost(PayloadXMLString, false);
            } else {
                QiwiResponse = new QiwiHTTPEmulator().setPublicKey();
            }
            setPublicKeyResponse = setPublicKeyParser.ParseResponse(QiwiResponse);
        } catch (Exception e) {
            setPublicKeyResponse.setResultCode(-1);
            setPublicKeyResponse.setResultMessage(e.getMessage());
        }

        OMFactory responseFactory = OMAbstractFactory.getOMFactory();

        return OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(setPublicKeyResponse.toString().getBytes())).getDocumentElement();
    }

    /**
     * Прокинуть запрос в чистом виде до КИВИ
     *
     * @param requestData Запрос в XML формате
     * @return Ответ от QIWI по запросу в XML формате
     */
    public OMElement ProxyToQiwi(OMElement requestData) {
        methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        HTTPModule httpModule = new HTTPModule();

        try {
            String QiwiResponse = httpModule.HTTPPost(requestData.toString(), true);

            OMFactory factory = OMAbstractFactory.getOMFactory();

            return OMXMLBuilderFactory.createOMBuilder(factory, new ByteArrayInputStream(QiwiResponse.getBytes())).getDocumentElement();
        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Получение версии сервиса
     *
     * @return Версия сервиса
     * @author Листопадов Александр Сергеевич
     * @since 2023-07-25
     */
    public OMElement Version(OMElement requestData) {

        Core.lm.generateGUID();
        Core.lm.debug(thisClassName, methodName, "Request: " + requestData);

        String SERVICE_VERSION = "1.0.0";
        String version = "<version>" + SERVICE_VERSION + "</version>";

        OMFactory responseFactory = OMAbstractFactory.getOMFactory();

        return OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(version.getBytes())).getDocumentElement();
    }
}