package com.motorolaintl.tests;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageProductDetail;

public class PRODUCTDETAIL01_NavigationTest extends TestBaseMotoIntl {
	
	
	Class<?> pageClass;
	Object o;
	PageBase pageBase = new PageBase();
	PageProductDetail pageProductDetail = new PageProductDetail();

	@DataProvider(name = "pageList")
	public Object[][] createData() {
		return new Object[][] { { "com.motorolaintl.pages.PageCategoryModCameras" },
				{ "com.motorolaintl.pages.PageCategoryPower" }, { "com.motorolaintl.pages.PageCategorySpeakers" },
				{ "com.motorolaintl.pages.PageCategoryShell" }, { "com.motorolaintl.pages.PageCategoryProjectors" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesCases" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesChargers" }

		};
	}

	
	@BeforeMethod
	public void beforeMethod(Object[] pageList, Method method, ITestContext ctx ) throws Exception {
		if (pageList.length >0){
			ctx.setAttribute("testName", (String) pageList[0]);
		}
		String pageName = (String) pageList[0];
		Log.info("Navigation test: " + pageName);
		pageClass = Class.forName(pageName);

		o = pageClass.newInstance();
		pageClass.getMethod("openPage").invoke(o);
		WbDriverManager.waitForPageLoad();
	}

	@Test(dataProvider = "pageList")
	public void navigationTest(String className) throws Exception {

		for (int i = 0; i < pageBase.getProductList().size(); i++) {
			WbDriverManager.waitForPageLoad();
			pageBase.getProductList().get(i).click();
			WbDriverManager.waitForPageLoad();
			// Check basic content
			pageClass.getMethod("verifyPageBasicContent").invoke(o);
			// Check click Logo icon
			pageClass.getMethod("clickMainLogoAndVerify").invoke(o);

			// Check link Motorola home page link
			pageClass.getMethod("verifyGotoMotorolaTopLeftLink").invoke(o);
			// Check link MotorolaPhone home page link
			pageClass.getMethod("verifyGotoMotorolaPhoneTopRightLink").invoke(o);

			// check menu category
			pageClass.getMethod("hoverMotoModMenuAndVerify").invoke(o);
			pageClass.getMethod("hoverAccessoriesMenuAndVerify").invoke(o);
			pageClass.getMethod("verifyMinicart").invoke(o);

			// check link footer header
			pageClass.getMethod("verifyFooterSupportContactLink").invoke(o);
			pageClass.getMethod("verifyHeaderSupportLink").invoke(o);
			pageClass.getMethod("verifyPrivacyPolicyLink").invoke(o);
			pageClass.getMethod("verifyFacebookLink").invoke(o);
			pageClass.getMethod("verifyTwitterLink").invoke(o);
			pageClass.getMethod("verifyInstagramLink").invoke(o);

			// Test Navigate to parents page using sub category menu
			String path = (String) pageClass.getField("PATH").get(o);
			pageBase.navigateToParentSubCategoryMenuAndVeridy(path);

			// Change country and verify
			pageClass.getMethod("changeCountryAndVerify").invoke(o);

		}
	}

}
