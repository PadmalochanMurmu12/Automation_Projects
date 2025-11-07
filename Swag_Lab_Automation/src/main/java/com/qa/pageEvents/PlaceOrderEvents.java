package com.qa.pageEvents;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.qa.base.Base;
import com.qa.pages.PlaceOrder;

public class PlaceOrderEvents extends Base
{
	PlaceOrder orders;
    WebDriver driver;
    
    public PlaceOrderEvents(WebDriver driver) {
        this.driver = driver;
        orders = new PlaceOrder(driver);
    }
    
    public void fillAddress()
    {
    	orders.getfirstName().sendKeys(prop.getProperty("firstname"));
    	orders.getlastName().sendKeys(prop.getProperty("lastname"));
    	orders.getZipCode().sendKeys(prop.getProperty("zipcode"));
    }
    
    public void clickContinue()
    {
    	orders.getcontinueBtn().click();
    }
    
    public void placeOrder()
    {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("window.scrollBy(0,500)");
    	
    	orders.getFinishBtn().click();
    }
    
}