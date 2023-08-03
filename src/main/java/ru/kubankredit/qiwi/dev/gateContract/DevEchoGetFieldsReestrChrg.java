package ru.kubankredit.qiwi.dev.gateContract;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.dev.tele2.DevGetChargesRequest;
import ru.kubankredit.qiwi.emulators.GateContractHTTPEmulator;
import ru.kubankredit.qiwi.methods.gates.gatecontract.echoGetFieldsReestrChrg.*;

import java.util.List;

public class DevEchoGetFieldsReestrChrg {

    public static void main(String[] args) {

        final String thisClassName = DevGetChargesRequest.class.getName();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();

        EchoGetFieldsReestrChrgRequest echoGetFieldsReestrChrgRequest = new EchoGetFieldsReestrChrgRequest();
        EchoGetFieldsReestrChrgPayload echoGetFieldsReestrChrgPayload = new EchoGetFieldsReestrChrgPayload();
        EchoGetFieldsReestrChrgResponseParser echoGetFieldsReestrChrgResponseParser = new EchoGetFieldsReestrChrgResponseParser();

        // Укажите актуальную живую сессию из J_SESSION
        echoGetFieldsReestrChrgRequest.setSessionId("4197C302-E0B5-4C2E-B084-5E3996227DED");
        // Укажите номер реестра начислений из ПЦ
        echoGetFieldsReestrChrgRequest.setTypeChrgID("7527");

        String xmlData = echoGetFieldsReestrChrgPayload.buildXMLPayload(echoGetFieldsReestrChrgRequest);

        try {
            String serviceResponse = new GateContractHTTPEmulator().echoGetFieldsReestrChrg_Error1();
            EchoGetFieldsReestrChrgResponse echoGetFieldsReestrChrgResponse = echoGetFieldsReestrChrgResponseParser.parseResponse(serviceResponse);

            if (echoGetFieldsReestrChrgResponse.getError() != 0) {
                throw new AbstractException(echoGetFieldsReestrChrgResponse.getErrorMessage(), echoGetFieldsReestrChrgResponse.getError());
            }

            System.out.println(echoGetFieldsReestrChrgResponse.getXml());

            EchoGetFieldsReestrChrgXMLParser echoGetFieldsReestrChrgXMLParser = new EchoGetFieldsReestrChrgXMLParser();
            echoGetFieldsReestrChrgXMLParser.parseResponse(echoGetFieldsReestrChrgResponse);

            List<EchoGetFieldsReestrChrgResponse.Field> fields = echoGetFieldsReestrChrgResponse.getFields();
            for (EchoGetFieldsReestrChrgResponse.Field field : fields) {
                System.out.println("K_ID=" + field.getK_id());
                System.out.println("FieldName=" + field.getField_name());
                System.out.println("ReestrName=" + field.getF_cod_reestr());
                System.out.println("Obligatory=" + field.getObligatory());
                System.out.println("\r\n");
            }

        } catch (AbstractException e) {
            Core.lm.error(thisClassName, methodName, e.getMessage());
        }

    }
}
