package com.motorolaintl.tests;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;
import com.motorolachargers.pages.PageBase;

public class CATEGORY01_NavigationTest extends TestBaseMotoIntl {

	Class<?> pageClass;
	Object o;
	PageBase pageBase = new PageBase();

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

	// Test open all sub menu and navigate to all product detail page
	@Test(dataProvider = "pageList")
	public void navigationToProductDetailPageTest(String pageName) throws Exception {

		pageClass.getMethod("clickProductAndVerify").invoke(o);

	}

}
