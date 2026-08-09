package com.weatherlab.pages;

import com.weatherlab.config.TestConfig;
import com.weatherlab.data.TestUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(TestConfig.applicationUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(testId("login-submit")));
        return this;
    }

    public LoginPage chooseRegistration() {
        clickable("auth-register-tab").click();
        visible("register-confirm-password");
        return this;
    }

    public void register(TestUser user, String confirmation) {
        replaceText("login-email", user.email());
        replaceText("login-password", user.password());
        replaceText("register-confirm-password", confirmation);
        clickable("login-submit").click();
    }

    public void login(String email, String password) {
        replaceText("login-email", email);
        replaceText("login-password", password);
        clickable("login-submit").click();
    }

    public void submitEmptyLogin() {
        clickable("login-submit").click();
    }

    public String errorMessage() {
        return visible("auth-error").getText();
    }

    public boolean isDisplayed() {
        return visible("login-submit").isDisplayed();
    }
}

