package com.motorolaintl.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PageShippingInformation;

public class SHIPPINGINFORMATION02_ContentVerification extends TestBaseMotoIntl {

	PageHome pageHome = new PageHome();
	PageBase pageBase = new PageBase();
	PageCart pageCart = new PageCart();
	PageShippingInformation pageShippingInformation = new PageShippingInformation();

	// Test go to checkout page
	@Test()
	public void contentVerification() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// go to cart page
		pageHome.navigateCartPage();
		WbDriverManager.waitForPageLoad();
		// Go to shipping information page
		pageCart.clickProceedCheckoutBtn();
		WbDriverManager.waitForPageLoad();
		// check Content information
		assertTrue(pageShippingInformation.getLogoIcon().isDisplayed());
		assertTrue(pageShippingInformation.getEmailAddressTbx().isDisplayed());
		assertTrue(pageShippingInformation.getFirstNameTbx().isDisplayed());
		assertTrue(pageShippingInformation.getLastNameTbx().isDisplayed());
		assertTrue(pageShippingInformation.getAddress1Tbx().isDisplayed());
		assertTrue(pageShippingInformation.getAddress2Tbx().isDisplayed());
		assertTrue(pageShippingInformation.getZipCodeTbx().isDisplayed());
		assertTrue(pageShippingInformation.getCityTbx().isDisplayed());
		assertTrue(pageShippingInformation.getCountryTbx().isDisplayed());
		assertTrue(pageShippingInformation.getStateDropBox().isDisplayed());
		assertTrue(pageShippingInformation.getTelephoneTbx().isDisplayed());
		pageShippingInformation.verifyShippingMethodIsDisplay();
		assertTrue(pageShippingInformation.getContinueBtn().isDisplayed());
	}
}
