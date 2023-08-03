package ru.kubankredit.qiwi.emulators;

import ru.kubankredit.qiwi.core.exceptions.AbstractException;

@SuppressWarnings("RedundantThrows")
public class GateContractHTTPEmulator {

    public String echoGetFieldsReestrChrg_OneField() throws AbstractException {

        return "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                  xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "                  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<soapenv:Header>\n" +
                "\t\t<BodySign xsi:type=\"xsd:string\"/>\n" +
                "\t</soapenv:Header>\n" +
                "\t<soapenv:Body xmlns:NS2=\"http://esb.bank.srv/gate/contract\"\n" +
                "\t              SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "\t\t<NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t\t\t<NS2:TXmlReturnMessage xsi:type=\"NS2:TXmlReturnMessage\">\n" +
                "\t\t\t\t<XML xsi:type=\"xsd:string\">&lt;xml xmlns:s='uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882' xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' xmlns:rs='urn:schemas-microsoft-com:rowset' xmlns:z='#RowsetSchema'>\n" +
                "  &lt;rs:data>\n" +
                "    &lt;z:row \n" +
                "\tK_ID='154706' \n" +
                "\tL_TYPE_REESTR='52331'\n" +
                "\tL_TYPE='161' \n" +
                "\tF_COD_REESTR='ACCOUNT'\n" +
                "\tF_NAME='PHONE' \n" +
                "\tOBLIGATORY='1' \n" +
                "\tDATE_ACTIVE='2022-11-01T15:05:07' \n" +
                "\tACTIVE='1' \n" +
                "\tDESCRIPTION='Номер лицевого счета' \n" +
                "\tF_ORDER='0'\n" +
                "\tF_REGEXP='^\\d{9}|\\d{10}$' \n" +
                "\tSEARCHABLE='0' \n" +
                "\tTYPE_REESTR='Проверка возможности создания платежа' \n" +
                "\tF_TYPE='VARCHAR2' \n" +
                "\tTABLE_NAME='CHRG$RA_PHONE' \n" +
                "\tFIELD_NAME='F_PHONE' \n" +
                "\tFIELD_SIZE='20' \n" +
                "\tEXACT_SRCH='1' \n" +
                "\tVISIBLE='1' \n" +
                "\tREPEATED='1' \n" +
                "\tMOBILE_VISIBLE='1' \n" +
                "\tHINT='9 или 10 цифр' \n" +
                "\tSPRAV_DESCR='НЕ УКАЗАН' \n" +
                "\tF_IS_SUMMA='0' \n" +
                "\tF_DBO_TEMPLATE='0'/>\n" +
                "  &lt;/rs:data>\n" +
                "&lt;/xml></XML>\n" +
                "\t\t\t\t<Error xsi:type=\"xsd:string\">0</Error>\n" +
                "\t\t\t\t<ErrorMessage xsi:type=\"xsd:string\">Получен список полей реестра</ErrorMessage>\n" +
                "\t\t\t</NS2:TXmlReturnMessage>\n" +
                "\t\t</NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    public String echoGetFieldsReestrChrg_TwoFields() throws AbstractException {

        return "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                  xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "                  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<soapenv:Header>\n" +
                "\t\t<BodySign xsi:type=\"xsd:string\"/>\n" +
                "\t</soapenv:Header>\n" +
                "\t<soapenv:Body xmlns:NS2=\"http://esb.bank.srv/gate/contract\"\n" +
                "\t              SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "\t\t<NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t\t\t<NS2:TXmlReturnMessage xsi:type=\"NS2:TXmlReturnMessage\">\n" +
                "\t\t\t\t<XML xsi:type=\"xsd:string\">&lt;xml xmlns:s='uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882' xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' xmlns:rs='urn:schemas-microsoft-com:rowset' xmlns:z='#RowsetSchema'>\n" +
                "&lt;rs:data>\n" +
                "\t&lt;z:row K_ID=\"114764\"\n" +
                "\t       L_TYPE_REESTR=\"37564\"\n" +
                "\t       L_TYPE=\"161\"\n" +
                "\t       F_COD_REESTR=\"ACCOUNT\"\n" +
                "\t       F_NAME=\"PHONE\"\n" +
                "\t       OBLIGATORY=\"1\"\n" +
                "\t       DATE_ACTIVE=\"2021-08-04T09:17:22\"\n" +
                "\t       ACTIVE=\"1\"\n" +
                "\t       DESCRIPTION=\"Номер мобильного телефона\"\n" +
                "\t       F_ORDER=\"0\"\n" +
                "\t       F_REGEXP=\"^\\d{10}$\"\n" +
                "\t       SEARCHABLE=\"0\"\n" +
                "\t       TYPE_REESTR=\"Проверка возможности создания платежа\"\n" +
                "\t       F_TYPE=\"VARCHAR2\"\n" +
                "\t       TABLE_NAME=\"CHRG$RA_PHONE\"\n" +
                "\t       FIELD_NAME=\"F_PHONE\"\n" +
                "\t       FIELD_SIZE=\"20\"\n" +
                "\t       EXACT_SRCH=\"0\"\n" +
                "\t       VISIBLE=\"1\"\n" +
                "\t       REPEATED=\"1\"\n" +
                "\t       MOBILE_VISIBLE=\"1\"\n" +
                "\t       HINT=\"10 цифр\"\n" +
                "\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t       F_IS_SUMMA=\"0\"/>\n" +
                "\t&lt;z:row K_ID=\"114765\"\n" +
                "\t       L_TYPE_REESTR=\"37564\"\n" +
                "\t       L_TYPE=\"1\"\n" +
                "\t       F_COD_REESTR=\"ACCOUNT_NUMBER\"\n" +
                "\t       F_NAME=\"ACCOUNT\"\n" +
                "\t       OBLIGATORY=\"0\"\n" +
                "\t       DATE_ACTIVE=\"2021-08-04T09:17:22\"\n" +
                "\t       ACTIVE=\"1\"\n" +
                "\t       DESCRIPTION=\"Номер счёта\"\n" +
                "\t       F_ORDER=\"100\"\n" +
                "\t       SEARCHABLE=\"0\"\n" +
                "\t       TYPE_REESTR=\"Проверка возможности создания платежа\"\n" +
                "\t       F_TYPE=\"VARCHAR2\"\n" +
                "\t       TABLE_NAME=\"CHRG$RA_ACCOUNT\"\n" +
                "\t       FIELD_NAME=\"F_SECONDNAME\"\n" +
                "\t       FIELD_SIZE=\"20\"\n" +
                "\t       EXACT_SRCH=\"0\"\n" +
                "\t       VISIBLE=\"0\"\n" +
                "\t       REPEATED=\"0\"\n" +
                "\t       MOBILE_VISIBLE=\"0\"\n" +
                "\t       SPRAV_DESCR=\"НЕ УКАЗАН\"\n" +
                "\t       F_IS_SUMMA=\"0\"\n" +
                "\t       F_DBO_TEMPLATE=\"0\"/>\n" +
                "&lt;/rs:data>" +
                "&lt;/xml></XML>\n" +
                "\t\t\t\t<Error xsi:type=\"xsd:string\">0</Error>\n" +
                "\t\t\t\t<ErrorMessage xsi:type=\"xsd:string\">Получен список полей реестра</ErrorMessage>\n" +
                "\t\t\t</NS2:TXmlReturnMessage>\n" +
                "\t\t</NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    public String echoGetFieldsReestrChrg_Error1() throws AbstractException {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                   xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "                   xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<SOAP-ENV:Header xmlns:NS1=\"urn:ConceptCar.Gate.WebSecure\"\n" +
                "\t                 SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "\t                 xsi:type=\"NS1:TLiteSoapHeader\">\n" +
                "\t\t<BodySign xsi:type=\"xsd:string\"/>\n" +
                "\t</SOAP-ENV:Header>\n" +
                "\t<SOAP-ENV:Body xmlns:NS2=\"http://esb.bank.srv/gate/contract\"\n" +
                "\t               SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "\t\t<NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t\t\t<return href=\"#2\"/>\n" +
                "\t\t</NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t\t<NS2:TXmlReturnMessage id=\"2\"\n" +
                "\t\t                       xsi:type=\"NS2:TXmlReturnMessage\">\n" +
                "\t\t\t<XML xsi:type=\"xsd:string\"/>\n" +
                "\t\t\t<Error xsi:type=\"xsd:string\">1</Error>\n" +
                "\t\t\t<ErrorMessage xsi:type=\"xsd:string\">ORA-01403: no data foundORA-06512: at \"P_CENTER.CHRG_META_LIB\", line 900ORA-06512: at \"P_CENTER.CHRG_META_LIB\", line 274ORA-06512: at \"P_CENTER.CHRG_META_LIB\", line 263ORA-06512: at \"P_CENTER.CHRG_META_LIB\", line 760ORA-06512: at line 2</ErrorMessage>\n" +
                "\t\t</NS2:TXmlReturnMessage>\n" +
                "\t</SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>";
    }

    public String echoGetFieldsReestrChrg_Error2() throws AbstractException {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                   xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "                   xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<SOAP-ENV:Header xmlns:NS1=\"urn:ConceptCar.Gate.WebSecure\"\n" +
                "\t                 SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
                "\t                 xsi:type=\"NS1:TLiteSoapHeader\">\n" +
                "\t\t<BodySign xsi:type=\"xsd:string\"/>\n" +
                "\t</SOAP-ENV:Header>\n" +
                "\t<SOAP-ENV:Body xmlns:NS2=\"http://esb.bank.srv/gate/contract\"\n" +
                "\t               SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "\t\t<NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t\t\t<return href=\"#2\"/>\n" +
                "\t\t</NS2:echoGetFieldsReestrChrgResponse>\n" +
                "\t\t<NS2:TXmlReturnMessage id=\"2\"\n" +
                "\t\t                       xsi:type=\"NS2:TXmlReturnMessage\">\n" +
                "\t\t\t<XML xsi:type=\"xsd:string\"/>\n" +
                "\t\t\t<Error xsi:type=\"xsd:string\">2</Error>\n" +
                "\t\t\t<ErrorMessage xsi:type=\"xsd:string\">Отсутствует привилегия: Поля реестра начислений(просмотр)</ErrorMessage>\n" +
                "\t\t</NS2:TXmlReturnMessage>\n" +
                "\t</SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>";
    }
}
