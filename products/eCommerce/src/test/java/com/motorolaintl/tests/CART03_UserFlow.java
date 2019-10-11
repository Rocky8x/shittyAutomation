package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;

import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PageProductDetail;

public class CART03_UserFlow extends TestBaseMotoIntl {

	Random rand = new Random();
	int value = 0 - rand.nextInt(300);
	PageHome pageHome = new PageHome();
	PageCart pageCart = new PageCart();
	PageProductDetail pageProductDetail = new PageProductDetail();
	String invalidPromoteCode = "MOTOROLA";

	@Test()
	public void userFlow01_ChangeQuantityValueToZero() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		List<String> productNameList = pageHome.getNameProductBuyableList();
		List<String> productNameNewList = new ArrayList<String>();
		for (int i = 1; i < productNameList.size(); i++) {
			productNameNewList.add(productNameList.get(i));
		}
		// go to cart page
		pageHome.navigateCartPage();
		WbDriverManager.waitForPageLoad();

		Double priceProduct0 = pageCart.convertPriceStringToDouble(pageCart.getSubTotalPriceList().get(0));

		Double priceSubTotal = pageCart.convertPriceStringToDouble(pageCart.getPriceSubTotalString());

		Double priceExpected = priceSubTotal - priceProduct0;

		// input zero value to quantity box
		pageCart.inputQuantity(0, 0);
		List<String> productNameNewInCart = pageCart.getNameProductList();

		Double priceSubTotalAfterInput = pageCart.convertPriceStringToDouble(pageCart.getPriceSubTotalString());

		// Check product list after input quantity = 0
		assertEquals(productNameNewInCart, productNameNewList);

		// Check SubTotal After input
		assertEquals(priceSubTotalAfterInput, priceExpected);

	}

	@Test()
	public void userFlow02_ChangeQuantityValueToNegativeNumber() throws Exception {
		// get list Product before input negative number
		pageHome.navigateCartPage();
		List<String> productNameNewInCart = pageCart.getNameProductList();
		String priceSubTotalBeforeTrash = pageCart.getPriceSubTotalString();
		pageCart.inputQuantity(value, 0);
		// get list Product after
		List<String> productNameNewInCartAfter = pageCart.getNameProductList();
		String priceSubTotalBeforeTrashAfter = pageCart.getPriceSubTotalString();

		// Check Subtotal, list name
		assertEquals(productNameNewInCartAfter, productNameNewInCart);
		assertEquals(priceSubTotalBeforeTrashAfter, priceSubTotalBeforeTrash);

		// get quantity after
		String quantity = pageCart.getValueQuantityProductBoxList().get(0);
		assertEquals(quantity, "1");
	}

	@Test()
	public void userFlow03_ChangeQuantityValueToExceedAvailable() throws Exception {
		// get list Product before input negative number
		pageHome.navigateCartPage();

		pageCart.inputQuantity(1000, 0);
		// get list Product after
		String error = pageCart.getErrorMessage();
		if (!error.contains("is not available")) {
			fail("Error is not match");
		}
		pageCart.inputQuantity(1, 0);
	}

	@Test()
	public void userFlow04_TrashProductOutofCartTest() throws Exception {
		// get list Product before input negative number
		pageHome.navigateCartPage();

		List<String> productNameNewInCart = pageCart.getNameProductList();
		List<String> productNameNewList = new ArrayList<String>();
		for (int i = 1; i < productNameNewInCart.size(); i++) {
			productNameNewList.add(productNameNewInCart.get(i));
		}

		Double priceProduct0 = pageCart.convertPriceStringToDouble(pageCart.getSubTotalPriceList().get(0));

		Double priceSubTotal = pageCart.convertPriceStringToDouble(pageCart.getPriceSubTotalString());

		Double priceExpected = priceSubTotal - priceProduct0;

		pageCart.trashProductOutOfCart(0);
		WbDriverManager.waitForPageLoad();
		// get list Product after
		List<String> productNameAfterTrash = pageCart.getNameProductList();

		Double priceSubTotalAfterTrash = pageCart.convertPriceStringToDouble(pageCart.getPriceSubTotalString());

		// Check product list after input quantity = 0
		assertEquals(productNameAfterTrash, productNameNewList);

		// Check SubTotal After input
		assertEquals(priceSubTotalAfterTrash, priceExpected);
	}

	@Test()
	public void userFlow05_AddProductSuggest() throws Exception {

		pageHome.navigateCartPage();
		// Click add to cart button at product suggest
		pageCart.addProductSuggestAndVerify();

	}

	@Test()
	public void userFlow06_ProceedCheckout() throws Exception {

		pageHome.navigateCartPage();
		pageCart.clickProceedToCheckoutBtnAndVerify();
		pageCart.clickCheckoutPaypalBtnAndVerify();
	}

	@Test()
	public void userFlow07_ViewAgainProductDetail() throws Exception {
		pageHome.navigateCartPage();
		// Navigate to product and verify
		pageCart.clickProductAndVerify();
	}

	@Test()
	public void userFlow08_AddInvalidCodePromote() throws Exception {
		pageHome.navigateCartPage();
		// input Promote code
		pageCart.inputPromoteCode(invalidPromoteCode);
		WbDriverManager.waitForPageLoad();
		// Check error
		String error = pageCart.getErrorMessage();
		String errorExpected = "Coupon code " + "\"" + invalidPromoteCode + "\"" + " is not valid.";
		assertEquals(error, errorExpected);
	}

}
