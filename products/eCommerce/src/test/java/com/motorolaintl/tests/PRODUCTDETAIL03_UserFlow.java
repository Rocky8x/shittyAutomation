package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PageProductDetail;

public class PRODUCTDETAIL03_UserFlow extends TestBaseMotoIntl {

	PageBase pageBase = new PageBase();
	PageHome pageHome = new PageHome();
	PageProductDetail pageProductDetail = new PageProductDetail();
	PageCart pageCart = new PageCart();
	String quantity = "5";
	String overQuantity = "1000";

	@Test()
	public void userFlow01_CheckMinusPlusQuantityBtn() throws Exception {

		pageHome.getProductDisplayPriceList().get(0).click();
		pageProductDetail.clickPlusQuantityBtn(10);
		assertEquals(pageProductDetail.getQuantityTbx().getAttribute("value"), "11");
		pageProductDetail.clickMinusQuantityBtn(8);
		assertEquals(pageProductDetail.getQuantityTbx().getAttribute("value"), "3");
		pageProductDetail.clickMinusQuantityBtn(2);
		assertEquals(pageProductDetail.getQuantityTbx().getAttribute("value"), "1");
		pageProductDetail.clickMinusQuantityBtn(10);
		assertEquals(pageProductDetail.getQuantityTbx().getAttribute("value"), "1");
		pageProductDetail.addProductToCart();
		String quantityInCart = pageCart.getValueQuantityProductBoxList().get(0);
		assertEquals(quantityInCart, "1");

	}

	@Test()
	public void userFlow02_InputOverQuantityProduct() throws Exception {

		pageHome.getProductDisplayPriceList().get(0).click();
		pageProductDetail.inputQuantity(overQuantity);
		String name = pageProductDetail.getTitleProductName();
		pageProductDetail.addProductToCart();
		String error = pageProductDetail.getErrorMessage();
		String errorExpected = "The requested quantity for " + "\"" + name + "\"" + " is not available.";
		assertEquals(error, errorExpected);

	}

	@Test()
	public void userFlow03_InputInvalidQuantityProduct() throws Exception {

		pageHome.getProductDisplayPriceList().get(1).click();
		//input special character
		pageProductDetail.inputQuantity("@@");
		pageProductDetail.addProductToCart();
		String quantityInCart = pageCart.getValueQuantityProductBoxList().get(1);
		assertEquals(quantityInCart, "1");
		WbDriverManager.backPreviousPage();
		//input negative number
		pageProductDetail.inputQuantity("-10");
		pageProductDetail.addProductToCart();
	    quantityInCart = pageCart.getValueQuantityProductBoxList().get(1);
		assertEquals(quantityInCart, "2");
	}
	
	@Test()
	public void userFlow04_InputValidQuantiyProduct() throws Exception {

		pageHome.getProductDisplayPriceList().get(2).click();
		pageProductDetail.inputQuantity(quantity);
		pageProductDetail.addProductToCart();
		String quantityInCart = pageCart.getValueQuantityProductBoxList().get(2);
		assertEquals(quantityInCart, quantity);
	}

	@Test()
	public void userFlow05_TryAddToCartProduct() throws Exception {
		// Try add to cart product out of Stock
		pageHome.getProductNotDisplayPriceList().get(0).click();
		pageProductDetail.clickOutOfStockLabelAndVerify();

		// Add to cart product Available
		pageHome.openPage();
		pageHome.getProductDisplayPriceList().get(2).click();
		pageProductDetail.addProductToCartAndVerify();
	}

}
