package com.weatherlab.driver;

import org.openqa.selenium.WebDriver;

public final class DriverSession {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverSession() {
    }

    public static void set(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver get() {
        return DRIVER.get();
    }

    public static void clear() {
        DRIVER.remove();
    }
}

