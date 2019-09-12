package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.driver.DriverManager;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageTestFlight;
import mobile.kodak.base.TestBaseIOS;

public class TM000_CheckUpdateApp extends TestBaseIOS{
	String testFlightBundleId = "com.apple.TestFlight";
	String kodakBundleId = "com.perimetersafe.kodaksmarthome";
	
	@Test(priority = -100, description = "Check and update app")
	public void checkAndUpdateApp() {
		// Close all permission
		PageBase.allowIosPermissionIfAsker(PageBase.IOS_PERMISSION_LOCATION);
		PageBase.allowIosPermissionIfAsker(PageBase.IOS_PERMISSION_NOTIFICATION);
		
		// Get current app version
		PageGetStart.checkAndSignin(c_username, c_password);
		String currentVer = PageDashboard.getCurrentAppVersion();
		PageDashboard.clickHomeButton();
		
		// Open TestFlight and check new version
		driverSetting.setBundleId(testFlightBundleId);
		DriverManager.createWebDriver(driverSetting);
		driverSetting.setBundleId(kodakBundleId);
		boolean rs = PageTestFlight.updateKodakApp(currentVer);
		String updatedVersion = PageDashboard.getCurrentAppVersion();
		if(rs) {
			Assert.assertNotEquals(updatedVersion, currentVer);
		}else {
			Assert.assertEquals(updatedVersion, currentVer);
		}
	}
}
