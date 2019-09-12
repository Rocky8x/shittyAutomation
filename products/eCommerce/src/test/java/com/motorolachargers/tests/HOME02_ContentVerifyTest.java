package com.motorolachargers.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;


import com.motorolachargers.pages.PageHome;
import com.motorolachargers.pages.PageProduct;

public class HOME02_ContentVerifyTest extends TestBaseChargers{
	
	
	PageHome pageHome = new PageHome();
	PageProduct pageProduct = new PageProduct();
	
	@Test()
	public void Home001_homePageContentVerifyTest() throws Exception {

		pageHome.openPage();
		pageHome.verifyHeader();
		pageHome.verifyFooter();
		assertTrue(pageHome.getHomeBanner().isDisplayed());
		List<String> nameProduct = new ArrayList<>();
		nameProduct = pageHome.getNameProductList();
		assertEquals(nameProduct, pageHome.product);
	}	
	
	@Test()
	public void Home002_productPageContentVerifyTest() throws Exception {
		pageProduct.openPage();
		pageProduct.verifyHeader();
		pageProduct.verifyFooter();
		assertTrue(pageProduct.getHomeBanner().isDisplayed());
		List<String> nameProduct = new ArrayList<>();
		nameProduct = pageHome.getNameProductList();
		assertEquals(nameProduct, pageHome.product);
	}	

}
