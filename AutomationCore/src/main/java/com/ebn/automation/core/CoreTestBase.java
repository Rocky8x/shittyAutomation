package com.ebn.automation.core;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.cinatic.FileHelper;
import com.cinatic.log.Log;

public class CoreTestBase {

	public static Map<String, String> testParams;

	@BeforeSuite
	public void beforeSuiteCore(ITestContext icontext) throws Exception {

		testParams = TestHelper.loadTestParams(icontext);

		try {
			FileHelper.clearFolder("/html/");
			FileHelper.clearFolder("/allure-results/");
		} catch (Exception e) {
			Log.info(String.format("Got exception while clean up previous report: %s",
					e.getMessage()));
		}
	}

	@BeforeMethod
	public void beforeMethodCore(Method method) throws Exception {

		Log.info(String.format("------ START: %s: %s ------",
				method.getDeclaringClass().getSimpleName(), method.getName()));
	}

	@AfterMethod
	public void afterMethodCore(ITestResult result) throws Exception {

		Log.info("---------------------- END: " + result.getMethod().getMethodName()
				+ " ----------------------");

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
	}

}
