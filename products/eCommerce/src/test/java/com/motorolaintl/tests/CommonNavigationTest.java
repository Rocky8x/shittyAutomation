package com.motorolaintl.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.cinatic.log.Log;
import com.motorolachargers.pages.PageBase;

public class CommonNavigationTest extends TestBaseMotoIntl {
	
	
	Class<?>		pageClass;
	Object			o;
	PageBase pageBase = new PageBase();

	@DataProvider(name = "pageList")
	public Object[][] createData() {
		return new Object[][] { { "com.motorolaintl.pages.PageHome" }, 
				{ "com.motorolaintl.pages.PageCart" }, { "com.motorolaintl.pages.PagePrivacyPolicy" },
				{ "com.motorolaintl.pages.PageSupport" }, { "com.motorolaintl.pages.PageCategoryMod" },
				{ "com.motorolaintl.pages.PageCategoryModShells" }, { "com.motorolaintl.pages.PageCategoryPower" },
				{ "com.motorolaintl.pages.PageCategorySpeakers" }, { "com.motorolaintl.pages.PageCategoryProjectors" },
				{ "com.motorolaintl.pages.PageCategoryModCameras" },
				{ "com.motorolaintl.pages.PageCategoryAcessories" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesCases" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesChargers" } };
	}
	
	@BeforeMethod
	public void beforeMethod(Object[] pageList) throws Exception {
		String pageName = (String) pageList[0]; 
		Log.info("Navigation test: " + pageName);
		pageClass = Class.forName(pageName);
		
		o = pageClass.newInstance();
		pageClass.getMethod("openPage").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void basicTest(String pageName) throws Exception {
		
		pageClass.getMethod("verifyPageBasicContent").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyGotoMotorolaTopLeftLink").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyGotoMotorolaPhoneTopRightLink").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void clickLogoTest(String pageName) throws Exception{
		
		pageClass.getMethod("clickMainLogoAndVerify").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void navigateSubMenuTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("hoverMotoModMenuAndVerify").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("hoverAccessoriesMenuAndVerify").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyHeaderSupportLink").invoke(o);
	}
	
	
	
	@Test(dataProvider = "pageList")
	public void navigateCartPageTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyMinicart").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void changeCountryTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("changeCountryAndVerify").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void socialMediaLinkTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyFacebookLink").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyTwitterLink").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyInstagramLink").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void footerLinkTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyFooterSupportContactLink").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyPrivacyPolicyLink").invoke(o);
	}



}
