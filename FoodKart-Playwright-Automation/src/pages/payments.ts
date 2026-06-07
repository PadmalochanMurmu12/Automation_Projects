import { Locator, Page , expect} from "@playwright/test";
import { BasePage } from "./base-page";

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
  //  readonly stripeFrame: any; 
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
     await expect(this.emailAddress).toBeVisible({ timeout: 15000 });
     await this.emailAddress.fill(details.emailAddress);
     await this.cardNumber.fill(details.cardNumber);
     await this.expiryDate.fill(details.expiryDate);
     await this.cvv.fill(details.cvv);
     await this.cardHolderName.fill(details.cardHolderName);
     
     if (details.zipCode && await this.zipCode.isVisible()) {
       await this.zipCode.fill(details.zipCode);
     }

     if (await this.saveInfoCheckbox.isVisible() && await this.saveInfoCheckbox.isChecked()) {
         await this.saveInfoCheckbox.uncheck();
     }

     await this.payBtn.click();
   }
   
}