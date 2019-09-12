package com.kodaksmarthome.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageSecurityCamera extends PageBase {

	public PageSecurityCamera() {

		PATH = "security-cameras/";
	}

	public PageSecurityCamera(PageBase P) {

		PATH			= "security-cameras/";
		this.baseURL	= P.baseURL;
		this.header		= P.header;
		this.footer		= P.footer;
	}

	/*
	 * ELEMENTS
	 */

	public WbElement getPageTitle() {

		String title = "//span[@class='ksm kred']";
		return new WbElement(By.xpath(title), "Page Title");
	}

	/*
	 * ACTIONS
	 */

	public void verifyPageContent() {

		verifyUrl();
		verifyPageHeader();
		verifyPageTitleDisplay();
		verifyPageFooter();
	}

	public void verifyPageTitleDisplay() {

		assertTrue(getPageTitle().isDisplayed());
	}
}
