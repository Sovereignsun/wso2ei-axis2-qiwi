package ru.kubankredit.qiwi.dev.tele2;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;

import java.io.ByteArrayInputStream;

public class Tele2GetChargesRequest {

    public static OMElement getRequest() {

        final String request = "<?xml version='1.0' encoding='utf-8'?>\n" +
                "        <GetCharges xmlns=\"http://ProxyService\">\n" +
                "            <xml>&lt;Operator>\n" +
                "                &lt;WhereOperator>And&lt;/WhereOperator>\n" +
                "                &lt;Nodes>\n" +
                "                &lt;Field>\n" +
                "                &lt;FieldOperator>StartingWith&lt;/FieldOperator>\n" +
                "                &lt;FieldName>PHONE&lt;/FieldName>\n" +
                "                &lt;FieldValue>\n" +
                "                &lt;Column>\n" +
                "                &lt;ID>0&lt;/ID>\n" +
                "                &lt;Kind>String&lt;/Kind>\n" +
                "                &lt;ColumnName>&lt;/ColumnName>\n" +
                "                &lt;ColumnTitle>&lt;/ColumnTitle>\n" +
                "                &lt;Obligatory>False&lt;/Obligatory>\n" +
                "                &lt;Active>False&lt;/Active>\n" +
                "                &lt;RegExp>&lt;/RegExp>\n" +
                "                &lt;Searchable>False&lt;/Searchable>\n" +
                "                &lt;SystemField>False&lt;/SystemField>\n" +
                "                &lt;ExactSrch>False&lt;/ExactSrch>\n" +
                "                &lt;/Column>\n" +
                "                &lt;Field>\n" +
                "                &lt;IsEmpty>False&lt;/IsEmpty>\n" +
                "                &lt;Data>1175689795&lt;/Data>\n" +
                "                &lt;/Field>\n" +
                "                &lt;/FieldValue>\n" +
                "                &lt;/Field>\n" +
                "                &lt;/Nodes>\n" +
                "                &lt;/Operator></xml>\n" +
                "            <dataList>\n" +
                "                <Data>\n" +
                "                    <Name>SESSION_ID</Name>\n" +
                "                    <Value>4197C302-E0B5-4C2E-B084-5E3996227DED</Value>\n" +
                "                </Data>\n" +
                "                <Data>\n" +
                "                    <Name>TYPE_CHRG_ID</Name>\n" +
                "                    <Value>7527</Value>\n" +
                "                </Data>\n" +
                "            </dataList>\n" +
                "        </GetCharges>";

        OMFactory factory = OMAbstractFactory.getOMFactory();

        return OMXMLBuilderFactory.createOMBuilder(factory, new ByteArrayInputStream(request.getBytes())).getDocumentElement();
    }
}
