package ru.kubankredit.qiwi.methods.qiwi.authorizePayment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumCurrency;
import ru.kubankredit.qiwi.core.enums.EnumMoneyType;
import ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments.EchoGetFieldsPaymentsResponse;
import ru.kubankredit.qiwi.methods.main.addPayment.AddPaymentRequest;
import ru.kubankredit.qiwi.serializers.PayloadSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

public class AuthorizePaymentPayload {

    final protected String thisClassName = this.getClass().getName();

    protected final PayloadSerializer payloadSerializer = new PayloadSerializer();

    /**
     * Запрос добавления платежа в АО "Киви"
     *
     * @param addPaymentRequest Объект AddPayment.Request
     * @return Ответ в виде запроса в АО "Киви" в формате XML
     */
    public String buildXMLPayload(AddPaymentRequest addPaymentRequest, EchoGetFieldsPaymentsResponse echoGetFieldsPaymentsResponse) {
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
            if (addPaymentRequest.getTerminal() != null) {
                client.setAttribute("terminal", addPaymentRequest.getTerminal());
            } else {
                client.setAttribute("terminal", terminalId);
            }
            client.setAttribute("software", software);
            request.appendChild(client);

            // Create the "providers" element
            Element providers = document.createElement("providers");
            request.appendChild(providers);

            // Create the "authorizePayment" element
            Element authorizePayment = document.createElement("authorizePayment");
            providers.appendChild(authorizePayment);

            // Create the "payment" element
            Element payment = document.createElement("payment");
            String k_ID = "K_ID";
            payment.setAttribute("id", addPaymentRequest.getAttributeValueByName(k_ID));

            // Create the "from" element
            Element from = document.createElement("from");
            from.setAttribute("currency", EnumCurrency.RUSSIAN_RUBLE.getStringCode());
            String SUMMA = "SUMMA";
            from.setAttribute("amount", addPaymentRequest.getAttributeValueByName(SUMMA));
            payment.appendChild(from);

            // Create the "to" element
            Element to = document.createElement("to");
            to.setAttribute("currency", EnumCurrency.RUSSIAN_RUBLE.getStringCode());
            to.setAttribute("service", addPaymentRequest.getServiceId());
            to.setAttribute("moneyType", EnumMoneyType.BANK_CARD_NO_COMMISSION.getStringCode());
            to.setAttribute("amount", addPaymentRequest.getAttributeValueByName(SUMMA));
            List<AddPaymentRequest.Attribute> attributes = addPaymentRequest.getAttributes();
            for (AddPaymentRequest.Attribute attribute : attributes) {
                EchoGetFieldsPaymentsResponse.Field pmntField = echoGetFieldsPaymentsResponse.findFieldByName(attribute.getName().toLowerCase());
                if (pmntField != null && pmntField.getObligatory()) {
                    to.setAttribute(pmntField.getF_name_in_reestr().toLowerCase(), attribute.getValue());
                }
            }
            payment.appendChild(to);

            // Create the "receipt" element
            Element receipt = document.createElement("receipt");
            String NUMBER_PAYM = "NUMBER_PAYM";
            receipt.setAttribute("id", addPaymentRequest.getAttributeValueByName(NUMBER_PAYM));
            String DATE_PAYM = "DATE_PAYM";
            receipt.setAttribute("date", addPaymentRequest.getAttributeValueByName(DATE_PAYM));
            payment.appendChild(receipt);

            // Append "payment" to the "authorizePayment" element
            authorizePayment.appendChild(payment);

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
                <authorizePayment>
                    <payment id="301">
                        <extras ev_paytype="5"
                                PAY_TYPE="5"/>
                        <from amount="0.01"
                              currency="643"/>
                        <to service="2"
                            account="1919"
                            amount="0.01"
                            currency="643"
                            moneyType="1"/>
                        <receipt id="132"
                                 date="2010-09-01T15:54:16"/>
                    </payment>
                </authorizePayment>
            </providers>
        </request>

    */
}

