package com.qa.pageEvents;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qa.base.Base;
import com.qa.pages.AddToCart;

public class AddtoCartEvents extends Base
{
    AddToCart cart;
    WebDriver driver;

    public AddtoCartEvents(WebDriver driver) {
        this.driver = driver;
        cart = new AddToCart(driver);
    }
    
    public void clickOnItem()
    {
        cart.getProductName().click();
    }
    
    public void clickAddToCart()
    {
        cart.getaddtocartBtn().click();
    }
    
    public void clickCartBadge()
    {
    	cart.getcartBadge().click();
    }
    
}