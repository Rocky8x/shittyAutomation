package com.motorolachargers.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class GroupHeader {

	public WbElement getLoginBtn() {
		String xpath = "//div[@class='header-links-right']/a[@class='mini-cart login']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Login Button");
	}

	public WbElement getCartBtn() {
		String xpath = "//div[@class='header-links-right']/a[@class='mini-cart']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Cart Button");
	}

	public WbElement getHomePageMotorolaTxt() {
		String xpath = "//div[@class='goToMotorola']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Go to Motorola Home Page text");
	}

	public WbElement getHomePageLogo() {
		String xpath = "//a[@class='logo']";
		return new WbElement(By.xpath(xpath), "Motorola Home Page Icon");
	}

	public WbElement getMenuProductTxt() {
		String xpath = "//ul[@class='nav mainnav']//*[contains(@href,'phone-chargers')]";
		return new WbElement(By.xpath(xpath), "Menu Product text");
	}

	public WbElement getMenuSupportTxt() {
		String xpath = "//ul[@class='nav mainnav']//*[contains(@href,'support')]";
		return new WbElement(By.xpath(xpath), "Menu Support text");
	}

	public WbElement getLookingForHelpTxt() {
		String xpath = "//li[@class='help']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Looking for help? text");
	}

	public WbElement getChangeCountryIcon() {
		String xpath = "//div[@class='menu-wrapper']/../div[@class='regionSelect']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Change Country Icon");
	}

}
