package com.ebn.base;

import static org.testng.Assert.assertEquals;

import com.ebn.automation.core.WbDriverManager;

public class PageBaseCommon {
	public String	baseURL	= "";
	public String	PATH	= "";

	public void verifyUrl() {
		assertEquals(WbDriverManager.getCurrentUrl(), getUrl());
	}

	public void openPage() {
		WbDriverManager.navigateToUrl(baseURL + PATH);
	}
	
	public void openPage(String url) {
		WbDriverManager.navigateToUrl(url);
	}
	
	public String getUrl() {
		return baseURL + PATH;
	}
	
	public void goBack() {

		WbDriverManager.getDriver().navigate().back();
	}
}
