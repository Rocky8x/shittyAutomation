package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.cinatic.TimeHelper;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PagePayment;
import com.motorolaintl.pages.PageShippingInformation;

public class PAYMENT03_UserFlow extends TestBaseMotoIntl{
	public String email = "test1@mailinator.com";
	public String firstName = "first";
	public String lastName = "last";
	public String address1 = "address1";
	public String address2 = "address2";
	public String zipCode = "700000";
	public String city = "city";
	public String region = "Arizona";
	public String telephone = "1900135791";
	public String creditCartNumber = "4111 1111 1111 1111";
	public String date = "10 - October";
	public String year = "22";
	public String securityCode = "123";
	public String titleMessageError = "Can you go back and look at:";
	
	public String fullNameShipping = "first_update last_update";
	public String telephoneShipping = "1900135791";
	public String address1Shipping = "address1_update";
	public String address2Shipping = "address2_update";
	public String zipCodeShipping = "700000";
	public String cityShipping = "city_update";
	public String regionShipping = "Alabama";
	public String company = "Company_update";
	
	List<String> fieldInformationList() {
		List<String> field = Arrays.asList("Credit Card number", "Credit Card expiration date (Month)", "Credit Card expiration date (Year)",  
				 "Credit Card CVV code");
		return field;
	}

	List<String> fieldShippingList() {
		List<String> field = Arrays.asList("Phone Number", "Address", "Zip/Postal Code",
				 "City");
		return field;
	}

	PageHome pageHome = new PageHome();
	PageBase pageBase = new PageBase();
	PageCart pageCart = new PageCart();
	PageShippingInformation pageShippingInformation = new PageShippingInformation();
	PagePayment pagePayment = new PagePayment();

	// Test go to checkout page
	@Test()
	public void userFlow01_NoInputInformation() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// go to cart page
		pageHome.navigateCartPage();
		// Go to shipping information page
		pageCart.clickProceedCheckoutBtn();
		// input information on shipping information page
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
		pageShippingInformation.clickContinueBtn();
		TimeHelper.sleep(5000);

		pagePayment.clickContinueBtn();
		//Check error
		String titlePopUp = pagePayment.getTitleErrorMessage();
		assertEquals(titlePopUp, titleMessageError);
		List<String> errorField = pagePayment.getListFieldErrorMessage();
		assertEquals(errorField, fieldInformationList());
		pagePayment.clickCloseError();

	}
	
	@Test()
	public void userFlow02_InputValidInformation() throws Exception {
		pageHome.navigateCheckoutPage();
	
		// Verify navigation to cart page
		pageShippingInformation.clickContinueBtn();
		TimeHelper.sleep(5000);
		
		pagePayment.clickCreditCardMethod();
		pagePayment.inputCardNumber(creditCartNumber);
		pagePayment.selectDateExpiration(date);
		pagePayment.selectYearExpiration(year);
		pagePayment.inputSecurityCode(securityCode);

		// check click Continue, navigate to order confirmation page
		pagePayment.clickContinueBtnAndVerify();

	}
	
	@Test()
	public void userFlow03_ChangeInformationShipping() throws Exception {
		pageHome.navigateCheckoutPage();
	
		// Verify navigation to cart page
		pageShippingInformation.clickContinueBtn();
		TimeHelper.sleep(5000);
		
		pagePayment.clickCreditCardMethod();
		pagePayment.inputCardNumber(creditCartNumber);
		pagePayment.selectDateExpiration(date);
		pagePayment.selectYearExpiration(year);
		pagePayment.inputSecurityCode(securityCode);

		// check click Continue, navigate to order confirmation page
		pagePayment.clickSameShippingAddressCkb();
		WbDriverManager.waitForPageLoad();
		pagePayment.inputFullNameTbx(fullNameShipping);
		pagePayment.inputPhoneNumberTbx(telephoneShipping);
		pagePayment.inputCompanyTbx(company);
		pagePayment.inputAddres1Tbx(address1Shipping);
		pagePayment.inputAddres2Tbx(address2Shipping);
		pagePayment.inputZipCodeTbx(zipCode);
		pagePayment.inputCityTbx(cityShipping);

		pagePayment.setStateDropBox(regionShipping);
		// check click Continue, navigate to order confirmation page
		pagePayment.clickContinueBtnAndVerify();

	}
	
	@Test()
	public void userFlow04_NullInformationShipping() throws Exception {
		pageHome.navigateCheckoutPage();
	
		// Verify navigation to cart page
		pageShippingInformation.clickContinueBtn();
		TimeHelper.sleep(5000);
		pagePayment.clickCreditCardMethod();
		pagePayment.inputCardNumber(creditCartNumber);
		pagePayment.selectDateExpiration(date);
		pagePayment.selectYearExpiration(year);
		pagePayment.inputSecurityCode(securityCode);

		// check click Continue, navigate to order confirmation page
		pagePayment.clickSameShippingAddressCkb();
		WbDriverManager.waitForPageLoad();
		pagePayment.inputFullNameTbx("");
		pagePayment.inputPhoneNumberTbx("");
		pagePayment.inputCompanyTbx("");
		pagePayment.inputAddres1Tbx("");
		pagePayment.inputAddres2Tbx("");
		pagePayment.inputZipCodeTbx("");
		pagePayment.inputCityTbx("");
		
		// check click Continue, navigate to order confirmation page
		pagePayment.clickContinueBtn();
		//check error message
		List<String> errorField = pagePayment.getListFieldErrorMessage();
		assertEquals(errorField, fieldShippingList());
		pagePayment.clickCloseError();

	}

}
