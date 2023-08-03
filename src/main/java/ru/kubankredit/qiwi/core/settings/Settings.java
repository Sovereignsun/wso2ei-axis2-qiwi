package ru.kubankredit.qiwi.core.settings;

import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;
import ru.kubankredit.qiwi.core.Core;
import ru.kubankredit.qiwi.core.crypt.RSAModule;
import ru.kubankredit.qiwi.core.enums.EnumService;
import ru.kubankredit.qiwi.core.enums.EnumSettings;
import ru.kubankredit.qiwi.core.runtime.Dynamics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

/**
 * <h2>Модуль настроек для QIWI интеграции</h2>
 * Модуль позволяет получать данные из config.settings по сервису
 *
 * @author Листопадов Александр Сергеевич
 * @version 1.0.0
 * @since 2023-03-21
 */
public class Settings {

    protected final String thisClassName = this.getClass().getName();

    private final OracleSettings oracleSettings = new OracleSettings();
    private final QiwiSettings qiwiSettings = new QiwiSettings();
    private final GateSettings gateContractSettings = new GateSettings();
    private final GateSettings gatePaymentSettings = new GateSettings();

    private final ActiveMQSettings activeMQSettings = new ActiveMQSettings();

    public Settings() {
        this.InitSettings(EnumSettings.Oracle, oracleSettings);
        this.InitSettings(EnumSettings.Qiwi, qiwiSettings);
        this.InitSettings(EnumSettings.GateContract, gateContractSettings);
        this.InitSettings(EnumSettings.GatePayments, gatePaymentSettings);
        this.InitSettings(EnumSettings.ActiveMQ, activeMQSettings);
    }

    public OracleSettings getOracleSettings() {
        return oracleSettings;
    }

    public QiwiSettings getQiwiSettings() {
        return qiwiSettings;
    }

    public GateSettings getGateSettings(EnumService gate) {
        switch (gate) {
            case GatePayments:
                return gatePaymentSettings;
            case GateContract:
                return gateContractSettings;
            default:
                return null;
        }
    }

    public ActiveMQSettings getActiveMQSettings() {
        return activeMQSettings;
    }

    private InputStream getSettingsFile() throws IOException {

        InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream("settings.yml");
        if (inputStream == null) {
            throw new FileNotFoundException("Settings file \"settings.yml\" not found in the classpath");
        }

        return inputStream;
    }

    private void InitSettings(EnumSettings setting, Object object) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        try {
            Yaml yaml = new Yaml();
            Map<String, Object> settings = yaml.load(getSettingsFile());
            Object settingsMap = settings.get(setting.getName());
            Map<String, Object> map;
            if (settingsMap instanceof Map) {
                map = (Map<String, Object>) settingsMap;
            } else {
                Core.lm.error(thisClassName, methodName, "Error converting " + setting.getName() + " to Map<String,Object>");
                return;
            }

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (Dynamics.findField(object, key)) {
                    Dynamics.setFieldValue(object, key, value);
                }
            }
        } catch (IOException e) {
            Core.lm.error(thisClassName, methodName, "Error getting " + setting.getName() + " settings: " + e.getMessage());
        }
    }

    /**
     * Метод позволяет получить приватный ключ из ресурного файла по пути или из файла из директории
     *
     * @return Байт массив данных из приватного ключа
     */
    public byte[] getPrivateKey() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Core.lm.trace(thisClassName, methodName, "GetPrivateKey Init");

        String privateKeyPath = qiwiSettings.getPrivateKeyPath();
        boolean keyIsResource = qiwiSettings.getKeyIsResource();

        Core.lm.trace(thisClassName, methodName, "PrivateKeyPath: " + privateKeyPath);

        byte[] bytes = new byte[0];

        if (keyIsResource) {
            try {
                InputStream inputStream = RSAModule.class.getResourceAsStream(privateKeyPath);
                assert inputStream != null;
                bytes = IOUtils.toByteArray(inputStream);
            } catch (Exception e) {
                Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
            }
            if (bytes.length == 0) {
                return null;
            }
        } else {
            try {
                File privateKeyFile = new File(privateKeyPath);
                bytes = Files.readAllBytes(privateKeyFile.toPath());
            } catch (Exception e) {
                Core.lm.error(thisClassName, methodName, "Exception: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        return bytes;
    }
}
