package com.motorolaintl.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.log.Log;

public class CATEGORY02_ContentVerification extends TestBaseMotoIntl {
	@DataProvider(name = "pageList")
	public Object[][] pageList() {
		return new Object[][] { { "com.motorolaintl.pages.PageCategoryMod" },
				{ "com.motorolaintl.pages.PageCategoryModShells" }, { "com.motorolaintl.pages.PageCategoryPower" },
				{ "com.motorolaintl.pages.PageCategorySpeakers" }, { "com.motorolaintl.pages.PageCategoryProjectors" },
				{ "com.motorolaintl.pages.PageCategoryModCameras" },
				{ "com.motorolaintl.pages.PageCategoryAcessories" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesCases" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesChargers" } };
	}

	// Test open all sub menu and verify content(basic content, name, price
	// product, click to product)
	@Test(dataProvider = "pageList")
	public void contentVerification(String pageName) throws Exception {
		Log.info("Navigation test: " + pageName);
		Class<?> pageClass = Class.forName(pageName);

		Object o = pageClass.newInstance();
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyPageBasicContent").invoke(o);
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("verifyListProduct").invoke(o);
		pageClass.getMethod("verifyPriceProduct").invoke(o);
		pageClass.getMethod("clickProductAndVerify").invoke(o);
	}

}
