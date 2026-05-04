package com.qa.pageEvents;

import org.openqa.selenium.WebDriver;
import com.qa.pages.AddToCart;

public class AddtoCartEvents {

	private AddToCart cart;
	private WebDriver driver;

	// Constructor strictly accepts WebDriver
	public AddtoCartEvents(WebDriver driver) {
		this.driver = driver;
		this.cart = new AddToCart(driver); // Passes the driver down to the locator class
	}

	public void clickOnItem() {
		cart.getProductName().click();
	}

	public void clickAddToCart() {
		cart.getaddtocartBtn().click();
	}

	public void clickCartBadge() {
		cart.getcartBadge().click();
	}
}