package ru.kubankredit.qiwi.methods.main.addPayment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import ru.kubankredit.qiwi.core.Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static ru.kubankredit.qiwi.serializers.XMLBeautifier.removeNamespaces;

public class AddPaymentXMLParser {

    final protected String thisClassName = this.getClass().getName();

    public AddPaymentRequest ParseRequest(AddPaymentRequest addPaymentRequest) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            String xmlString = removeNamespaces(addPaymentRequest.getXml());
            // Create the DOM parser
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the Row element
            Element row = (Element) rootElement.getElementsByTagName("row").item(0);

            // Find all 'row' attributes
            NamedNodeMap attributesMap = row.getAttributes();

            List<AddPaymentRequest.Attribute> attributesList = new ArrayList<>();

            // Iterate over each 'attribute' and save to Attribute
            for (int i = 0; i < attributesMap.getLength(); i++) {
                AddPaymentRequest.Attribute attribute = new AddPaymentRequest.Attribute();
                Node attr = attributesMap.item(i);
                attribute.setName(attr.getNodeName().toLowerCase());
                attribute.setValue(attr.getNodeValue());
                attributesList.add(attribute);
            }

            addPaymentRequest.setAttributes(attributesList);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "{Exception}: " + e.getMessage());
        }

        return addPaymentRequest;
    }
}
