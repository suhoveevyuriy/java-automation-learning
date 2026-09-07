package webshop.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "Не знайдено файл config.properties"
                );
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Не вдалося прочитати конфігурацію", e
            );
        }
    }

    private Config() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Не заповнено параметр: " + key
            );
        }

        return value;
    }

    public static String baseUrl() {
        return get("base.url");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static double timeoutMs() {
        return Double.parseDouble(get("timeout.ms"));
    }

    public static String userEmail() {
        return environmentOrProperty("TEST_USER_EMAIL", "user.email");
    }

    public static String userPassword() {
        return environmentOrProperty(
                "TEST_USER_PASSWORD", "user.password"
        );
    }

    private static String environmentOrProperty(
            String environmentName,
            String propertyName
    ) {
        String value = System.getenv(environmentName);

        if (value != null && !value.isBlank()) {
            return value;
        }

        return get(propertyName);
    }
}