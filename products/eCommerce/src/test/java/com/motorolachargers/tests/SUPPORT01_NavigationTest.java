package com.motorolachargers.tests;

import java.util.ArrayList;
import java.util.List;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.cinatic.TimeHelper;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;
import com.motorolachargers.pages.PageBase;
import com.motorolachargers.pages.PageHome;
import com.motorolachargers.pages.PageSupport;

public class SUPPORT01_NavigationTest extends TestBaseChargers{
	PageBase pageBase = new PageBase();


	

	@DataProvider(name = "country")
	public Object[][] country() {
		return new Object[][] {
			{"United States"},
			{"United Kingdom"},
			{"Canada - English"},
//			{"Canada - French"},
			{"Europe - English"},
//			{"Germany"},
//			{"France"},
//			{"Spain"},
//			{"Italy"},
//			{"Mexico"},
			};
	}

	@Test(dataProvider = "country")
	public void supportPage001_navigationToSubCategoriesPage(String country) throws Exception {
		PageHome pageHome = new PageHome(pageBase);
		pageHome.changeCountryHeaderIcon(country);
		String url = WbDriverManager.getCurrentUrl();
		pageBase.baseURL = url;
		PageSupport pageSupport = new PageSupport(pageBase);
		pageSupport.openPage();
		pageSupport.navigateCategoriesItemAndVerify();
	}
	
	@Test(dataProvider = "country")
	public void supportPage002_navigationToSupportProductDetailPage(String country) {	
		PageHome pageHome = new PageHome(pageBase);
		pageHome.changeCountryHeaderIcon(country);
		String url = WbDriverManager.getCurrentUrl();
		pageBase.baseURL = url;
		PageSupport pageSupport = new PageSupport(pageBase);
		pageSupport.openPage();
		pageSupport.clickViewAllBtn();
		List<String> nameProduct = new ArrayList<>();
		nameProduct = pageSupport.getNameProduct();
		List<String> linkProduct = new ArrayList<>();
		linkProduct = pageSupport.getLinkProduct();
		for(int i= 0 ; i<nameProduct.size();i++) {
			Log.info("Navigate to Product Item: "+ nameProduct.get(i));
			pageSupport.navigateSupportProductDetail(nameProduct.get(i));
			TimeHelper.sleep(2000);
			String currentUrl = WbDriverManager.getCurrentUrl();
			Assert.assertEquals(currentUrl,linkProduct.get(i));
			pageSupport.openPage();
			pageSupport.clickViewAllBtn();
		}
	}

}
