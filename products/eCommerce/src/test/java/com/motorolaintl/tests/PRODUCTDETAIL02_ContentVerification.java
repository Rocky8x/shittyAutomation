package com.motorolaintl.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.ebn.automation.core.WbDriverManager;
import com.motorolaintl.pages.PageBase;
import com.motorolaintl.pages.PageProductDetail;

public class PRODUCTDETAIL02_ContentVerification extends TestBaseMotoIntl {

	@DataProvider(name = "pageList")
	public Object[][] createData() {
		return new Object[][] { { "com.motorolaintl.pages.PageCategoryModCameras" },
				{ "com.motorolaintl.pages.PageCategoryPower" }, { "com.motorolaintl.pages.PageCategorySpeakers" },
				{ "com.motorolaintl.pages.PageCategoryShell" }, { "com.motorolaintl.pages.PageCategoryProjectors" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesCases" },
				{ "com.motorolaintl.pages.PageCategoryAccessoriesChargers" },

		};
	}

	PageBase pageBase = new PageBase();
	PageProductDetail pageProductDetail = new PageProductDetail();

	@Test(dataProvider = "pageList")
	public void contentVerificationTest(String className) throws Exception {

		Class<?> pageClass = Class.forName(className);

		Object o = pageClass.newInstance();
		pageClass.getMethod("openPage").invoke(o);

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
