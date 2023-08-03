package ru.kubankredit.qiwi.core.enums;

/**
 * <h1>Онлайн статусы платежа ПЦ</h1>
 * Перечень онлайн статусов платежа ПЦ
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-27
 */
public enum EnumOnlineStatus {
    /**
     * Неопределён (не онлайн) (Код 0)
     */
    ONLINE_PMNT_EMPTY(0, "Неопределён"),
    /**
     * В обработке (Код 1)
     */
    ONLINE_PMNT_WORK(1, "В обработке"),
    /**
     * Обработан (Код 2)
     */
    ONLINE_PMNT_GOOD(2, "Обработан"),
    /**
     * Ошибка (Код 3)
     */
    ONLINE_PMNT_ERROR(3, "Ошибка");

    private final int status;
    private final String name;

    /**
     * Конструктор онлайн статуса платежа ПЦ
     *
     * @param status Код статуса.
     * @param name   Наименование статуса.
     */
    EnumOnlineStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    /**
     * Возвращает код онлайн статуса
     *
     * @return Код онлайн статуса.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Возвращает наименование онлайн статуса.
     *
     * @return Наименование онлайн статуса.
     */
    public String getName() {
        return name;
    }
}

