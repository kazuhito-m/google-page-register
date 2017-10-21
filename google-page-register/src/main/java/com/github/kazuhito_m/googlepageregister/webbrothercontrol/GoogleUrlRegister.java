package com.github.kazuhito_m.googlepageregister.webbrothercontrol;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@PropertySource(value = "classpath:application.yml")
public class GoogleUrlRegister {
    private static Logger logger = LoggerFactory.getLogger(GoogleUrlRegister.class);

    @Value("${google.id}")
    private String googleId;

    @Value("${google.pass}")
    private String googlePass;

    public void register(List<URL> links) throws IOException {
        WebDriver driver = new WebDriverSelector().choice();

        // サンプルソース
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        logger.info("Page title1 is: " + driver.getTitle());
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().startsWith("cheese!");
            }
        });
        logger.info("Page title2 is: " + driver.getTitle());
        driver.quit();
    }
}
