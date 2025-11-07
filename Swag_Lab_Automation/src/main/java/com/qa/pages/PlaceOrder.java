package com.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.Base;

public class PlaceOrder extends Base
{
	static WebDriver driver;
	public WebDriverWait wait;

	public PlaceOrder(WebDriver driver)
	{
		this.driver= driver;
		this.wait= new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	By firstName= By.id("first-name");
	By lastName= By.id("last-name");
	By zipCode= By.id("postal-code");
	By continueBtn= By.cssSelector("div.checkout_buttons input[type='submit']");
	By finishBtn= By.cssSelector("button#finish");

	public WebElement getfirstName()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
	}

	public WebElement getlastName()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
	}

	public WebElement getZipCode()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(zipCode));
	}

	public WebElement getcontinueBtn()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(continueBtn));
	}
	
	public WebElement getFinishBtn()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(finishBtn));
	}
}