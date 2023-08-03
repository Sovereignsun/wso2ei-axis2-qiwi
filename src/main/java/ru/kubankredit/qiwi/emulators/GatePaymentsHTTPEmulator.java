package ru.kubankredit.qiwi.emulators;

import ru.kubankredit.qiwi.core.exceptions.AbstractException;

@SuppressWarnings("RedundantThrows")
public class GatePaymentsHTTPEmulator {

    public String echoGetFieldsPayments_OneField() throws AbstractException {

        return "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                  xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "                  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<soapenv:Body xmlns:NS1=\"http://esb.bank.srv/gate/payments\"\n" +
                "\t              SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "\t\t<NS1:echoGetFieldsPayments_Response>\n" +
                "\t\t\t<NS1:TXmlReturnMessage_ xsi:type=\"NS1:TXmlReturnMessage_\">\n" +
                "\t\t\t\t<Error xsi:type=\"xsd:int\">0</Error>\n" +
                "\t\t\t\t<ErrorMessage xsi:type=\"xsd:string\"/>\n" +
                "\t\t\t\t<XML xsi:type=\"xsd:string\">&lt;xml xmlns:s=\"uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882\"\n" +
                "     xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\"\n" +
                "     xmlns:rs=\"urn:schemas-microsoft-com:rowset\"\n" +
                "     xmlns:z=\"#RowsetSchema\">\n" +
                "\t&lt;rs:data>\n" +
                "\t\t&lt;z:row K_ID=\"575376\"\n" +
                "\t\t       L_TYPE_PAYMENT=\"80949\"\n" +
                "\t\t       L_TYPE=\"388\"\n" +
                "\t\t       L_FIELD_CHRG=\"154706\"\n" +
                "\t\t       F_NAME_IN_REESTR=\"ACCOUNT\"\n" +
                "\t\t       F_NAME=\"PHONE\"\n" +
                "\t\t       OBLIGATORY=\"1\"\n" +
                "\t\t       DATE_ACTIVE=\"2022-11-01T15:05:08\"\n" +
                "\t\t       ACTIVE=\"1\"\n" +
                "\t\t       DESCRIPTION=\"Номер лицевого счета\"\n" +
                "\t\t       F_FOLLOWUP=\"\"\n" +
                "\t\t       F_ORDER=\"0\"\n" +
                "\t\t       EDITABLE=\"1\"\n" +
                "\t\t       F_REGEXP=\"^\\d{9}|\\d{10}$\"\n" +
                "\t\t       SEARCHABLE=\"\"\n" +
                "\t\t       F_IS_ON_LINE=\"0\"\n" +
                "\t\t       F_VISIBLE=\"1\"\n" +
                "\t\t       F_INITIAL_WIDTH=\"0\"\n" +
                "\t\t       F_IS_PAYMENT_SUM=\"-1\"\n" +
                "\t\t       F_TYPE=\"VARCHAR2\"\n" +
                "\t\t       TABLE_NAME=\"PMNT$RA_PHONE\"\n" +
                "\t\t       FIELD_NAME=\"PHONE\"\n" +
                "\t\t       FIELD_SIZE=\"15\"\n" +
                "\t\t       F_IS_PAY_PROP=\"0\"\n" +
                "\t\t       F_FIELD_IDENT=\"0\"\n" +
                "\t\t       F_HINT=\"9 или 10 цифр\"\n" +
                "\t\t       F_MASK=\"9999999999\"\n" +
                "\t\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t\t       F_BND_FIELD=\"0\"\n" +
                "\t\t       F_DBO_TEMPLATE=\"1\"\n" +
                "\t\t       F_IS_FORMULA=\"0\"\n" +
                "\t\t       FRML_BITMSK=\"0\"\n" +
                "\t\t       L_ADDPROC=\"-1\"/>\n" +
                "\t\t&lt;z:row K_ID=\"575377\"\n" +
                "\t\t       L_TYPE_PAYMENT=\"80949\"\n" +
                "\t\t       L_TYPE=\"41\"\n" +
                "\t\t       F_NAME_IN_REESTR=\"INKASS\"\n" +
                "\t\t       F_NAME=\"INKASS\"\n" +
                "\t\t       OBLIGATORY=\"0\"\n" +
                "\t\t       DATE_ACTIVE=\"2022-11-01T15:05:08\"\n" +
                "\t\t       ACTIVE=\"1\"\n" +
                "\t\t       DESCRIPTION=\"Номер инкассации\"\n" +
                "\t\t       F_ORDER=\"80\"\n" +
                "\t\t       EDITABLE=\"0\"\n" +
                "\t\t       F_IS_ON_LINE=\"0\"\n" +
                "\t\t       F_VISIBLE=\"0\"\n" +
                "\t\t       F_INITIAL_WIDTH=\"0\"\n" +
                "\t\t       F_IS_PAYMENT_SUM=\"-1\"\n" +
                "\t\t       F_TYPE=\"NUMBER\"\n" +
                "\t\t       TABLE_NAME=\"PMNT$RA_COUNTER\"\n" +
                "\t\t       FIELD_NAME=\"F_CNTR\"\n" +
                "\t\t       FIELD_SIZE=\"10\"\n" +
                "\t\t       F_IS_PAY_PROP=\"0\"\n" +
                "\t\t       F_FIELD_IDENT=\"0\"\n" +
                "\t\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t\t       F_BND_FIELD=\"3\"\n" +
                "\t\t       F_DBO_TEMPLATE=\"1\"\n" +
                "\t\t       F_IS_FORMULA=\"0\"\n" +
                "\t\t       FRML_BITMSK=\"0\"\n" +
                "\t\t       L_ADDPROC=\"-1\"/>\n" +
                "\t\t&lt;z:row K_ID=\"575378\"\n" +
                "\t\t       L_TYPE_PAYMENT=\"80949\"\n" +
                "\t\t       L_TYPE=\"81\"\n" +
                "\t\t       F_NAME_IN_REESTR=\"PAN_MASK\"\n" +
                "\t\t       F_NAME=\"PAN_MASK\"\n" +
                "\t\t       OBLIGATORY=\"0\"\n" +
                "\t\t       DATE_ACTIVE=\"2022-11-01T15:05:08\"\n" +
                "\t\t       ACTIVE=\"1\"\n" +
                "\t\t       DESCRIPTION=\"PAN карты клиента\"\n" +
                "\t\t       F_ORDER=\"100\"\n" +
                "\t\t       EDITABLE=\"0\"\n" +
                "\t\t       F_IS_ON_LINE=\"0\"\n" +
                "\t\t       F_VISIBLE=\"0\"\n" +
                "\t\t       F_INITIAL_WIDTH=\"0\"\n" +
                "\t\t       F_IS_PAYMENT_SUM=\"-1\"\n" +
                "\t\t       F_TYPE=\"VARCHAR2\"\n" +
                "\t\t       TABLE_NAME=\"PMNT$RA_TEXT50\"\n" +
                "\t\t       FIELD_NAME=\"TEXT50\"\n" +
                "\t\t       FIELD_SIZE=\"50\"\n" +
                "\t\t       F_IS_PAY_PROP=\"0\"\n" +
                "\t\t       F_FIELD_IDENT=\"0\"\n" +
                "\t\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t\t       F_BND_FIELD=\"0\"\n" +
                "\t\t       F_DBO_TEMPLATE=\"1\"\n" +
                "\t\t       F_IS_FORMULA=\"0\"\n" +
                "\t\t       FRML_BITMSK=\"0\"\n" +
                "\t\t       L_ADDPROC=\"-1\"/>\n" +
                "\t&lt;/rs:data>\n" +
                "&lt;/xml></XML>\n" +
                "\t\t\t</NS1:TXmlReturnMessage_>\n" +
                "\t\t</NS1:echoGetFieldsPayments_Response>\n" +
                "\t</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    public String echoGetFieldsPayments_TwoFields() throws AbstractException {

        return "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                  xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "                  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<soapenv:Body xmlns:NS1=\"http://esb.bank.srv/gate/payments\"\n" +
                "\t              SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "\t\t<NS1:echoGetFieldsPayments_Response>\n" +
                "\t\t\t<NS1:TXmlReturnMessage_ xsi:type=\"NS1:TXmlReturnMessage_\">\n" +
                "\t\t\t\t<Error xsi:type=\"xsd:int\">0</Error>\n" +
                "\t\t\t\t<ErrorMessage xsi:type=\"xsd:string\"/>\n" +
                "\t\t\t\t<XML xsi:type=\"xsd:string\">&lt;xml xmlns:s=\"uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882\"\n" +
                "     xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\"\n" +
                "     xmlns:rs=\"urn:schemas-microsoft-com:rowset\"\n" +
                "     xmlns:z=\"#RowsetSchema\">\n" +
                "\t&lt;rs:data>\n" +
                "\t\t&lt;z:row K_ID=\"575376\"\n" +
                "\t\t       L_TYPE_PAYMENT=\"80949\"\n" +
                "\t\t       L_TYPE=\"388\"\n" +
                "\t\t       L_FIELD_CHRG=\"154706\"\n" +
                "\t\t       F_NAME_IN_REESTR=\"ACCOUNT\"\n" +
                "\t\t       F_NAME=\"PHONE\"\n" +
                "\t\t       OBLIGATORY=\"1\"\n" +
                "\t\t       DATE_ACTIVE=\"2022-11-01T15:05:08\"\n" +
                "\t\t       ACTIVE=\"1\"\n" +
                "\t\t       DESCRIPTION=\"Номер лицевого счета\"\n" +
                "\t\t       F_FOLLOWUP=\"\"\n" +
                "\t\t       F_ORDER=\"0\"\n" +
                "\t\t       EDITABLE=\"1\"\n" +
                "\t\t       F_REGEXP=\"^\\d{9}|\\d{10}$\"\n" +
                "\t\t       SEARCHABLE=\"\"\n" +
                "\t\t       F_IS_ON_LINE=\"0\"\n" +
                "\t\t       F_VISIBLE=\"1\"\n" +
                "\t\t       F_INITIAL_WIDTH=\"0\"\n" +
                "\t\t       F_IS_PAYMENT_SUM=\"-1\"\n" +
                "\t\t       F_TYPE=\"VARCHAR2\"\n" +
                "\t\t       TABLE_NAME=\"PMNT$RA_PHONE\"\n" +
                "\t\t       FIELD_NAME=\"PHONE\"\n" +
                "\t\t       FIELD_SIZE=\"15\"\n" +
                "\t\t       F_IS_PAY_PROP=\"0\"\n" +
                "\t\t       F_FIELD_IDENT=\"0\"\n" +
                "\t\t       F_HINT=\"9 или 10 цифр\"\n" +
                "\t\t       F_MASK=\"9999999999\"\n" +
                "\t\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t\t       F_BND_FIELD=\"0\"\n" +
                "\t\t       F_DBO_TEMPLATE=\"1\"\n" +
                "\t\t       F_IS_FORMULA=\"0\"\n" +
                "\t\t       FRML_BITMSK=\"0\"\n" +
                "\t\t       L_ADDPROC=\"-1\"/>\n" +
                "\t\t&lt;z:row K_ID=\"575377\"\n" +
                "\t\t       L_TYPE_PAYMENT=\"80949\"\n" +
                "\t\t       L_TYPE=\"41\"\n" +
                "\t\t       F_NAME_IN_REESTR=\"ACCOUNT_NUMBER\"\n" +
                "\t\t       F_NAME=\"ACCOUNT\"\n" +
                "\t\t       OBLIGATORY=\"1\"\n" +
                "\t\t       DATE_ACTIVE=\"2022-11-01T15:05:08\"\n" +
                "\t\t       ACTIVE=\"1\"\n" +
                "\t\t       DESCRIPTION=\"Номер инкассации\"\n" +
                "\t\t       F_ORDER=\"80\"\n" +
                "\t\t       EDITABLE=\"0\"\n" +
                "\t\t       F_IS_ON_LINE=\"0\"\n" +
                "\t\t       F_VISIBLE=\"0\"\n" +
                "\t\t       F_INITIAL_WIDTH=\"0\"\n" +
                "\t\t       F_IS_PAYMENT_SUM=\"-1\"\n" +
                "\t\t       F_TYPE=\"NUMBER\"\n" +
                "\t\t       TABLE_NAME=\"PMNT$RA_COUNTER\"\n" +
                "\t\t       FIELD_NAME=\"F_CNTR\"\n" +
                "\t\t       FIELD_SIZE=\"10\"\n" +
                "\t\t       F_IS_PAY_PROP=\"0\"\n" +
                "\t\t       F_FIELD_IDENT=\"0\"\n" +
                "\t\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t\t       F_BND_FIELD=\"3\"\n" +
                "\t\t       F_DBO_TEMPLATE=\"1\"\n" +
                "\t\t       F_IS_FORMULA=\"0\"\n" +
                "\t\t       FRML_BITMSK=\"0\"\n" +
                "\t\t       L_ADDPROC=\"-1\"/>\n" +
                "\t\t&lt;z:row K_ID=\"575378\"\n" +
                "\t\t       L_TYPE_PAYMENT=\"80949\"\n" +
                "\t\t       L_TYPE=\"81\"\n" +
                "\t\t       F_NAME_IN_REESTR=\"PAN_MASK\"\n" +
                "\t\t       F_NAME=\"PAN_MASK\"\n" +
                "\t\t       OBLIGATORY=\"0\"\n" +
                "\t\t       DATE_ACTIVE=\"2022-11-01T15:05:08\"\n" +
                "\t\t       ACTIVE=\"1\"\n" +
                "\t\t       DESCRIPTION=\"PAN карты клиента\"\n" +
                "\t\t       F_ORDER=\"100\"\n" +
                "\t\t       EDITABLE=\"0\"\n" +
                "\t\t       F_IS_ON_LINE=\"0\"\n" +
                "\t\t       F_VISIBLE=\"0\"\n" +
                "\t\t       F_INITIAL_WIDTH=\"0\"\n" +
                "\t\t       F_IS_PAYMENT_SUM=\"-1\"\n" +
                "\t\t       F_TYPE=\"VARCHAR2\"\n" +
                "\t\t       TABLE_NAME=\"PMNT$RA_TEXT50\"\n" +
                "\t\t       FIELD_NAME=\"TEXT50\"\n" +
                "\t\t       FIELD_SIZE=\"50\"\n" +
                "\t\t       F_IS_PAY_PROP=\"0\"\n" +
                "\t\t       F_FIELD_IDENT=\"0\"\n" +
                "\t\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t\t       F_BND_FIELD=\"0\"\n" +
                "\t\t       F_DBO_TEMPLATE=\"1\"\n" +
                "\t\t       F_IS_FORMULA=\"0\"\n" +
                "\t\t       FRML_BITMSK=\"0\"\n" +
                "\t\t       L_ADDPROC=\"-1\"/>\n" +
                "\t&lt;/rs:data>\n" +
                "&lt;/xml></XML>\n" +
                "\t\t\t</NS1:TXmlReturnMessage_>\n" +
                "\t\t</NS1:echoGetFieldsPayments_Response>\n" +
                "\t</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
}
