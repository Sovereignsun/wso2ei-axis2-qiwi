package ru.kubankredit.qiwi.core.crypt;

/**
 * <h1>Крипто модуль для QIWI системы</h1>
 * Модуль позволяет подписывать и верифицировать данные по интеграции с QIWI,
 * а также шифровать данные в BASE64.
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-04-28
 */
public class RSAModule {

    protected final CryptoModule cryptoModule = new CryptoModule();
    private final String signedData;

    //The constructor of Message class builds the list that will be written to the file.
    //The list consists of the message and the signature.
    public RSAModule(String data, String keyFile) throws Exception {
        signedData = cryptoModule.sign(data, keyFile);
    }

    public RSAModule(String data, byte[] keyBytes) throws Exception {
        if (keyBytes.length == 0) {
            throw new Exception("Cannot initialize the byte array");
        }
        signedData = cryptoModule.sign(data, keyBytes);
    }

    public String getSignedData() {
        return signedData;
    }

}
