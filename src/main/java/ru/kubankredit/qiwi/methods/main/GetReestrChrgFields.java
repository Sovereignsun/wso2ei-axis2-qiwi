package ru.kubankredit.qiwi.methods.main;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.enums.EnumService;
import ru.kubankredit.qiwi.core.exceptions.AbstractException;
import ru.kubankredit.qiwi.core.http.PCServicesModule;
import ru.kubankredit.qiwi.emulators.GateContractHTTPEmulator;
import ru.kubankredit.qiwi.methods.gates.gatecontract.echoGetFieldsReestrChrg.*;
import ru.kubankredit.qiwi.methods.main.getCharges.GetChargesRequest;

public class GetReestrChrgFields {
    public EchoGetFieldsReestrChrgResponse getReestrChrgFields(GetChargesRequest getChargesRequest) throws AbstractException {

        EchoGetFieldsReestrChrgRequest echoGetFieldsReestrChrgRequest = new EchoGetFieldsReestrChrgRequest();
        EchoGetFieldsReestrChrgPayload echoGetFieldsReestrChrgPayload = new EchoGetFieldsReestrChrgPayload();
        EchoGetFieldsReestrChrgResponseParser echoGetFieldsReestrChrgResponseParser = new EchoGetFieldsReestrChrgResponseParser();

        echoGetFieldsReestrChrgRequest.setSessionId(getChargesRequest.getDataValueByName("SESSION_ID"));
        echoGetFieldsReestrChrgRequest.setTypeChrgID(getChargesRequest.getDataValueByName("TYPE_CHRG_ID"));

        String xmlData = echoGetFieldsReestrChrgPayload.buildXMLPayload(echoGetFieldsReestrChrgRequest);

        String serviceResponse;

        if (!Core.DeveloperMode) {
            PCServicesModule pcServicesModule = new PCServicesModule();
            serviceResponse = pcServicesModule.SOAPPost(xmlData, EnumService.GateContract, echoGetFieldsReestrChrgRequest.SOAPAction);
        } else {
            serviceResponse = new GateContractHTTPEmulator().echoGetFieldsReestrChrg_OneField();
        }
        EchoGetFieldsReestrChrgResponse echoGetFieldsReestrChrgResponse = echoGetFieldsReestrChrgResponseParser.parseResponse(serviceResponse);

        if (echoGetFieldsReestrChrgResponse.getError() != 0) {
            throw new AbstractException(echoGetFieldsReestrChrgResponse.getErrorMessage(), echoGetFieldsReestrChrgResponse.getError());
        }

        EchoGetFieldsReestrChrgXMLParser echoGetFieldsReestrChrgXMLParser = new EchoGetFieldsReestrChrgXMLParser();
        echoGetFieldsReestrChrgXMLParser.parseResponse(echoGetFieldsReestrChrgResponse);

        return echoGetFieldsReestrChrgResponse;
    }
}
