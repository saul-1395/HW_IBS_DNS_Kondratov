package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static ru.appline.framework.managers.DriverManager.getWebDriver;

public class StartPage extends BasePage {


//
//    @FindBy(xpath = "//input[@name='q' and @placeholder='Поиск по сайту']")
//    WebElement search;
//
//    @FindBy(xpath = "//div[@class='catalog-item']")
//    List<WebElement> list;


    WebElement product;
    WebElement buttonBuy;
    int backetCost;

    /**
     * Переход на страницу корзины
     * @return
     */
    public CartPage goToCart() {
        clickButton(cart);
        return app.getCartPage();
    }

    /**
     * Калдем продукт в корзину и ожидаем, что кнопка "Купить" смениться на "В корзине"
     * Изменяем текущую стоимомть корзины
     * @see StartPage#changeBucketCost(int)
     * @return
     */
    public StartPage putOneProductToCart() {
        product = getWebDriver().findElement(By.xpath("//div[@id='product-page']"));
        buttonBuy = product.findElement(By.xpath(".//button[text()='Купить']"));
        String cost = product.findElement(By.xpath(".//div[@class='product-card-price__current-wrap']")).getText();
        int productCost = converter(cost);
        backetCost = converter(cart.getText());
        // System.out.println(" корзина до покупки " + backetCost + " добавим цену товара " + productCost);
        clickButton(buttonBuy);
        wait.until(ExpectedConditions.textToBePresentInElement(buttonBuy, "В корзине"));
        System.out.println(productCost + " productcost");
        changeBucketCost(productCost);
        return this;
    }


    /**
     * Изменяем стоимость корзины
     * @param productCost
     * @return
     */
    private int changeBucketCost(int productCost) {
        // //span[@class='cart-link__price']
        int currentCost = converter(cart.getText());
        Assertions.assertEquals(backetCost + productCost, currentCost, " Ошибка в добавлении стоимости товаров");
        backetCost += productCost;
        return backetCost;
    }


    /**
     * Поиск продукта по названию в текущем списке продуктов
     * @see BasePage#findElementForNameFromListCard(List, String)
     * @param value
     * @return
     */
    public StartPage findElementForNameFromSearchList(String value) {
        product = findElementForNameFromListCard(list, value);
        return this;
    }

    /**
     * Добавление страховки с помощью радиобатона
     * @param value
     * @param insurance
     * @return
     */
    public StartPage addInsurance(String value, String insurance) {
        // System.out.println("cartlist size " + list.size() + "  ");
        product = findElementForNameFromListCard(list, value);
        // System.out.println("cartlist size " + cartlist.size() +  "  " + webElement.getText());
        radioButtonClick(product, insurance);
        changeBucketCost(5400);
        return this;
    }

    /**
     * Выбираем из списка продукт и переходим на страницу продукта
     * @see ProductPage
     * @return
     */
    public ProductPage clickForProduct() {
        clickButton(product.findElement(By.xpath(".//a[@class='ui-link']")));
        return app.getProductPage();
    }
}
