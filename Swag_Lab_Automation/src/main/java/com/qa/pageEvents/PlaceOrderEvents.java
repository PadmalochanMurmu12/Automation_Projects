package com.qa.pageEvents;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.qa.base.Base;
import com.qa.pages.PlaceOrder;

// Removed: extends Base
public class PlaceOrderEvents {

	private PlaceOrder orders;
	private WebDriver driver;

	public PlaceOrderEvents(WebDriver driver) {
		this.driver = driver;
		this.orders = new PlaceOrder(driver);
	}

	public void fillAddress() {
		// Accessing data statically from Base class
		orders.getfirstName().sendKeys(Base.prop.getProperty("firstname"));
		orders.getlastName().sendKeys(Base.prop.getProperty("lastname"));
		orders.getZipCode().sendKeys(Base.prop.getProperty("zipcode"));
	}

	public void clickContinue() {
		orders.getcontinueBtn().click();
	}

	public void placeOrder() {
		// Your scroll logic is perfect here
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		orders.getFinishBtn().click();
	}

	/**
	 * The final validation method for the End-to-End test.
	 */
	public boolean isOrderConfirmationDisplayed() {
		try {
			String text = orders.getCompleteHeader().getText();
			return text.equalsIgnoreCase("Thank you for your order!");
		} catch (Exception e) {
			return false;
		}
	}
}