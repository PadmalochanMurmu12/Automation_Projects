package com.qa.pageEvents;

import org.openqa.selenium.WebDriver;

import com.qa.base.Base;
import com.qa.pages.Checkout;

public class CheckoutPageEvents extends Base
{
	Checkout checkout;
    WebDriver driver;

    public CheckoutPageEvents(WebDriver driver) {
        this.driver = driver;
        checkout = new Checkout(driver);
    }
    
    public void proceedToCheckout()
    {
    	checkout.getcheckoutBtn().click();
    }
}