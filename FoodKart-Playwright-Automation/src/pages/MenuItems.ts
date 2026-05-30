import { Locator, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class MenuItems extends BasePage {
    readonly cartBadge: Locator;

    constructor(page: Page) {
        super(page);
        this.cartBadge = page.getByTestId('nav-cart-link');
    }

  // --- STATIC METHODS ---

  async addSpecificItemToCart(itemName: string) {
    console.log(`Locating and adding: ${itemName}`);
    const itemCard = this.page.locator('[data-testid^="food-card-"]')
        .filter({ hasText: itemName }); 
        
    const addToCartBtn = itemCard.getByRole('button', { name: 'Add to Cart' });
    
    await addToCartBtn.click();
  }

  // --- DYNAMIC SINGLE-ITEM SCRAPER ---

  async addRandomItemToCart(): Promise<string> {
    console.log('Locating all available menu items dynamically...');
    const itemCards = this.page.locator('[data-testid^="food-card-"]');
    await itemCards.first().waitFor({ state: 'visible' });
    
    const count = await itemCards.count();
    if (count === 0) {
      throw new Error('Critical Failure: No food items rendered on the menu!');
    }

    const randomIndex = Math.floor(Math.random() * count);
    console.log(`Dynamically selected item index: ${randomIndex} out of ${count}`);
    
    const randomCard = itemCards.nth(randomIndex);
    
    const itemNameLocator = randomCard.locator('h3'); 
    const itemName = await itemNameLocator.innerText();
    
    console.log(`Extracted Random Item Name: ${itemName}`);

    const addToCartBtn = randomCard.getByRole('button', { name: 'Add to Cart' });
    await addToCartBtn.click();
    
    return itemName; 
  }

  // --- DYNAMIC MULTI-ITEM SCRAPER ---

  async addMultipleRandomItemsToCart(numberOfItems: number): Promise<string[]> {
    console.log(`Locating menu items to dynamically select ${numberOfItems} unique dishes...`);
    const itemCards = this.page.locator('[data-testid^="food-card-"]');
    
    await itemCards.first().waitFor({ state: 'visible' });
    const count = await itemCards.count();

    if (count < numberOfItems) {
      throw new Error(`Critical Failure: You asked for ${numberOfItems} random items, but only ${count} are on the menu. Do the math.`);
    }

    const selectedIndices = new Set<number>();
    while (selectedIndices.size < numberOfItems) {
      selectedIndices.add(Math.floor(Math.random() * count));
    }

    console.log(`Dynamically selected indices: ${Array.from(selectedIndices).join(', ')}`);
    
    // Array to store our dynamically scraped names
    const scrapedItemNames: string[] = [];

    for (const index of selectedIndices) {
      const randomCard = itemCards.nth(index);
      
      // Scrape the name before we click
      const itemNameLocator = randomCard.locator('h3'); 
      const itemName = await itemNameLocator.innerText();
      scrapedItemNames.push(itemName);

      const addToCartBtn = randomCard.getByRole('button', { name: 'Add to Cart' });
      
      await addToCartBtn.click();
      await this.page.waitForTimeout(100); // Small buffer for UI animation
    }
    
    console.log(`Extracted Random Items: ${scrapedItemNames.join(', ')}`);
    
    // Hand the array of names back to the framework
    return scrapedItemNames;
  }

  async clickCartBadge() {
    await this.cartBadge.click();
  }
}