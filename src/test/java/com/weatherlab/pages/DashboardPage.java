package com.weatherlab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public final class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage waitUntilLoaded() {
        visible("weather-result");
        return this;
    }

    public DashboardPage waitForFavoritesReady() {
        wait.until(ExpectedConditions.attributeToBe(testId("favorites-section"), "aria-busy", "false"));
        clickable("toggle-favorite");
        return this;
    }

    public void search(String query) {
        replaceText("city-search", query);
        clickable("city-search-submit").click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(testId("weather-result"), "Austin"));
    }

    public String weatherResultText() {
        return visible("weather-result").getText();
    }

    public String currentTemperature() {
        return visible("current-temperature").getText();
    }

    public boolean searchFieldIsRequired() {
        return Boolean.parseBoolean(visible("city-search").getAttribute("required"));
    }

    public void submitEmptySearch() {
        visible("city-search").clear();
        clickable("city-search-submit").click();
    }

    public String searchMessage() {
        return visible("search-message").getText();
    }

    public void toggleFavorite() {
        clickable("toggle-favorite").click();
    }

    public String favoriteButtonText() {
        return clickable("toggle-favorite").getText();
    }

    public void waitForFavoriteButton(String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(testId("toggle-favorite"), text));
    }

    public List<WebElement> favoriteCards() {
        return visible("favorites-list").findElements(By.cssSelector(".favorite-card"));
    }

    public boolean emptyFavoritesIsVisible() {
        return visible("favorites-empty").isDisplayed();
    }

    public void removeFavorite(String city) {
        String selector = "[aria-label='Remove " + city + " from favorites']";
        By removeButton = By.cssSelector(selector);
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        wait.until(ignored -> Boolean.TRUE.equals(javascript.executeScript("""
                const button = document.querySelector(arguments[0]);
                if (!button || button.disabled) return false;
                button.scrollIntoView({block: 'center', behavior: 'instant'});
                button.click();
                return true;
                """, selector)));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(removeButton));
    }

    public LoginPage logout() {
        clickable("profile-menu").click();
        clickable("logout-button").click();
        return new LoginPage(driver);
    }
}
