package ru.kubankredit.qiwi.core.runtime;

import ru.kubankredit.qiwi.core.Core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <h2>Класс динамики с использованием рефлексии</h2>
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-07-24
 */
public class Dynamics {

    protected static final String thisClassName = Dynamics.class.getName();

    /**
     * Найти поле у класса
     *
     * @param object    Экземпляр класса
     * @param fieldName Наименование поля
     * @return True если найдено, иначе false
     */
    public static boolean findField(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        try {
            clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * Установить для приватного поля значение через сеттер метод используя рефлексию
     *
     * @param object     Экземпляр класса
     * @param fieldName  Наименование поля
     * @param fieldValue Значения для поля
     */
    public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Class<?> clazz = object.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            Object convertedValue = castValue(fieldValue, field.getType());
            Method setterMethod = clazz.getDeclaredMethod("set" + capitalizeFirstLetter(fieldName), field.getType());
            setterMethod.invoke(object, convertedValue);
        } catch (Exception e) {
            Core.lm.error(thisClassName, methodName, "Cannot access or invoke the setter method.");
        }
    }

    /**
     * Сделать первую букву строки заглавной
     *
     * @param string Строка, в которой необходимо сделать первую букву заглавной
     * @return Изменённая строка
     */
    private static String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    /**
     * Преобразование типов из одного в другой для динамики
     *
     * @param fieldValue Значение для поля
     * @param fieldType  Тип поля (класс)
     * @param <T>        Тип класс, неопределённый, абстрактный
     * @return Новый класс типа поля исходя из конвертации от значения поля
     * @throws Exception Ошибка преобразования
     */
    private static <T> T castValue(Object fieldValue, Class<T> fieldType) throws Exception {
        Class<?> fieldValueClass = fieldValue.getClass();
        if (fieldValueClass.getName().equalsIgnoreCase("java.lang.String")) {
            switch (fieldType.getName()) {
                case "java.lang.String":
                    return fieldType.cast(fieldValue);
                case "java.lang.Integer":
                    return fieldType.cast(Integer.parseInt(String.valueOf(fieldValue)));
                case "java.lang.Boolean":
                    if (String.valueOf(fieldValue).equalsIgnoreCase("0") ||
                            String.valueOf(fieldValue).equalsIgnoreCase("false")) {
                        return fieldType.cast(false);
                    } else {
                        return fieldType.cast(true);
                    }
                default:
                    throw new Exception("Unhandled conversion error");
            }
        } else if (fieldValueClass.getName().equalsIgnoreCase("java.lang.Boolean")) {
            switch (fieldType.getName()) {
                case "java.lang.String":
                    return fieldType.cast(String.valueOf(fieldValue));
                case "java.lang.Integer":
                    if ((Boolean) fieldValue) {
                        return fieldType.cast(1);
                    } else {
                        return fieldType.cast(0);
                    }
                case "java.lang.Boolean":
                    return fieldType.cast(fieldValue);
                default:
                    throw new Exception("Unhandled conversion error");
            }
        } else if (fieldValueClass.getName().equalsIgnoreCase("java.lang.Integer")) {
            switch (fieldType.getName()) {
                case "java.lang.String":
                    return fieldType.cast(String.valueOf(fieldValue));
                case "java.lang.Integer":
                    return fieldType.cast(Integer.parseInt(String.valueOf(fieldValue)));
                case "java.lang.Boolean":
                    if ((Integer) fieldValue == 0) {
                        return fieldType.cast(false);
                    } else {
                        return fieldType.cast(true);
                    }
                default:
                    throw new Exception("Unhandled conversion error");
            }
        }
        throw new Exception("Unhandled FieldValue Type Error");
    }

}
