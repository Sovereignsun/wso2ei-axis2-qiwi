package ru.kubankredit.qiwi;

import org.junit.Test;
import ru.kubankredit.qiwi.core.crypt.HashingModule;
import ru.kubankredit.qiwi.core.crypt.RSAModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class QIWIModuleTest {

    @Test
    public void testMD5Hash() {

        try {
            HashingModule hashingModule = new HashingModule();

            // Hash data using MD5
            String dataToHash = "person_password";
            String testValue = "e8cfdbdfc718b22489329048555fb320";
            String resultValue = hashingModule.HashMD5(dataToHash);

            assertEquals(testValue, resultValue);

        } catch (AssertionError e) {
            fail("Test failed: Result does not equal test value");
        } catch (Exception e) {
            fail("Test failed");
        }
    }

    @Test
    public void testSHA1andBASE64() {

        try {

            String privateKey =
                    "-----BEGIN RSA PRIVATE KEY-----\n" +
                            "MIICXgIBAAKBgQC3bXVsHI4LjHe05gAr+1DeKzQjRsmb/eRsCqdSNletOJkRzwBH\n" +
                            "MY0brx+utqkNFjUifLMGLJAaX+PVnj07kDXTjpdFO9aFXnh/KCkr3zXgRr7FY2pM\n" +
                            "Esr3dhK1QIYrkHUW+fgbH4qRU7vucGEIm2GyWHFEtqYIz0mQ0e/oDQUIgwIDAQAB\n" +
                            "AoGBAJ7kl9ouJjIE3VF8B9wRmSzLWh/Q2yZg2jVpSSYjYvHv3FEb8s65MtYvbo+H\n" +
                            "NYvbl+2m/eG0SqQ+Onu61qo5MxX1iYAPCoGUef0OgSGd7cGH+OBMKgLoOSl+hBVl\n" +
                            "01US3t9hyVYZKkEeYEiGPM8sHU8uA3/6ULbcRA29Fk1MHePZAkEA8WbTPh9TgXz+\n" +
                            "hSiT8fz2ay+lLc6OesKimRBXFC1/0rfAZojUyNuTb0HK3WlK+vk1yhbz62Clp6iJ\n" +
                            "YZy7tTIVHwJBAMKFIMnzRkSgjRW1zjEb25PprBf2aOAx/XbzfxZcKoLu+K3rWGs3\n" +
                            "r89yvnxbO6nCI9efP/9flyAFqyiuJJuO3B0CQQDUF6+JaSSJJDTK8XfqqIIoclgC\n" +
                            "AvUTzmXlbYHUbJVdoawdaiLpxmGm4ntGRPhnwQUNZVUxfFyPDXiGlpok/LepAkEA\n" +
                            "kyoBCoigyt7knl4zpve6ggcxideEtMldR5ckuRf1maWOQpZhCIzS2BGph4/GbcJR\n" +
                            "N5pl4qDBRxrCyT4muAG3CQJAfBRBD3XJ/rVY6kRwmPNXYogAepWNU1OAmCOwAVt5\n" +
                            "LWZnxbo7BsZPYZ4ZlIu0IelsBqyodPlC4RT9V8EHFVsoHw==\n" +
                            "-----END RSA PRIVATE KEY-----\n";

            String signedData = "ClXn4wZHkrMEttl/t3Mj/AJRS87ggjLxmDRB98/JX+RvzFrnfF/JPUNX7NCYBEbVyvFNTg/gGQd/R1rfXNIqx1YM+lBUbnAtz7a4GrlmL8QIY6Ev+he3bhz1a4g4k9eJWAN9Jil9A7p1IvpO2PuekxaalUHWJaIe2cJTuSrdg+c=";

            // XML Payload to Sign

            String dataToSign = "<?xml version=\"1.0\" encoding=\"windows-1251\"?><request><client terminal=\"10788430\" software=\"Dealer v0\"/><agents><getBalance/></agents></request>";

            RSAModule rsam = new RSAModule(dataToSign, privateKey.getBytes());

            assertEquals(signedData, rsam.getSignedData());

        } catch (AssertionError e) {
            fail("Test failed: Result does not equal test value");
        } catch (Exception e) {
            fail("Test failed");
        }
    }
}
