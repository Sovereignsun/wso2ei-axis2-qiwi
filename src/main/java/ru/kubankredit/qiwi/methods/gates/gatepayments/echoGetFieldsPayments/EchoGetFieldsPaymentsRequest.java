package ru.kubankredit.qiwi.methods.gates.gatepayments.echoGetFieldsPayments;

public class EchoGetFieldsPaymentsRequest {

    public final String SOAPAction = "http://esb.bank.srv/gate/payments#echoGetFieldsPayments_";

    private String SessionId;
    private String TypePmntID;

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getTypePmntID() {
        return TypePmntID;
    }

    public void setTypePmntID(String typePmntID) {
        TypePmntID = typePmntID;
    }

}
