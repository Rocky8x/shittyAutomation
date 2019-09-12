package com.motorolaintl.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageStoreDetect {

	public static WbElement getDismissButton() {
		String xpath = "//button[contains(@class,'button btn-dismiss')]";
		return new WbElement(By.xpath(xpath),"Dismiss button");
	}
}
