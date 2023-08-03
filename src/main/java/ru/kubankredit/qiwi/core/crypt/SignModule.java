package ru.kubankredit.qiwi.core.crypt;

import ru.kubankredit.qiwi.core.Core;

public class SignModule {

    public String SignAndEncode(String XMLPayload) throws Exception {

        try {
            RSAModule rsaModule = new RSAModule(XMLPayload, Core.settings.getPrivateKey());
            return rsaModule.getSignedData();
        } catch (Exception e) {
            throw new Exception("Не удалось подписать запрос");
        }
    }

}
