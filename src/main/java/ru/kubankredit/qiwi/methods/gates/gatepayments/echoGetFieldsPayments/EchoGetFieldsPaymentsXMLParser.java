package ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.runtime.Dynamics;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static ru.kubankredit.qiwi.serializers.XMLBeautifier.removeNamespaces;

public class EchoGetFieldsPaymentsXMLParser {

    final protected String thisClassName = this.getClass().getName();

    @SuppressWarnings("UnusedReturnValue")
    public EchoGetFieldsPaymentsResponse parseResponse(EchoGetFieldsPaymentsResponse echoGetFieldsPaymentsResponse) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            String xmlString = removeNamespaces(echoGetFieldsPaymentsResponse.getXml());
            // Create the DOM parser
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Parse the rows
            NodeList rows = rootElement.getElementsByTagName("row");
            List<EchoGetFieldsPaymentsResponse.Field> fieldsList = new ArrayList<>();
            for (int i = 0; i < rows.getLength(); i++) {
                Node row = rows.item(i);

                // Find all 'row' attributes
                NamedNodeMap attributesMap = row.getAttributes();
                EchoGetFieldsPaymentsResponse.Field field = new EchoGetFieldsPaymentsResponse.Field();
                // Iterate over each 'attribute' and save to Field
                for (int j = 0; j < attributesMap.getLength(); j++) {
                    Node attr = attributesMap.item(j);
                    String fieldName = attr.getNodeName().toLowerCase();
                    String fieldValue = attr.getNodeValue();
                    if (Dynamics.findField(field, fieldName)) {
                        Dynamics.setFieldValue(field, fieldName, fieldValue);
                    }
                }
                fieldsList.add(field);
            }

            echoGetFieldsPaymentsResponse.setFields(fieldsList);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "{Exception}: " + e.getMessage());
        }

        return echoGetFieldsPaymentsResponse;
    }
}
