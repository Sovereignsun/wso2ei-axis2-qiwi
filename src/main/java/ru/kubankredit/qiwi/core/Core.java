package ru.kubankredit.qiwi.core;

import org.apache.axis2.context.MessageContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.synapse.transport.nhttp.NhttpConstants;
import org.w3c.dom.Document;
import ru.kubankredit.qiwi.core.logging.LogModule;
import ru.kubankredit.qiwi.core.settings.Settings;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1>Ядро обработки, центральный класс</h1>
 * Класс содержит центральные методы для различного рода обработок
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-02
 */
public class Core {

    public static final LogModule lm = new LogModule();
    public static final Settings settings = new Settings();

    private static AtomicReference<String> SESSION_ID = new AtomicReference<>();

    /**
     * Экземпляр класса брокера сообщений ActiveMQ
     */
    public static final ActiveMQCore activeMQCore = ActiveMQCore.getInstance();

    /**
     * Включить режим разработчика
     */
    public static final Boolean DeveloperMode = settings.getQiwiSettings().getDevMode();
    final protected String thisClassName = this.getClass().getName();

    public static void setCurrentSessionId(String sessionId) {
        if (!sessionId.isEmpty()) {
            SESSION_ID.set(sessionId);
        }
    }

    public static AtomicReference<String> getSessionId() {
        return SESSION_ID;
    }

    /**
     * Получение текущей даты
     *
     * @return Текущая дата в формате <b></b>yyyy-MM-dd'T'HH:mm:ss</b>
     */
    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return now.format(formatter);
    }

    /**
     * Трансформация документа в строку
     *
     * @param document Документ в DOM формате
     * @return Документ в виде строки
     * @throws TransformerException Ошибка трансформации документа в строку
     */
    public static String convertDocumentToString(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

    /**
     * Метод позволяет залогировать заголовки (Headers), которые фигурируют в HTTP соединении
     *
     * @param connection Объект HttpURLConnection у которого есть Headers
     */
    public static void logConnectionHeaders(HttpURLConnection connection) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        // Get the headers
        Map<String, List<String>> headers = connection.getRequestProperties();

        // Print all the headers
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            for (String value : values) {
                Core.lm.debug(Core.class.getName(), methodName, key + ": " + value);
            }
        }
    }

    /**
     * Получение QueryParam из MessageContext переданного сообщения
     *
     * @param queryParamName Наименование QueryParam в REST_URL_POSTFIX входящего запроса, значение которого необходимо вернуть
     * @param messageContext Контекст сообщения
     * @return Значение QueryParam из REST_URL_POSTFIX входящего запроса
     */
    public String getQueryParam(String queryParamName, MessageContext messageContext) {

        String queryString = (String) messageContext.getProperty(NhttpConstants.REST_URL_POSTFIX);

        if (!StringUtils.isEmpty(queryString)) {
            if (queryString.contains("?")) {
                queryString = queryString.substring(queryString.indexOf("?") + 1);
            }
            String[] queryParams = queryString.split("&");
            Map<String, String> queryParamsMap = new HashMap<>();
            String[] queryParamArray;
            String queryParamInstance, queryParamValue = "";
            for (String queryParam : queryParams) {
                queryParamArray = queryParam.split("=");
                if (queryParamArray.length == 2) {
                    queryParamInstance = queryParamArray[0];
                    queryParamValue = queryParamArray[1];
                } else {
                    queryParamInstance = queryParamArray[0];
                }
                queryParamsMap.put(queryParamInstance, queryParamValue);
            }

            return queryParamsMap.get(queryParamName);
        }
        return null;
    }

}
