package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageSettings;
import ios.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class HUMTMP02_TemperatureTest extends TestBaseIOS{

	Terminal terminal;
	ApiHelper api;
	Device device;

	@BeforeMethod
	public void precondition() throws SerialPortException {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		terminal = new Terminal(c_comport);
	}

	@Test(priority = 21, description = "Verify temperature (C and F) will show according phone setting")
	public void verifyTemperatureSetup() throws SerialPortException {

//		 Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);

		// Go to home menu setting page
		PageDashboard.gotoHomeMenuSettingPage();
		PageSettings.selectFahrenheitUnit();
		Assert.assertTrue(PageSettings.verifyFahrenheitUnitSelected(), "Fahrenheit unit should selected.");

		// Back to dashboard page
		PageBase.clickBackButton();
		PageDashboard.clickHomeButton();
		
		// Go to Stream view and verify temperature unit
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.isDisplayed();
		Assert.assertTrue(PageStreamView.verifyTemperatureUnitInStreamView("°F"), "Temperature unit should display with Fahrenheit.");
		
		// Go to device setting and verify temperature unit on temperature setting
		PageBase.clickBackButton();
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		PageDeviceSettings.enableTemperatureSetting();
		Assert.assertTrue(PageDeviceSettings.verifyTemperatureUnit("°F"), "Temperature unit should display with Fahrenheit.");

		// Setting temperature with Celsius unit
		PageBase.clickBackButton();
		PageDashboard.gotoHomeMenuSettingPage();
		PageSettings.selectCelsiusUnit();
		Assert.assertTrue(PageSettings.verifyCelsiusUnitSelected(), "Celsius unit should selected.");

		// Back to dashboard page
		PageBase.clickBackButton();
		PageDashboard.clickHomeButton();
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.isDisplayed();

		// Get temperature in camera console and verify
		terminal.clearTeratermLog();
		terminal.sendCommand("atecmd get_temp");
		String cameraTemp = terminal.getTeratermLog();
		String temp = StringHelper.getStringByString(cameraTemp, "Temperature = ", " degree Celsius", false);
		Log.info("Temperature in terminal: " + temp);
		
		// Verify temperature sync between app and camera
		Assert.assertTrue(PageStreamView.verifyTemperatureSyncWithAppAndCamera(temp), "Temperature should sync between app and camera.");
		Assert.assertTrue(PageStreamView.verifyTemperatureUnitInStreamView("°C"), "Temperature unit should display with Celsius.");

		// Go to device setting and verify temperature unit on temperature setting
		PageBase.clickBackButton();
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		PageDeviceSettings.enableTemperatureSetting();
		Assert.assertTrue(PageDeviceSettings.verifyTemperatureUnit("°C"), "Temperature unit should display with Celsius.");
	}
	
	@AfterMethod
	public void cleanUp() throws SerialPortException {
		terminal.closePort();
	}
}
