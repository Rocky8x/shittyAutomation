package com.kodaksmarthome.tests;

import org.testng.annotations.BeforeMethod;

import com.ebn.automation.core.WbDriverManager;
import com.ebn.base.TestBaseCommon;

public class TestBaseKodak extends TestBaseCommon{
	
	public String baseURL = "https://kodaksmarthome.com";
	
	@BeforeMethod
	public void setup() {
		WbDriverManager.getDriver().navigate().to(baseURL);
	}
}
