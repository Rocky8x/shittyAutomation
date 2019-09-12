package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageSettings;
import mobile.kodak.base.TestBaseIOS;

public class DETECT01_TemperatureDetection extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	@BeforeClass
	public void setup() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 27, description = "Verify UI temperature detection")
	public void UITest() {
		String minLow = "10";
		String maxLow = "20";
		
		String minHigh = "21";
		String maxHigh = "37";
		
		PageGetStart.checkAndSignin(c_username, c_password);
		// Go to home menu setting page
		PageDashboard.gotoHomeMenuSettingPage();
		PageSettings.selectCelsiusUnit();
		Assert.assertTrue(PageSettings.verifyCelsiusUnitSelected(), "Celsius unit should selected.");
		
		// Back to dashboard page
		PageBase.clickBackButton();
		PageDashboard.clickHomeButton();
		PageDashboard.openDeviceSetting(device.getName());
		
		PageDeviceSettings.gotoCameraSettingDetail();
		PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_OFF);
		PageDeviceSettings.enableTemperatureSetting();
		boolean rs = PageDeviceSettings.verifyTemperatureSettingEnable(true);
		Assert.assertTrue(rs, "Temperature setting should enable");
		
		rs = PageDeviceSettings.verifyMaximumTempSetting(maxLow, maxHigh);
		Assert.assertTrue(rs, "Maximum temperature display wrong");
		
		rs = PageDeviceSettings.verifyMinimumTempSetting(minLow, minHigh);
		Assert.assertTrue(rs, "Minimum temperature display wrong");
		
		PageDeviceSettings.disableTemperatureSetting();
		rs = PageDeviceSettings.verifyTemperatureSettingEnable(false);
		Assert.assertTrue(rs, "Temperature setting should disable");
		
	}
}
