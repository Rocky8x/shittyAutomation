package com.motorolaintl.tests;

import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;

public class CART01_NavigationTest extends TestBaseMotoIntl {

	PageHome pageHome = new PageHome();
	PageCart pageCart = new PageCart();

	// Test go to checkout page
	@Test()
	public void navigationTest() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// go to cart page
		pageHome.navigateCartPage();
		WbDriverManager.waitForPageLoad();
		// Check navigation
		pageCart.clickProceedToCheckoutBtnAndVerify();
		pageCart.clickCheckoutPaypalBtnAndVerify();
	}
}
