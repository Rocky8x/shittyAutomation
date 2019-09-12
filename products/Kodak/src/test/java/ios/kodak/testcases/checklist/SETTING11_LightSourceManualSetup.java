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

public class SETTING11_LightSourceManualSetup extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}
	
	@Test(priority = 46, description = "Verify that user can change Light Source Frequency manual set via camera setting page")
	public void verifyChangeLightSourceFrequencyManual() {
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Open camera setting and go to detail setting
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		
		// Change light source frequency
		String currentSource = PageDeviceSettings.getCurrentLightSourceFrequency();
		String sourceChange = PageDeviceSettings.changeLightSourceFrequency(currentSource);
		Assert.assertNotEquals(PageDeviceSettings.getCurrentLightSourceFrequency(), currentSource, "Light source frequency should change.");
		
		// Change back to default
		PageDeviceSettings.changeLightSourceFrequency(sourceChange);
		Assert.assertEquals(PageDeviceSettings.getCurrentLightSourceFrequency(), currentSource, "Light source frequency should change.");
	}
	
}
