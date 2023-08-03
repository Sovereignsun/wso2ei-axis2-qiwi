package ru.kubankredit.qiwi.dev;

import org.yaml.snakeyaml.Yaml;
import ru.kubankredit.qiwi.core.runtime.Dynamics;
import ru.kubankredit.qiwi.core.settings.QiwiSettings;
import ru.kubankredit.qiwi.core.settings.Settings;

import java.io.InputStream;
import java.util.Map;

public class DevSettings {

    public static void main(String[] args) {

        InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream("settings.yml");

        QiwiSettings qiwiSettings = new QiwiSettings();

        // Parse YAML contents
        Yaml yaml = new Yaml();
        Map<String, Object> settings = yaml.load(inputStream);

        Map<String, Object> map = (Map<String, Object>) settings.get("Qiwi");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (Dynamics.findField(qiwiSettings, key)) {
                Dynamics.setFieldValue(qiwiSettings, key, value);
            }
        }
    }
}
