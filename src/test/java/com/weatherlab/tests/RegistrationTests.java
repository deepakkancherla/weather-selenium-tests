package com.weatherlab.tests;

import com.weatherlab.data.TestDataFactory;
import com.weatherlab.data.TestUser;
import com.weatherlab.pages.DashboardPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("registration")
class RegistrationTests extends BaseUiTest {
    @Test
    @Tag("smoke")
    void reg001_newVisitorCanCreateAnAccount() {
        TestUser user = TestDataFactory.uniqueUser("REG-001");
        rememberForCleanup(user);

        loginPage.chooseRegistration().register(user, user.password());

        assertTrue(new DashboardPage(driver).waitUntilLoaded().weatherResultText().contains("Austin"));
    }

    @Test
    void reg003_registrationRejectsInvalidEmail() {
        loginPage.chooseRegistration().register(new TestUser("not-an-email", "Weather!123"), "Weather!123");

        assertEquals("Enter a valid email address to continue.", loginPage.errorMessage());
    }

    @Test
    void reg004_registrationRejectsShortPassword() {
        loginPage.chooseRegistration().register(new TestUser("qa@example.com", "12345"), "12345");

        assertEquals("Your password must contain at least 6 characters.", loginPage.errorMessage());
    }

    @Test
    void reg005_registrationRejectsMismatchedPasswords() {
        loginPage.chooseRegistration().register(new TestUser("qa@example.com", "Weather!123"), "Different!123");

        assertEquals("The passwords do not match. Try entering them again.", loginPage.errorMessage());
    }
}

