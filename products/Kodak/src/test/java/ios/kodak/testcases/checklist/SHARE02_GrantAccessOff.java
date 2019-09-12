package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageGrantAccess;
import ios.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class SHARE02_GrantAccessOff extends TestBaseIOS{

	ApiHelper api;
	Device device;
	
	@Test(priority = 38, description = "Verify that user can share camera via with allow access right off")
	public void shareDeviceViaEmailWithAccessRightOff() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		
		String username = "iostest1235";
		String emailGranted = "iostest1235@restmail.net";
		String pwdGranted = "Cinatic123";
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.gotoGrantAccessPage();
		
		// Remove grant access email if existed
		if(PageGrantAccess.verifyEmailWasGranted(emailGranted)) {
			PageGrantAccess.deleteUserShared(emailGranted);
		}
		// Grant access to another user with allow access off
		PageGrantAccess.grantAccessToUser(emailGranted, device.getName(), false);
		Assert.assertTrue(PageGrantAccess.verifyEmailWasGranted(emailGranted));
		
		// Confirm by email
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
		restMailHelper.deleteAllRestMail();
		restMailHelper.confirmGrantAccesEmail(username, pwdGranted, device.getName());
		
		// Sign out current user and login with email was granted
		PageBase.clickBackButton();
		PageDashboard.signOut();
		PageLogin.loginApp(username, pwdGranted);
		TimeHelper.sleep(10000);
		
		// Go to Camera setting and verify
		PageBase.closeAnyTutorial(false);
		PageDashboard.openDeviceSetting(device.getName());
		Assert.assertFalse(PageDeviceSettings.verifySettingTitleDisplay(), "Verify user should not access to Camera setting.");
		PageDeviceSettings.gotoDetailsCamera();
		String userShared = PageDeviceSettings.getUserShared();
		Assert.assertEquals(userShared, c_username);
		
		// Cleanup device
		PageDeviceSettings.DeleteDevice();
	}
}
