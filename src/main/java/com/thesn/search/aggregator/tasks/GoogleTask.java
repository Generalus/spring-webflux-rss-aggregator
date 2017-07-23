package com.thesn.search.aggregator.tasks;

import com.thesn.search.aggregator.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

public class GoogleTask implements Task {
    public static final String URL = "http://www.google.com";
    private static WebDriver driver = SeleniumDriver.get();

    public static void main(String[] args) {
        new GoogleTask().find("тест");
    }

    public Optional<String> find(String query) {

        // And now use this to visit Google
        driver.get(URL);
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys(query);

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(d -> d.getTitle().startsWith(query));

        return Optional.of(driver.getTitle()).filter(d -> d.startsWith(query));
    }
}
