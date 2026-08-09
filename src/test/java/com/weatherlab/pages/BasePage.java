package com.weatherlab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected By testId(String value) {
        return By.cssSelector("[data-testid='" + value + "']");
    }

    protected WebElement visible(String testId) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(testId(testId)));
    }

    protected WebElement clickable(String testId) {
        return wait.until(ExpectedConditions.elementToBeClickable(testId(testId)));
    }

    protected void replaceText(String testId, String value) {
        WebElement field = visible(testId);
        field.clear();
        field.sendKeys(value);
    }
}

