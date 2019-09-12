package com.motorolachargers.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageCart extends PageBase {

	public PageCart() {
		PATH = "checkout/cart/";
	}

	public PageCart(PageBase p) {
		PATH = "checkout/cart/";
		baseURL = p.baseURL;
	}

	public WbElement getCheckoutBtn() {
		String xpath = "//button[contains(@class,' btn-checkout')]";
		return new WbElement(By.xpath(xpath), "Check out button");
	}

}
