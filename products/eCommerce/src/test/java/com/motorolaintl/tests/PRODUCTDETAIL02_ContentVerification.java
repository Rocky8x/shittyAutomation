package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageProductDetail;

public class PRODUCTDETAIL02_ContentVerification extends TestBaseMotoIntl {
	
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
				{ "com.motorolaintl.pages.PageCategoryAccessoriesChargers" },

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
	public void contentVerificationTest(String className) throws Exception {

		for (int i = 0; i < pageBase.getProductList().size(); i++) {
			// Check product is buyable
			WbDriverManager.waitForPageLoad();
			List<String> product = (List<String>) pageClass.getMethod("productList").invoke(o);
			boolean status = pageBase.isProductBuyAble(product.get(i));
			pageBase.getProductList().get(i).click();
			WbDriverManager.waitForPageLoad();

			// Check name
			assertEquals(pageProductDetail.getTitleProductName(), product.get(i));

			// Check description is display
			assertTrue(pageProductDetail.getDescriptionProduct().isDisplayed());

			// Check image
			assertTrue(pageProductDetail.getImageProductCurrentShow().isDisplayed());
			pageProductDetail.chageImageProductAndVerify();

			// Check Status
			if (status) {
				assertTrue(pageProductDetail.getAddToCartBtn().isDisplayed());
			} else {
				try {
					assertTrue(pageProductDetail.getOutOfStockStatus().isDisplayed());
				} catch (Exception e) {
					assertTrue(pageProductDetail.getVerizonHiddenBtn().isDisplayed());
				}
			}
			pageClass.getMethod("openPage").invoke(o);
		}

	}
}
