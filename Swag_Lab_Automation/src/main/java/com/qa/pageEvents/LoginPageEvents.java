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
        // Fetching via the Smart Fetcher so it pulls from GitHub Secrets
        String user = Base.getSecureConfig("username");
        String pass = Base.getSecureConfig("password");
        
        // Feed the secure strings into your LoginPage (lp) elements
        lp.getUserName().sendKeys(user); // Replace getUsernameField() with your actual method name
        lp.getPassword().sendKeys(pass); // Replace getPasswordField() with your actual method name
    }

	public boolean isLoginSuccessful() {
		logger.info("Verifying login success via URL validation.");
		String expectedUrl = Base.getSecureConfig("expectedURL");
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