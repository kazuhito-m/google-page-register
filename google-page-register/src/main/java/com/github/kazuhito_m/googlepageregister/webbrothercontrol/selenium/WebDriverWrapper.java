package com.github.kazuhito_m.googlepageregister.webbrothercontrol.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class WebDriverWrapper {
    private WebDriver driver;
    private WebDriverWait wait;

    private final WebDriverSelector driverSelector;

    public void initialize() throws IOException {
        quit();
        driver = driverSelector.choice();
        wait = new WebDriverWait(driver, 5);
    }

    public void quit() {
        if (driver != null) driver.quit();
        driver = null;
        wait = null;
    }

    public WebDriver driver() {
        return driver;
    }

    public WebDriverWait driverWait() {
        return wait;
    }

    public WebDriverWrapper(WebDriverSelector driverSelector) throws IOException {
        this.driverSelector = driverSelector;
        this.initialize();
    }
}