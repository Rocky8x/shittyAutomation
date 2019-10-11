package com.motorolaintl.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class GroupFooter {
	public WbElement getMotorolaLogo() {
		String xpath = "//div[@class='footer-container']//div[@class='footer-logo']";
		return new WbElement(By.xpath(xpath), "Footer: Motorola image");
	}

	public WbElement getCountrySwitch() {
		String xpath = "//div[@class='footer']//div[contains(@class,'storeSwitcher')]/button";
		return new WbElement(By.xpath(xpath), "Footer: Country switch");
	}

	public WbElement getCountrySwitchLabel() {
		String xpath = "//div[@class='footer']//span[@class='store-switcher-label']";
		return new WbElement(By.xpath(xpath), "Footer: Country switch label");
	}

	public WbElement getInfomationLabel() {
		String xpath = "//div[@class='footer-title']";
		return new WbElement(By.xpath(xpath), "Footer: Infomation label");
	}

	public WbElement getSupportAndContactLink() {
		String xpath = "//div[@class='links']/a[1]";
		return new WbElement(By.xpath(xpath), "Footer: Support & Contact link");
	}

	public WbElement getPrivacyPolicyLink() {
		String xpath = "//div[@class='links']/a[2]";
		return new WbElement(By.xpath(xpath), "Footer: Privacy policy link");
	}

	public WbElement getSocialLinkFacebook() {
		String xpath = "//div[@class='footer-social']/a[contains(@class,'facebook')]";
		return new WbElement(By.xpath(xpath), "Footer: Facebook page link");
	}

	public WbElement getSocialLinkTwitter() {
		String xpath = "//div[@class='footer-social']/a[contains(@class,'twitter')]";
		return new WbElement(By.xpath(xpath), "Footer: Twitter page link");
	}

	public WbElement getSocialLinkInstagram() {
		String xpath = "//div[@class='footer-social']/a[contains(@class,'instagram')]";
		return new WbElement(By.xpath(xpath), "Footer: Instagram page link");
	}

	public WbElement getCopyrightLabel() {
		String xpath = "//div[contains(@class,'footer-copy')]";
		return new WbElement(By.xpath(xpath), "Footer: Instagram page link");
	}

	public WbElement getPoweredByLabel() {
		String xpath = "//a[contains(@class,'powered-by-toggle')]";
		return new WbElement(By.xpath(xpath), "Footer: Powered by");
	}
	
	public WbElement getPrivacyPolicyOnPoweredByPopUp() {
		String xpath = "//div[@class='powered-by-content']//a[text()='Privacy Policy']";
		return new WbElement(By.xpath(xpath), "Footer: Privacy Policy link on PoweredBy PopUp");
	}
	
	public WbElement getVisitEbuyNowOnPoweredByPopUp() {
		String xpath = "//div[@class='powered-by-content']//a[text()='Visit eBuyNow.com']";
		return new WbElement(By.xpath(xpath), "Footer: Visit ebuynow link on PoweredBy PopUp");
	}
	
	public WbElement getGotoMotorolaOnPoweredByPopUp() {
		String xpath = "//div[@class='powered-by-content']//a[text()='Go to Motorola.com']";
		return new WbElement(By.xpath(xpath), "Footer: Goto Motorola.com link on PoweredBy PopUp");
	}
	
	public WbElement getMotorolaPrivacyPolicyOnPoweredByPopUp() {
		String xpath = "//div[@class='powered-by-content']//a[contains(text(),'Go to Motorola.com Privacy Policy')]";
		return new WbElement(By.xpath(xpath), "Footer: Motorola Privacy Policy link on PoweredBy PopUp");
	}
	
}
