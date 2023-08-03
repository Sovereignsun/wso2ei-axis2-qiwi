package ru.kubankredit.qiwi.methods.main.getCharges;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.kubankredit.qiwi.core.Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static ru.kubankredit.qiwi.serializers.XMLBeautifier.removeNamespaces;

public class GetChargesXMLParser {

    final protected String thisClassName = this.getClass().getName();

    public GetChargesRequest ParseRequest(GetChargesRequest getChargesRequest) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            String xmlString = removeNamespaces(getChargesRequest.getXml());
            // Create the DOM parser
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the Nodes element
            Element nodes = (Element) rootElement.getElementsByTagName("Nodes").item(0);

            // Find all 'Field' tags
            NodeList fieldNodes = nodes.getElementsByTagName("Field");

            List<GetChargesRequest.Field> fields = new ArrayList<>();

            // Iterate over each 'Field' tag and save its contents
            for (int i = 0; i < fieldNodes.getLength(); i++) {
                Node fieldNode = fieldNodes.item(i);
                Element parentNode = (Element) fieldNode.getParentNode();
                if (fieldNode.getNodeType() == Node.ELEMENT_NODE && parentNode.getTagName().equals("Nodes")) {
                    Element fieldElement = (Element) fieldNode;
                    GetChargesRequest.Field field = new GetChargesRequest.Field();

                    String fieldName = fieldElement.getElementsByTagName("FieldName").item(0).getTextContent();
                    String fieldValue = fieldElement.getElementsByTagName("Data").item(0).getTextContent();
                    String fieldId = fieldElement.getElementsByTagName("ID").item(0).getTextContent();

                    field.setFieldName(fieldName);
                    field.setFieldValue(fieldValue);
                    field.setFieldId(fieldId);
                    fields.add(field);
                }
            }

            getChargesRequest.setFields(fields);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "{Exception}: " + e.getMessage());
        }

        return getChargesRequest;
    }
}
