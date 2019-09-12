package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageCart;
import com.motorolaintl.pages.PageHome;

public class CART02_ContentVerification extends TestBaseMotoIntl {

	PageHome pageHome = new PageHome();
	PageCart pageCart = new PageCart();

	@Test()
	public void contentVerifyTest() throws Exception {
		// add product buy-able on Home page
		pageHome.addProductToCart();
		// get name and price product can buy-able
		List<String> nameProductBuyable = pageHome.getNameProductBuyableList();
		List<String> priceProductBuyable = pageHome.getPriceProductBuyableList();

		// Goto cart page
		pageHome.navigateCartPage();
		WbDriverManager.waitForPageLoad();

		// Check Name --- Get list name on Cart page and compare with list name
		// on Home page
		List<String> nameProductInCart = pageCart.getNameProductList();
		assertEquals(nameProductInCart, nameProductBuyable);

		// Check price --- Get list price on list product and compare with list
		// price product on Home page
		List<String> priceProductInCart = pageCart.getPriceProductList();
		assertEquals(priceProductInCart, priceProductBuyable);

		// Check quantity
		List<String> quatityProductInCart = pageCart.getValueQuantityProductBoxList();
		for (String quantity : quatityProductInCart) {
			assertEquals(quantity, "1");
		}

		// Check price subtotal --- Get list name on Cart page and compare with
		// list name on Home page
		List<String> subTotalPrice = pageCart.getSubTotalPriceList();
		assertEquals(subTotalPrice, priceProductBuyable);

		// Check trash icon product is display
		pageCart.verifyTrashProductIsDisplay();

		// Check method checkout
		assertTrue(pageCart.getCheckoutPaypalBtn().isDisplayed());
		assertTrue(pageCart.getProceedToCheckoutBtn().isDisplayed());

		// Check promotion code
		pageCart.verifyPromoCodeIsDisplay();

		// Check total, shipping, amount
		assertTrue(pageCart.getPriceSubTotal().isDisplayed());
		assertTrue(pageCart.getPriceShipping().isDisplayed());
		assertTrue(pageCart.getPriceAmountPayable().isDisplayed());

		// Check Shipping Selection
		pageCart.clickShowShippingSelectionAndVerfiy();

	}

}
