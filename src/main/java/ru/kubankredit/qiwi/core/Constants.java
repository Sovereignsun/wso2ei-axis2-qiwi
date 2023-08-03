package ru.kubankredit.qiwi.core;

/**
 * <h1>Класс содержащий константы общие для всего сервиса</h1>
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-25
 */
public class Constants {

    /**
     * Очередь сообщений проверки статуса платежа
     */
    static final public String QIWI_GET_PAYMENT_STATUS_QUEUE = Core.settings.getActiveMQSettings().getPaymentStatusQueueName();
    /**
     * Идентификатор терминала не был передан
     */
    public static final String NO_TERMINAL_ID = "Идентификатор терминала не был передан";
    /**
     * Идентификатор сервиса не был передан
     */
    public static final String NO_SERVICE_ID = "Идентификатор сервиса не был передан";
    /**
     * Пожалуйста передайте идентификатор сервиса в виде QueryParam под именем id
     */
    public static final String NO_SERVICE_ID_DETAIL = "Пожалуйста передайте идентификатор сервиса в виде QueryParam под именем id";

}
