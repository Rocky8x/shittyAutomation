package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseIOS;

public class SETTING06_PreviewMode extends TestBaseIOS{

	@Test(priority = 39, description = "Verify preview mode in dashboard include Preview in mobile network and wi-fi network")
	public void verifyPreviewModeInDashboard() {
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to home menu dash board
		PageDashboard.gotoHomeMenuDashboard();
		
		// Disable all preview mode in dashboard
		PageDashboard.enableAllPreviewMode(false);
		
		// Back to dashboard - go to home menu dashboard again and verify
		PageDashboard.clickHomeButton();
		PageDashboard.gotoHomeMenuDashboard();
		Assert.assertTrue(PageDashboard.verifyAllPreviewModeEnable(false), "All preview mode in dashboard should disable.");
		
		// Enable all preview mode and verify
		PageDashboard.enableAllPreviewMode(true);
		Assert.assertTrue(PageDashboard.verifyAllPreviewModeEnable(true), "All preview mode in dashboard should enable.");
	}
}
