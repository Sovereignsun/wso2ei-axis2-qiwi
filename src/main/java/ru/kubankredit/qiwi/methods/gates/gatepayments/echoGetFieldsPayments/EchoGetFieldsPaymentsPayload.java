package ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.serializers.PayloadSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * <h1>Запрос списка полей платежа по типу платежа</h1>
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-29
 */
public class EchoGetFieldsPaymentsPayload {

    final protected String thisClassName = this.getClass().getName();

    protected final PayloadSerializer payloadSerializer = new PayloadSerializer();

    /**
     * Запрос списка полей платежа по типу платежа
     *
     * @param echoGetFieldsPaymentsRequest Объект EchoGetFieldsPaymentsRequest
     * @return Ответ в виде запроса в сервис GateContract в формате XML
     */
    public String buildXMLPayload(EchoGetFieldsPaymentsRequest echoGetFieldsPaymentsRequest) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            // Create a new DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Create a new Document
            Document document = dBuilder.newDocument();

            // Set the XML document declaration
            document.setXmlVersion("1.0");
            document.setXmlStandalone(true);

            Element envelope = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Envelope");
            document.appendChild(envelope);

            Element body = document.createElement("soapenv:Body");
            envelope.appendChild(body);

            // Create the root element
            Element root = document.createElement("echoGetFieldsPayments_");
            body.appendChild(root);

            // Create the "value" element
            Element value = document.createElement("Value");

            // Create the "sessionId" element
            Element sessionId = document.createElement("SessionId");
            Text sessionIdText = document.createTextNode(echoGetFieldsPaymentsRequest.getSessionId());
            sessionId.appendChild(sessionIdText);
            value.appendChild(sessionId);

            // Create the "id" element
            Element id = document.createElement("Id");
            Text idText = document.createTextNode(echoGetFieldsPaymentsRequest.getTypePmntID());
            id.appendChild(idText);
            value.appendChild(id);

            root.appendChild(value);

            document.getDocumentElement().normalize();

            // Return the serialized XML Payload
            return payloadSerializer.serializeXMLPayload(document);
        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }
        return null;
    }
}

