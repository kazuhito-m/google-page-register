package com.github.kazuhito_m.googlepageregister.webbrothercontrol;

import com.github.kazuhito_m.googlepageregister.webbrothercontrol.googleaccount.Account;
import com.github.kazuhito_m.googlepageregister.webbrothercontrol.googleaccount.AccountSelector;
import com.github.kazuhito_m.googlepageregister.webbrothercontrol.imagerecognition.AvoidRoughlyClicker;
import com.github.kazuhito_m.googlepageregister.webbrothercontrol.selenium.WebDriverSelector;
import com.github.kazuhito_m.googlepageregister.webbrothercontrol.selenium.WebDriverWrapper;
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

    @Value("${settings.resumeIndex}")
    private int resumeIndex;

    @Value("${settings.accountChangeInterval}")
    private int accountChangeInterval;

    public void register(List<URL> links) throws IOException, InterruptedException {
        WebDriverWrapper driverWrapper = null;
        try {
            driverWrapper = new WebDriverWrapper(new WebDriverSelector());
            operation(links, driverWrapper);
        } finally {
            if (driverWrapper != null) driverWrapper.quit();
        }
    }

    private void operation(List<URL> links, WebDriverWrapper driverWrapper) throws IOException {
        int i = 0;
        for (URL articleLinkUrl : links) {
            if (resumeIndex > i) continue;  // 途中からの場合は、飛ばす
            if (i == resumeIndex || i % accountChangeInterval == 0) {
                logger.info(String.format("login:%d", i));
                driverWrapper.initialize();
                login(accountSelector.next(), i, driverWrapper.driver(), driverWrapper.driverWait());
            }
            logger.info(String.format("registerUrl:%d,%s", i, articleLinkUrl.toString()));
            registerUrlForGoogle(articleLinkUrl, i++, driverWrapper.driver(), driverWrapper.driverWait());
        }
    }

    private void registerUrlForGoogle(URL articleLinkUrl, int index, WebDriver driver, WebDriverWait wait) throws IOException {
        driver.get("https://www.google.com/webmasters/tools/submit-url");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("urlnt")));

        WebElement urlInput = driver.findElement(By.name("urlnt"));
        urlInput.sendKeys(articleLinkUrl.toString());

        avoidRoughlyClicker.searchAndClick();

        WebElement submitButton = driver.findElement(By.name("submitButton"));
        submitButton.click();

        // 登録完了まで待つ
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("status-message-text")));
    }

    private void login(Account googleAcount, int index, WebDriver driver, WebDriverWait wait) {
        driver.get("https://www.google.com/accounts/Login?hl=ja");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("identifierId")));

        WebElement emailInput = driver.findElement(By.id("identifierId"));
        emailInput.sendKeys(googleAcount.gmail());

        WebElement nextButton = driver.findElement(By.id("identifierNext"));
        nextButton.click();

        // アニメーションが終わるまで、少し待つ。
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("profileIdentifier")));

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(googleAcount.password());

        WebElement passNextButton = driver.findElement(By.id("passwordNext"));
        passNextButton.click();

        // ログイン完了まで待つ
        wait.until(ExpectedConditions.titleContains("アカウント情報"));
    }

    private final AvoidRoughlyClicker avoidRoughlyClicker;
    private final AccountSelector accountSelector;

    public GoogleUrlRegister(AccountSelector accountSelector, AvoidRoughlyClicker avoidRoughlyClicker) {
        this.accountSelector = accountSelector;
        this.avoidRoughlyClicker = avoidRoughlyClicker;
    }

}
