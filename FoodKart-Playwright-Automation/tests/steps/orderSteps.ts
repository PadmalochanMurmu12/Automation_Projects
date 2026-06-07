import { createBdd } from 'playwright-bdd';
import { test, expect } from '../../src/fixtures/PageFixtures';
import { PaymentDetails } from '../../src/pages/payments'; 
import * as testData from '../../src/constants/testData.json';

const { Before, Given, When, Then } = createBdd(test);

Before(async ({ $testInfo, browserName }) => {
  if (browserName === 'firefox' || browserName === 'webkit') {
    $testInfo.skip(); 
  }
});

Given('I am on the FoodKart home page', async ({ homePage }) => {
  await homePage.goTo('/');
});

// ==========================================
// STATIC STEPS (JSON Driven)
// ==========================================

When('I add the {string} to my cart', async ({ homePage, menuItems }, itemName: string) => {
  await homePage.clickOrderNow(); 
  await menuItems.addSpecificItemToCart(itemName);
  await menuItems.clickCartBadge();
});

When('I increase the quantity of {string} to {int}', async ({ cartItems }, itemName: string, targetQty: number) => {
  await cartItems.increaseItemQuantity(itemName, targetQty);
});

Then('the cart should reflect the updated quantity of {string} to {int}', async ({ cartItems }, itemName: string, targetQty: number) => {
  await cartItems.verifyItemTotals(itemName, targetQty);
});

When('I configure my cart with the static multi-quantity items from JSON', async ({ homePage, menuItems, cartItems }) => {
  await homePage.clickOrderNow(); 
  
  const orderConfig = testData.staticMultiQuantityOrder;

  for (const item of orderConfig) {
    await menuItems.addSpecificItemToCart(item.name);
  }

  await menuItems.clickCartBadge();

  for (const item of orderConfig) {
    await cartItems.increaseItemQuantity(item.name, item.quantity);
    await cartItems.verifyItemTotals(item.name, item.quantity); 
  }
});

When('I add all products from the multiple items configuration to my cart', async ({ homePage, menuItems }) => {
  await homePage.clickOrderNow(); 
  const productList = testData.multipleItemsOrder.products;
  for (const product of productList) {
    await menuItems.addSpecificItemToCart(product);
  }
  await menuItems.clickCartBadge();
});

// ==========================================
// DYNAMIC SINGLE-ITEM STEPS
// ==========================================

// FIX: Destructuring 'orderState' from the test fixture to store dynamic data safely
When('I add a random item from the menu to my cart', async ({ homePage, menuItems, orderState }) => {
  await homePage.clickOrderNow(); 
  
  const selectedItemName = await menuItems.addRandomItemToCart();
  orderState.dynamicItemName = selectedItemName; 
  
  await menuItems.clickCartBadge();
});

When('I increase the quantity of the dynamically added item to {int}', async ({ cartItems, orderState }, targetQty: number) => {
  const itemName = orderState.dynamicItemName;
  orderState.dynamicTargetQty = targetQty;
  
  await cartItems.increaseItemQuantity(itemName, targetQty);
});

Then('the cart should accurately reflect the dynamic total price', async ({ cartItems, orderState }) => {
  const itemName = orderState.dynamicItemName;
  // Fallback to 1 just in case this is called in a scenario without quantity increases
  const targetQty = orderState.dynamicTargetQty || 1; 
  
  await cartItems.verifyItemTotals(itemName, targetQty);
});

// ==========================================
// DYNAMIC MULTI-ITEM STEPS
// ==========================================

When('I dynamically add {int} random items from the menu to my cart', async ({ homePage, menuItems, orderState }, itemCount: number) => {
  await homePage.clickOrderNow(); 
  
  const selectedItems = await menuItems.addMultipleRandomItemsToCart(itemCount);
  orderState.dynamicItemsArray = selectedItems; 
  
  await menuItems.clickCartBadge();
});

When('I increase the quantity of all dynamically added items to {int}', async ({ cartItems, orderState }, targetQty: number) => {
  orderState.dynamicTargetQty = targetQty;
  
  for (const itemName of orderState.dynamicItemsArray) {
    await cartItems.increaseItemQuantity(itemName, targetQty);
  }
});

Then('the cart should accurately reflect the dynamic total prices for all items', async ({ cartItems, orderState }) => {
  const targetQty = orderState.dynamicTargetQty || 1;
  
  for (const itemName of orderState.dynamicItemsArray) {
    await cartItems.verifyItemTotals(itemName, targetQty);
  }
});

// ==========================================
// CHECKOUT & PAYMENT STEPS
// ==========================================

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
    zipCode: testData.mockCardDetails.zipCode,
  };
  await payments.submitPayment(paymentDetails);
});

Then('my order should be placed successfully', async ({ payments }) => {
  await payments.page.waitForURL(/.*success/i, { timeout: 30000 });
  await expect(payments.page).toHaveURL(/.*success/i);
});