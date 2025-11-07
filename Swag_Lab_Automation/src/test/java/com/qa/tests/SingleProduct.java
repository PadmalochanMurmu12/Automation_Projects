package com.qa.tests;

import org.testng.annotations.Test;
import com.qa.base.Base;
import com.qa.pageEvents.*;

public class SingleProduct extends Base
{
	@Test(description="Complete flow: Login -> Select Product -> Add to Cart")
	public void testCompleteProductPurchaseFlow() throws InterruptedException
	{
		// Step 1: Login
		LoginPageEvents loginEvent = new LoginPageEvents(driver);
		loginEvent.enterCreds();
//		loginEvent.validateSuccessfulLogin();

		AddtoCartEvents cartEvent = new AddtoCartEvents(driver);
		cartEvent.clickOnItem();

		Thread.sleep(3000);
		cartEvent.clickAddToCart();

		Thread.sleep(3000);
		System.out.println("✅ Product added to cart successfully");
		
		cartEvent.clickCartBadge();
		Thread.sleep(3000);
		
		CheckoutPageEvents checkoutEvents= new CheckoutPageEvents(driver);
		checkoutEvents.proceedToCheckout();
		Thread.sleep(3000);
		
		PlaceOrderEvents orderEvents= new PlaceOrderEvents(driver);
		orderEvents.fillAddress();
		orderEvents.clickContinue();
		Thread.sleep(3000);
		
		orderEvents.placeOrder();
		Thread.sleep(3000);
		
	}
}