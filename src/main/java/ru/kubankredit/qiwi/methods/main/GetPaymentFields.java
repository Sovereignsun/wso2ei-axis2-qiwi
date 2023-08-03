package ru.kubankredit.qiwi.methods.main;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumService;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.core.http.PCServicesModule;
import ru.kubankredit.qiwi.emulators.GatePaymentsHTTPEmulator;
import ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments.*;
import ru.kubankredit.qiwi.methods.main.addPayment.AddPaymentRequest;
import ru.kubankredit.qiwi.methods.main.possPayment.PossPaymentRequest;

public class GetPaymentFields {

    public EchoGetFieldsPaymentsResponse getPaymentsFields(Object request) throws AbstractException {

        EchoGetFieldsPaymentsRequest echoGetFieldsPaymentsRequest = new EchoGetFieldsPaymentsRequest();
        EchoGetFieldsPaymentsPayload echoGetFieldsPaymentsPayload = new EchoGetFieldsPaymentsPayload();
        EchoGetFieldsPaymentsResponseParser echoGetFieldsPaymentsResponseParser = new EchoGetFieldsPaymentsResponseParser();

        if (request instanceof AddPaymentRequest) {
            AddPaymentRequest addPaymentRequest = (AddPaymentRequest) request;
            echoGetFieldsPaymentsRequest.setSessionId(addPaymentRequest.getDataValueByName("SESSION_ID"));
            echoGetFieldsPaymentsRequest.setTypePmntID(addPaymentRequest.getDataValueByName("TYPE_PMNT_ID"));
        } else if (request instanceof PossPaymentRequest) {
            PossPaymentRequest possPaymentRequest = (PossPaymentRequest) request;
            echoGetFieldsPaymentsRequest.setSessionId(possPaymentRequest.getDataValueByName("SESSION_ID"));
            echoGetFieldsPaymentsRequest.setTypePmntID(possPaymentRequest.getDataValueByName("TYPE_PMNT_ID"));
        }

        String xmlData = echoGetFieldsPaymentsPayload.buildXMLPayload(echoGetFieldsPaymentsRequest);

        String serviceResponse;

        if (!Core.DeveloperMode) {
            PCServicesModule pcServicesModule = new PCServicesModule();
            serviceResponse = pcServicesModule.SOAPPost(xmlData, EnumService.GatePayments, echoGetFieldsPaymentsRequest.SOAPAction);
        } else {
            serviceResponse = new GatePaymentsHTTPEmulator().echoGetFieldsPayments_OneField();
        }
        EchoGetFieldsPaymentsResponse echoGetFieldsPaymentsResponse = echoGetFieldsPaymentsResponseParser.parseResponse(serviceResponse);

        if (echoGetFieldsPaymentsResponse.getError() != 0) {
            throw new AbstractException(echoGetFieldsPaymentsResponse.getErrorMessage(), echoGetFieldsPaymentsResponse.getError());
        }

        EchoGetFieldsPaymentsXMLParser echoGetFieldsPaymentsXMLParser = new EchoGetFieldsPaymentsXMLParser();
        echoGetFieldsPaymentsXMLParser.parseResponse(echoGetFieldsPaymentsResponse);

        return echoGetFieldsPaymentsResponse;
    }
}
