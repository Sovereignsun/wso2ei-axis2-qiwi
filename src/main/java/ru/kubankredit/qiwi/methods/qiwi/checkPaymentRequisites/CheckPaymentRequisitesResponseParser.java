package ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites;

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

public class CheckPaymentRequisitesResponseParser {

    final protected String thisClassName = this.getClass().getName();

    public CheckPaymentRequisitesResponse parseResponse(String xmlResponse) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        CheckPaymentRequisitesResponse checkPaymentRequisitesResponse = new CheckPaymentRequisitesResponse();

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

            checkPaymentRequisitesResponse.setFatal(fatal);
            checkPaymentRequisitesResponse.setResult(result);
            checkPaymentRequisitesResponse.setStatus(status);
            checkPaymentRequisitesResponse.setUid(uid);

            List<CheckPaymentRequisitesResponse.Extra> extras = new ArrayList<>();
            Element extrasElement = (Element) paymentElement.getElementsByTagName("extras").item(0);
            NamedNodeMap attributes = extrasElement.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                CheckPaymentRequisitesResponse.Extra extra = new CheckPaymentRequisitesResponse.Extra();
                Node attr = attributes.item(i);
                extra.setName(attr.getNodeName().toLowerCase());
                extra.setValue(attr.getNodeValue());
                extras.add(extra);
            }

            checkPaymentRequisitesResponse.setExtras(extras);

            return checkPaymentRequisitesResponse;

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return null;
    }

    /*public static void main(String[] args) {
        String xml = "<response result=\"0\">\n" +
                "  <providers>\n" +
                "    <checkPaymentRequisites result=\"0\">\n" +
                "      <payment date=\"2023-07-25T19:51:20+03:00\"\n" +
                "               fatal=\"false\"\n" +
                "               id=\"1\"\n" +
                "               result=\"0\"\n" +
                "               saved=\"false\"\n" +
                "               status=\"3\"\n" +
                "               uid=\"29625929065008\">\n" +
                "        <extras AUTH_HOST=\"s1250.qiwi.com\"\n" +
                "                BEE_ID=\"791454\"\n" +
                "                MTS_TXN_ID=\"285911\"\n" +
                "                PARSER_HOST=\"s1250.qiwi.com\"\n" +
                "                PRV_TXN_ID=\"531679\"/>\n" +
                "      </payment>\n" +
                "    </checkPaymentRequisites>\n" +
                "  </providers>\n" +
                "</response>";

        CheckPaymentRequisitesResponseParser parser = new CheckPaymentRequisitesResponseParser();
        CheckPaymentRequisitesResponse response = parser.parseResponse(xml);
    }*/
}
