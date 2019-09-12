package com.kodaksmarthome.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageCloudPortal extends PageBase {

	public PageCloudPortal() {

		baseURL	= "https://app-hk.kodaksmarthome.com/web/#/user/login";
		PATH	= "";
	}

	public PageCloudPortal(PageBase P) {

		baseURL	= "https://app-hk.kodaksmarthome.com/web/#/user/login";
		PATH	= "";
		this.footer		= P.footer;
		this.header		= P.header;
	}

	/*
	 * ELEMENTS
	 */

	public WbElement getDataProtectionPolicyLink() {

		String ele = "//a[contains(@href, 'dataprotectionpolicy')]";
		return new WbElement(By.xpath(ele), "Data Protection Policy");
	}

	public WbElement getDataRequestPolicy() {

		String ele = "//a[contains(@href, 'datarequestpolicy')]";
		return new WbElement(By.xpath(ele), "Data Request Policy");
	}

	/*
	 * ACTIONS
	 */

	public void verifyPageHeader() {

		assertTrue(header.getMainLogo().isDisplayed());
		assertTrue(header.getAccountIcon().isDisplayed());
		assertTrue(header.getFlagStoreIcon().isDisplayed());
	}

	public void verifyPageFooter() {

		assertTrue(footer.getTermServiceLink().isDisplayed());
		assertTrue(footer.getPrivacyPolicyLink().isDisplayed());
		assertTrue(footer.getFacebookIcon().isDisplayed());
		assertTrue(footer.getTwitterIcon().isDisplayed());
		assertTrue(footer.getInstagramIcon().isDisplayed());
		assertTrue(footer.getYoutubeIcon().isDisplayed());
		assertTrue(getDataProtectionPolicyLink().isDisplayed());
		assertTrue(getDataRequestPolicy().isDisplayed());
	}

	public void verifyPageContent() {

		verifyUrl();
		verifyPageHeader();
		verifyPageFooter();
	}
}
