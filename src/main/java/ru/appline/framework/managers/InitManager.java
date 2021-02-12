package ru.appline.framework.managers;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static ru.appline.framework.managers.DriverManager.getWebDriver;
import static ru.appline.framework.managers.DriverManager.quitDriver;
import static ru.appline.framework.utils.PropertyConst.IMPLICITLY_WAIT;
import static ru.appline.framework.utils.PropertyConst.PAGE_LOAD_TIMEOUT;

public class InitManager {


    private static final PropertyManager propertyManager = PropertyManager.getPropertyManager();

    /**
     * иницилизируем параметры запуска теста
     */
    public static void initFramevork() {
        getWebDriver().manage().window().maximize();
        getWebDriver().manage().timeouts().implicitlyWait(Integer.parseInt(propertyManager.getProperties(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        getWebDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(propertyManager.getProperties(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
    }

    /**
     * отключаем драйвер
     */
    public static void quitFramework() {
        quitDriver();
    }
}
