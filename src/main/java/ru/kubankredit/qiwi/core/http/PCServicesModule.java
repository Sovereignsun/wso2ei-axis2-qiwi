package ru.kubankredit.qiwi.core.http;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumService;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.serializers.XMLBeautifier;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * <h2>Класс отправки SOAP запросов во внутренние сервисы ПЦ</h2>
 * Модуль позволяет отправлять сформированные запросы во внутренние сервисы ПЦ по протоколу SOAP
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-26
 */
public class PCServicesModule {
    final protected String thisClassName = this.getClass().getName();

    /**
     * Метод позволяет отправлять POST запрос во внутренние сервисы ПЦ
     *
     * @param xmlData          Данные в формате XML необходимые к отправке
     * @param service          Сервис для которого вызывается обращение
     * @param soapActionHeader SOAPAction заголовок запроса
     * @return Ответ от сервиса в виде XML
     */
    public String SOAPPost(String xmlData, EnumService service, String soapActionHeader) throws AbstractException {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        HttpURLConnection connection = null;
        try {
            String userAgent = Core.settings.getQiwiSettings().getUserAgent();

            String url = Core.settings.getGateSettings(service).getUrl();

            URL endpointUrl = new URL(url);
            Core.lm.debug(thisClassName, methodName, "Endpoint: " + endpointUrl);

            connection = (HttpURLConnection) endpointUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-agent", userAgent);
            connection.setRequestProperty("SOAPAction", soapActionHeader);

            try {
                Integer connectionTimeout = Core.settings.getGateSettings(service).getConnectionTimeout();
                Integer readTimeout = Core.settings.getGateSettings(service).getReadTimeout();
                connection.setConnectTimeout(connectionTimeout * 1000);
                connection.setReadTimeout(readTimeout * 1000);
            } catch (Exception E) {
                connection.setConnectTimeout(10 * 1000);
                connection.setReadTimeout(30 * 1000);
            }

            Core.logConnectionHeaders(connection);
            Core.lm.info(thisClassName, methodName, "Post-Request: \n" + XMLBeautifier.IndentXML(xmlData));

            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(xmlData);
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();

            Core.lm.debug(thisClassName, methodName, "ResponseCode: " + responseCode);

            // Handle response code
            if (responseCode == 200) {
                // Read response data
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseXMLString = response.toString();

                responseXMLString = responseXMLString.replaceAll("^<\\?xml[^>]+>", "");

                Core.lm.info(thisClassName, methodName, "Post-Response: \n" + XMLBeautifier.IndentXML(responseXMLString));
                // Return response data
                return responseXMLString;
            } else {
                // Throw custom exception if response code is not 200
                throw new AbstractException("HTTP Error: " + responseCode);
            }
        } catch (ProtocolException e) {
            throw new AbstractException(e.getMessage(), "ProtocolException");
        } catch (MalformedURLException e) {
            throw new AbstractException(e.getMessage(), "MalformedURLException");
        } catch (UnsupportedEncodingException e) {
            throw new AbstractException(e.getMessage(), "UnsupportedEncodingException");
        } catch (IOException e) {
            throw new AbstractException(e.getMessage(), "IOException");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
