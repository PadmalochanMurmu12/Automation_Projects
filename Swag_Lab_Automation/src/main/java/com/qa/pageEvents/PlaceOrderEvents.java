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
		// Accessing data dynamically via Smart Fetcher (supports both Cloud Secrets and Local Properties)
		orders.getfirstName().sendKeys(Base.getSecureConfig("firstname"));
		orders.getlastName().sendKeys(Base.getSecureConfig("lastname"));
		orders.getZipCode().sendKeys(Base.getSecureConfig("zipcode"));
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