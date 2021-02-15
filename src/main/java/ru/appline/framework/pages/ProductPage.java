package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static java.lang.Thread.sleep;


public class ProductPage extends BasePage {

    int tempCost;
    int PScost;
    int PSInscost;
    int gamecost;
    WebElement buttonBuy;

    /**
     * карточка продукта
     */
    @FindBy(xpath = "//*[@id='product-page']")
    WebElement product;

    /**
     * стоимость продукта
     */
    @FindBy(xpath = "//*[@id='product-page']/..//div[@class='product-card-price__current-wrap']")
    WebElement cost;

    /**
     * стоимость страховки по активному радиобатону
     */
    @FindBy(xpath = "//*[@id='product-page']/..//span[@class='product-card-price__current product-card-price__current_active']")
    WebElement inscost;


//*[@id='product-page']/..//span[@class='product-card-price__current']
    //                     ..//select/option[contains(text(), '2 года')]

    /**
     * выбор страховки из выпадающего списка
     */
    public ProductPage checkInsurance() {
        PScost = converter(cost.getText());
        System.out.println(" PScost " + PScost);
        System.out.println(" product " + product.getText());
        // clickButton(product.findElement(By.xpath(".//select")));
        clickButton(product.findElement(By.xpath(".//select/option[contains(text(), '2 года')]")));

        if (PScost == converter(inscost.getText())) {
            Assertions.fail(" Значения не поменялись ");
        }
        PSInscost = converter(inscost.getText());
        System.out.println(" currentCost " + PSInscost);
        return this;
    }

    /**
     * кладем продукт в корзину
     * @see BasePage#clickButton(WebElement)
     */
    public ProductPage putProductToCart() {
//        System.out.println(" put to cart cost = " + tempCost);
//        System.out.println(" all cost " + product.findElement(By.xpath(".//div[@class='product-card-price__current-wrap']")).getText());
        buttonBuy = product.findElement(By.xpath(".//button[text()='Купить']"));
        clickButton(product.findElement(By.xpath(".//button[text()='Купить']")));
        return this;
    }

}
