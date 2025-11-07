package com.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

public class LoginPage
{
	static WebDriver driver;
	public WebDriverWait wait;

	public LoginPage(WebDriver driver)
	{
		this.driver= driver;
		this.wait= new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	//	Locators
	public By userName= By.id("user-name");
	public By password= By.id("password");
	public By loginBtn= By.id("login-button");

	public WebElement getUserName()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(userName));
	}
	
	public WebElement getPassword()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(password));
	}
	
	public WebElement getLoginBtn()
	{
		return wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn));
	}
}
