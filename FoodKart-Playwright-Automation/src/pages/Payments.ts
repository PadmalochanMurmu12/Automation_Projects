import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";

export interface PaymentDetails {
   emailAddress: string;
   cardNumber: string;
   expiryDate: string;
   cvv: string;
   cardHolderName: string;
   zipCode?: string;
   phoneNumber?: string;
}

export class Payments extends BasePage {
   readonly emailAddress: Locator;
   readonly cardNumber: Locator;
   readonly expiryDate: Locator;
   readonly cvv: Locator;
   readonly cardHolderName: Locator;
   readonly zipCode: Locator;
   readonly saveInfoCheckbox: Locator;
   readonly payBtn: Locator;

   constructor(page: Page) {
     super(page);
     this.emailAddress = page.getByPlaceholder('email@example.com');
     this.cardNumber = page.getByLabel('Card number');
     this.expiryDate = page.getByLabel('Expiration');
     this.cvv = page.getByPlaceholder('CVC');
     this.cardHolderName = page.getByPlaceholder('Full name on card');
     this.zipCode = page.getByLabel('ZIP');
     
     this.saveInfoCheckbox = page.locator('#enableStripePass');
     
     this.payBtn = page.getByTestId('submit-button-processing-label'); 
   }

   async submitPayment(details: PaymentDetails) {
     await this.payBtn.waitFor({ state: 'visible', timeout: 30000 });

     await this.emailAddress.fill(details.emailAddress, {force: true});
     await this.cardNumber.fill(details.cardNumber, {force: true});
     await this.expiryDate.fill(details.expiryDate, {force: true});
     await this.cvv.fill(details.cvv, {force: true});
     await this.cardHolderName.fill(details.cardHolderName, {force: true});
     
     if (details.zipCode && await this.zipCode.isVisible()) {
       await this.zipCode.fill(details.zipCode, {force: true});
     }

     if (await this.saveInfoCheckbox.isVisible() && await this.saveInfoCheckbox.isChecked()) {
         await this.saveInfoCheckbox.uncheck({ force: true });
     }

     await this.payBtn.click({force: true});
   }
}