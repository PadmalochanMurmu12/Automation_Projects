package com.qa.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer {

	private int count = 0;
	private static final int maxTry = 1; // Easily configurable

	@Override
	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) { 
			if (count < maxTry) {
				count++;
				return true; // Tells TestNG to re-run the test
			}
		}
		return false; // Stop retrying
	}
}