package com.github.kazuhito_m.googlepageregister.webbrothercontrol.selenium;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WebDriverSelector {

    private static Logger logger = LoggerFactory.getLogger(WebDriverSelector.class);

    private boolean initialized = false;

    public WebDriver choice() throws IOException {
        if (!initialized) webDriverEnvironmentInitializeByOs();
        return new ChromeDriver();
    }

    private void webDriverEnvironmentInitializeByOs() throws IOException {
        URL webDriverUrlInJar = webDriverUrl();

        // Jar内のファイルでは「実行」出来ない。一時ファイルにコピー・実行権限を与える。
        File tempWebDriverPath = tempWebDriverPath();
        FileUtils.copyURLToFile(webDriverUrlInJar, tempWebDriverPath);
        tempWebDriverPath.setExecutable(true);
        logger.info("Copy WebDriver to executable temp file : " + tempWebDriverPath.getCanonicalPath());

        System.setProperty("webdriver.chrome.driver", tempWebDriverPath.getCanonicalPath());
        initialized = true;
    }

    private URL webDriverUrl() {
        String envType = environmentName();
        String extension = "win32".equals(envType) ? ".exe" : "";
        String webDriverPath = String.format("webdriver/%s/chromedriver%s", envType, extension);
        URL url = this.getClass().getResource(webDriverPath);
        return url;
    }

    private String environmentName() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("linux")) return "linux64";
        if (os.startsWith("mac")) return "mac64";
        return "win32";
    }

    private File tempWebDriverPath() throws IOException {
        return File.createTempFile("webdriver", ".temp");
    }
}
