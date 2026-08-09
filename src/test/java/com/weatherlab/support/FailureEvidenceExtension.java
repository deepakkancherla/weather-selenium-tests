package com.weatherlab.support;

import com.weatherlab.driver.DriverSession;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FailureEvidenceExtension implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        WebDriver driver = DriverSession.get();
        if (driver != null) {
            capture(driver, safeName(context.getDisplayName()));
        }
        throw throwable;
    }

    private void capture(WebDriver driver, String name) {
        try {
            Path evidence = Path.of("target", "evidence");
            Files.createDirectories(evidence);
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(evidence.resolve(name + ".png"), screenshot);
            Files.writeString(evidence.resolve(name + ".html"), driver.getPageSource(), StandardCharsets.UTF_8);
        } catch (IOException | RuntimeException ignored) {
            // Evidence collection must never hide the original test failure.
        }
    }

    private String safeName(String value) {
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}

