package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageDoNotDisturb;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import mobile.kodak.base.TestBaseAndroid;

public class DND01_DoNotDisturb extends TestBaseAndroid {

	@Test(priority = 13, description = "Do not disturb")
	public void verifyDoNotDisturbUi() {
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.gotoHomeMenuPage();
		// Enable do not disturb
		PageHomeMenu.gotoNotificationPage();
		PageDoNotDisturb.enableDoNotDisturb(true);
		// Go to dashboard and go back to notification page verify
		PageBase.exitPage();
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoNotificationPage();
		Assert.assertTrue(PageDoNotDisturb.verifyDoNotDisturbEnable(true), "Do not disturb should enable.");
		// Disable do not disturb
		PageDoNotDisturb.enableDoNotDisturb(false);
		Assert.assertTrue(PageDoNotDisturb.verifyDoNotDisturbEnable(false), "Do not disturb should disable.");
		PageDoNotDisturb.enableDoNotDisturb(true);
	}
}
