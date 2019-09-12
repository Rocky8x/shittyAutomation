package com.kodaksmarthome.tests;

import org.testng.annotations.Test;

import com.ebn.automation.core.WbDriverManager;
import com.kodaksmarthome.pages.PageCategoryBabyMonitors;
import com.kodaksmarthome.pages.PageCategorySecurityCameras;
import com.kodaksmarthome.pages.PageHome;

public class NavigateToProductPagesFromCatgoryPagesTest  extends TestBaseKodak{

	@Test
	public void navigateToEachProductPageAndVerifyContent() {
		
		PageHome home = new PageHome();
		home.getCategoryBabyMonitors().click();
		
		PageCategoryBabyMonitors babyCat = new PageCategoryBabyMonitors();
		babyCat.verifyProductLinks();
		
		WbDriverManager.getDriver().navigate().back();
		home.getCategorySecurityCamera().click();
		PageCategorySecurityCameras secCat = new PageCategorySecurityCameras();
		secCat.verifyProductLinks();
	}
}
