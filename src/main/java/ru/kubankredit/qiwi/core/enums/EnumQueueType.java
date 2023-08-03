package ru.kubankredit.qiwi.core.enums;

import static ru.kubankredit.qiwi.core.Constants.QIWI_GET_PAYMENT_STATUS_QUEUE;

/**
 * <h1>Типы очередей в ActiveMQ</h1>
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-26
 */
public enum EnumQueueType {
    /**
     * Ошибка проведения (Код 0 - финальный)
     */
    GET_PAYMENT_STATUS("GetPaymentStatus", QIWI_GET_PAYMENT_STATUS_QUEUE);

    private final String xmlType;
    private final String queueName;

    /**
     * Конструктор типа очереди
     *
     * @param xmlType   XML тип сообщения
     * @param queueName Наименование очереди
     */
    EnumQueueType(String xmlType, String queueName) {
        this.xmlType = xmlType;
        this.queueName = queueName;
    }

    public String getXmlType() {
        return xmlType;
    }

    public String getQueueName() {
        return queueName;
    }

}
