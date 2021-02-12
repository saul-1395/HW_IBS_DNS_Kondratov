package ru.appline.tests;

import org.junit.jupiter.api.Test;
import ru.appline.baseTests.BaseTests;

import static java.lang.Thread.sleep;


public class DNSTest extends BaseTests {


    @Test
    public void method2() throws InterruptedException {

        app.getStartPage().search("Sony playstation")
                .findElementForNameFromSearchList("PlayStation 4 Slim Black")
                .clickForProduct()
                .checkInsurance()
                .putProductToCart()
                .search("Detroit")
                .putOneProductToCart()
                .goToCart()
                .findElementForNameFromCartList("PlayStation 4 Slim Black")
                .checkInsurance("24")
                .checkSumm()
                .removeProduct("Detroit")
                .checkProductOut("Detroit")
                .addProduct("PlayStation 4 Slim Black", "2")
                .restoreDeletedProduct();

        sleep(5000);
    }
}



