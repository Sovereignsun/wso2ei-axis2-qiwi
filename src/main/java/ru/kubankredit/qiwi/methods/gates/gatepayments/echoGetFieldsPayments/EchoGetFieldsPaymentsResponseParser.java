package ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import ru.kubankredit.qiwi.core.Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class EchoGetFieldsPaymentsResponseParser {

    final protected String thisClassName = this.getClass().getName();

    public EchoGetFieldsPaymentsResponse parseResponse(String response) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(response)));

            // Get the root element
            Element root = document.getDocumentElement();

            // Create an instance of EchoGetFieldsPaymentsResponse POJO
            EchoGetFieldsPaymentsResponse pmntResponse = new EchoGetFieldsPaymentsResponse();

            // Set the xmlNode value
            Node xmlNode = root.getElementsByTagName("XML").item(0);
            pmntResponse.setXml(xmlNode.getTextContent());

            // Set the errorNode value
            Node errorNode = root.getElementsByTagName("Error").item(0);
            pmntResponse.setError(Integer.parseInt(errorNode.getTextContent()));

            // Set the errorMessageNode value
            Node errorMessageNode = root.getElementsByTagName("ErrorMessage").item(0);
            pmntResponse.setErrorMessage(errorMessageNode.getTextContent());

            return pmntResponse;

        } catch (Exception e) {
            e.printStackTrace();
            Core.lm.error(thisClassName, methodName, "Exception " + e.getMessage());
            return null;
        }
    }

}
