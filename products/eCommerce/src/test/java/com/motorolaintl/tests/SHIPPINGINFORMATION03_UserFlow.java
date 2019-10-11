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

public class SHIPPINGINFORMATION03_UserFlow extends TestBaseMotoIntl {

	public String email = "test1@mailinator.com";
	public String firstName = "first";
	public String lastName = "last";
	public String address1 = "address1";
	public String address2 = "address2";
	public String zipCode = "700000";
	public String city = "city";
	public String region = "Arizona";
	public String telephone = "1900135791";

	public String invalidEmail = "1";
	public String invalidFirstName = "1";
	public String invalidLastName = "1";
	public String invalidzipCode = "1";
	public String invalidRegion = "-- Please select --";
	public String invalidTelephone = "1";

	public String titleMessageErrorNullInput = "Can you go back and look at:";
	public String titleMessageErrorInvalidInput = "Error Occured";

	PageHome pageHome = new PageHome();
	PageBase pageBase = new PageBase();
	PageCart pageCart = new PageCart();
	PageShippingInformation pageShippingInformation = new PageShippingInformation();

	List<String> fieldInformationList() {
		List<String> field = Arrays.asList("Email", "First Name", "Last Name", "Address", "Zip/Postal Code", "City",
				"State/Province", "Phone Number");
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
		WbDriverManager.waitForPageLoad();
		String titlePopUp = pageShippingInformation.getTitleErrorMessage();
		assertEquals(titlePopUp, titleMessageErrorNullInput);
		List<String> errorField = pageShippingInformation.getListFieldErrorMessage();
		assertEquals(errorField, fieldInformationList());
		pageShippingInformation.clickCloseError();
	}

	@Test()
	public void userFlow2_InputInValidInformation() throws Exception {
		pageHome.navigateCheckoutPage();

		// input information
		pageShippingInformation.inputInformationUser(invalidEmail, invalidFirstName, invalidLastName, address1,
				address2, invalidzipCode, city, invalidRegion, invalidTelephone);

		pageShippingInformation.clickContinueBtn();
		String titlePopUp = pageShippingInformation.getTitleErrorMessage();
		assertEquals(titlePopUp, titleMessageErrorInvalidInput);
		String contentError = pageShippingInformation.getContentErrorMessage();
		String contentErrorExpected = "\"Telephone\" is a required value.\n"
				+ "\"Telephone\" length must be equal or greater than 1 characters.\n"
				+ "\"State/Province\" is a required value.";
		assertEquals(contentError, contentErrorExpected);
		pageShippingInformation.clickCloseError();

	}

	@Test()
	public void userFlow3_InputValidInformation() throws Exception {
		pageHome.navigateCheckoutPage();

		// input information
		pageShippingInformation.inputInformationUser(email, firstName, lastName, address1, address2, zipCode, city,
				region, telephone);

		// Verify navigation to payment page
		pageShippingInformation.clickContinueBtnAndVerify();

	}

	@Test()
	public void userFlow4_GetBackToCart() throws Exception {
		pageHome.navigateCheckoutPage();
		WbDriverManager.waitForPageLoad();
		pageShippingInformation.clickLogoIconAndVerify();
	}

	@Test()
	public void userFlow5_NavigateToPayment() throws Exception {
		pageHome.navigateCheckoutPage();

		pageShippingInformation.inputInformationUser(email, firstName, lastName, address1, address2, zipCode, city,
				region, telephone);
		// Verify navigation to payment page
		pageShippingInformation.clickContinueBtnAndVerify();

	}
}
