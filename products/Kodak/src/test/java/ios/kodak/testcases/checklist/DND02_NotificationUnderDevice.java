package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageNotification;
import mobile.kodak.base.TestBaseIOS;

public class DND02_NotificationUnderDevice extends TestBaseIOS{

	@Test(priority = 34, description = "Verify user can enable - disable notification setting")
	public void verifyNotificationUnderDevice() {
		// Check and login app
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Verify camera existed
		Assert.assertTrue(PageDashboard.getListCameraSettingIcon().size() > 0, "There are no camera in this account.");
		
		// Enable all notification
		PageDashboard.gotoDoNotDisturbPage();
		PageNotification.disableDoNotDisturb();
		PageNotification.enableNotificationAllDevice();
		
		// Back to dashboard
		PageBase.clickBackButton();
		PageDashboard.clickHomeButton();
		
		// Go to notification page again and verify
		PageDashboard.gotoDoNotDisturbPage();
		Assert.assertTrue(PageNotification.verifyNotificationAllDeviceEnable(), "All notification under device should enable.");
		
		// Disable all notification and verify
		PageNotification.disableNotificationAllDevice();
		Assert.assertTrue(PageNotification.verifyNotificationAllDeviceDisable(), "All notification under device should disable.");
	}
}
