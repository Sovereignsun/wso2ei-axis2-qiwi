package ru.kubankredit.qiwi.methods.gates.gatecontract.echoGetFieldsReestrChrg;

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

public class EchoGetFieldsReestrChrgXMLParser {

    final protected String thisClassName = this.getClass().getName();

    @SuppressWarnings("UnusedReturnValue")
    public EchoGetFieldsReestrChrgResponse parseResponse(EchoGetFieldsReestrChrgResponse echoGetFieldsReestrChrgResponse) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            String xmlString = removeNamespaces(echoGetFieldsReestrChrgResponse.getXml());
            // Create the DOM parser
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Parse the rows
            NodeList rows = rootElement.getElementsByTagName("row");
            List<EchoGetFieldsReestrChrgResponse.Field> fieldsList = new ArrayList<>();
            for (int i = 0; i < rows.getLength(); i++) {
                Node row = rows.item(i);

                // Find all 'row' attributes
                NamedNodeMap attributesMap = row.getAttributes();
                EchoGetFieldsReestrChrgResponse.Field field = new EchoGetFieldsReestrChrgResponse.Field();
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

            echoGetFieldsReestrChrgResponse.setFields(fieldsList);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "{Exception}: " + e.getMessage());
        }

        return echoGetFieldsReestrChrgResponse;
    }
}
