package com.github.kazuhito_m.googlepageregister.webbrothercontrol;

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
        WebDriverWait wait = new WebDriverWait(driver, 5);

        login(driver, wait);


        URL articleLinkUrl = links.get(0);

        driver.get("https://www.google.com/webmasters/tools/submit-url");

        WebElement urlInput = driver.findElement(By.name("urlnt"));
        urlInput.sendKeys(articleLinkUrl.toString());

        WebElement robotAvoidanceCheck = driver.findElement(By.cssSelector("div.recaptcha-checkbox-checkmark"));
        robotAvoidanceCheck.click();

        // TODO 画像認識＆クリック
        Thread.sleep(120000);

        driver.quit();
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
}
