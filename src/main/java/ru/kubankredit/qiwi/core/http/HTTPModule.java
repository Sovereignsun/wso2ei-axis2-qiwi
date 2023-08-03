package ru.kubankredit.qiwi.core.http;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.crypt.EncodingUtils;
import ru.kubankredit.qiwi.core.crypt.RSAModule;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.serializers.XMLBeautifier;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * <h2>Класс отправки HTTP запросов в QIWI</h2>
 * Модуль позволяет отправлять сформированные запросы в QIWI в рамках интеграции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-04
 */
public class HTTPModule {
    final protected String thisClassName = this.getClass().getName();
    final EncodingUtils encodingUtils = new EncodingUtils();

    /**
     * Метод позволяет отправлять POST запрос в QIWI
     *
     * @param xmlData          Данные в формате XML необходимые к отправке
     * @param withXDigitalSign Признак того, что необходимо указать в Headers цифровую подпись
     * @return Строка с захэшированными данными
     */
    public String HTTPPost(String xmlData, Boolean withXDigitalSign) throws AbstractException {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        HttpURLConnection connection = null;
        try {
            String userAgent = Core.settings.getQiwiSettings().getUserAgent();

            URL endpointUrl = new URL(Core.settings.getQiwiSettings().getUrl());
            Core.lm.debug(thisClassName, methodName, "QiwiEndpoint: " + endpointUrl);

            connection = (HttpURLConnection) endpointUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setRequestProperty("Accept", "text/html, */*");
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-agent", userAgent);

            try {
                Integer connectionTimeout = Core.settings.getQiwiSettings().getConnectionTimeout();
                Integer readTimeout = Core.settings.getQiwiSettings().getReadTimeout();
                connection.setConnectTimeout(connectionTimeout * 1000);
                connection.setReadTimeout(readTimeout * 1000);
            } catch (Exception E) {
                connection.setConnectTimeout(10 * 1000);
                connection.setReadTimeout(30 * 1000);
            }

            if (withXDigitalSign) {
                String personLogin = Core.settings.getQiwiSettings().getLogin();
                try {
                    RSAModule rsaModule = new RSAModule(xmlData, Core.settings.getPrivateKey());
                    String signedString = rsaModule.getSignedData();
                    Core.lm.debug(thisClassName, methodName, "SignedString: " + signedString);
                    connection.setRequestProperty("X-Digital-Sign", signedString);
                    connection.setRequestProperty("X-Digital-Sign-Alg", Core.settings.getQiwiSettings().getDigitalSignAlgorithm());
                    connection.setRequestProperty("X-Digital-Sign-Login", encodingUtils.base64Encode(personLogin.getBytes(StandardCharsets.UTF_8)));
                } catch (Exception e) {
                    Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
                    throw new RuntimeException("Couldn't form the X-Digital-Sign header");
                }
            }

            Core.logConnectionHeaders(connection);
            Core.lm.info(thisClassName, methodName, "Post-Request: \n" + XMLBeautifier.IndentXML(xmlData));

            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(xmlData);
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();

            Core.lm.info(thisClassName, methodName, "ResponseCode: " + responseCode);

            // Handle response code
            if (responseCode == 200) {
                // Read response data
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "Windows-1251"));
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
