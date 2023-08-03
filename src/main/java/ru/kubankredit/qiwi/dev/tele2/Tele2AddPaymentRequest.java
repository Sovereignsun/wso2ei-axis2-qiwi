package ru.kubankredit.qiwi.dev.tele2;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;

import java.io.ByteArrayInputStream;

public class Tele2AddPaymentRequest {

    public static OMElement getRequest() {

        final String request = "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<AddPayment xmlns=\"http://ProxyService\">\n" +
                "\t\t\t<xml>&lt;xml xmlns:s=\"uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882\" xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\" xmlns:rs=\"urn:schemas-microsoft-com:rowset\" xmlns:z=\"#RowsetSchema\">\n" +
                "  &lt;rs:data>\n" +
                "    &lt;z:row \n" +
                "\tID=\"171681\" \n" +
                "\tK_ID=\"171681\" \n" +
                "\tL_PAYMENTS=\"54921\" \n" +
                "\tSERV_ID=\"55473\" \n" +
                "\tSERVICE=\"TELE2\"\n" +
                "\tF_DATE=\"2023-07-19T13:49:02\" \n" +
                "\tL_CHRG_RECORDS=\"\" \n" +
                "\tDATE_TRAN=\"1899-12-30T00:00:00\" \n" +
                "\tDATE_CREATE=\"2023-07-19T13:49:00\" \n" +
                "\tDATE_VAL=\"1899-12-30T00:00:00\" \n" +
                "\tDATE_PAYM=\"2023-07-19T13:48:59\" \n" +
                "\tNUMBER_PAYM=\"510818\" \n" +
                "\tSUMMA=\"800\"\n" +
                "\tFULL_SUMM=\"800\" \n" +
                "\tIS_QUICK=\"0\" \n" +
                "\tUSER_ID=\"4684\" \n" +
                "\tOWNER=\"USER_GATEISIMPLE\" \n" +
                "\tCONTROLLER=\"\" \n" +
                "\tSHORT_NAME=\"\" \n" +
                "\tL_TPP=\"274\" STATUS=\"1\" \n" +
                "\tF_ONLINE_STATUS=\"1\"\n" +
                "\tF_GIS_STATUS=\"0\" \n" +
                "\tL_OUT_RECORD=\"0\" \n" +
                "\tF_GUID=\"0289ecc748d54e3dac76\" \n" +
                "\tF_ATTENTION=\"0\" \n" +
                "\tCOMMISSION=\"0\" \n" +
                "\tF_KASS_SYMB=\"\" \n" +
                "\tL_CUST_ID=\"4288816\" \n" +
                "\tL_BCUST_ID=\"\" \n" +
                "\tL_CUST_DOC_ID=\"\" \n" +
                "\tL_BCUST_DOC_ID=\"\" \n" +
                "\tL_CUST_XXI_ID=\"\" \n" +
                "\tL_BCUST_XXI_ID=\"\"\n" +
                "\tL_CUST_IDENT_RESULT_CODE=\"\" \n" +
                "\tL_CUST_IDENT_RESULT_DESCR=\"\" \n" +
                "\tL_BCUST_IDENT_RESULT_CODE=\"\" \n" +
                "\tL_BCUST_IDENT_RESULT_DESCR=\"\" \n" +
                "\tF_EXT_ID=\"\" \n" +
                "\tGR_USER=\"3\" \n" +
                "\tL_DEPARTMENT=\"0\" \n" +
                "\tF_CHILD_ID=\"0289ecc748d54e3dac76\" \n" +
                "\tF_BIT_MASK=\"0\" \n" +
                "\tL_BANDEROL=\"\" \n" +
                "\tL_PERIOD_TRANS=\"21\" \n" +
                "\tL_CUSTOMER=\"\"\n" +
                "\tF_ISCASHLESS=\"1\" \n" +
                "\tF_AUTHORIZATIONCODE=\"\" \n" +
                "\tF_REFERENCENUMBER=\"\" \n" +
                "\tF_RECONCILIATION=\"1\" \n" +
                "\tF_ERROR_TEXT=\"\" \n" +
                "\tF_ERROR_TEXT_GIS=\"\"\n" +
                "\tF_ACQUIRER_RRN=\"\" \n" +
                "\tF_ACQUIRER_PAN=\"\" \n" +
                "\tL_ACQUIRER_TERM=\"\" \n" +
                "\tF_ACQUIRER_TERM_ID=\"\" \n" +
                "\tF_ACQUIRER_INTREF=\"\" \n" +
                "\tF_ACQUIRER_AUTHCODE=\"\" \n" +
                "\tF_ACQUIRER_TIMESTAMP=\"1899-12-30T00:00:00\" \n" +
                "\tF_ACQUIRER_ID=\"\" \n" +
                "\tF_COMMISSION_SUM=\"\" \n" +
                "\tF_REIMBURSE_SUM=\"\" \n" +
                "\tF_CHCK_RECEIVER=\"\" \n" +
                "\tF_CHCK_STATUS=\"\" \n" +
                "\tF_CHCK_ERROR_TXT=\"\" \n" +
                "\tF_CHCK_STATE_DATE=\"1899-12-30T00:00:00\" \n" +
                "\tF_INKASS=\"0\" \n" +
                "\tIS_PDL=\"0\" \n" +
                "\tPAYER_NAME_FULL=\"СТАРКОВА ДАРЬЯ ВИКТОРОВНА\" \n" +
                "\tPAYER_PHONES=\"\" PAYER_ADDR=\"Краснодарский, ул, Горького, 17, \" \n" +
                "\tBENEFIT_NAME_FULL=\"\" \n" +
                "\tBENEFIT_PHONES=\"\" \n" +
                "\tL_SEGMENT=\"17\" \n" +
                "\tSEGMENT_NAME=\"Связь, интернет, каб.телевидение\" \n" +
                "\tPHONE=\"1175689795\" \n" +
                "\tINKASS=\"0\" \n" +
                "\tPAN_MASK=\"\"/>\n" +
                "  &lt;/rs:data>\n" +
                "&lt;/xml></xml>\n" +
                "\t\t\t<dataList>\n" +
                "\t\t\t\t<Data>\n" +
                "\t\t\t\t\t<Name>SESSION_ID</Name>\n" +
                "\t\t\t\t\t<Value>4197C302-E0B5-4C2E-B084-5E3996227DED</Value>\n" +
                "\t\t\t\t</Data>\n" +
                "\t\t\t\t<Data>\n" +
                "\t\t\t\t\t<Name>SERVICE_ID</Name>\n" +
                "\t\t\t\t\t<Value>55473</Value>\n" +
                "\t\t\t\t</Data>\n" +
                "\t\t\t\t<Data>\n" +
                "\t\t\t\t\t<Name>TYPE_PMNT_ID</Name>\n" +
                "\t\t\t\t\t<Value>7167</Value>\n" +
                "\t\t\t\t</Data>\n" +
                "\t\t\t</dataList>\n" +
                "\t\t</AddPayment>";

        OMFactory factory = OMAbstractFactory.getOMFactory();

        return OMXMLBuilderFactory.createOMBuilder(factory, new ByteArrayInputStream(request.getBytes())).getDocumentElement();
    }
}
