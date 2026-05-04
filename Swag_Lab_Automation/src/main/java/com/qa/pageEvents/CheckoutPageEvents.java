package com.qa.pageEvents;

import org.openqa.selenium.WebDriver;
import com.qa.pages.Checkout;

public class CheckoutPageEvents {
    
    private Checkout checkout;
    private WebDriver driver;

    public CheckoutPageEvents(WebDriver driver) {
        this.driver = driver;
        checkout = new Checkout(driver);
    }
    
    public void proceedToCheckout() {
        checkout.getcheckoutBtn().click();
    }

    public boolean isCheckoutButtonVisible() {
        try {
            return checkout.getcheckoutBtn().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}