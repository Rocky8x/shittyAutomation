package com.motorolachargers.tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;
import com.motorolachargers.pages.PageHome;
import com.motorolachargers.pages.PageProductDetail;

public class PRODUCTDETAIL02_ContentVerifyTest extends TestBaseChargers{
	
	
	PageHome pageHome = new PageHome();
	PageProductDetail pageProductDetail = new PageProductDetail();
	
	@Test()
	public void contentVerifyTest() throws Exception {
		pageHome.openPage();
		List<String> nameProductList = new ArrayList<String>();
		nameProductList = pageHome.getNameProductList();
		for(int i = 0 ; i < nameProductList.size(); i++) {
			pageHome.getProductList().get(i).clickByJavaScripts();
			String nameTitle = pageProductDetail.getProductName();
			assertTrue(nameTitle.contains(nameProductList.get(i)));
			assertTrue(pageProductDetail.getImageProductCurrentShow().isDisplayed());
			pageProductDetail.swipeAllProductImageAndVerify();
			pageProductDetail.verifyPriceAndStatus();	
			WbDriverManager.getDriver().navigate().back();
		}
	}	

}
