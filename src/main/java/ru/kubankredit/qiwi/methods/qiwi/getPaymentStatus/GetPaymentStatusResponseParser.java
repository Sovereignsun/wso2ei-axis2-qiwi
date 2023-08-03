package ru.kubankredit.qiwi.methods.qiwi.getPaymentStatus;

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

public class GetPaymentStatusResponseParser {

    final protected String thisClassName = this.getClass().getName();

    public GetPaymentStatusResponse parseResponse(String xmlResponse) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        GetPaymentStatusResponse getPaymentStatusResponse = new GetPaymentStatusResponse();

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

            getPaymentStatusResponse.setFatal(fatal);
            getPaymentStatusResponse.setResult(result);
            getPaymentStatusResponse.setStatus(status);
            getPaymentStatusResponse.setUid(uid);

            List<GetPaymentStatusResponse.Extra> extras = new ArrayList<>();
            Element extrasElement = (Element) paymentElement.getElementsByTagName("extras").item(0);
            NamedNodeMap attributes = extrasElement.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                GetPaymentStatusResponse.Extra extra = new GetPaymentStatusResponse.Extra();
                Node attr = attributes.item(i);
                extra.setName(attr.getNodeName().toLowerCase());
                extra.setValue(attr.getNodeValue());
                extras.add(extra);
            }

            getPaymentStatusResponse.setExtras(extras);

            return getPaymentStatusResponse;

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return null;
    }

    /*public static void main(String[] args) {
        String xml = "<?xml version="1.0" encoding="utf-8"?>
                    <response result="0">
                    <providers>
                    <getPaymentStatus result="0">
                    <payment date="2011-12-04T19:23:10+04:00" fatal="false" id="301"
                    result="0" status="2" uid="10625592616002">
                    <extras AUTH_HOST="app2.osmp.ru" PARSER_HOST="app5.osmp.ru"/>
                    </payment>
                    </getPaymentStatus>
                    </providers>
                    </response>";

        GetPaymentStatusResponseParser parser = new GetPaymentStatusResponseParser();
        GetPaymentStatusResponse response = parser.parseResponse(xml);
    }*/
}
