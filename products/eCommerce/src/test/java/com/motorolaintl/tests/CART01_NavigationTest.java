package com.motorolaintl.tests;

import org.testng.annotations.Test;

import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;
import com.motorolaintl.pages.PageProductDetail;

public class CART01_NavigationTest extends TestBaseMotoIntl {

	PageHome pageHome = new PageHome();
	PageCart pageCart = new PageCart();
	PageProductDetail pageProductDetail = new PageProductDetail();

	// Test go to checkout page
	@Test()
	public void navigationTest() throws Exception {
		// add product buy-able on Home page
		pageHome.getImageProductDisplayPriceList().get(0).click();
		pageProductDetail.addProductToCart();

		// Check navigation
		pageCart.clickProceedToCheckoutBtnAndVerify();
		pageCart.clickCheckoutPaypalBtnAndVerify();
		WbDriverManager.waitForPageLoad();
		pageCart.changeCountryFooterAndVerify();
//		pageCart.changeCountryAndVerify();
		
	}
}
