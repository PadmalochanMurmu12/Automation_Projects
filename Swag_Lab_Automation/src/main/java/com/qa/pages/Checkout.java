package com.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.Base;

public class Checkout extends Base
{
	static WebDriver driver;
	public WebDriverWait wait;

	public Checkout(WebDriver driver)
	{
		this.driver= driver;
		this.wait= new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	By checkoutBtn= By.cssSelector("button#checkout");
	
	public WebElement getcheckoutBtn()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutBtn));
	}
	
	
}
