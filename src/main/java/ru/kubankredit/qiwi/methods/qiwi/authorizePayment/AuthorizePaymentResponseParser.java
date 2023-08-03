package ru.kubankredit.qiwi.methods.qiwi.authorizePayment;

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

public class AuthorizePaymentResponseParser {

    final protected String thisClassName = this.getClass().getName();

    public AuthorizePaymentResponse parseResponse(String xmlResponse) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        AuthorizePaymentResponse authorizePaymentResponse = new AuthorizePaymentResponse();

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

            authorizePaymentResponse.setFatal(fatal);
            authorizePaymentResponse.setResult(result);
            authorizePaymentResponse.setStatus(status);
            authorizePaymentResponse.setUid(uid);

            List<AuthorizePaymentResponse.Extra> extras = new ArrayList<>();
            Element extrasElement = (Element) paymentElement.getElementsByTagName("extras").item(0);
            NamedNodeMap attributes = extrasElement.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                AuthorizePaymentResponse.Extra extra = new AuthorizePaymentResponse.Extra();
                Node attr = attributes.item(i);
                extra.setName(attr.getNodeName().toLowerCase());
                extra.setValue(attr.getNodeValue());
                extras.add(extra);
            }

            authorizePaymentResponse.setExtras(extras);

            return authorizePaymentResponse;

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return null;
    }

    /*public static void main(String[] args) {
        String xml = "<response result=\"0\">\n" +
                " <providers>\n" +
                " <authorizePayment result=\"0\">\n" +
                " <payment date=\"2010-09-01T15:54:38+04:00\" id=\"301\" result=\"0\" \n" +
                " fatal=\"false\" status=\"3\" uid=\"7777777777777\">\n" +
                " <extras AUTH_HOST=\"app8.osmp.ru\" PARSER_HOST=\"app8.osmp.ru\"/>\n" +
                " </payment>\n" +
                " </authorizePayment>\n" +
                " </providers>\n" +
                "</response>\n";

        AuthorizePaymentResponseParser parser = new AuthorizePaymentResponseParser();
        AuthorizePaymentResponse response = parser.parseResponse(xml);
    }*/
}
