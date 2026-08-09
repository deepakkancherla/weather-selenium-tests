package com.weatherlab.tests;

import com.weatherlab.data.TestUser;
import com.weatherlab.pages.DashboardPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("authentication")
class AuthenticationTests extends BaseUiTest {
    @Test
    @Tag("smoke")
    void auth001_registeredUserCanSignIn() {
        TestUser user = createApiUser("AUTH-001");

        DashboardPage dashboard = logIn(user);

        assertTrue(dashboard.weatherResultText().contains("Austin"));
    }

    @Test
    void auth002_wrongPasswordShowsHelpfulError() {
        TestUser user = createApiUser("AUTH-002");

        loginPage.login(user.email(), "Incorrect!999");

        assertEquals("The email address or password is incorrect.", loginPage.errorMessage());
    }

    @Test
    void auth003_emptyFormShowsRequiredEmailError() {
        loginPage.submitEmptyLogin();

        assertEquals("Enter a valid email address to continue.", loginPage.errorMessage());
    }

    @Test
    @Tag("smoke")
    void auth005_userCanSignOut() {
        TestUser user = createApiUser("AUTH-005");
        DashboardPage dashboard = logIn(user);

        assertTrue(dashboard.logout().isDisplayed());
    }
}

