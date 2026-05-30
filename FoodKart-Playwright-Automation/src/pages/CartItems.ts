import { Locator, Page, expect } from '@playwright/test';
import { BasePage } from './BasePage';

export class CartItems extends BasePage {
    readonly checkoutBtn: Locator;

    constructor(page: Page) {
        super(page);
        this.checkoutBtn = page.getByRole('button', { name: /Proceed to Checkout/i });
    }

    async increaseItemQuantity(itemName: string, targetQuantity: number) {
        const itemRow = this.page.locator(`li:has-text("${itemName}")`);
        
        const plusBtn = itemRow.getByTestId(/increase-qty-/);
        const qtyDisplay = itemRow.getByTestId(/item-qty-/);

        const currentQty = parseInt((await qtyDisplay.innerText()).trim(), 10);
        const clicksNeeded = targetQuantity - currentQty;

        for (let i = 0; i < clicksNeeded; i++) {
            await plusBtn.click();
            // Wait for the text to update to avoid race conditions
            const expectedQty = currentQty + i + 1;
            await expect(qtyDisplay).toHaveText(expectedQty.toString());
        }
    }

    async verifyItemTotals(itemName: string, expectedQuantity: number) {
        const itemRow = this.page.locator(`li:has-text("${itemName}")`);
        
        const qtyDisplay = itemRow.getByTestId(/item-qty-/);
        await expect(qtyDisplay).toHaveText(expectedQuantity.toString());

        const unitPriceElement = itemRow.locator('p.text-slate-500');
        const unitPriceText = await unitPriceElement.innerText();
        const unitPrice = parseInt(unitPriceText.replace(/[^0-9]/g, ''), 10);

        const totalPriceElement = itemRow.getByTestId(/item-total-/);
        const totalPriceText = await totalPriceElement.innerText();
        const actualTotal = parseInt(totalPriceText.replace(/[^0-9]/g, ''), 10);

        const expectedTotal = unitPrice * expectedQuantity;
        expect(actualTotal).toBe(expectedTotal);
    }

  async proceedToCheckout() {

    await expect(this.page.getByText('Total Amount')).toBeVisible();
    await expect(this.checkoutBtn).toBeEnabled();
    await this.checkoutBtn.click({ delay: 50 });
    await this.page.waitForURL(/.*checkout\.stripe\.com.*/i, { timeout: 30000, waitUntil: 'commit' });
    
  }
}