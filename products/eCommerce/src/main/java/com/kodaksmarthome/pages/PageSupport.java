package com.kodaksmarthome.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageSupport extends PageBase {

	public PageSupport() {

		baseURL	= "https://support.kodaksmarthome.com/";
		PATH	= "hc/en-us";
	}

	public PageSupport(PageBase P) {

		baseURL		= "https://support.kodaksmarthome.com/";
		PATH		= "hc/en-us";
		this.header	= P.header;
		this.footer	= P.footer;
	}

	/*
	 * ELEMENTS
	 */

	public WbElement getSubmitRequestLink() {

		String link = "//a[@class='submit-a-request']";
		return new WbElement(By.xpath(link), "Submit a Request");
	}

	public WbElement getSignInLink() {

		String link = "//a[@class='login']";
		return new WbElement(By.xpath(link), "Sign In");
	}

	public WbElement getSearchField() {

		String field = "//form[@role='search']";
		return new WbElement(By.xpath(field), "Search field");
	}

	public WbElement getLanguageSelector() {

		String xpath = "//div[@class='footer-language-selector']";
		return new WbElement(By.xpath(xpath), "Language selector");
	}

	public WbElement getKodakSmartHomeLink() {

		String link = "//div[@class='footer-inner']/a";
		return new WbElement(By.xpath(link), "Kodak Link");
	}

	/*
	 * ACTIONS
	 */

	public void verifyPageHeader() {

		assertTrue(header.getMainLogo().isDisplayed());
		assertTrue(getSignInLink().isDisplayed());
		assertTrue(getSubmitRequestLink().isDisplayed());
	}

	public void verifyPageFooter() {

		assertTrue(getLanguageSelector().isDisplayed());
		assertTrue(getKodakSmartHomeLink().isDisplayed());
	}

	public void verifyPageContent() {

		verifyUrl();
		verifyPageHeader();
		verifySupportPageDisplay();
		verifyPageFooter();
	}

	public void verifySupportPageDisplay() {

		assertTrue(getSearchField().isDisplayed());
	}
}
