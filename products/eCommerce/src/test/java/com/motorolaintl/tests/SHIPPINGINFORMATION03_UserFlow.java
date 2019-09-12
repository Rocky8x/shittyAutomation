package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PageShippingInformation;

public class SHIPPINGINFORMATION03_UserFlow extends TestBaseMotoIntl{
	
	public String email = "test1@mailinator.com";
	public String firstName = "first";
	public String lastName = "last";
	public String address1 = "address1";
	public String address2 = "address2";
	public String zipCode = "700000";
	public String city = "city";
	public String region = "Arizona";
	public String telephone = "1900135791";
	public String titleMessageError = "Can you go back and look at:";

	PageHome pageHome = new PageHome();
	PageBase pageBase = new PageBase();
	PageCart pageCart = new PageCart();
	PageShippingInformation pageShippingInformation = new PageShippingInformation();
	
	List<String> fieldInformationList() {
		List<String> field = Arrays.asList("Email", "First Name", "Last Name", "Address", "Zip/Postal Code", "City", "State/Province", "Phone Number");
		return field;
	}

	// Test go to checkout page
	@Test()
	public void userFlow1_NotInputInformation() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// go to cart page
		pageHome.navigateCartPage();
		WbDriverManager.waitForPageLoad();
		// Go to shipping information page
		pageCart.clickProceedCheckoutBtn();
		WbDriverManager.waitForPageLoad();
		// check Content information
		pageShippingInformation.clickContinueBtn();
		String titlePopUp = pageShippingInformation.getTitleErrorMessage();
		assertEquals(titlePopUp, titleMessageError);
		List<String> errorField = pageShippingInformation.getListFieldErrorMessage();
		assertEquals(errorField, fieldInformationList());
		pageShippingInformation.clickCloseError();
	}
	
	@Test()
	public void userFlow2_InputValidInformation() throws Exception {
		pageHome.navigateCheckoutPage();
	
		// Verify navigation to cart page
		pageShippingInformation.clickLogoIconAndVerify();
		
		// input information
		pageShippingInformation.inputEmailAddress(email);
		pageShippingInformation.inputFirstName(firstName);
		pageShippingInformation.inputLastName(lastName);
		pageShippingInformation.inputAddressStreet1(address1);
		pageShippingInformation.inputAddressStreet2(address2);
		pageShippingInformation.inputZipCode(zipCode);
		pageShippingInformation.inputCity(city);
		pageShippingInformation.selectRegion(region);
		pageShippingInformation.inputTelephone(telephone);

		// Verify navigation to payment page
		pageShippingInformation.clickContinueBtnAndVerify();

	}
}
