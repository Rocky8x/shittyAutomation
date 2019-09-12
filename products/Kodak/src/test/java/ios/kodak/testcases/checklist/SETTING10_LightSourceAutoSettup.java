package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseIOS;

public class SETTING10_LightSourceAutoSettup extends TestBaseIOS{
	
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}

	@Test(priority = 45, description = "Verify light source frequency auto set during setup")
	public void verifyLightSourceFrequencyAutoSettup(){
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to camera setting
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();
		
		// get time zone
		float timeZone = PageDeviceSettings.getTimeZoneSystem();
		PageDeviceSettings.gotoDetailsCamera();
		
		// Get light source frequency
		PageDeviceSettings.gotoCameraSettingDetail();
		String source = PageDeviceSettings.getCurrentLightSourceFrequency();
		
		// verify result
		Assert.assertTrue(PageDeviceSettings.verifyLightSourceFrequencyByTimeZone(timeZone, source));
	}
}
