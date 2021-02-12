package ru.appline.framework.managers;

import org.apache.commons.exec.OS;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static ru.appline.framework.utils.PropertyConst.*;


public class DriverManager {
    private static DriverManager driverManager;
    private static WebDriver webDriver;
    private final PropertyManager propertyManager = PropertyManager.getPropertyManager();

    /**
     * При создании DriverManager происходит иницилизация драйвера
     */
    private DriverManager() {
        initDriver();
    }

    /**
     * Синглтон драйвера
     * @return
     */
    public static WebDriver getWebDriver() {
        if (driverManager == null) {
            driverManager = new DriverManager();
        }
        return webDriver;
    }

    /**
     * метод отключения драйвера
     */
    public static void quitDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }

//    private void webDriverInit() {
//        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-notifications");
//        options.addArguments("start-maximized");
//        webDriver = new ChromeDriver(options);
//        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }

    /**
     * Выбираем какая текущая система и выбираем соответствующий метод иницилизации
     */
    private void initDriver() {
        if (OS.isFamilyWindows()) {
            initDriverWindowsOsFamily();
        } else {
            System.out.println("поставьте виндовс");
        }
    }

    /**
     * передаем в метод иницилизации все нужные драйвера. СОБЛЮДАТЬ ПОРЯДОК ПЕРЕДАЧИ АРГУМЕНТОВ
     */
    private void initDriverWindowsOsFamily() {
        initDriverAnyOsFamily( PATH_GECKO_DRIVER_WINDOWS, PATH_CHROME_DRIVER_WINDOWS);
    }

    /**
     * определяем тип браузера из пропертей и иницилизируем соответствующий драйвер
     */
    private void initDriverAnyOsFamily(String gecko, String chrome) {
        switch (propertyManager.getProperties(TYPE_BROWSER)) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-notifications");
                System.setProperty("webdriver.gecko.driver", propertyManager.getProperties(gecko));
                webDriver = new FirefoxDriver(firefoxOptions);

                break;
            case "chrome":
                ChromeOptions сhromeOptions = new ChromeOptions();
                сhromeOptions.addArguments("--disable-notifications");
                System.setProperty("webdriver.chrome.driver", propertyManager.getProperties(chrome));
                webDriver = new ChromeDriver(сhromeOptions);

                break;
            default:
                Assertions.fail("Типа браузера '" + propertyManager.getProperties(TYPE_BROWSER) + "' не существует во фреймворке");
        }
    }



}
