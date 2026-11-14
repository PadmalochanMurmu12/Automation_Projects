package com.qa.utils;

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
			return Base.getDriver().findElement(By.xpath(identifierValue));
			
		case "CSS":
			return Base.getDriver().findElement(By.cssSelector(identifierValue));
			
		case "CLASS":
			return Base.getDriver().findElement(By.className(identifierValue));
			
		case "NAME":
			return Base.getDriver().findElement(By.name(identifierValue));
			
		case "ID":
			return Base.getDriver().findElement(By.id(identifierValue));
			
		case "LINK_TEXT":
			return Base.getDriver().findElement(By.linkText(identifierValue));
			
		case "PARTIAL_LINKTEXT":
			return Base.getDriver().findElement(By.partialLinkText(identifierValue));
			
		case "TAG_NAME":
			return Base.getDriver().findElement(By.tagName(identifierValue));
			
			default:
				return null;
		
		}
	}
	
	public List<WebElement> getWebElements(String identifierType, String identifierValue)
	{
		switch(identifierType)
		{
		
		case "XPATH":
			return Base.getDriver().findElements(By.xpath(identifierValue));
			
		case "CSS":
			return Base.getDriver().findElements(By.cssSelector(identifierValue));
			
		case "CLASS":
			return Base.getDriver().findElements(By.className(identifierValue));
			
		case "NAME":
			return Base.getDriver().findElements(By.name(identifierValue));
			
		case "ID":
			return Base.getDriver().findElements(By.id(identifierValue));
			
		case "LINK_TEXT":
			return Base.getDriver().findElements(By.linkText(identifierValue));
			
		case "PARTIAL_LINKTEXT":
			return Base.getDriver().findElements(By.partialLinkText(identifierValue));
			
		case "TAG_NAME":
			return Base.getDriver().findElements(By.tagName(identifierValue));
			
			default:
				return null;
		
		}
	}
}
