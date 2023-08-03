package ru.kubankredit.qiwi.dev.tele2;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;

import java.io.ByteArrayInputStream;

public class Tele2PossPaymentRequest {

    public static OMElement getRequest() {

        final String request = "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<PossPayment xmlns=\"http://ProxyService\">\n" +
                "\t<xml>&lt;xml xmlns:s=\"uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882\" xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\" xmlns:rs=\"urn:schemas-microsoft-com:rowset\" xmlns:z=\"#RowsetSchema\">\n" +
                "\t&lt;rs:data xmlns:rs=\"urn:abc\">\n" +
                "\t&lt;z:row xmlns:z=\"urn:abc\" \n" +
                "\tDATE_CREATE=\"2023-07-19T14:13:00\" \n" +
                "\tDATE_PAYM=\"2023-07-19T14:12:59\" \n" +
                "\tNUMBER_PAYM=\"512619\" \n" +
                "\tSUMMA=\"100.5\" \n" +
                "\tCOMMISSION=\"10.23\" \n" +
                "\tTPP=\"274\" \n" +
                "\tIS_QUICK=\"0\"\n" +
                "\tF_GUID=\"d4920aef8c424e06823e\" \n" +
                "\tF_CHILD_ID=\"d4920aef8c424e06823e\" \n" +
                "\tF_ATTENTION=\"0\" \n" +
                "\tF_ISCASHLESS=\"1\" \n" +
                "\tPHONE=\"1175689795\"\n" +
                "\tINKASS=\"0\" \n" +
                "\tPAN_MASK=\"\" \n" +
                "\tXXI_CUST_ID=\"371496266\"/>\n" +
                "\t&lt;/rs:data>\n" +
                "\t&lt;/xml></xml>\n" +
                "\t<dataList>\n" +
                "\t\t<Data>\n" +
                "\t\t\t<Name>SESSION_ID</Name>\n" +
                "\t\t\t<Value>4197C302-E0B5-4C2E-B084-5E3996227DED</Value>\n" +
                "\t\t</Data>\n" +
                "\t\t<Data>\n" +
                "\t\t\t<Name>SERVICE_ID</Name>\n" +
                "\t\t\t<Value>55473</Value>\n" +
                "\t\t</Data>\n" +
                "\t\t<Data>\n" +
                "\t\t\t<Name>TYPE_PMNT_ID</Name>\n" +
                "\t\t\t<Value>7167</Value>\n" +
                "\t\t</Data>\n" +
                "\t</dataList>\n" +
                "</PossPayment>";

        OMFactory factory = OMAbstractFactory.getOMFactory();

        return OMXMLBuilderFactory.createOMBuilder(factory, new ByteArrayInputStream(request.getBytes())).getDocumentElement();
    }
}
