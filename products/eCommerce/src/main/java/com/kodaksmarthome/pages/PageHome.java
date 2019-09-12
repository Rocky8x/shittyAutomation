package com.kodaksmarthome.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageHome extends PageBase {

	public PageHome() {

		PATH = "";
	}

	public PageHome(PageBase P) {

		PATH			= "";
		this.baseURL	= P.baseURL;
		header			= P.header;
		footer			= P.footer;
	}

	/*
	 * ELEMENTS
	 */

	public WbElement getCategoryBabyMonitors() {

		String xpath = "//div[@class='col link-babycam']";
		return new WbElement(By.xpath(xpath), "Baby monitors category in page body");
	}

	public WbElement getCategorySecurityCamera() {

		String xpath = "//div[@class='col link-seccam']";
		return new WbElement(By.xpath(xpath), "Security cameras category in page body");
	}

	public WbElement getAppLink() {

		String xpath = "//div[@class='col link-app']";
		return new WbElement(By.xpath(xpath), "Link to install mobile app in page body");
	}

	public WbElement getExploreBabyMonitorsButton() {

		String xpath = "//div[@class='video-banner']//a[@class='btn btn-red']";
		return new WbElement(By.xpath(xpath), "Explore baby monitors button");
	}

	public WbElement getCustomerSupportBanner() {

		String xpath = "//div[@class='full-banner shortcuts-support']//h2";
		return new WbElement(By.xpath(xpath), "Customer support section banner");

	}

	public WbElement getReadFAQsButton() {

		String xpath = "//div[@class='full-banner shortcuts-support']//div[@class='col link-faqs']";
		return new WbElement(By.xpath(xpath), "CS section: FAQs button");
	}

	public WbElement getEmailSupportButton() {

		String xpath = "//div[@class='full-banner shortcuts-support']//div[@class='col link-email']";
		return new WbElement(By.xpath(xpath), "CS section: Email support button");
	}

	public WbElement getAlwaysInTheMomentText() {

		String xpath = "//div[@class='video-banner-content']//div[@class='container light-bg']/h2";
		return new WbElement(By.xpath(xpath), "Always in the moment slogan");
	}

	public WbElement getBbMonitorTextInBanner() {

		String xpath = "//div[@class='video-banner-content']//div[@class='container light-bg']/p";
		return new WbElement(By.xpath(xpath), "Kodak Baby monitors text");
	}
	
	public WbElement getVideoInBanner() {

		String xpath = "//video[@id='homepage-video']/source";
		return new WbElement(By.xpath(xpath), "Video Banner");
	}
	/*
	 * ACTIONS
	 */

	public void verifyPageContent() {

		verifyUrl();
		verifyPageHeader();
		verifyVideoBannerInHomePageDisplay();
		verifyPageFooter();
	}

	public void verifyVideoBannerInHomePageDisplay() {

		assertTrue(getVideoInBanner() != null);
	}
}
