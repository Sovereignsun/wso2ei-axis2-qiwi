package ru.kubankredit.qiwi.dev.gatePayment;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.emulators.GatePaymentsHTTPEmulator;
import ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments.*;

import java.util.List;

public class DevEchoGetFieldsPayments {

    public static void main(String[] args) {

        final String thisClassName = DevEchoGetFieldsPayments.class.getName();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();

        EchoGetFieldsPaymentsRequest echoGetFieldsPaymentsRequest = new EchoGetFieldsPaymentsRequest();
        EchoGetFieldsPaymentsPayload echoGetFieldsPaymentsPayload = new EchoGetFieldsPaymentsPayload();
        EchoGetFieldsPaymentsResponseParser echoGetFieldsPaymentsResponseParser = new EchoGetFieldsPaymentsResponseParser();

        // Укажите актуальную живую сессию из J_SESSION
        echoGetFieldsPaymentsRequest.setSessionId("4197C302-E0B5-4C2E-B084-5E3996227DED");
        // Укажите номер реестра начислений из ПЦ
        echoGetFieldsPaymentsRequest.setTypePmntID("7167");

        String xmlData = echoGetFieldsPaymentsPayload.buildXMLPayload(echoGetFieldsPaymentsRequest);

        try {
            String serviceResponse = new GatePaymentsHTTPEmulator().echoGetFieldsPayments_OneField();
            EchoGetFieldsPaymentsResponse echoGetFieldsPaymentsResponse = echoGetFieldsPaymentsResponseParser.parseResponse(serviceResponse);

            if (echoGetFieldsPaymentsResponse.getError() != 0) {
                throw new AbstractException(echoGetFieldsPaymentsResponse.getErrorMessage(), echoGetFieldsPaymentsResponse.getError());
            }

            System.out.println(echoGetFieldsPaymentsResponse.getXml());

            EchoGetFieldsPaymentsXMLParser echoGetFieldsReestrChrgXMLParser = new EchoGetFieldsPaymentsXMLParser();
            echoGetFieldsReestrChrgXMLParser.parseResponse(echoGetFieldsPaymentsResponse);

            List<EchoGetFieldsPaymentsResponse.Field> fields = echoGetFieldsPaymentsResponse.getFields();
            for (EchoGetFieldsPaymentsResponse.Field field : fields) {
                System.out.println("K_ID=" + field.getK_id());
                System.out.println("FieldName=" + field.getField_name());
                System.out.println("ReestrName=" + field.getF_name_in_reestr());
                System.out.println("Obligatory=" + field.getObligatory());
                System.out.println("\r\n");
            }

        } catch (AbstractException e) {
            Core.lm.error(thisClassName, methodName, e.getMessage());
        }

    }

}
