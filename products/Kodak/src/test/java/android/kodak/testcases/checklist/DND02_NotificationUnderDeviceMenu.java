package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageDoNotDisturb;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseAndroid;

public class DND02_NotificationUnderDeviceMenu extends TestBaseAndroid {
	
	@Test(priority = 11, description = "Verify setting notification for devices")
	public void verifyNotificationUnderDevice() {
		
		PageGetStart.checkAndSignin(c_username, c_password);
		// Verify camera existed.
		Assert.assertTrue(PageDashboard.getListCameraNameInDashBoard().size() > 0, "There are no camera in this account.");
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoNotificationPage();
		
		// Disable notification
		PageDoNotDisturb.enableAllDeviceNotification(false);
		PageBase.exitPage();
		
		// Go notification page and verify
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoNotificationPage();
		Assert.assertTrue(PageDoNotDisturb.verifyAllDeviceNotificationEnable(false), "Notification should disable.");
		
		// Enable notification
		PageDoNotDisturb.enableAllDeviceNotification(true);
		PageBase.exitPage();
		
		// Logout - login again and verify
		PageGetStart.goToSigninPage();
		PageLogin.loginApp(c_username, c_password);
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoNotificationPage();
		Assert.assertTrue(PageDoNotDisturb.verifyAllDeviceNotificationEnable(true), "Notification should enable.");
	}
}
