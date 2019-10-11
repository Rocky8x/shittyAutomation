package com.motorolaintl.tests;

import org.testng.annotations.Test;
import com.cinatic.TimeHelper;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PageOrderConfirmation;
import com.motorolaintl.pages.PagePayment;
import com.motorolaintl.pages.PageShippingInformation;

public class ORDERCONFIRMATION02_NavigationTest extends TestBaseMotoIntl{
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

	PageHome pageHome = new PageHome();
	PageBase pageBase = new PageBase();
	PageCart pageCart = new PageCart();
	PageShippingInformation pageShippingInformation = new PageShippingInformation();
	PagePayment pagePayment = new PagePayment();
	PageOrderConfirmation pageOrderConfirmation = new PageOrderConfirmation();

	// Test go to checkout page
	@Test()
	public void navigationTest() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// go to cart page
		pageHome.navigateCartPage();
		//Get all information in Cart page
		
		// Go to shipping information page
		pageCart.clickProceedCheckoutBtn();
		// input information on shipping information page
		pageShippingInformation.inputInformationUser(email, firstName, lastName, address1, address2, zipCode, city, region, telephone);
		// Verify navigation to payment page
		pageShippingInformation.clickContinueBtn();
		
		pagePayment.clickCreditCardMethod();
		pagePayment.inputInformationCard(creditCartNumber, date, year, securityCode);
		pagePayment.clickContinueBtn();
		WbDriverManager.waitForPageLoad();
		
		//Click logo and navigate to Cart Page
		pageOrderConfirmation.clickLogoIconAndVerify();
		
	}

}
