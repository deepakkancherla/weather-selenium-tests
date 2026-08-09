package com.weatherlab.config;

public final class TestConfig {
    private static final String DEFAULT_BASE_URL = "https://weather-app-nine-vert-81.vercel.app";
    private static final String DEFAULT_FIREBASE_API_KEY = "AIzaSyB1wphW8lKc9oZghsk6S7i8sRuFkg-03fU";

    private TestConfig() {
    }

    public static String baseUrl() {
        return read("baseUrl", "TEST_BASE_URL", DEFAULT_BASE_URL).replaceAll("/+$", "");
    }

    public static String applicationUrl() {
        String separator = baseUrl().contains("?") ? "&" : "?";
        return baseUrl() + separator + "weatherMode=mock";
    }

    public static String browser() {
        return read("browser", "TEST_BROWSER", "chrome").toLowerCase();
    }

    public static boolean headless() {
        return Boolean.parseBoolean(read("headless", "TEST_HEADLESS", "true"));
    }

    public static String firebaseApiKey() {
        return read("firebaseApiKey", "FIREBASE_WEB_API_KEY", DEFAULT_FIREBASE_API_KEY);
    }

    private static String read(String systemProperty, String environmentVariable, String defaultValue) {
        String propertyValue = System.getProperty(systemProperty);
        if (propertyValue != null && !propertyValue.isBlank() && !propertyValue.startsWith("${")) {
            return propertyValue;
        }
        String environmentValue = System.getenv(environmentVariable);
        return environmentValue == null || environmentValue.isBlank() ? defaultValue : environmentValue;
    }
}

