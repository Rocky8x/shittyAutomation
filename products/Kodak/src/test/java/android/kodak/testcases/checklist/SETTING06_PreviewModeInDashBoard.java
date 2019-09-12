package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageHomeSetting;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING06_PreviewModeInDashBoard extends TestBaseAndroid {

	@Test(priority = 11, description = "Preview mode in dashboard")
	public void verifyPreviewModeInDashBoard() {
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		// Enable preview mode
		PageHomeSetting.enablePreviewMode(true);
		PageBase.exitPage();
		// Go to home menu settings and verify
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		boolean rs = PageHomeSetting.verifyPreviewMode("ON");
		Assert.assertTrue(rs, "Preview mode should enable.");
		// Disable preview mode
		PageHomeSetting.enablePreviewMode(false);
		PageBase.exitPage();
		// Go to home menu settings and verify
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		rs = PageHomeSetting.verifyPreviewMode("OFF");
		Assert.assertTrue(rs, "Preview mode should disable.");
	}
	
	@Test(priority = 11, description = "Preview in mobile network")
	public void verifyPreviewInMobileNetwork() {
		PageGetStart.checkAndSignin(c_username, c_password);
		// Enable preview mode
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.clickOnHomeDashboardTextView();
		PageHomeMenu.enablePreviewMode(true);
		// Go to dashboard and go back again to verify
		PageHomeMenu.exitPage();
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.clickOnHomeDashboardTextView();
		Assert.assertTrue(PageHomeMenu.verifyPreviewEnable(true), "Preview mode should enable.");
		PageHomeMenu.enablePreviewMode(false);
		Assert.assertTrue(PageHomeMenu.verifyPreviewEnable(false), "Preview mode should disable.");
	}
}
