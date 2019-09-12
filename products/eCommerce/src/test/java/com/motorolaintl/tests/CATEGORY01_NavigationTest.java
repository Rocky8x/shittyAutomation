package com.motorolaintl.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.log.Log;

public class CATEGORY01_NavigationTest extends TestBaseMotoIntl {

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

	// Test open all sub menu and navigate to all product detail page
	@Test(dataProvider = "pageList")
	public void navigationToProductDetailPageTest(String pageName) throws Exception {

		Log.info("Navigation test: " + pageName);
		Class<?> pageClass = Class.forName(pageName);

		Object o = pageClass.newInstance();
		pageClass.getMethod("openPage").invoke(o);
		pageClass.getMethod("clickProductAndVerify").invoke(o);

	}

}
