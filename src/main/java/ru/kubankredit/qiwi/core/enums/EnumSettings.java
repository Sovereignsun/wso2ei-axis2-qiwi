package ru.kubankredit.qiwi.core.enums;

/**
 * <h1>Группы настроек</h1>
 * Перечень групп настроек
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-26
 */
public enum EnumSettings {

    GatePayments("GatePayments"),
    GateContract("GateContract"),
    Oracle("Oracle"),
    ActiveMQ("ActiveMQ"),
    Qiwi("Qiwi");

    private final String name;

    /**
     * Конструктор группы настроек
     *
     * @param name Наименование группы настроек в <b>YML</b> файле
     */
    EnumSettings(String name) {
        this.name = name;
    }

    /**
     * Возвращает наименование группы настройки
     *
     * @return Наименование группы настройки
     */
    public String getName() {
        return name;
    }
}
