package ru.kubankredit.qiwi.methods.main.getCharges;

import org.apache.axiom.om.OMElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.kubankredit.qiwi.core.Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GetChargesRequestParser {

    final protected String thisClassName = this.getClass().getName();

    public GetChargesRequest parseRequest(OMElement request) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        try {
            // Convert the OMElement to an org.w3c.dom.Element
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            request.serialize(outputStream);
            String xmlSerializedString = outputStream.toString();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xmlSerializedString)));

            // Get the root element
            Element root = document.getDocumentElement();

            // Create an instance of GetCharges POJO
            GetChargesRequest getCharges = new GetChargesRequest();

            // Set the xml value
            Node xmlNode = root.getElementsByTagName("xml").item(0);
            getCharges.setXml(xmlNode.getTextContent());

            // Parse the dataList
            NodeList dataListNodes = root.getElementsByTagName("Data");
            List<GetChargesRequest.Data> dataList = new ArrayList<>();
            for (int i = 0; i < dataListNodes.getLength(); i++) {
                Node dataNode = dataListNodes.item(i);

                // Create an instance of GetChargesRequest.Data POJO
                GetChargesRequest.Data data = new GetChargesRequest.Data();

                // Set the name value
                Node nameNode = ((Element) dataNode).getElementsByTagName("Name").item(0);
                data.setName(nameNode.getTextContent());

                // Set the value
                Node valueNode = ((Element) dataNode).getElementsByTagName("Value").item(0);
                data.setValue(valueNode.getTextContent());

                // Add the Data to the dataList
                dataList.add(data);
            }

            // Set the dataList value
            getCharges.setDataList(dataList);

            return getCharges;

        } catch (Exception e) {
            e.printStackTrace();
            Core.lm.error(thisClassName, methodName, "Exception " + e.getMessage());
            return null;
        }
    }

}
