package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer
{
	int count=0, retryCount= 1;
	public boolean retry(ITestResult result)
	{
		while(count<retryCount)
		{
			count++;
			return true;
		}
		
		return false;
	}
}