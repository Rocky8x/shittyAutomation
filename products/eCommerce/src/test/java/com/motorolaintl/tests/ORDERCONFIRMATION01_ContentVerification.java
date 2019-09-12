package com.motorolaintl.tests;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.cinatic.TimeHelper;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PageOrderConfirmation;
import com.motorolaintl.pages.PagePayment;
import com.motorolaintl.pages.PageShippingInformation;

public class ORDERCONFIRMATION01_ContentVerification extends TestBaseMotoIntl{

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
	public void contentVerificationTest() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// go to cart page
		pageHome.navigateCartPage();
		//Get all information in Cart page
		List<String> productInCart = pageCart.getNameProductList();
		List<String> priceInCart = pageCart.getPriceProductList();
		List<String> subTotalInCart = pageCart.getSubTotalPriceList();
		List<String> quantityInCart = pageCart.getValueQuantityProductBoxList();
		String subTotalAllInCart = pageCart.getPriceSubTotalString();
		String shippingfeeInCart = pageCart.getPriceShippingString();
		String amountPayable = pageCart.getPriceAmountPayableString();
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
		
		pagePayment.clickCreditCardMethod();
		pagePayment.inputCardNumber(creditCartNumber);
		pagePayment.selectDateExpiration(date);
		pagePayment.selectYearExpiration(year);
		pagePayment.inputSecurityCode(securityCode);
		pagePayment.clickContinueBtn();
		TimeHelper.sleep(5000);
		
		//verify logo icon is displayed
		assertTrue(pageOrderConfirmation.getLogoIcon().isDisplayed());
		
		//Get information product on Order Confirm page
		List<String> productInOrderConfirm = pageOrderConfirmation.getNameProduct();
		List<String> priceInOrderConfirm = pageOrderConfirmation.getPriceProduct();
		List<String> subTotalInOrderConfirm = pageOrderConfirmation.getPriceSubTotalProduct();
		List<String> quantityInOrderConfirm = pageOrderConfirmation.getQuantityProduct();
		String subTotalAllOrderConfirm = pageOrderConfirmation.getSubTotalPriceTotalTable();
		String shippingFeeOrderConfirm = pageOrderConfirmation.getShippingPriceTotalTable();
		String grandTotal = pageOrderConfirmation.getTotalPrice();
		
		// Compare all informations with cart page
		assertEquals(productInOrderConfirm, productInCart);
		assertEquals(priceInOrderConfirm, priceInCart);
		assertEquals(subTotalInOrderConfirm, subTotalInCart);
		assertEquals(quantityInOrderConfirm, quantityInCart);
		assertEquals(subTotalAllOrderConfirm, subTotalAllInCart);
		assertEquals(shippingFeeOrderConfirm,shippingfeeInCart);
		assertEquals(grandTotal, amountPayable);
		
		//verify Place Order Button is displayed
		assertTrue(pageOrderConfirmation.getPlaceOrderBtn().isDisplayed());
		
	}
}
