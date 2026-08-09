package com.weatherlab.tests;

import com.weatherlab.data.TestUser;
import com.weatherlab.pages.DashboardPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("session")
class SessionTests extends BaseUiTest {
    @Test
    void session001_refreshKeepsTheUserSignedIn() {
        TestUser user = createApiUser("SESSION-001");
        logIn(user);

        driver.navigate().refresh();

        assertTrue(new DashboardPage(driver).waitUntilLoaded().weatherResultText().contains("Austin"));
    }
}
