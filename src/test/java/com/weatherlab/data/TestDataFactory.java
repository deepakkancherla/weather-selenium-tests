package com.weatherlab.data;

import java.util.Locale;
import java.util.UUID;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static TestUser uniqueUser(String caseId) {
        String suffix = UUID.randomUUID().toString().substring(0, 8).toLowerCase(Locale.ROOT);
        String normalizedCase = caseId.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
        return new TestUser("selenium+" + normalizedCase + "-" + suffix + "@example.com", "Weather!" + suffix + "9");
    }
}

