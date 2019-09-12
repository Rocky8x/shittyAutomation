package com.motorolachargers.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class GroupFooter {

	public WbElement getChangeCountry() {
		String xpath = "//div[@class='footer-logo-wrapper row']//div[@class='regionSelect']";
		return new WbElement(By.xpath(xpath), "Change country on Footer");
	}

	public WbElement getPrivacyPolicyTxt() {
		String xpath = "//div[@class='footer-middle']//*[contains(@href,'/privacy-policy')]";
		return new WbElement(By.xpath(xpath), "Footer Privacy-Policy text");
	}

	public WbElement getTermofServiceTxt() {
		String xpath = "//div[@class='footer-middle']//*[contains(@href,'/terms-of-service')]";
		return new WbElement(By.xpath(xpath), "Footer Terms of Service text");
	}

	public WbElement getReturnPolicyTxt() {
		String xpath = "//div[@class='footer-middle']//*[contains(@href,'/return-policy')]";
		return new WbElement(By.xpath(xpath), "Footer Return Policy text");
	}

	public WbElement getContactUsTxt() {
		String xpath = "//div[@class='footer-middle']//*[contains(@href,'/contact-us')]";
		return new WbElement(By.xpath(xpath), "Footer Contact Us text");
	}

	public WbElement getDeleveryPolicyTxt() {
		String xpath = "//div[@class='footer-middle']//*[contains(@href,'/delivery-policy')]";
		return new WbElement(By.xpath(xpath), "Footer Deleviry Policy text");
	}

	public WbElement getHelpCenterTxt() {
		String xpath = "//div[@class='block-content']//*[contains(@href,'/support')]";
		return new WbElement(By.xpath(xpath), "Footer Help Center text");
	}

	public WbElement getAboutUsTxt() {
		String xpath = "//div[@class='block-content']//*[contains(@href,'/about')]";
		return new WbElement(By.xpath(xpath), "Footer About Us text");
	}

	public WbElement getFacebookIcon() {
		String xpath = "//li[@class='footer-facebook']/a";
		return new WbElement(By.xpath(xpath), "Footer Facebook Icon");
	}

	public WbElement getTwitterIcon() {
		String xpath = "//li[@class='footer-twitter']/a";
		return new WbElement(By.xpath(xpath), "Footer Twitter Icon");
	}

	public WbElement getInstagramIcon() {
		String xpath = "//li[@class='footer-instagram']/a";
		return new WbElement(By.xpath(xpath), "Footer Instagram Icon");
	}

	public WbElement getEmailNewsLetterTbx() {
		String id = "newsletter_footer";
		return new WbElement(By.id(id), "Email Address NewsLetter Textbox");
	}

	public static WbElement getEmailNewsLetterSubmitBtn() {
		String xpath = "//input[@id='newsletter_footer']/../button[@type='submit']";
		return new WbElement(By.xpath(xpath), "Email Address NewsLetter Submit");
	}

	public WbElement getChangeCountryIcon() {
		String xpath = "//div[@class='region-switcher-wrap']//div[@class='regionSelect']";
		return new WbElement(By.xpath(xpath), "Change Country Icon");
	}

}
