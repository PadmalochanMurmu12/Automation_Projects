import { test as base } from 'playwright-bdd';
import { expect } from '@playwright/test';
import { HomePage } from '../pages/home-page';
import { MenuItems } from '../pages/menu-items';
import { CartItems } from '../pages/cart-items';
import { Payments } from '../pages/payments';


export type OrderState = {
  dynamicItemName: string;
  dynamicTargetQty: number;
  dynamicItemsArray: string[];
};

type MyFixtures = {
  homePage: HomePage;
  menuItems: MenuItems;
  cartItems: CartItems;
  payments: Payments;
  orderState: OrderState; 
};

export const test = base.extend<MyFixtures>({

  context: async ({ context }, use) => {
    await context.grantPermissions(['geolocation', 'notifications']);
    await use(context);
  },

  orderState: async ({}, use) => {
    await use({
      dynamicItemName: '',
      dynamicTargetQty: 1,
      dynamicItemsArray: [],
    });
  },

  homePage: async ({ page }, use) => { await use(new HomePage(page)); },
  menuItems: async ({ page }, use) => { await use(new MenuItems(page)); },
  cartItems: async ({ page }, use) => { await use(new CartItems(page)); },
  payments: async ({ page }, use) => { await use(new Payments(page)); },
  
});

export { expect };