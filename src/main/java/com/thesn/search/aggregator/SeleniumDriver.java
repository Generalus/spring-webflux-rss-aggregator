package com.thesn.search.aggregator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class SeleniumDriver {
    private static WebDriver driver;

    private static boolean isInitialized;

    public static WebDriver get() {
        if(!isInitialized) {
            driver = new FirefoxDriver() {
                @Override
                public void quit() {}
            };
            isInitialized = true;
        }
        return driver;
    }

    private SeleniumDriver() {
    }
}
