package ru.kubankredit.qiwi.methods.qiwi.confirmPayment;

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

public class ConfirmPaymentResponseParser {

    final protected String thisClassName = this.getClass().getName();

    public ConfirmPaymentResponse parseResponse(String xmlResponse) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        ConfirmPaymentResponse confirmPaymentResponse = new ConfirmPaymentResponse();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

            // Normalize the document
            document.getDocumentElement().normalize();

            Element paymentElement = (Element) document.getElementsByTagName("payment").item(0);
            boolean fatal = Boolean.parseBoolean(paymentElement.getAttribute("fatal"));
            int result = Integer.parseInt(paymentElement.getAttribute("result"));
            int status = Integer.parseInt(paymentElement.getAttribute("status"));
            String uid = paymentElement.getAttribute("uid");

            confirmPaymentResponse.setFatal(fatal);
            confirmPaymentResponse.setResult(result);
            confirmPaymentResponse.setStatus(status);
            confirmPaymentResponse.setUid(uid);

            List<ConfirmPaymentResponse.Extra> extras = new ArrayList<>();
            Element extrasElement = (Element) paymentElement.getElementsByTagName("extras").item(0);
            NamedNodeMap attributes = extrasElement.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                ConfirmPaymentResponse.Extra extra = new ConfirmPaymentResponse.Extra();
                Node attr = attributes.item(i);
                extra.setName(attr.getNodeName().toLowerCase());
                extra.setValue(attr.getNodeValue());
                extras.add(extra);
            }

            confirmPaymentResponse.setExtras(extras);

            return confirmPaymentResponse;

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return null;
    }

    /*public static void main(String[] args) {
        String xml = "<response result=\"0\">\n" +
                " <providers>\n" +
                " <confirmPayment result=\"0\">\n" +
                " <payment date=\"2010-09-01T15:57:34+04:00\" id=\"301\"\n" +
                " result=\"0\" fatal=\"false\" status=\"1\" uid=\"7777777777777\">\n" +
                " <extras AUTH_HOST=\"app8.osmp.ru\"/>\n" +
                " </payment>\n" +
                " </confirmPayment>\n" +
                " </providers>\n" +
                "</response>\n\n";

        ConfirmPaymentResponseParser parser = new ConfirmPaymentResponseParser();
        ConfirmPaymentResponse response = parser.parseResponse(xml);
    }*/
}
