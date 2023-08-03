package ru.kubankredit.qiwi.core.crypt;

import ru.kubankredit.qiwi.core.Core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

/**
 * <h1>Крипто модуль для QIWI системы</h1>
 * Модуль позволяет подписывать и верифицировать данные по интеграции с QIWI,
 * и возвращать в закодированном формате Base64
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-06-20
 */
public class CryptoModule {

    protected final EncodingUtils encodingUtils = new EncodingUtils();

    final protected String thisClassName = this.getClass().getName();

    /**
     * Подписывание данных с помощью закрытого ключа при помощи SHA1
     * и кодируем в BASE64
     *
     * @param data    Данные, которые необходимо подписать
     * @param keyFile Закрытый ключ, которым подписываем (путь до файла)
     * @return Зашифрованную строку, обёрнутую в BASE64
     * @throws InvalidKeyException Неверно сформированный ключ или ошибка чтения
     * @throws Exception           Ошибка общего характера
     * @author Листопадов Александр Сергеевич
     * @since 2023-06-20
     */
    public String sign(String data, String keyFile) throws InvalidKeyException, Exception {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Core.lm.trace(thisClassName, methodName, "Signed value: " + Arrays.toString(data.getBytes(StandardCharsets.UTF_8)));
        Signature rsa = Signature.getInstance("SHA1WithRSA");
        rsa.initSign(loadDecryptionKey(keyFile));
        rsa.update(data.getBytes(StandardCharsets.UTF_8));

        byte[] signature = rsa.sign();

        return encodingUtils.base64Encode(signature);
    }

    /**
     * Подписывание данных с помощью закрытого ключа при помощи SHA1
     * и кодируем в BASE64
     *
     * @param data     Данные, которые необходимо подписать
     * @param keyBytes Закрытый ключ, которым подписываем (байт массив)
     * @return Зашифрованную строку, обёрнутую в BASE64
     * @throws InvalidKeyException Неверно сформированный ключ или ошибка чтения
     * @throws Exception           Ошибка общего характера
     * @author Листопадов Александр Сергеевич
     * @since 2023-06-20
     */
    public String sign(String data, byte[] keyBytes) throws InvalidKeyException, Exception {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Core.lm.trace(thisClassName, methodName, "Signed value: " + Arrays.toString(data.getBytes(StandardCharsets.UTF_8)));
        Signature rsa = Signature.getInstance("SHA1WithRSA");
        rsa.initSign(loadDecryptionKey(keyBytes));
        rsa.update(data.getBytes(StandardCharsets.UTF_8));

        byte[] signature = rsa.sign();


        return encodingUtils.base64Encode(signature);
    }

    /**
     * Create a PrivateKey instance from raw PKCS#8 bytes.
     *
     * @param pkcs8Bytes ByteData for the PKCS#8 Private key
     * @return PrivateKey object
     * @throws GeneralSecurityException Unhandled security exception
     * @author Листопадов Александр Сергеевич
     * @since 2023-06-20
     */
    private PrivateKey readPkcs8PrivateKey(byte[] pkcs8Bytes) throws GeneralSecurityException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Bytes);
        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException("Unexpected key format!", e);
        }
    }

    /**
     * Create a PrivateKey instance from raw PKCS#1 bytes.
     *
     * @param pkcs1Bytes ByteData for the PKCS#1 Private key
     * @return PrivateKey object
     * @throws GeneralSecurityException Unhandled security exception
     * @author Листопадов Александр Сергеевич
     * @since 2023-06-20
     */
    private PrivateKey readPkcs1PrivateKey(byte[] pkcs1Bytes) throws GeneralSecurityException {

        ByteUtils byteUtils = new ByteUtils();
        // We can't use Java internal APIs to parse ASN.1 structures, so we build a PKCS#8 key Java can understand
        int pkcs1Length = pkcs1Bytes.length;
        int totalLength = pkcs1Length + 22;
        byte[] pkcs8Header = new byte[]{
                0x30, (byte) 0x82, (byte) ((totalLength >> 8) & 0xff), (byte) (totalLength & 0xff), // Sequence + total length
                0x2, 0x1, 0x0, // Integer (0)
                0x30, 0xD, 0x6, 0x9, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0xD, 0x1, 0x1, 0x1, 0x5, 0x0, // Sequence: 1.2.840.113549.1.1.1, NULL
                0x4, (byte) 0x82, (byte) ((pkcs1Length >> 8) & 0xff), (byte) (pkcs1Length & 0xff) // Octet string + length
        };
        byte[] pkcs8bytes = byteUtils.concat(pkcs8Header, pkcs1Bytes);
        return readPkcs8PrivateKey(pkcs8bytes);
    }

    /**
     * Generates a PKCS#8 PrivateKey from an array of bytes
     *
     * @param keyDataBytes Byte array data for the private key
     * @return PrivateKey object
     * @throws GeneralSecurityException Unhandled security exception
     * @author Листопадов Александр Сергеевич
     * @since 2023-06-20
     */
    public PrivateKey generatePrivateKey(byte[] keyDataBytes) throws GeneralSecurityException {
        EncodingUtils encodingUtils = new EncodingUtils();
        String keyDataString = new String(keyDataBytes, StandardCharsets.UTF_8);

        // Переменные для PKCS#1, чтобы исключить их при разборе с ключом в методах
        String PKCS_1_PEM_HEADER = "-----BEGIN RSA PRIVATE KEY-----";
        if (keyDataString.contains(PKCS_1_PEM_HEADER)) {
            // OpenSSL / PKCS#1 Base64 PEM encoded file
            keyDataString = keyDataString.replace(PKCS_1_PEM_HEADER, "");
            String PKCS_1_PEM_FOOTER = "-----END RSA PRIVATE KEY-----";
            keyDataString = keyDataString.replace(PKCS_1_PEM_FOOTER, "");
            keyDataString = keyDataString.replace("\n", "");
            keyDataString = keyDataString.replace("\r", "");
            keyDataString = keyDataString.replace("\r\n", "");
            return readPkcs1PrivateKey(encodingUtils.base64Decode(keyDataString));
        }

        String PKCS_8_PEM_HEADER = "-----BEGIN PRIVATE KEY-----";
        if (keyDataString.contains(PKCS_8_PEM_HEADER)) {
            // PKCS#8 Base64 PEM encoded file
            keyDataString = keyDataString.replace(PKCS_8_PEM_HEADER, "");
            String PKCS_8_PEM_FOOTER = "-----END PRIVATE KEY-----";
            keyDataString = keyDataString.replace(PKCS_8_PEM_FOOTER, "");
            keyDataString = keyDataString.replace("\n", "");
            keyDataString = keyDataString.replace("\r", "");
            keyDataString = keyDataString.replace("\r\n", "");
            return readPkcs8PrivateKey(encodingUtils.base64Decode(keyDataString));
        }

        // We assume it's a PKCS#8 DER encoded binary file
        return readPkcs8PrivateKey(keyDataBytes);
    }

    /**
     * Loads a RSA decryption key from a file (KEY, PEM or DER) and converts it to a byte[] array.
     *
     * @param keyFilePath Path to the private key file
     * @return PrivateKey object
     * @throws GeneralSecurityException Unhandled security exception
     * @author Листопадов Александр Сергеевич
     * @since 2023-06-20
     */
    public PrivateKey loadDecryptionKey(String keyFilePath) throws GeneralSecurityException, IOException {
        Path path = Paths.get(keyFilePath);
        byte[] keyDataBytes = Files.readAllBytes(path);

        return generatePrivateKey(keyDataBytes);
    }

    /**
     * Loads a RSA decryption key from a byte[] array (KEY, PEM or DER).
     *
     * @param keyDataBytes Byte array data for the private key
     * @return PrivateKey object
     * @throws GeneralSecurityException Unhandled security exception
     * @author Листопадов Александр Сергеевич
     * @since 2023-06-20
     */
    public PrivateKey loadDecryptionKey(byte[] keyDataBytes) throws GeneralSecurityException {
        return generatePrivateKey(keyDataBytes);
    }

}
