package com.weatherlab.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestConfigTests {
    @AfterEach
    void clearProperties() {
        System.clearProperty("baseUrl");
        System.clearProperty("vercelAutomationBypassSecret");
    }

    @Test
    void applicationUrlIncludesMockModeWithoutABypassSecret() {
        System.setProperty("baseUrl", "https://preview.example.com/");

        assertEquals(
                "https://preview.example.com?weatherMode=mock",
                TestConfig.applicationUrl());
    }

    @Test
    void applicationUrlEncodesVercelAutomationBypassSecret() {
        System.setProperty("baseUrl", "https://preview.example.com");
        System.setProperty("vercelAutomationBypassSecret", "secret value/+");

        assertEquals(
                "https://preview.example.com?weatherMode=mock"
                        + "&x-vercel-protection-bypass=secret+value%2F%2B"
                        + "&x-vercel-set-bypass-cookie=true",
                TestConfig.applicationUrl());
    }
}
