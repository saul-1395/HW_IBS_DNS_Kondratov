package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static ru.appline.framework.managers.DriverManager.getWebDriver;

public class CartPage extends BasePage {
    /**
     * список элементов в корзине
     */
    @FindBy(xpath = "//div[@class='cart-items__product']")
    private List<WebElement> cartlist;

    /**
     * счетчик элементов в корзине (вверху справа)
     */
    @FindBy(xpath = "//div[@class='cart-products-count']")
    private WebElement productCount;


    private WebElement product;
    private WebElement radioButton;
    private int productCost;


    /**
     * ставит страховку радиобатонами
     */
    public CartPage addInsurance(String value, String insurance) {
        System.out.println("cartlist size " + cartlist.size() + "  ");
        product = findElementForNameFromListCard(cartlist, value);
        // System.out.println("cartlist size " + cartlist.size() +  "  " + webElement.getText());
        radioButtonClick(product, insurance);
        return this;
    }

    /**
     * ищет элемент в корзине по имени, назначает переменную this#product
     */
    public CartPage findElementForNameFromCartList(String value) {
        product = findElementForNameFromListCard(cartlist, value);
        return this;
    }

    /**
     * сравнивает тип страховки с фактическим по названию страховки.
     * находит активный радиобатон и берёт значение атрибута и сравнивает с переданным
     */
    public CartPage checkInsurance(String value) {
//div[@class='cart-items__product']/..//*[contains(text(), '24')]
        radioButton = product.findElement(By.xpath(".//span[@class='base-ui-radio-button__icon base-ui-radio-button__icon_checked']"));
        System.out.println(radioButton.getText() + " insurance");
        Assertions.assertEquals(converter(value), converter(radioButton.getText()));

        return this;
    }

    /**
     * сравнивает сумму стоимости продуктов, с полученным значением стоимости корзины
     *
     * @see CartPage#summCostList()
     */
    public CartPage checkSumm() {

        Assertions.assertEquals(summCostList(), converter(cart.getText()));
        return this;
    }

    /**
     * Возвращает суммарную стоимость всех продуктов и их страховок, если есть
     */
    private int summCostList() {
        System.out.println(cartlist.size() + " listsize");
        int summ = cartlist.stream()
                .map(x -> converter(x.findElement(By.xpath(".//span[@class='price__current']")).getText()))
                .mapToInt(y -> y)
                .sum();
        //  System.out.println(" summ = " + summ);
        int sumIns = cartlist.stream()
                .map(x -> {
                    try {
                        return converter(x.findElement(By.xpath(".//span[@class='base-ui-radio-button__icon base-ui-radio-button__icon_checked']/../..//span[@class='additional-warranties-row__price']")).getText());
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .mapToInt(y -> y)
                .sum();
        //    System.out.println(" sumIns = " + sumIns);

        return sumIns + summ;
    }


    /**
     * Удаляет продукт из корзины по названию, и проверяет, что сумма оставшихся продуктов равна значению стоимости корзины
     */
    public CartPage removeProduct(String productName) {
        product = findElementForNameFromListCard(cartlist, productName);
        productCost = converter(product.findElement(By.xpath(".//span[@class='price__current']")).getText());
        // System.out.println(" перед удалением найдена " + product.getText());
        WebElement button;
        List<WebElement> buttonList = product.findElements(By.xpath(".//button[@class='menu-control-button']"));
        button = buttonList.stream()
                .filter(x -> x.getText().equals("Удалить"))
                .findFirst()
                .orElse(null);
        int tempCartCost = converter(cart.getText());
        System.out.println(" fgh " + tempCartCost);

        clickButton(button);

        wait.until(ExpectedConditions.attributeContains(productCount, "outerText", "1 товар"));

        int temp = tempCartCost;
        while (temp == converter(cart.getText())) {
            try {
                temp = converter(cart.getText());
                System.out.println(temp);

            } catch (Exception e) {
                System.out.println("попытка");
            }
        }
        Assertions.assertEquals(converter(cart.getText()) + productCost, tempCartCost, "стоимость корзины не совпадает со стоимость коризны до удаления - удаленный товар");
        return this;
    }

    /**
     * Проверяет ОТСУТСТВИЕ продутка по его названию
     *
     * @see BasePage#findElementForNameFromListCard(List, String)
     */
    public CartPage checkProductOut(String productName) {
        Assertions.assertNull(findElementForNameFromListCard(cartlist, productName), " объект есть " + productName);
        return this;
    }

    /***
     * Увеличивает количество продукта в корзине по нажатию на + по названию и указанию кол-ва добавляемых продуктов
     * и проверяет соответствие суммарной стоимости корзины.
     * @see CartPage#findElementForNameFromCartList(String)
     */
    public CartPage addProduct(String productName, String count) {
        findElementForNameFromCartList(productName);
        int i = converter(count);
        int countProd = converter(productCount.getText());    // получаем  кол-во товаров между - и + которое, у нас в начале 1
        int costOnProd = (converter(product.findElement(By.xpath(".//span[@class='price__current']")).getText())) / countProd;

        WebElement battonPlus = product.findElement(By.xpath("//i[@class='count-buttons__icon-plus']")); //кнопка +

        while (i > 0) {                                        // передаём как аргумент нужного кол-ва товаров
            int tempcount = converter(productCount.getText()); // получаем  кол-во товаров между - и + которое, у нас в начале 1
            clickButton(battonPlus);                           // нажимаем +
            while (tempcount == countProd) {                   // если начальное и текущее значения равны
                tempcount = converter(productCount.getText()); // запрашиваем текущее значение кол-ва товаров, цикл запроса крутится пока темпкаунт не станет 2
            }
            countProd++;                                       // увеличили счетчик на 1
            i--;                                               // тут понятно и так пока i в ноль не обратится
        }

        int sumProd = costOnProd * (converter(productCount.getText()));
        Assertions.assertEquals(sumProd, converter(product.findElement(By.xpath(".//span[@class='price__current']")).getText()), " суммарная стоимость " + count + " товаров " + productName + " не совпадает");
        return this;
    }


    /**
     * Восстановление удалённого продукта
     */
    public CartPage restoreDeletedProduct() {
        wait.until(ExpectedConditions.attributeContains(productCount, "outerText", "3 товара"));

        while (converter(cart.getText()) != summCostList()) {
            System.out.println("попытка");
        }
        // System.out.println(productCount.getAttribute("outerText"));
        int costCartBefore = converter(cart.getText());
        boolean flag = true;
        while (flag) {
            try {
                System.out.println(converter(cart.getText()));
                flag = false;
            } catch (Exception e) {
                System.out.println("попытка восст");
            }
        }
        clickButton(getWebDriver().findElement(By.xpath("//div[@class='group-tabs']//span[@class='restore-last-removed']")));

        //System.out.println(productCount.getAttribute("outerText"));

        wait.until(ExpectedConditions.attributeContains(productCount, "outerText", "4 товара"));
        product = findElementForNameFromListCard(cartlist, "Detroit");

        int costProd = (converter(product.findElement(By.xpath(".//span[@class='price__current']")).getText()));
        int costCarAfter = converter(cart.getText());

        Assertions.assertEquals(costCartBefore + costProd, costCarAfter, " стоимость корзины не совпадает с суммой удаленного товара и пред. знач. корзины");
        return this;
    }


}
