package ru.kubankredit.qiwi.db.entities.setExternalId;

public class SetExternalIdRequest {

    private String sessionId;
    private int paymentId;

    private String extId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    /**
     * Возвращает объект запроса установки внешнего идентификатора платежа
     *
     * @return Объект запроса установки внешнего идентификатора платежа.
     */
    @Override
    public String toString() {
        return "\r\nСессия: " + sessionId +
                "\r\nИдентификатор платежа: " + paymentId +
                "\r\nВнешний идентификатор: " + extId;
    }

}
