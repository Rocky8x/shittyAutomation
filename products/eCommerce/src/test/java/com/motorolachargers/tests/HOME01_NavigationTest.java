package com.motorolachargers.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;
import com.motorolachargers.pages.PageBase;
import com.motorolachargers.pages.PageHome;
import com.motorolachargers.pages.PageProduct;
import com.motorolachargers.pages.PageProductDetail;


public class HOME01_NavigationTest extends TestBaseChargers{
	
	PageBase pageBase = new PageBase();
	PageProductDetail pageProductDetail = new PageProductDetail(pageBase);
	
	
	@DataProvider(name = "country")
	public Object[][] country() {
		return new Object[][] {
			{"United States"},
			{"United Kingdom"},
			{"Canada - English"},
			{"Canada - French"},
			{"Europe - English"},
//			{"Germany"},
			{"France"},
			{"Spain"},
			{"Italy"},
			{"Mexico"},
			};
	}

	@Test(dataProvider = "country")
	public void Home001_homePageNavigationToProductDeatailPageTest(String country) throws Exception {
		pageBase.changeCountryHeaderIcon(country);
		String url = WbDriverManager.getCurrentUrl();
		pageBase.baseURL = url;
		PageHome pageHome = new PageHome(pageBase);
		pageHome.openPage();
		pageHome.clickProductAndVerify();
	}	

	
	@Test(dataProvider = "country")
	public void Home002_ProductPageNavigationToProductDeatailPageTest(String country) throws Exception {
		pageBase.changeCountryHeaderIcon(country);
		String url = WbDriverManager.getCurrentUrl();
		pageBase.baseURL = url;
		PageProduct pageProduct = new PageProduct(pageBase);
		pageProduct.openPage();
		PageHome pageHome = new PageHome(pageBase);
		pageHome.clickProductAndVerify();
	}
}
