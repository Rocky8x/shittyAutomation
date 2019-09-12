package com.ebn.base;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;

public class TestBaseCommon {

	WebDriver				driver;
	public static String	BROWSER;

	@Parameters({ "browser" })
	@BeforeSuite
	public void beforeSuiteBaseCommon(String browser) {

		BROWSER = System.getProperty("browser", browser);
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	}
//
//	@BeforeTest
//	public void beforeTestBaseCommon() {
//
//		WebDriverManager.newBrowser(BROWSER);
//		driver = WebDriverManager.getDriver();
//		driver.manage().window().maximize();
//	}
//
//	@AfterTest
//	public void afterTestBaseCommon() {
//
//		WebDriverManager.closeBrowser();
//	}
	
	@BeforeClass
	public void beforeClassBaseCommon() {

		WbDriverManager.newBrowser(BROWSER);
		driver = WbDriverManager.getDriver();
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClassBaseCommon() {

		WbDriverManager.closeBrowser();
	}
	
	

	@BeforeMethod
	public void beforeMethod(Method method) throws Exception {

		Log.info(String.format("---------------------- START: %s ----------------------",
				method.getName()));
	}

	
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception {

		Log.info("---------------------- END: " + result.getMethod().getMethodName()
				+ " ----------------------");
		String testName = result.getMethod().getMethodName();
		if (result.getStatus() != 1) {
			Log.info("----- Trace: ");
			result.getThrowable().printStackTrace();
		}

		String status = "UNKNOWN";
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "SUCCESS";
			break;
		case ITestResult.FAILURE:
			status = "FAILURE";
			break;
		case ITestResult.SKIP:
			status = "SKIPPED";
			break;
		default:
			break;
		}
		Log.info("  | Result : " + status);
		Log.info(result.getInstanceName());
		System.out.println();
		String screenshot = "test-output/html/" + result.getMethod().getMethodName() + ".png";
		WbDriverManager.takeScreenShot(screenshot);
	}
}
