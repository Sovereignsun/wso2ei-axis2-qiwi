package ru.kubankredit.qiwi.dev;

import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.emulators.QiwiHTTPEmulator;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalanceParser;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalancePayload;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalanceRequest;
import ru.kubankredit.qiwi.methods.qiwi.getBalance.GetBalanceResponse;

public class DevGetBalance {

    public static void main(String[] args) {

        final String thisClassName = DevGetBalance.class.getName();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        Core.lm.generateGUID();

        GetBalanceRequest getBalanceRequest = new GetBalanceRequest();
        GetBalanceResponse result = new GetBalanceResponse();
        GetBalanceParser getBalanceParser = new GetBalanceParser();
        GetBalancePayload getBalancePayload = new GetBalancePayload();
        QiwiHTTPEmulator httpModule = new QiwiHTTPEmulator();

        getBalanceRequest.setTerminal("10788430");

        String XMLPayloadString = getBalancePayload.buildXMLPayload(getBalanceRequest);

        Core.lm.debug(thisClassName, methodName, "XMLPayloadString: " + XMLPayloadString);

        try {
            String QiwiResponse = httpModule.getBalance();
            result = getBalanceParser.ParseResponse(QiwiResponse);
            Core.lm.debug(thisClassName, methodName, "ResultCode: " + result.getResultCode());
            Core.lm.debug(thisClassName, methodName, "Balance: " + result.getBalance());
            Core.lm.debug(thisClassName, methodName, "AgentID: " + result.getAgentID());
            Core.lm.debug(thisClassName, methodName, "Overdraft: " + result.getOverdraft());
            Core.lm.debug(thisClassName, methodName, "TreeBalance: " + result.getTreeBalance());
        } catch (Exception e) {
            result.setResultCode(-1);
            Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
        }

    }

}
