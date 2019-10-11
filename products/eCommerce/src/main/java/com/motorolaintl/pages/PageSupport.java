package com.motorolaintl.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageSupport extends PageBase {

	public PageSupport() {
		PATH = "contact-us/";
	}
	
	public WbElement getSupportPageTitle() {
		String xpath = "//h1[text()='Support Information']";
		return new WbElement(By.xpath(xpath),"Support page title");
	}
	
	public WbElement getGotoMotorolaSupportLink() {
		String xpath = "//a[text()='Visit support.motorola.com']";
		return new WbElement(By.xpath(xpath),"Goto Motorola support link");
	}

}
