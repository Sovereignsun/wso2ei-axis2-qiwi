package ru.kubankredit.qiwi.methods.qiwi.getPaymentStatus;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.methods.main.getStatus.GetPaymentStatusRequest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class GetPaymentStatusXMLParser {

    final protected String thisClassName = this.getClass().getName();

    public GetPaymentStatusRequest parseResponse(String xmlResponse) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        GetPaymentStatusRequest getPaymentStatusRequest = new GetPaymentStatusRequest();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

            // Normalize the document
            document.getDocumentElement().normalize();

            Element clientElement = (Element) document.getElementsByTagName("client").item(0);
            String terminalId = clientElement.getAttribute("terminal");

            Element paymentElement = (Element) document.getElementsByTagName("payment").item(0);
            String pmntId = paymentElement.getAttribute("id");

            getPaymentStatusRequest.setPmntId(pmntId);
            getPaymentStatusRequest.setTerminal(terminalId);

            return getPaymentStatusRequest;

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
