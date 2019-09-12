package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class SETTING13_EditSoundMotionDetectionSettingsAndBack extends TestBaseIOS{
	
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}

	@Test(priority = 49, description = "Edit Sound/Motion Detection Settings and back from stream view. (camera has no event)")
	public void verifyEditSoundMotionDetectionSetting() {
		
		// Check and login
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();

		// Delete all event
		PageDeviceSettings.deleteAllEvent();

		// Back to stream view and click Edit sound/motion detection setting
		PageBase.clickBackButton();
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.clickEditSoundMotionDetection();
		
		// Verify app navigate to device setting page
		Assert.assertTrue(PageDeviceSettings.isDisplayed(), "App should navigate to Device Settings page");

		// Click back button and verify
		PageBase.clickBackButton();
		Assert.assertTrue(PageStreamView.isDisplayed(), "App should move back to stream view page.");
	}
}
