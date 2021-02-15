package ru.appline.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.managers.PageManager;

import java.util.List;

import static ru.appline.framework.managers.DriverManager.getWebDriver;
import static ru.appline.framework.managers.PageManager.getPageManager;

public class BasePage {

    protected PageManager app = getPageManager();

    WebDriverWait wait = new WebDriverWait(getWebDriver(), 15, 1000);
    /**
     * Находит стоимость корзины в верхней панели
     */
    @FindBy(xpath = "//span[@class='cart-link__price']")
    WebElement cart;

    /**
     * Поле поиска на всех страницах
     */
    @FindBy(xpath = "//input[@name='q' and @placeholder='Поиск по сайту']")
    WebElement search;

    /**
     * лист карточек товаров
     */
    @FindBy(xpath = "//div[@class='catalog-item']")
    List<WebElement> list;

    public BasePage() {
        PageFactory.initElements(getWebDriver(), this);
    }

    /**
     * метод ищет в поданом списке товар по названию
     *
     * @param list
     * @param value
     * @return
     */
    protected WebElement findElementForNameFromListCard(List<WebElement> list, String value) {
        System.out.println(list.size() + " listsize" + " value " + value);
        WebElement product = list.stream()
                .filter(x -> {
                    try {
                        x.findElement(By.xpath(".//*[contains(text(), '" + value + "')]")).isDisplayed();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);
//        System.out.println(" ** " + product.getText() + " find " + value + " size " + list.size());
        return product;
    }

    /**
     * кликает кнопкой с проверкой
     *
     * @see BasePage#elementClickable(WebElement)
     */
    protected WebElement clickButton(WebElement button) {
        elementClickable(button);
        button.click();
        return button;
    }

    /**
     * проверяем, что эл-т виден и кликается
     */
    public WebElement elementClickable(WebElement element) {
        // System.out.println(element.getText());
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    /**
     * конвертер, принимает String, оставляет только цифры и возвращает int.
     * Только для целочисленных! Запятые тоже потрёт.
     */
    protected int converter(String value) {
        String result = "";
        int i = 0;
        while (i < value.length()) {
            if (Character.isDigit(value.charAt(i))) {
                result = result + value.charAt(i);
            }
            i++;
        }
        return Integer.parseInt(result);
    }

    /**
     * Метод находит в карточке товара радиобатон по тексту (радиобатона)
     */
    public void radioButtonClick(WebElement product, String value) {

        List<WebElement> radioButtonList = product.findElements(By.xpath(".//div[@class='base-ui-radio-button additional-warranties-row__radio']"));
        //System.out.println("templist size " + templist.size() + "  " + templist.get(2).toString() );
        WebElement radioButton = findElementForNameFromListCard(radioButtonList, value);
        clickButton(radioButton.findElement(By.xpath("./..")));
    }

    /**
     * метод поиска, со встроенным Enter-ом
     */
    public StartPage search(String value) {
        elementClickable(search).sendKeys(value + "\n");
        list.clear();
        return getPageManager().getStartPage();
    }


}
