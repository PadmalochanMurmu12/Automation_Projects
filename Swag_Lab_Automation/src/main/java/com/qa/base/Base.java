package com.qa.base;

import java.io.*;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;  // ✅ Add import
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Base
{
	String path = System.getProperty("user.dir");
	public static WebDriver driver;
	public static FileInputStream file1, file2;
	public static Properties prop = new Properties();
	public static Properties loc = new Properties();
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;

	@BeforeTest
	public void beforeTest()
	{
//		sparkReporter = new ExtentSparkReporter(path + File.separator + "Reports" + File.separator + "TestReports.html");
//		src\test\resources\Reports
		sparkReporter = new ExtentSparkReporter(path +"/src/test/resources/Reports"+"TestReports.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		sparkReporter.config().setTheme(Theme.STANDARD);
		extent.setSystemInfo("HostName", "null");
		extent.setSystemInfo("Username", "Root");
		extent.setSystemInfo("OS", "Windows 11");
		sparkReporter.config().setDocumentTitle("Test Report");
		sparkReporter.config().setReportName("Automation Test Result");
	}

	@BeforeMethod
	@Parameters("browser")
	public void beforeMethod(String browser, Method testMethod) throws IOException
	{
		logger = extent.createTest(testMethod.getName());
		setupDriver(browser);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	}

	@AfterMethod
	public void afterMethod(ITestResult result)
	{
		if(result.getStatus() == ITestResult.FAILURE)
		{
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case failed", ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " Test case failed", ExtentColor.RED));
		}
		else if(result.getStatus() == ITestResult.SKIP)
		{
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test case Skipped", ExtentColor.ORANGE));
		}
		else if(result.getStatus() == ITestResult.SUCCESS)
		{
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test case Passed", ExtentColor.GREEN));
		}

		if(driver != null)
		{
			driver.quit();
			driver = null;
		}
	}

	@AfterTest
	public void afterTest()
	{
		extent.flush();
	}

	public void setupDriver(String browser) throws IOException
	{
		if (prop.isEmpty())
		{
			file1 = new FileInputStream(path + "/src/main/java/com/qa/config/config.properties");
			file2 = new FileInputStream(path + "/src/main/java/com/qa/config/locators.properties");
			prop.load(file1);
			loc.load(file2);
		}

		if(browser.equalsIgnoreCase("chrome"))
		{
			// ✅ Add ChromeOptions to disable password popup
			ChromeOptions options = new ChromeOptions();

			// Additional safeguards
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			options.addArguments("--disable-blink-features=AutomationControlled");
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

			driver = new ChromeDriver(options);  // ✅ Pass options to ChromeDriver
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("edge"))
		{
			driver = new EdgeDriver();
		}
	}
}