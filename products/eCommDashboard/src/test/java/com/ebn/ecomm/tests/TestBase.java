package com.ebn.ecomm.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;

import com.cinatic.log.Log;

public class TestBase {
	@BeforeMethod
	public void beforeMethod(Method method) throws Exception {
		Log.info(String.format("---------------------- START: %s ----------------------", method.getName()));
	}
}
