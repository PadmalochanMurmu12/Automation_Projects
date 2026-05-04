package com.qa.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;
import com.qa.base.Base;
import com.qa.pageEvents.*;

@Epic("Regression Suite")
@Feature("Complete Product Purchase Flow")
public class SingleProduct extends Base {
	private static final Logger logger = LogManager.getLogger(SingleProduct.class);
	@Test(description = "End-to-End purchase flow")
	public void testCompleteProductPurchaseFlow() {

		// 1. Get the thread-safe driver instance
		WebDriver currentDriver = getDriver();

		// 2. Login Phase
		logger.info("Phase 1: Login");
		LoginPageEvents loginEvent = new LoginPageEvents(currentDriver);
		loginEvent.enterCreds();
		// Fail-Fast: If login fails, don't proceed.
		Assert.assertTrue(loginEvent.isLoginSuccessful(), "Login failed: Current URL does not match inventory page.");

		// 3. Product Selection Phase
		logger.info("Phase 2: Adding Product to Cart");
		AddtoCartEvents cartEvent = new AddtoCartEvents(currentDriver);
		cartEvent.clickOnItem();
		cartEvent.clickAddToCart();
		System.out.println("✅ Product added to cart");

		cartEvent.clickCartBadge();

		// 4. Checkout Phase
		logger.info("Phase 3: Checkout and Order Placement");
		CheckoutPageEvents checkoutEvents = new CheckoutPageEvents(currentDriver);
		// Verify we are actually on the checkout page before clicking
		Assert.assertTrue(checkoutEvents.isCheckoutButtonVisible(), "Checkout button not found on cart page.");
		checkoutEvents.proceedToCheckout();

		// 5. Order Placement Phase
		PlaceOrderEvents orderEvents = new PlaceOrderEvents(currentDriver);
		orderEvents.fillAddress();
		orderEvents.clickContinue();
		orderEvents.placeOrder();

		// 6. Final End-to-End Assertion
		Assert.assertTrue(orderEvents.isOrderConfirmationDisplayed(), "Final order confirmation message was not displayed!");
		logger.info("Test Case Completed Successfully.");
	}
}