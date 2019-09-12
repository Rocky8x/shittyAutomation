package com.motorolaintl.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageProductDetail;

public class PRODUCTDETAIL01_NavigationTest extends TestBaseMotoIntl {

	@DataProvider(name = "pageList")
	public Object[][] createData() {
		return new Object[][] { { "com.motorolaintl.pages.PageCategoryModCameras" },
				{ "com.motorolaintl.pages.PageCategoryPower" }, { "com.motorolaintl.pages.PageCategorySpeakers" },
				{ "com.motorolaintl.pages.PageCategoryShell" }, { "com.motorolaintl.pages.PageCategoryProjectors" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesCases" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesChargers" }

		};
	}

	PageBase pageBase = new PageBase();
	PageProductDetail pageProductDetail = new PageProductDetail();

	@Test(dataProvider = "pageList")
	public void navigationTest(String className) throws Exception {

		Class<?> pageClass = Class.forName(className);

		Object o = pageClass.newInstance();
		pageClass.getMethod("openPage").invoke(o);

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
