package com.motorolachargers.tests;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;
import com.ebn.base.PageBaseCommon;
import com.motorolachargers.pages.PageBase;
import com.motorolachargers.pages.PageHome;
import com.motorolachargers.pages.PageProductDetail;

public class PRODUCTDETAIL01_NavigationTest extends TestBaseChargers{
	
	PageBase pageBase = new PageBase();
	PageHome pageHome = new PageHome(pageBase);

	
	@DataProvider(name = "country")
	public Object[][] country() {
		return new Object[][] {
			{"United States"},
			{"United Kingdom"},
			{"India"},
			{"Canada - English"},
			{"Canada - French"},
			{"Europe - English"},
			{"Germany"},
			{"France"},
			{"Spain"},
			{"Italy"},
			{"Mexico"},
			};
	}
	
	@Test()
	public void productDetail001_BasicNavigationTest() {
//		pageHome.changeCountryHeaderIcon(country);
		String mainUrl = WbDriverManager.getCurrentUrl();
		pageBase.baseURL = mainUrl;
		PageProductDetail pageProductDetail = new PageProductDetail(pageBase);
		List<String> productLink = new ArrayList<>();
		productLink = pageHome.getLinkProduct();
		for (String link : productLink){
			String path = link.replace(mainUrl, "");
			pageProductDetail.PATH = path;
			pageProductDetail.openPage(link);
			pageProductDetail.verifyBasicContent();
			pageProductDetail.clickMainLogoAndVerify();
			pageProductDetail.clickHeaderMotorolaHomePageTxtAndVerify();
			pageProductDetail.clickCartButtonAndVerify();
			pageProductDetail.clickCartButtonAndVerify();
			pageProductDetail.clickMenuProductTxtAndVerify();
			pageProductDetail.clickMenuSupportTxtAndVerify();
			pageProductDetail.clickLookingForHelpTxtAndVerify();
			pageProductDetail.verifySocialMediaLink();
			pageProductDetail.verifyDeliveryPolicyLink();
			pageProductDetail.verifyHelpCenterLink();
			pageProductDetail.verifyContactUsLink();
			pageProductDetail.verifyPrivacyPolicyLink();
			pageProductDetail.verifyTermsOfServiceLink();
			pageProductDetail.verifyReturnPolicyLink();
			pageProductDetail.verifyAboutUsLink();
			pageProductDetail.changeCountryAndVerify();
	     }	
	}
	
	@Test(dataProvider = "country")
	public void productDetail002_NavigateTest(String country) {	
		pageHome.changeCountryHeaderIcon(country);
		String mainUrl = WbDriverManager.getCurrentUrl();
		pageBase.baseURL = mainUrl;
		PageProductDetail pageProductDetail = new PageProductDetail(pageBase);
		List<String> productLink = new ArrayList<>();
		productLink = pageHome.getLinkProduct();
		 for (String link : productLink){
			String path = link.replace(mainUrl, "");
			pageProductDetail.PATH = path;
			pageProductDetail.openPage(link);
			pageProductDetail.navigateTest();
	     }
	}

}
