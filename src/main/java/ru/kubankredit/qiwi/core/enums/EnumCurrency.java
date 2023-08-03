package ru.kubankredit.qiwi.core.enums;

/**
 * <h1>Основные коды валют</h1>
 * Перечень основных кодов валют из документации в протоколе АО "Киви"
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-26
 */
public enum EnumCurrency {
    /**
     * Российский рубль (Код 643)
     */
    RUSSIAN_RUBLE(643, "Российский рубль"),
    /**
     * Доллар США (Код 840)
     */
    US_DOLLAR(840, "Доллар США"),
    /**
     * Евро (Код 978)
     */
    EURO(978, "Евро"),
    /**
     * Украинская гривна (Код 980)
     */
    UKRAINIAN_HRYVNIA(980, "Украинская гривна"),
    /**
     * Грузинский лари (Код 981)
     */
    GEORGIAN_LARI(981, "Грузинский лари"),
    /**
     * Таджикский сомони (Код 972)
     */
    TAJIK_SOMONI(972, "Таджикский сомони");

    private final int code;

    private final String description;

    /**
     * Конструктор кода валют
     *
     * @param code        Код валюты.
     * @param description Наименование валюты.
     */
    EnumCurrency(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Возвращает код валюты в виде числа
     *
     * @return Код валюты.
     */
    public int getIntCode() {
        return code;
    }

    /**
     * Возвращает код валюты в виде строки
     *
     * @return Код валюты.
     */
    public String getStringCode() {
        return String.valueOf(code);
    }

    /**
     * Возвращает наименование валюты
     *
     * @return Наименование валюты.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает наименование валюты
     *
     * @return Наименование валюты.
     */
    @Override
    public String toString() {
        switch (this) {
            case RUSSIAN_RUBLE:
                return "Российский рубль";
            case US_DOLLAR:
                return "Доллар США";
            case EURO:
                return "Евро";
            case UKRAINIAN_HRYVNIA:
                return "Украинская гривна";
            case GEORGIAN_LARI:
                return "Грузинский лари";
            case TAJIK_SOMONI:
                return "Таджикский сомони";
            default:
                return super.toString();
        }
    }

}
