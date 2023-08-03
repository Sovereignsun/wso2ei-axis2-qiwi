package ru.kubankredit.qiwi.core.crypt;

import ru.kubankredit.qiwi.core.Core;

import java.util.Arrays;
import java.util.Base64;

/**
 * <h1>Модуть кодирования для QIWI системы</h1>
 * Модуль позволяет кодировать в Base64 и декодировать из Base64 данные по интеграции с QIWI,
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-03-21
 */
public class EncodingUtils {

    final protected String thisClassName = this.getClass().getName();

    public EncodingUtils() {
    }

    public String hexEncode(byte[] bytes) {
        if (null == bytes) {
            throw new IllegalArgumentException("Can't hex encode a null value!");
        }
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    public byte[] hexDecode(String value) {
        if (null == value) {
            throw new IllegalArgumentException("Can't hex decode a null value!");
        }
        if ("".equals(value)) {
            return new byte[0];
        }
        if (!value.matches("\\p{XDigit}+")) {
            throw new IllegalArgumentException("The provided value is not an hex string!");
        }
        int length = value.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4) + Character.digit(value.charAt(i + 1), 16));
        }
        return bytes;
    }

    /**
     * Раскодирование строки из Base64 в байт массив
     *
     * @param value Строку в Base64, которую требуется раскодировать
     * @return Байт массив, который был закодирован при помощи Base64
     * @author Листопадов Александр Сергеевич
     * @since 2023-03-21
     */
    public byte[] base64Decode(String value) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        if (null == value) {
            throw new IllegalArgumentException("Can't base64 decode a null value!");
        }
        try {
            Core.lm.trace(thisClassName, methodName, "Base64Decode value: " + value);
            return Base64.getDecoder().decode(value);
        } catch (Exception ex) {
            return Base64.getUrlDecoder().decode(value);
        }
    }

    /**
     * Кодирование байт массива в Base64
     *
     * @param bytes Байт массив, который требуется кодировать при помощи Base64
     * @return Зашифрованную строку, обёрнутую в Base64
     * @author Листопадов Александр Сергеевич
     * @since 2023-03-21
     */
    public String base64Encode(byte[] bytes) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        if (null == bytes) {
            throw new IllegalArgumentException("Can't base64 encode a null value!");
        }
        Core.lm.trace(thisClassName, methodName, "Base64Encode value: " + Arrays.toString(bytes));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Кодирование байт массива в Base64, но без добавления дополнительных символов в конце закодированных байтовых данных
     *
     * @param bytes Байт массив, который требуется кодировать при помощи Base64
     * @return Зашифрованную строку, обёрнутую в Base64
     * @author Листопадов Александр Сергеевич
     * @since 2023-03-21
     */
    public String base64EncodeWithoutPadding(byte[] bytes) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Core.lm.trace(thisClassName, methodName, "Base64UrlEncode value: " + Arrays.toString(bytes));
        return Base64.getEncoder().withoutPadding().encodeToString(bytes);
    }
}
