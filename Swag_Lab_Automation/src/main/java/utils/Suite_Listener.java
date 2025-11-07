package utils;

import java.io.*;
import java.lang.reflect.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import com.qa.base.Base;

public class Suite_Listener implements ITestListener, IAnnotationTransformer
{
    String path = System.getProperty("user.dir");
    
    public void onTestFailure(ITestResult result)
    {
        if (Base.driver != null)
        {
            String fileName = (path + File.separator + "Screenshots" + File.separator + result.getMethod().getMethodName());
            File file = ((TakesScreenshot)Base.driver).getScreenshotAs(OutputType.FILE);
            
            try {
                FileUtils.copyFile(file, new File(fileName + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Driver is null, cannot take screenshot");
        }
    }
    
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod)
    {
        annotation.setRetryAnalyzer(RetryTest.class);
    }
}