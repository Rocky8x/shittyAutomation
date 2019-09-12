package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageGrantAccess;
import android.kodak.object.PageHomeMenu;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;
import net.restmail.KodakRestMailHelper;

public class SHARE01_GrantAccessWithAccessRightOn extends TestBaseAndroid {
	private Device device;
	
	@BeforeMethod
	public void Precondition() throws SerialPortException {

		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 12, description = "Grant Access and Share devices with access right ON")
	public void grantAccessViaEmailWithAccessRightOn() {

		String username = "qatest1235";
		String emailGranted = "qatest1235@restmail.net";
		String password = "Cinatic123";
		
		PageGetStart.checkAndSignin(c_username, c_password);
		Assert.assertEquals(PageDashboard.getAddDeviceBigBtn().isEnabled(), true);
		
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoGrantAccessPage();
			// Remove grant access to email if existed
		try {
			if(PageGrantAccess.verifyEmailWasGrantAccess(emailGranted)) {
				PageGrantAccess.removeAllSharedUser();
			}
			//grant access to user
			PageGrantAccess.grantAccessToUser(emailGranted, device.getName(), true);
			Assert.assertTrue(PageGrantAccess.verifyEmailWasGrantAccess(emailGranted), "Email: " + emailGranted + " should appear in list user shared.");
			PageBase.exitPage();
			KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
			restMailHelper.deleteAllRestMail();
			restMailHelper.confirmGrantAccesEmail(username, password, device.getName());

			PageGetStart.checkAndSignin(username, password);
			PageDashboard.openDeviceSetting(device.getName());

			// verify shared user and setting
			Assert.assertTrue(PageCameraSetting.verifyDeviceSharedBy(c_username), "Owner device should display with: " + c_username);
			Assert.assertTrue(PageCameraSetting.verifySettingTitleDisplay(), "SETTINGS title in camera setting page should display.");
		}finally {
			// login to main account and remove grant access user
			PageBase.exitPage();
			PageGetStart.checkAndSignin(c_username, c_password);
			PageDashboard.gotoHomeMenuPage();
			PageHomeMenu.gotoGrantAccessPage();
			if(PageGrantAccess.verifyEmailWasGrantAccess(emailGranted)) {
				PageGrantAccess.removeAllSharedUser();
			}
		}
	}
}
