package ru.kubankredit.qiwi.db.entities.setOnlineStatus;

import ru.kubankredit.qiwi.core.enums.EnumOnlineStatus;

/**
 * <h1>Объект с данными для запроса установки онлайн статуса платежа</h1>
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @apiNote Онлайн статус берём из EnumOnlineStatus
 * @since 2023-07-27
 */
public class SetOnlineStatusRequest {

    /**
     * Идентификатор сессии ПЦ из J_SESSION
     */
    private String sessionId;
    /**
     * Идентификатор платежа
     */
    private int paymentId;
    /**
     * Код онлайн статуса из EnumOnlineStatus
     */
    private EnumOnlineStatus onlineStatus;
    /**
     * Код ошибки (если необходимо)
     */
    private String errorCode;
    /**
     * Текст ошибки (если необходимо)
     */
    private String errorText;

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

    public EnumOnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(EnumOnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * Возвращает запрос установки онлайн статуса платежа
     *
     * @return Объект запроса установки онлайн статуса платежа.
     */
    @Override
    public String toString() {
        return "\r\nСессия: " + sessionId +
                "\r\nИдентификатор платежа: " + paymentId +
                "\r\nСтатус: " + onlineStatus.getName() +
                "\r\nОшибка: " + errorText +
                "\r\nКод ошибки: " + errorCode;
    }

}
