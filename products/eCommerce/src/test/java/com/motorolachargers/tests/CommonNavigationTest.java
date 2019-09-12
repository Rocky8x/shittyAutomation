package com.motorolachargers.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;
import com.motorolachargers.pages.PageBase;


public class CommonNavigationTest extends TestBaseChargers {
	
	
	Class<?>		pageClass;
	Object			o;
	PageBase pageBase = new PageBase();
	
	
	@DataProvider(name = "pageList")
	public Object[][] pageList() {
		return new Object[][] {
			{"com.motorolachargers.pages.PageHome"},
			{"com.motorolachargers.pages.PageLogin"},
			{"com.motorolachargers.pages.PageCart"},
			{"com.motorolachargers.pages.PageProduct"},
			{"com.motorolachargers.pages.PageSupport"},
			{"com.motorolachargers.pages.PageDeliveryPolicy"},
			{"com.motorolachargers.pages.PageContactUs"},
			{"com.motorolachargers.pages.PagePrivacyPolicy"},
			{"com.motorolachargers.pages.PageTermsOfService"},
			{"com.motorolachargers.pages.PageReturnPolicy"},
			{"com.motorolachargers.pages.PageAboutUs"},
			{"com.motorolachargers.pages.PageSignUp"},
			{"com.motorolachargers.pages.PageRecoverPassword"},
			};
	}
	@BeforeMethod
	public void beforeMethod(Object[] pageList) throws Exception {
		if(WbDriverManager.getDriver() == null ) {
			WbDriverManager.newBrowser(BROWSER);
		}
		String pageName = (String) pageList[0]; 
		Log.info("Navigation test: " + pageName);
		pageClass = Class.forName(pageName);
		
		o = pageClass.newInstance();
		pageClass.getMethod("openPage").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void basicTest(String pageName) throws Exception {
		
		pageClass.getMethod("verifyPageBasicContent").invoke(o);
		pageClass.getMethod("clickHeaderMotorolaHomePageTxtAndVerify").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void clickLogoTest(String pageName) throws Exception{
		
		pageClass.getMethod("clickMainLogoAndVerify").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void navigateSubMenuTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("clickMenuProductTxtAndVerify").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("clickMenuSupportTxtAndVerify").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("clickLookingForHelpTxtAndVerify").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void navigateLoginPageTest(String pageName) throws Exception{
		if(!pageName.equals("com.motorolachargers.pages.PageSupport")) {
			pageClass.getMethod("openPage").invoke(o);
			pageClass.getMethod("clickLoginButtonAndVerify").invoke(o);
		}
	}
	
	@Test(dataProvider = "pageList")
	public void navigateCartPageTest(String pageName) throws Exception{
		
		if(!pageName.equals("com.motorolachargers.pages.PageSupport")) {
			pageClass.getMethod("openPage").invoke(o);
			pageClass.getMethod("clickCartButtonAndVerify").invoke(o);
		}
	}
	
	@Test(dataProvider = "pageList")
	public void changeCountryTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("changeCountryAndVerify").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void socialMediaLinkTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifySocialMediaLink").invoke(o);
	}
	
	@Test(dataProvider = "pageList")
	public void footerLinkTest(String pageName) throws Exception{
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyDeliveryPolicyLink").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyHelpCenterLink").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyContactUsLink").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyPrivacyPolicyLink").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyTermsOfServiceLink").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyReturnPolicyLink").invoke(o);
		
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyAboutUsLink").invoke(o);	
		
	}

}
