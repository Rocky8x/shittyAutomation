package ios.kodak.testcases.checklist;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageManageCloudStorage;
import mobile.kodak.base.TestBaseIOS;

public class SETTING07_Subcription extends TestBaseIOS{
	
	@Test(priority = 40, description = "Verify free cloud storage plan")
	public void verifyFreeCloudStoragePlan() {
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Get current storage plan in device setting page
		HashMap<String, String> settingCurrentStorage = PageDeviceSettings.getCurrentStorage();
		
		// Get current storage plan in Manage Cloud Storage page
		PageDashboard.gotoDevice_ManageCloudStorage();
		HashMap<String, String> manageCurrentStorage = PageManageCloudStorage.getYourCurrentPlan();
		
		Assert.assertTrue(settingCurrentStorage.equals(manageCurrentStorage));
	}
}
