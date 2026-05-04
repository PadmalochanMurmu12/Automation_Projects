package com.qa.pageEvents;

import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import com.qa.base.Base;
import com.qa.pages.LoginPage;

public class LoginPageEvents {

	private static final Logger logger = LogManager.getLogger(LoginPageEvents.class);
	private LoginPage lp;
	private WebDriver driver;

	// Constructor strictly accepts WebDriver
	public LoginPageEvents(WebDriver driver) {
		this.driver = driver;
		this.lp = new LoginPage(driver);
	}

	public void enterCreds() {
		logger.info("Attempting to enter credentials from properties file.");
		lp.getUserName().sendKeys(Base.prop.getProperty("username"));
		lp.getPassword().sendKeys(Base.prop.getProperty("password"), Keys.ENTER);
	}

	public boolean isLoginSuccessful() {
		logger.info("Verifying login success via URL validation.");
		String expectedUrl = Base.prop.getProperty("expectedURL");
		String actualUrl = driver.getCurrentUrl();
		
		if(actualUrl.equals(expectedUrl)) {
            logger.info("Login verified successfully. Redirected to: " + actualUrl);
            return true;
        } else {
            logger.error("Login verification FAILED. Expected: " + expectedUrl + " but got: " + actualUrl);
            return false;
        }
	}
}