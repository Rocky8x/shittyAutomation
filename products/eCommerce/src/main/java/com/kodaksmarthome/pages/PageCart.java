package com.kodaksmarthome.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageCart extends PageBase {

	public PageCart() {

		PATH = "checkout/cart/";
	}

	public PageCart(String baseUrl) {

		PATH			= "checkout/cart/";
		this.baseURL	= baseUrl;
	}

	/*
	 * ELEMENTS
	 */

	public WbElement getPageTitle() {

		String title = "//div[contains(@class,'page-title')]/h1";
		return new WbElement(By.xpath(title), "Page Title");
	}

	public WbElement getProductName() {

		String xpath = "//h2[@class='product-name']";
		return new WbElement(By.xpath(xpath), "In-cart product name");
	}

	public WbElement getPrice() {

		return null;

	}
	
	public WbElement getProceedToCheckoutBtn() {

		return null;
	}

	public WbElement getCheckoutWithPayPalBtn() {

		return null;
	}
	
	public WbElement getSubtotal() {

		return null;
	}
	/*
	 * ACTIONS
	 */
	public void verifyPageContent() {

		verifyUrl();
		verifyPageHeader();
		verifyPageTitleDisplay();
		verifyPageFooter();

	}

	public void verifyPageTitleDisplay() {

		assertTrue(getPageTitle().isDisplayed());
	}

}
