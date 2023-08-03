package ru.kubankredit.qiwi.core.crypt;

import ru.kubankredit.qiwi.core.Core;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h2>Модуль хэширования для QIWI интеграции</h2>
 * Модуль позволяет оборачивать данные в MD5
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-03-21
 */
public class HashingModule {

    final protected String thisClassName = this.getClass().getName();

    /**
     * Метод позволяет хэшировать данные при помощи MD5
     *
     * @param unhashedString Строка, которую нужно захэшировать
     * @return Строка с захэшированными данными
     */
    public String HashMD5(String unhashedString) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        String signAlgorithm = Core.settings.getQiwiSettings().getSignAlgorithm();

        Core.lm.trace(thisClassName, methodName, "HashMD5 initiated");
        try {
            MessageDigest md = MessageDigest.getInstance(signAlgorithm);
            md.update(unhashedString.getBytes());
            byte[] digest = md.digest();
            String result = new BigInteger(1, digest).toString(16);
            Core.lm.trace(thisClassName, methodName, "HashedString: " + result);
            return result;
        } catch (NoSuchAlgorithmException e) {
            Core.lm.error(thisClassName, methodName, "NoSuchAlgorithmException: " + e.getMessage());
        }
        return "";
    }

	/*public static void main(String[] args) {

		// String to MD5 Hash
		String unhashedString = "dm7mr8y4mD";

		System.out.println(unhashedString);

		HashingModule hashingModule = new HashingModule();

		// Hash data using MD5
		String resultValue = hashingModule.HashMD5(unhashedString);

		System.out.println(resultValue);
	}*/

}
