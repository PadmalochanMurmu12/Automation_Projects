import { Locator, Page } from "@playwright/test";
import {BasePage} from "./BasePage";

export class HomePage extends BasePage {
  readonly orderNowBtn: Locator;

  constructor(page: Page) {
    super(page);
    this.orderNowBtn = page.getByRole('button', { name: 'Order Now' });
  }

  async clickOrderNow() {
    await this.orderNowBtn.click();
  }

}