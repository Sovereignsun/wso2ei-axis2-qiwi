package ru.kubankredit.qiwi.responses;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.kubankredit.qiwi.core.Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * <h1>Класс обработки операции запроса начислений</h1>
 * Содержит интерфейсный метод для выполнения требуемой логики для операции
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @apiNote Пример запроса в пакете <i><b>Examples -> Responses -> UniformResponses</b></i>
 * @since 2023-07-04
 */
public class UniformResponse {

    final protected String thisClassName = this.getClass().getName();

    /**
     * Метод формирует униформатный ответ для ПЦ на основании входящих данных
     * и возвращает в виде OMElement XML данных
     *
     * @param operationName Наименование операции
     * @param message       Основное сообщение
     * @param detailMessage Детализированное сообщение
     * @param state         <i>(Boolean)</i> состояние <b>true=Success</b> или <b>false=Error</b>
     * @param xmlData       Дополнительная информация, которая попадёт в элемент xml
     * @return OMElement с XML данными
     */
    public OMElement respond(OperationName operationName, String message, String detailMessage, Boolean state, OMElement xmlData) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.debug(thisClassName, methodName, "Operation name: " + operationName);

        try {

            // Create document builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Create the document
            Document document = builder.newDocument();

            // Create the root element
            Element root = document.createElement(operationName + "Response");
            root.setAttribute("xmlns", "http://ProxyService");
            document.appendChild(root);

            // Create the PossPaymentResult element
            Element responseResult = document.createElement(operationName + "Result");
            responseResult.setAttribute("xmlns:a", "http://schemas.datacontract.org/2004/07/ProxyService");
            responseResult.setAttribute("xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
            root.appendChild(responseResult);

            // Create the Message element
            Element messageElement = document.createElement("a:Message");
            if (message == null || message.isEmpty()) {
                messageElement.setTextContent("Success");
            } else {
                messageElement.setTextContent(message);
            }
            responseResult.appendChild(messageElement);

            // Create the DetailedMessage element
            Element detailedMessageElement = document.createElement("a:DetailedMessage");
            if (detailMessage == null || detailMessage.isEmpty()) {
                detailedMessageElement.setTextContent("Ok");
            } else {
                detailedMessageElement.setTextContent(detailMessage);
            }
            responseResult.appendChild(detailedMessageElement);

            // Create the State element
            Element stateElement = document.createElement("a:State");
            if (state) {
                stateElement.setTextContent("Success");
            } else {
                stateElement.setTextContent("Error");
            }
            responseResult.appendChild(stateElement);

            // Create the Xml element
            Element xml = document.createElement("a:Xml");
            responseResult.appendChild(xml);

            if (xmlData != null) {
                // Convert the OMElement to an org.w3c.dom.Element
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                xmlData.serialize(outputStream);
                String xmlSerializedString = outputStream.toString();

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document documentOM = documentBuilder.parse(new InputSource(new StringReader(xmlSerializedString)));

                Element importedElement = (Element) document.importNode(documentOM.getDocumentElement(), true);

                // Append the xmlElement to the Xml element
                xml.appendChild(importedElement);
            }

            // Convert the Document to a string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            // Create a new string writer for the output
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            // Transform the DOM source into a string
            transformer.transform(source, result);

            // Get the string representation of the XML
            String xmlString = writer.toString();

            Core.lm.info(thisClassName, methodName, "UniformResponse: \r\n" + xmlString);

            OMFactory responseFactory = OMAbstractFactory.getOMFactory();

            return OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(xmlString.getBytes())).getDocumentElement();

        } catch (ParserConfigurationException | TransformerException | XMLStreamException | SAXException |
                 IOException e) {
            Core.lm.error(thisClassName, methodName, "Exception " + e.getMessage());
        }
        return null;
    }

    /**
     * Метод формирует униформатный ответ для ПЦ на основании входящих данных
     * и возвращает в виде OMElement XML данных
     *
     * @param operationName Наименование операции
     * @param message       Основное сообщение
     * @param detailMessage Детализированное сообщение
     * @param state         <i>(Boolean)</i> состояние <b>true=Success</b> или <b>false=Error</b>
     * @return OMElement с XML данными
     */
    public OMElement respond(OperationName operationName, String message, String detailMessage, Boolean state) {
        return this.respond(operationName, message, detailMessage, state, null);
    }

    /**
     * Список поддерживаемых операций ПЦ в рамках универсального построителя ответа
     *
     * @author Листопадов Александр Сергеевич
     * @version 1.0.0
     * @since 2023-07-04
     */
    public enum OperationName {
        PossPayment, GetCharges, AddPayment, DeletePayment, EditPayment
    }

    /*public static void main(String[] args) {
        UniformResponse uniformResponse = new UniformResponse();

        // Without XMLData inside the xml element
        OMElement response = uniformResponse.respond(OperationName.PossPayment,null,"",true);

        System.out.println(response);

        String XMLData = "<result><name>Ok</name></result>";

        OMFactory responseFactory = OMAbstractFactory.getOMFactory();
        OMElement responseElement = OMXMLBuilderFactory.createOMBuilder(responseFactory, new ByteArrayInputStream(XMLData.getBytes())).getDocumentElement();

        // With XMLData inside the xml element
        OMElement response2 = uniformResponse.respond(OperationName.PossPayment,null,"",true,responseElement);

        System.out.println(response2);
    }*/
}
