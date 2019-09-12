package android.kodak.testcases.bugs;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.driver.DriverManager;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class Bug5190_ClickCameraToViewStream extends TestBaseAndroid {

	@Test(priority = 0, description = "TC001: Press here for live view button show in the first time")
	public void TC001_VerifyShowLiveStreamButtonInTheFirstTime() throws Exception {
		String outPut = adbClearAppData();
		Assert.assertTrue(outPut.contains("Success"));
		DriverManager.createWebDriver(driverSetting);
		PageGetStart.checkAndSignin(c_username, c_password);
		Assert.assertTrue(PageDashboard.verifyPressForLiveViewButtonDisplay(true),
				"Live View Button should display at the first time.");
	}

	@Test(priority = 2, description = "TC002: Click on Press here for live view button")
	public void TC002_VerifyClickOnShowLiveStreamButton() {
		PageGetStart.checkAndSignin(c_username, c_password);
		Assert.assertTrue(PageDashboard.verifyPressForLiveViewButtonDisplay(true),
				"Live View Button should display at the first time.");
		PageDashboard.getClickForLiveViewButton().click();
		Assert.assertTrue(PageStreamView.getMenuImage().getWebElement().isDisplayed(), "Live stream page should display");
	}

	@Test(priority = 3, description = "TC003: Press here for live view button should not show because user already pressed it before.")
	public void TC003_VerifyShowLiveStreamButtonNotShow() {
		PageGetStart.checkAndSignin(c_username, c_password);
		Assert.assertTrue(PageDashboard.verifyPressForLiveViewButtonDisplay(false),
				"Press here for live view button should not show because user already pressed it before.");
	}	
}
