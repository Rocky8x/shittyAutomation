package com.motorolaintl.tests;

import org.testng.annotations.BeforeMethod;

import com.ebn.automation.core.WbDriverManager;
import com.ebn.base.TestBaseCommon;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageStoreDetect;

public class TestBaseMotoIntl extends TestBaseCommon {
	@BeforeMethod
	public void before() throws Exception {
		WbDriverManager.navigateToUrl((new PageBase()).baseURL);
		try {
			PageStoreDetect.getDismissButton().click();
		} catch (Exception e) {
		}
	}
}
