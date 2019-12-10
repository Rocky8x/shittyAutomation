package com.ebn.automation.core;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.testng.ITestContext;

import com.cinatic.log.Log;

public class TestHelper {

	public static final String testArgsPrefix = "testArg.";

	/**
	 * Load all params provided in the xml file and command line input arguments (VM args),
	 * VM args have higher priority
	 * @param context 
	 * @return testParams
	 */
	public static Map<String, String> loadTestParams(ITestContext context) {

		// retrieve test parameter from command line input if there's any
		// if not, use the parameter from testNG xml file
		Map<String, String> testParams = context.getCurrentXmlTest().getAllParameters();

		Properties p = System.getProperties();
		for (String s : p.stringPropertyNames()) {
			if (s.startsWith(testArgsPrefix)) {
				testParams.put(s.split("\\.")[1], p.getProperty(s));
			}
		}
		return testParams;
	}

	public static void printParams(Map<String, String> params) {

		Log.info("Parameters:");
		Set<String> keys = params.keySet();
		for (String key : keys) { System.out.println("   " + key + " : " + params.get(key)); }
	}
}
