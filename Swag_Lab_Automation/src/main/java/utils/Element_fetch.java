package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.qa.base.Base;

public class Element_fetch
{
	public WebElement getWebElement(String identifierType, String identifierValue)
	{
		switch(identifierType)
		{
		
		case "XPATH":
			return Base.driver.findElement(By.xpath(identifierValue));
			
		case "CSS":
			return Base.driver.findElement(By.cssSelector(identifierValue));
			
		case "CLASS":
			return Base.driver.findElement(By.className(identifierValue));
			
		case "NAME":
			return Base.driver.findElement(By.name(identifierValue));
			
		case "ID":
			return Base.driver.findElement(By.id(identifierValue));
			
		case "LINK_TEXT":
			return Base.driver.findElement(By.linkText(identifierValue));
			
		case "PARTIAL_LINKTEXT":
			return Base.driver.findElement(By.partialLinkText(identifierValue));
			
		case "TAG_NAME":
			return Base.driver.findElement(By.tagName(identifierValue));
			
			default:
				return null;
		
		}
	}
	
	public List<WebElement> getWebElements(String identifierType, String identifierValue)
	{
		switch(identifierType)
		{
		
		case "XPATH":
			return Base.driver.findElements(By.xpath(identifierValue));
			
		case "CSS":
			return Base.driver.findElements(By.cssSelector(identifierValue));
			
		case "CLASS":
			return Base.driver.findElements(By.className(identifierValue));
			
		case "NAME":
			return Base.driver.findElements(By.name(identifierValue));
			
		case "ID":
			return Base.driver.findElements(By.id(identifierValue));
			
		case "LINK_TEXT":
			return Base.driver.findElements(By.linkText(identifierValue));
			
		case "PARTIAL_LINKTEXT":
			return Base.driver.findElements(By.partialLinkText(identifierValue));
			
		case "TAG_NAME":
			return Base.driver.findElements(By.tagName(identifierValue));
			
			default:
				return null;
		
		}
	}
}
