import { createBdd } from 'playwright-bdd';
import { test, expect } from '../../src/fixtures/PageFixtures';
import { PaymentDetails } from '../../src/pages/Payments';
import * as testData from '../../src/constants/testData.json';

const { Before, Given, When, Then } = createBdd(test);

// --- GLOBAL HOOKS ---
Before(async ({ $testInfo, browserName }) => {
  if (browserName === 'firefox' || browserName === 'webkit') {
    $testInfo.skip(); 
  }
});

Given('I am on the FoodKart home page', async ({ homePage }) => {
  await homePage.goTo('/');
});

When('I proceed to the checkout', async ({ cartItems }) => {
  await cartItems.proceedToCheckout();
});

When('I submit my payment details', async ({ payments }) => {
  const paymentDetails: PaymentDetails = {
    emailAddress: testData.mockCardDetails.emailAddress,
    cardNumber: testData.mockCardDetails.cardNumber,
    expiryDate: testData.mockCardDetails.expiryDate,
    cvv: testData.mockCardDetails.cvv,
    cardHolderName: testData.mockCardDetails.cardHolderName,
  };
  await payments.submitPayment(paymentDetails);
});

Then('my order should be placed successfully', async ({ payments }) => {
  await payments.page.waitForURL(/.*success/i, { timeout: 30000 });
  await expect(payments.page).toHaveURL(/.*success/i);
});

When('I add the {string} to my cart', async ({ homePage, menuItems }, itemName: string) => {
  await homePage.clickOrderNow(); 
  await menuItems.addSpecificItemToCart(itemName);
  await menuItems.clickCartBadge();
});

When('I add a random item from the menu to my cart', async ({ homePage, menuItems }) => {
  await homePage.clickOrderNow(); 
  await menuItems.addRandomItemToCart();
  await menuItems.clickCartBadge();
});

When('I add all products from the multiple items configuration to my cart', async ({ homePage, menuItems }) => {
  await homePage.clickOrderNow(); 
  const productList = testData.multipleItemsOrder.products;
  for (const product of productList) {
    await menuItems.addSpecificItemToCart(product);
  }
  await menuItems.clickCartBadge();
});

When('I add {int} random items from the menu to my cart', async ({ homePage, menuItems }, itemCount: number) => {
  await homePage.clickOrderNow(); 
  await menuItems.addMultipleRandomItemsToCart(itemCount);
  await menuItems.clickCartBadge();
});