package com.qa.utils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import io.qameta.allure.Allure;
import com.qa.base.Base;

public class Suite_Listener implements ITestListener, IAnnotationTransformer {

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = Base.getDriver();

		if (driver != null) {
			Allure.addAttachment(result.getMethod().getMethodName() + " Failure Screenshot", 
					new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
			System.out.println("📸 Screenshot attached to Allure report for failed test: " + result.getName());
		} else {
			System.out.println("❌ Driver is null, cannot take screenshot");
		}
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// Automatically applies your RetryAnalyzer to every single test method
		annotation.setRetryAnalyzer(RetryTest.class);
	}

}