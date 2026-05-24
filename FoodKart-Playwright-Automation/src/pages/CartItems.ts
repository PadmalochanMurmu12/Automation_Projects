import { Locator, Page, expect } from '@playwright/test';
import { BasePage } from './BasePage';

export class CartItems extends BasePage {
    readonly checkoutBtn: Locator;

    constructor(page: Page) {
        super(page);
        this.checkoutBtn = page.getByRole('button', { name: /Proceed to Checkout/i });
    }

  async proceedToCheckout() {

    await expect(this.page.getByText('Total Amount')).toBeVisible();
    await expect(this.checkoutBtn).toBeEnabled();
    await this.checkoutBtn.click({ delay: 50 });
    await this.page.waitForURL(/.*checkout\.stripe\.com.*/i, { timeout: 30000, waitUntil: 'commit' });
    
  }
}