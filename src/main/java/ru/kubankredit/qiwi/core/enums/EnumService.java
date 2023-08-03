package ru.kubankredit.qiwi.core.enums;

/**
 * <h1>Сервисы</h1>
 * Перечень сервисов платёжного центра доступных для обращения
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-26
 */
public enum EnumService {

    GatePayments("GatePayments"),
    GateContract("GateContract");

    private final String name;

    /**
     * Конструктор сервиса
     *
     * @param name Наименование сервиса
     */
    EnumService(String name) {
        this.name = name;
    }

    /**
     * Возвращает наименование сервиса платёжного центра
     *
     * @return Наименование сервиса.
     */
    public String getName() {
        return name;
    }
}
