package com.kodaksmarthome.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class GroupFooter {

	public WbElement getAboutUsLink() {
		String link = "//div[contains(@class,'footer')]//a[contains(@href, 'about')]";
		return new WbElement(By.xpath(link), "About Us link");
	}
	
	public WbElement getSupportLink() {
		String link = "//div[contains(@class,'footer')]//a[contains(@href, 'support') and not (contains(@href, 'deviceregistration'))]";
		return new WbElement(By.xpath(link), "Support");
	}
	
	public WbElement getRegisterProductLink() {
		String link = "//div[contains(@class,'footer')]//a[contains(@href, 'deviceregistration')]";
		return new WbElement(By.xpath(link), "Register Product");
	}
	
	public WbElement getPrivacyPolicyLink() {
		String link = "//div[contains(@class,'footer')]//a[contains(@href, 'privacypolicy')]";
		return new WbElement(By.xpath(link), "Privacy Policy");
	}
	
	public WbElement getTermServiceLink() {
		String link = "//div[contains(@class,'footer')]//a[contains(@href, 'tos')]";
		return new WbElement(By.xpath(link), "Term of Service");
	}
	
	public WbElement getContactUsLink() {
		String link = "//div[contains(@class,'footer')]//a[contains(@href, 'contact')]";
		return new WbElement(By.xpath(link), "Contact Us");
	}
	
	public WbElement getFacebookIcon() {
		String icon = "//footer//a[contains(@href,'https://www.facebook.com/KodakSmartHome')]";
		return new WbElement(By.xpath(icon), "Facebook Icon");
	}
	
	public WbElement getInstagramIcon() {
		String icon = "//footer//a[contains(@href, 'https://www.instagram.com/KodakSmartHome')]";
		return new WbElement(By.xpath(icon), "Instagram icon");
	}
	
	public WbElement getTwitterIcon() {
		String icon = "//footer//a[contains(@href, 'https://twitter.com/KodakSmartHome')]";
		return new WbElement(By.xpath(icon), "Twiter icon");
	}
	
	public WbElement getYoutubeIcon() {
		String icon = "//footer//a[contains(@href, 'https://www.youtube.com/channel/')]";
		return new WbElement(By.xpath(icon), "Youtube Icon");
	}
	
	public WbElement getContrySwitchMenu() {
		String menu = "//footer[contains(@class, 'footer')]//button[@data-toggle='modal']";
		return new WbElement(By.xpath(menu), "Switching menu");
	}
	
	public WbElement getRegisterEmailEle() {
		String xpath = "//input[@id='newsletter']";
		return new WbElement(By.xpath(xpath), "Email Field");
	}
	
	public WbElement getCopyRightEle() {
		String xpath = "//footer[contains(@class, 'footer')]//div[@class='footer-copy']";
		return new WbElement(By.xpath(xpath), "Copy Right");
	}
}
