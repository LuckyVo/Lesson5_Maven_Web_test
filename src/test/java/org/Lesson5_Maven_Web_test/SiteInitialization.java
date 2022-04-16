package org.Lesson5_Maven_Web_test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public abstract class SiteInitialization {

    private static WebDriver driver;

    @BeforeAll
    static void initDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--incognito");
//        options.addArguments("disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
//        driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
    }

    @BeforeEach
    void initMainPage() {
        Assertions.assertDoesNotThrow( ()-> driver.navigate().to("https://xochy-xochy.com/"),
                "Страница не доступна");
        Assertions.assertTrue(true);
    }

    @AfterAll
    public static void exit() {
//        if(driver !=null) driver.quit();
    }

    public WebDriver getDriver() {
        return this.driver;
    }
}
