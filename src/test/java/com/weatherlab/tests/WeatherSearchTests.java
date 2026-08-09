package com.weatherlab.tests;

import com.weatherlab.data.TestUser;
import com.weatherlab.pages.DashboardPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("weather")
class WeatherSearchTests extends BaseUiTest {
    @Test
    @Tag("smoke")
    void weather001_citySearchDisplaysWeather() {
        TestUser user = createApiUser("WEATHER-001");
        DashboardPage dashboard = logIn(user);

        dashboard.search("Austin");

        assertTrue(dashboard.weatherResultText().contains("Austin"));
        assertFalse(dashboard.currentTemperature().isBlank());
    }

    @Test
    void weather004_emptySearchExplainsWhatIsNeeded() {
        TestUser user = createApiUser("WEATHER-004");
        DashboardPage dashboard = logIn(user);

        dashboard.submitEmptySearch();

        assertEquals("Enter a city name to search.", dashboard.searchMessage());
    }
}

