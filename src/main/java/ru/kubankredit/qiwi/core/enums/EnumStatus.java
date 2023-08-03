package ru.kubankredit.qiwi.core.enums;

/**
 * <h1>Статусы платежа</h1>
 * Перечень статусов платежа из документации в протоколе АО "Киви"
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-26
 */
public enum EnumStatus {
    /**
     * Ошибка проведения (Код 0 - финальный)
     */
    AUTH_ERROR(0, "Ошибка проведения", true, EnumOnlineStatus.ONLINE_PMNT_ERROR),
    /**
     * В процессе проведения (Код 1 - не финальный)
     */
    IN_PROGRESS(1, "Проводится", false, EnumOnlineStatus.ONLINE_PMNT_WORK),
    /**
     * Проведён (Код 2 - финальный)
     */
    COMPLETED(2, "Проведен", true, EnumOnlineStatus.ONLINE_PMNT_GOOD),
    /**
     * Авторизирован (Код 3 - не финальный)
     */
    AUTHORIZED(3, "Авторизован", false, EnumOnlineStatus.ONLINE_PMNT_WORK);

    private final int status;
    private final String name;
    private final boolean isFinal;

    private final EnumOnlineStatus enumOnlineStatus;

    /**
     * Конструктор статуса
     *
     * @param status  Код статуса.
     * @param name    Наименование статуса.
     * @param isFinal Признак финальности статуса.
     */
    EnumStatus(int status, String name, boolean isFinal, EnumOnlineStatus enumOnlineStatus) {
        this.status = status;
        this.name = name;
        this.isFinal = isFinal;
        this.enumOnlineStatus = enumOnlineStatus;
    }

    /**
     * Возвращает статус при поиске по коду статуса
     *
     * @param status Код статуса
     * @return Объект статуса
     * @throws IllegalArgumentException Исключение недопустимого аргумента при не нахождении статуса по коду
     */
    public static EnumStatus getStatusByCode(int status) {
        for (EnumStatus result : EnumStatus.values()) {
            if (result.status == status) {
                return result;
            }
        }
        throw new IllegalArgumentException("Статус под указанным кодом \"" + status + "\" не найден.");
    }

    /**
     * Возвращает код статуса
     *
     * @return Код статуса.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Возвращает наименование статуса.
     *
     * @return Наименование статуса.
     */
    public String getName() {
        return name;
    }

    /**
     * Возращается булево значение финальности статуса
     *
     * @return True если статус финальный, false если нет.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Возращается булево значение финальности статуса
     *
     * @return True если статус финальный, false если нет.
     */
    public String getIsFinal() {
        if (isFinal) {
            return "Финальный";
        } else {
            return "Не финальный";
        }
    }

    public EnumOnlineStatus getEnumOnlineStatus() {
        return enumOnlineStatus;
    }
}
