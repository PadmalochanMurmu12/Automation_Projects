package sample;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class Sample5
{
    static WebDriver driver;
    
    private static void init()
    {
    	ChromeOptions options = new ChromeOptions();
    	Map<String, Object> prefs = new HashMap<>();
    	prefs.put("credentials_enable_service", false);
    	prefs.put("profile.password_manager_enabled", false);  // old + new prefs
    	prefs.put("profile.default_content_setting_values.notifications", 2); // disable notifications
    	options.setExperimentalOption("prefs", prefs);

    	// optional: start with a fresh profile (avoid user profile that has chrome extension popups)
    	options.addArguments("--no-default-browser-check");
    	options.addArguments("--disable-extensions");
    	options.addArguments("--disable-popup-blocking");
    	options.addArguments("--disable-notifications");

    	WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/v1/");
    }
    
    private static void login()
    {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce", Keys.ENTER);
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        init();
        login();
        Thread.sleep(5000); // Wait to see result
        driver.quit();
    }
}