package com.weatherlab.tests;

import com.weatherlab.data.FirebaseTestDataClient;
import com.weatherlab.data.TestUser;
import com.weatherlab.driver.DriverFactory;
import com.weatherlab.driver.DriverSession;
import com.weatherlab.pages.DashboardPage;
import com.weatherlab.pages.LoginPage;
import com.weatherlab.support.FailureEvidenceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(FailureEvidenceExtension.class)
abstract class BaseUiTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected final FirebaseTestDataClient firebase = new FirebaseTestDataClient();
    private final List<TestUser> usersToDelete = new ArrayList<>();

    @BeforeEach
    void startBrowser() {
        driver = DriverFactory.create();
        DriverSession.set(driver);
        loginPage = new LoginPage(driver).open();
    }

    @AfterEach
    void cleanUp() {
        try {
            usersToDelete.forEach(firebase::deleteUser);
        } finally {
            if (driver != null) {
                driver.quit();
            }
            DriverSession.clear();
        }
    }

    protected TestUser createApiUser(String caseId) {
        TestUser user = firebase.createUser(caseId);
        rememberForCleanup(user);
        return user;
    }

    protected void rememberForCleanup(TestUser user) {
        usersToDelete.add(user);
    }

    protected DashboardPage logIn(TestUser user) {
        loginPage.login(user.email(), user.password());
        return new DashboardPage(driver).waitUntilLoaded();
    }
}
