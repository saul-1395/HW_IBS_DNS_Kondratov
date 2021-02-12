package ru.appline.framework.managers;


import ru.appline.framework.pages.CartPage;
import ru.appline.framework.pages.ProductPage;
import ru.appline.framework.pages.StartPage;

/**
 * менеджер страниц синглтонов
 */
public class PageManager {
    private static PageManager pageManager;
    private StartPage startPage;
    private CartPage cartPage;
    private ProductPage productPage;

    private PageManager() {
    }

    public static PageManager getPageManager() {
        if(pageManager==null){
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public StartPage getStartPage() {
        if(startPage==null){
            startPage=new StartPage();
        }
        return startPage;
    }

    public CartPage getCartPage() {
        if(cartPage==null){
            cartPage=new CartPage();
        }
        return cartPage;
    }

    public ProductPage getProductPage() {
        if(productPage==null){
            productPage = new ProductPage();
        }
        return productPage;
    }
}
