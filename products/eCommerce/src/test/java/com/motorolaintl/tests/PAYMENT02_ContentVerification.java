package com.motorolaintl.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PagePayment;
import com.motorolaintl.pages.PageShippingInformation;

public class PAYMENT02_ContentVerification extends TestBaseMotoIntl {
	public String email = "test1@mailinator.com";
	public String firstName = "first";
	public String lastName = "last";
	public String address1 = "address1";
	public String address2 = "address2";
	public String zipCode = "700000";
	public String city = "city";
	public String region = "Arizona";
	public String telephone = "1900135791";

	PageHome pageHome = new PageHome();
	PageBase pageBase = new PageBase();
	PageCart pageCart = new PageCart();
	PageShippingInformation pageShippingInformation = new PageShippingInformation();
	PagePayment pagePayment = new PagePayment();

	// Test go to checkout page
	@Test()
	public void contentVerificationTest() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// go to cart page
		pageHome.navigateCartPage();
		// Go to shipping information page
		pageCart.clickProceedCheckoutBtn();
		// input information on shipping information page
		pageShippingInformation.inputInformationUser(email, firstName, lastName, address1, address2, zipCode, city, region, telephone);
		// Verify navigation to payment page
		pageShippingInformation.clickContinueBtn();
		WbDriverManager.waitForPageLoad();

		assertTrue(pagePayment.getLogoIcon().isDisplayed());
		assertTrue(pagePayment.getCreditCardMethodRadio().isDisplayed());
		assertTrue(pagePayment.getPayPalMethodLabel().isDisplayed());
		assertTrue(pagePayment.getCreditCardNumberTbx().isDisplayed());
		assertTrue(pagePayment.getExpirationDate().isDisplayed());
		assertTrue(pagePayment.getExpirationYear().isDisplayed());
		assertTrue(pagePayment.getSecurityCode().isDisplayed());
		assertTrue(pagePayment.getSameShippingAddressCkb().isDisplayed());
		pagePayment.clickSameShippingAddressCkb();
		WbDriverManager.waitForPageLoad();
		assertTrue(pagePayment.getFullNameTbx().isDisplayed());
		assertTrue(pagePayment.getPhoneNumberTbx().isDisplayed());
		assertTrue(pagePayment.getCompanyTbx().isDisplayed());
		assertTrue(pagePayment.getAddres1Tbx().isDisplayed());
		assertTrue(pagePayment.getAddres2Tbx().isDisplayed());
		assertTrue(pagePayment.getZipCodeTbx().isDisplayed());
		assertTrue(pagePayment.getCityTbx().isDisplayed());
		assertTrue(pagePayment.getCountryTbx().isDisplayed());
		assertTrue(pagePayment.getStateDropBox().isDisplayed());
	}

}
