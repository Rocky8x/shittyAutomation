package com.motorolachargers.tests;
import org.testng.annotations.BeforeMethod;
import com.ebn.automation.core.WbDriverManager;
import com.ebn.base.TestBaseCommon;
import com.motorolachargers.pages.PageBase;



public class TestBaseChargers extends TestBaseCommon {
	@BeforeMethod
	public void before() throws Exception {
		WbDriverManager.navigateToUrl((new PageBase()).baseURL);
	}
}
