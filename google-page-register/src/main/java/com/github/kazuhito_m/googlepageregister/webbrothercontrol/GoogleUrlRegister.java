package com.github.kazuhito_m.googlepageregister.webbrothercontrol;

import com.github.kazuhito_m.googlepageregister.webbrothercontrol.imagerecognition.AvoidRoughlyClicker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    public void register(List<URL> links) throws IOException, InterruptedException {
        WebDriver driver = new WebDriverSelector().choice();
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            operation(driver, wait, links);
        } finally {
            driver.quit();
        }
    }

    private void operation(WebDriver driver, WebDriverWait wait, List<URL> links) throws IOException {
        int i = 0;
        login(driver, wait);
        for (URL articleLinkUrl : links) {
            registerUrlForGoogle(driver, wait, articleLinkUrl, i++);
        }
    }

    private void registerUrlForGoogle(WebDriver driver, WebDriverWait wait, URL articleLinkUrl, int index) throws IOException {
        logger.info(String.format("%d,%s", index, articleLinkUrl.toString()));

        driver.get("https://www.google.com/webmasters/tools/submit-url");

        WebElement urlInput = driver.findElement(By.name("urlnt"));
        urlInput.sendKeys(articleLinkUrl.toString());

        avoidRoughlyClicker.searchAndClick();

        WebElement submitButton = driver.findElement(By.name("submitButton"));
        submitButton.click();

        // 登録完了まで待つ
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("status-message-text")));
    }

    private void login(WebDriver driver, WebDriverWait wait) {
        driver.get("https://www.google.com/accounts/Login?hl=ja");

        WebElement emailInput = driver.findElement(By.id("identifierId"));
        emailInput.sendKeys(googleId);

        WebElement nextButton = driver.findElement(By.id("identifierNext"));
        nextButton.click();

        // アニメーションが終わるまで、少し待つ。
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("profileIdentifier")));

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(googlePass);

        WebElement passNextButton = driver.findElement(By.id("passwordNext"));
        passNextButton.click();

        // ログイン完了まで待つ
        wait.until(ExpectedConditions.titleContains("アカウント情報"));
    }

    private final AvoidRoughlyClicker avoidRoughlyClicker;

    public GoogleUrlRegister(AvoidRoughlyClicker avoidRoughlyClicker) {
        this.avoidRoughlyClicker = avoidRoughlyClicker;
    }

}
