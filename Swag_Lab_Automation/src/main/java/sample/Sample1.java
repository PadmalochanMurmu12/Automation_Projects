//package sample;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//public class Sample1
//{
//	private static WebDriver driver;
//
//	public static void main(String[] args)
//	{
//		String itemsNeeded= "Sauce Labs Backpack, Sauce Labs Bike Light,Sauce Labs Bolt T-Shirt,Sauce Labs Fleece Jacket";
//
//		String items[]= itemsNeeded.split(",");
//	}
//
//	private void launchBrowser()
//	{
//		driver= new ChromeDriver();
//		driver.get("https://www.saucedemo.com/");
//	}
//
//	private void login()
//	{
//		driver.findElement(By.id("user-name")).sendKeys("standard_user",Keys.SHIFT);
//		driver.findElement(By.id("password")).sendKeys("secret_sauce",Keys.ENTER);
//	}
//
//	private void addProducts()
//	{
//		
//		int j= 0;
//		List<WebElement> productNames= driver.findElements(By.cssSelector("div.inventory_item"));
//		System.out.println("Total available products are"+productNames.size());
//		
//		for(int i=0; i<productNames.size();i++)
//		{
//			String productName[]= productNames.get(i).getText().split("-");
//			String formattedName= productName[0].trim();
//			List myProducts= Arrays.asList(items);
//		}
//
//	}
//}