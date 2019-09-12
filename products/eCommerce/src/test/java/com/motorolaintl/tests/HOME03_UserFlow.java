package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.motorolaintl.pages.PageHome;

public class HOME03_UserFlow extends TestBaseMotoIntl{
	
	PageHome pageHome = new PageHome();
	

	
	@Test()
	public void userFlow01_checkQuantityCartPopUp() throws Exception {
		// get 
		pageHome.addProductToCart();
		int countProduct = pageHome.getCountProductCartPopUp();
		// Check Count Product Cart Pop Up
		assertEquals(countProduct, pageHome.getNameProductBuyableList().size());
	}
	
	@Test()
	public void userFlow02_checkListProductNameCartPopUp() throws Exception {
		List<String> listProductExpected = pageHome.getNameProductBuyableList();
		Collections.reverse(listProductExpected);
		// get list Name on cart Pop Up
		List<String> listProductCartPopUp = pageHome.getListProductNameCartPopUp();
		// Check Name Product Cart Pop Up
		for(int i = 0; i < listProductCartPopUp.size(); i++) {
			assertEquals(listProductCartPopUp.get(i), listProductExpected.get(i));
		}
	}
	
	@Test()
	public void userFlow03_NavigateCheckout() throws Exception {
		pageHome.navigateCartAndVerify();
		pageHome.navigateCheckoutPageAndVerify();
		pageHome.navigateCheckouPaypalPageAndVerify();
		
	}

}
