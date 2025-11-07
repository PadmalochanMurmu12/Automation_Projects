package sample;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Sample1 {
	
	static WebDriver driver;
	
	public static void launchBrowser()
	{
		driver= new ChromeDriver();
		driver.get("https://www.google.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}
	
	public static void sendQuery()
	{
		WebElement searchBar= driver.findElement(By.name("q"));
		searchBar.sendKeys("swag labs");
		List<WebElement> autoComplete= driver.findElements(By.xpath("//ul[@role= 'listbox']/li"));
		
		for(WebElement options: autoComplete)
		{
			if(options.getText().equalsIgnoreCase("swag labs"));
			options.click();
			break;
		}
	}
	
	public static void clickSearchResult()
	{
		WebElement searchResult= driver.findElement(By.xpath("//*[text()='Swag Labs']"));
		searchResult.click();
	}
	
	public static void main(String[] args)
	{
		launchBrowser();
		sendQuery();
		clickSearchResult();
	}
}
