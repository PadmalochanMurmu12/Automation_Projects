package com.qa.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddToCart {

	// NO static keyword here. 
	private WebDriver driver;
	private WebDriverWait wait;

	// Locators
	private By productName = By.xpath("//*[text()='Sauce Labs Bolt T-Shirt']");
	private By addtocartBtn = By.cssSelector("button.btn_primary.btn_inventory");
	private By cartBadge = By.cssSelector("a.shopping_cart_link");

	// Constructor strictly accepts WebDriver
	public AddToCart(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public WebElement getProductName() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
	}

	public WebElement getaddtocartBtn() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(addtocartBtn));
	}

	public WebElement getcartBadge() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
	}
}