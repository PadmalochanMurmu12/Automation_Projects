import { test as base } from 'playwright-bdd';
import { expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';
import { MenuItems } from '../pages/MenuItems';
import { CartItems } from '../pages/CartItems';
import { Payments } from '../pages/Payments';

type MyFixtures = {
  homePage: HomePage;
  menuItems: MenuItems;
  cartItems: CartItems;
  payments: Payments;
};

export const test = base.extend<MyFixtures>({

  context: async ({ context }, use) => {
    await context.grantPermissions(['geolocation', 'notifications']);
    await use(context);
  },

  homePage: async ({ page }, use) => { await use(new HomePage(page)); },
  menuItems: async ({ page }, use) => { await use(new MenuItems(page)); },
  cartItems: async ({ page }, use) => { await use(new CartItems(page)); },
  payments: async ({ page }, use) => { await use(new Payments(page)); },
  
});

export { expect };