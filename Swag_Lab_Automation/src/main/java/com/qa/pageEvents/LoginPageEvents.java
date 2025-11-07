package com.qa.pageEvents;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qa.base.Base;
import com.qa.pages.LoginPage;

public class LoginPageEvents extends Base
{
	LoginPage lp;
	WebDriver driver;

	public LoginPageEvents(WebDriver driver) {
		this.driver = driver;
		lp = new LoginPage(driver);
	}

	public void enterCreds()
	{
		lp.getUserName().sendKeys(prop.getProperty("username"));
		lp.getPassword().sendKeys(prop.getProperty("password"), Keys.ENTER);
	}

//	public void validateSuccessfulLogin()
//	{
//		String expected_url = prop.getProperty("expectedURL");
//		String actual_url = driver.getCurrentUrl();
//
//		Assert.assertEquals(actual_url, expected_url, "Login failed - URL mismatch");
//	}
}