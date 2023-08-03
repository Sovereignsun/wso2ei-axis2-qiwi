package ru.kubankredit.qiwi.methods.qiwi.checkPaymentRequisites;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumCurrency;
import ru.kubankredit.qiwi.core.enums.EnumMoneyType;
import ru.kubankredit.qiwi.methods.gates.gatecontract.echoGetFieldsReestrChrg.EchoGetFieldsReestrChrgResponse;
import ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments.EchoGetFieldsPaymentsResponse;
import ru.kubankredit.qiwi.methods.main.getCharges.GetChargesRequest;
import ru.kubankredit.qiwi.methods.main.possPayment.PossPaymentRequest;
import ru.kubankredit.qiwi.serializers.PayloadSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

public class CheckPaymentRequisitesPayload {

    final protected String thisClassName = this.getClass().getName();

    protected final PayloadSerializer payloadSerializer = new PayloadSerializer();

    public String buildXMLPayload(GetChargesRequest getChargesRequestPayload, EchoGetFieldsReestrChrgResponse echoGetFieldsReestrChrgResponse) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            // Initialize the parameters from the configuration file
            String software = Core.settings.getQiwiSettings().getSoftware();
            String terminalId = Core.settings.getQiwiSettings().getTerminalId();
            String fixedAmount = Core.settings.getQiwiSettings().getFixedAmount();

            // Create a new DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Create a new Document
            Document document = dBuilder.newDocument();

            // Set the XML document declaration
            document.setXmlVersion("1.0");
            document.setXmlStandalone(true);

            // Create the root element
            Element request = document.createElement("request");
            document.appendChild(request);

            // Create the "client" element with terminal and software attributes
            Element client = document.createElement("client");
            if (getChargesRequestPayload.getTerminal() != null) {
                client.setAttribute("terminal", getChargesRequestPayload.getTerminal());
            } else {
                client.setAttribute("terminal", terminalId);
            }
            client.setAttribute("software", software);
            request.appendChild(client);

            // Create the "providers" element
            Element providers = document.createElement("providers");
            request.appendChild(providers);

            // Create the "checkPaymentRequisites" element
            Element checkPaymentRequisites = document.createElement("checkPaymentRequisites");
            providers.appendChild(checkPaymentRequisites);

            // Create the "payment" element
            Element payment = document.createElement("payment");
            payment.setAttribute("id", "1");

            // Create the "from" element
            Element from = document.createElement("from");
            from.setAttribute("currency", EnumCurrency.RUSSIAN_RUBLE.getStringCode());
            from.setAttribute("amount", fixedAmount);
            payment.appendChild(from);

            // Create the "to" element
            Element to = document.createElement("to");
            to.setAttribute("currency", EnumCurrency.RUSSIAN_RUBLE.getStringCode());
            to.setAttribute("service", getChargesRequestPayload.getServiceId());
            to.setAttribute("moneyType", EnumMoneyType.BANK_CARD_NO_COMMISSION.getStringCode());
            to.setAttribute("amount", fixedAmount);
            List<GetChargesRequest.Field> requestFields = getChargesRequestPayload.getFields();
            for (GetChargesRequest.Field field : requestFields) {
                EchoGetFieldsReestrChrgResponse.Field chrgField = echoGetFieldsReestrChrgResponse.findFieldByName(field.getFieldName().toLowerCase());
                if (chrgField.getObligatory()) {
                    to.setAttribute(chrgField.getF_cod_reestr().toLowerCase(), field.getFieldValue());
                }
            }
            payment.appendChild(to);

            // Create the "receipt" element
            Element receipt = document.createElement("receipt");
            receipt.setAttribute("id", "1");
            receipt.setAttribute("date", Core.getCurrentDate());
            payment.appendChild(receipt);

            // Append "payment" to the "checkPaymentRequisites" element
            checkPaymentRequisites.appendChild(payment);

            document.getDocumentElement().normalize();

            // Return the serialized XML Payload
            return payloadSerializer.serializeXMLPayload(document);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return null;
    }

    public String buildXMLPayload(PossPaymentRequest possPaymentRequestPayload, EchoGetFieldsPaymentsResponse echoGetFieldsPaymentsResponse) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            // Initialize the parameters from the configuration file
            String software = Core.settings.getQiwiSettings().getSoftware();
            String terminalId = Core.settings.getQiwiSettings().getTerminalId();

            // Create a new DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Create a new Document
            Document document = dBuilder.newDocument();

            // Set the XML document declaration
            document.setXmlVersion("1.0");
            document.setXmlStandalone(true);

            // Create the root element
            Element request = document.createElement("request");
            document.appendChild(request);

            // Create the "client" element with terminal and software attributes
            Element client = document.createElement("client");
            if (possPaymentRequestPayload.getTerminal() != null) {
                client.setAttribute("terminal", possPaymentRequestPayload.getTerminal());
            } else {
                client.setAttribute("terminal", terminalId);
            }
            client.setAttribute("software", software);
            request.appendChild(client);

            // Create the "providers" element
            Element providers = document.createElement("providers");
            request.appendChild(providers);

            // Create the "checkPaymentRequisites" element
            Element checkPaymentRequisites = document.createElement("checkPaymentRequisites");
            providers.appendChild(checkPaymentRequisites);

            // Create the "payment" element
            Element payment = document.createElement("payment");
            payment.setAttribute("id", "1");

            // Create the "from" element
            Element from = document.createElement("from");
            from.setAttribute("currency", EnumCurrency.RUSSIAN_RUBLE.getStringCode());
            String SUMMA = "SUMMA";
            from.setAttribute("amount", possPaymentRequestPayload.getAttributeValueByName(SUMMA));
            payment.appendChild(from);

            // Create the "to" element
            Element to = document.createElement("to");
            to.setAttribute("currency", EnumCurrency.RUSSIAN_RUBLE.getStringCode());
            to.setAttribute("service", possPaymentRequestPayload.getServiceId());
            to.setAttribute("moneyType", EnumMoneyType.BANK_CARD_NO_COMMISSION.getStringCode());
            to.setAttribute("amount", possPaymentRequestPayload.getAttributeValueByName(SUMMA));
            List<PossPaymentRequest.Attribute> attributes = possPaymentRequestPayload.getAttributes();
            for (PossPaymentRequest.Attribute attribute : attributes) {
                EchoGetFieldsPaymentsResponse.Field pmntField = echoGetFieldsPaymentsResponse.findFieldByName(attribute.getName().toLowerCase());
                if (pmntField != null && pmntField.getObligatory()) {
                    to.setAttribute(pmntField.getF_name_in_reestr().toLowerCase(), attribute.getValue());
                }
            }
            payment.appendChild(to);

            // Create the "receipt" element
            Element receipt = document.createElement("receipt");
            String NUMBER_PAYM = "NUMBER_PAYM";
            receipt.setAttribute("id", possPaymentRequestPayload.getAttributeValueByName(NUMBER_PAYM));
            String DATE_PAYM = "DATE_PAYM";
            receipt.setAttribute("date", possPaymentRequestPayload.getAttributeValueByName(DATE_PAYM));
            payment.appendChild(receipt);

            // Append "payment" to the "checkPaymentRequisites" element
            checkPaymentRequisites.appendChild(payment);

            document.getDocumentElement().normalize();

            // Return the serialized XML Payload
            return payloadSerializer.serializeXMLPayload(document);

        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

        return null;
    }

    /* Example XML Payload

        <?xml version="1.0" encoding="utf-8"?>
        <request>
            <client terminal="111"
                    software="Dealer v0"/>
            <providers>
                <checkPaymentRequisites>
                    <payment id="501">
                        <from currency="643"
                              amount="11.00"/>
                        <to currency="643"
                            service="2"
                            amount="11.00"
                            account="111"
                            moneyType="1"/>
                        <receipt id="132"
                                 date="2010-08-16T12:43:01"/>
                    </payment>
                </checkPaymentRequisites>
            </providers>
        </request>

    */
}

