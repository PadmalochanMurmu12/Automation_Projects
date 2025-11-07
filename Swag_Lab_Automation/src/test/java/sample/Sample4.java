package sample;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class Sample4
{
	private static WebDriver driver;
	
	@BeforeMethod
	private void init()
	{
		driver= new ChromeDriver();
		driver.get("https://www.saucedemo.com/v1/");
		driver.manage().window().maximize();
	}
	
	@Test(dataProviderClass= Sample3.class, dataProvider="testData")
	private void Login(String username, String pass)
	{
		driver.findElement(By.id("user-name")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(pass, Keys.ENTER);
	}
}