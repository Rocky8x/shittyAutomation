package com.kodaksmarthome.tests;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.ebn.automation.core.WbElement;
import com.kodaksmarthome.pages.PageApp;
import com.kodaksmarthome.pages.PageCart;
import com.kodaksmarthome.pages.PageCategoryBabyMonitors;
import com.kodaksmarthome.pages.PageCategorySecurityCameras;
import com.kodaksmarthome.pages.PageHome;
import com.kodaksmarthome.pages.PageProduct;

public class ContentVerification extends TestBaseKodak {

	PageHome					pageHome					= new PageHome();
	PageCategoryBabyMonitors	pageCategoryBabyMonitors	= new PageCategoryBabyMonitors();
	PageCategorySecurityCameras	pageCategorySecurityCameras	= new PageCategorySecurityCameras();
	PageApp						pageApp						= new PageApp();

	@Test
	public void contentVerificationHomePage() {

		pageHome.verifyPageHeader();
		pageHome.verifyPageFooter();
		pageHome.getCategoryBabyMonitors().click();
		pageCategoryBabyMonitors.verifyUrl();
		pageCategoryBabyMonitors.goBack();
		pageHome.getCategorySecurityCamera().click();
		pageCategorySecurityCameras.verifyUrl();
		pageCategorySecurityCameras.goBack();

		pageHome.getExploreBabyMonitorsButton().click();
		pageCategoryBabyMonitors.verifyUrl();
		pageCategoryBabyMonitors.goBack();

		pageHome.getAppLink().click();
		pageApp.verifyUrl();
		pageApp.goBack();

		assertTrue(pageHome.getReadFAQsButton().isDisplayed());
		assertTrue(pageHome.getEmailSupportButton().isDisplayed());
		assertTrue(pageHome.getBbMonitorTextInBanner().isDisplayed());
		assertTrue(pageHome.getAlwaysInTheMomentText().isDisplayed());
		assertTrue(pageHome.getVideoInBanner().isEnabled());
	}

	@Test
	public void contentVerificationCategoryBabyMon() {

		pageHome.getCategoryBabyMonitors().click();
		pageCategoryBabyMonitors.verifyPageContent();

	}

	@Test
	public void contentVerificationCategorySecurityCam() {

		pageHome.getCategorySecurityCamera().click();
		pageCategorySecurityCameras.verifyPageContent();
	}

	@Test
	public void contentVerificationProducDetail() throws Exception {

		pageHome.getCategoryBabyMonitors().click();
		HashMap<String, WbElement> productsBabyCam = pageCategoryBabyMonitors.getDefinedProducts();
		for (String productName : productsBabyCam.keySet()) {
			pageCategoryBabyMonitors.getProductByName(productName).click();
			PageProduct pageProduct = new PageProduct();
			pageProduct.verifyPageContent();
			pageProduct.goBack();
		}

		pageCategoryBabyMonitors.goBack();
		pageHome.getCategorySecurityCamera().click();
		HashMap<String, WbElement> productsSecurityCam = pageCategorySecurityCameras
				.getDefinedProducts();
		for (String productName : productsSecurityCam.keySet()) {
			pageCategorySecurityCameras.getProductByName(productName).click();
			PageProduct pageProduct = new PageProduct();
			pageProduct.verifyPageContent();
			pageProduct.goBack();
		}
	}

	@Test
	public void contentVerificationCart() {

		pageHome.getCategoryBabyMonitors().click();
		WbElement	itemToAddToCart	= null;
		WbElement	addToCartButton	= null;

		for (WbElement product : pageCategoryBabyMonitors.getProductList()) {
			addToCartButton = pageCategoryBabyMonitors.getButtonOfProductElement(product);
			if (addToCartButton.getText().equals("Buy Now")) {
				itemToAddToCart = product;
				break;
			}
		}
		addToCartButton.click();
		PageCart pageCart = new PageCart();
		pageCart.verifyPageContent();
	}

	public void contentVerificationLogin() {

	}
}
