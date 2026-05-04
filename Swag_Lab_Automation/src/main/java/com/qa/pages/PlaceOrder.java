package com.qa.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Removed: extends Base
public class PlaceOrder {

	// Encapsulated and Thread-Safe
	private WebDriver driver;
	private WebDriverWait wait;

	public PlaceOrder(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	// Locators strictly encapsulated
	private By firstName = By.id("first-name");
	private By lastName = By.id("last-name");
	private By zipCode = By.id("postal-code");
	private By continueBtn = By.cssSelector("div.checkout_buttons input[type='submit']");
	private By finishBtn = By.cssSelector("button#finish");

	// Optional: Add locator for the success message to use in assertions
	private By completeHeader = By.cssSelector("h2.complete-header");

	public WebElement getfirstName() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
	}

	public WebElement getlastName() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
	}

	public WebElement getZipCode() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(zipCode));
	}

	public WebElement getcontinueBtn() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(continueBtn));
	}

	public WebElement getFinishBtn() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(finishBtn));
	}

	public WebElement getCompleteHeader() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
	}
}