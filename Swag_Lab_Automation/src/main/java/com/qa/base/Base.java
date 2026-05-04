package com.qa.base;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Optional;

import java.io.*;
import java.time.Duration;
import java.util.*;

public class Base {
    
    protected String path = System.getProperty("user.dir");
    
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static Properties prop = new Properties();
    public static Properties loc = new Properties();

    public static WebDriver getDriver() {
        return driver.get();
    }

    // ==========================================
    // THE SMART FETCHER (NEW)
    // ==========================================
    public static String getSecureConfig(String key) {
        // 1. Check if the Cloud (Maven -D flag) has the secret
        String value = System.getProperty(key);
        
        // 2. If null (running locally), grab it from the local properties file
        if (value == null || value.trim().isEmpty()) {
            value = prop.getProperty(key);
        }
        
        // 3. Failsafe: If it's STILL null, throw an error so you aren't debugging a ghost
        if (value == null) {
            throw new RuntimeException("CRITICAL: Configuration key '" + key + "' is missing from both Cloud Secrets and Local Properties!");
        }
        
        return value;
    }

    @BeforeSuite
    public void loadConfig() {
        // 1. Attempt to load the local Config file (Will fail in the Cloud, which is expected)
        try {
            FileInputStream file1 = new FileInputStream(path + "/src/main/java/com/qa/config/config.properties");
            prop.load(file1);
        } catch (IOException e) {
            System.out.println("WARNING: config.properties not found locally. Framework will rely entirely on Cloud System Properties (Secrets).");
        }

        // 2. Attempt to load the Locators file (Must NEVER fail, as this file is pushed to GitHub)
        try {
            FileInputStream file2 = new FileInputStream(path + "/src/main/java/com/qa/config/locators.properties");
            loc.load(file2);
        } catch (IOException e) {
            // If locators are missing, the test cannot proceed under any circumstances
            throw new RuntimeException("CRITICAL FAILURE: locators.properties is missing from the repository! " + e.getMessage());
        }
    }

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethod(@Optional("chrome") String browser) {
        setupDriver(browser);
        WebDriver currentDriver = getDriver(); 
        
        currentDriver.manage().window().maximize();
        currentDriver.manage().deleteAllCookies(); 
        
        // UPDATED: Now uses the Smart Fetcher instead of direct prop fetching
        currentDriver.get(getSecureConfig("url")); 
        
        currentDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        currentDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterMethod
    public void afterMethod(ITestResult result) { 
        WebDriver currentDriver = getDriver();
        
        if (result.getStatus() == ITestResult.FAILURE && currentDriver != null) { 
            Allure.addAttachment("Screenshot on Failure",
                new ByteArrayInputStream(((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.BYTES)));
        }

        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }

    public void setupDriver(String browser) {
        // ==========================================
        // CI/CD AUTO-DETECTOR (NEW)
        // ==========================================
        boolean isCI = System.getenv("GITHUB_ACTIONS") != null;
        boolean isHeadless = isCI || Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            if (isHeadless) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
            }
            driver.set(new ChromeDriver(options));
            
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("-headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");
            }
            driver.set(new FirefoxDriver(options));
            
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
            }
            driver.set(new EdgeDriver(options));
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }
}