package com.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.Base;

public class AddToCart extends Base
{
	static WebDriver driver;
	public WebDriverWait wait;

	public AddToCart(WebDriver driver)
	{
		this.driver= driver;
		this.wait= new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	By productName= By.xpath("//*[text()='Sauce Labs Bolt T-Shirt']");
	By addtocartBtn= By.cssSelector("button.btn_primary.btn_inventory");
	By cartBadge= By.cssSelector("a.shopping_cart_link");
	
	public WebElement getProductName()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
	}
	
	public WebElement getaddtocartBtn()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(addtocartBtn));
	}
	
	public WebElement getcartBadge()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
	}
}