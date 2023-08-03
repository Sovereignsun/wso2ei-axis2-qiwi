package ru.kubankredit.qiwi.methods.gates.gatecontract.echoGetFieldsReestrChrg;

public class EchoGetFieldsReestrChrgRequest {

    public final String SOAPAction = "http://esb.bank.srv/gate/contract#echoGetFieldsReestrChrg";

    private String SessionId;
    private String TypeChrgID;

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getTypeChrgID() {
        return TypeChrgID;
    }

    public void setTypeChrgID(String typeChrgID) {
        TypeChrgID = typeChrgID;
    }

}
