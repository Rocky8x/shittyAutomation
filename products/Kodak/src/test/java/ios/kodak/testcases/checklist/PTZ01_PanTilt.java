package ios.kodak.testcases.checklist;


import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageIOSSetting;
import ios.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class PTZ01_PanTilt extends TestBaseIOS{
	ApiHelper api;
	Device device;
	Terminal terminal;
//	String settingBundleID = "com.apple.Preferences";
	
	@BeforeClass
	@Parameters({"comport1", "deviceid1"})
	public void precondition(String comport1, String deviceid1) throws SerialPortException {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(deviceid1);
		
		terminal = new Terminal(comport1);
	}
	
//	@BeforeMethod
//	@Override
//	public void beforeMethod(Method method) {
//		Log.info(String.format("---------------------- START: %s ----------------------", method.getName()));
//		driverSetting = (DriverSetting) context.getBean(TestBase.c_platform);
//		driverSetting.setBundleId(settingBundleID);
//		DriverManager.createWebDriver(driverSetting);
//	}
	
	@Test(priority = 56, description = "Pan tilt with natural PTZ off")
	public void panTiltWithNaturalPTZOff() {
		final String SETTING_BUNDLE_ID = "com.apple.Preferences";
		final String KODAK_BUNDLE_ID = "com.perimetersafe.kodaksmarthome";
		
		// Disable Natural PTZ direction
		PageBase.switchApp(SETTING_BUNDLE_ID, DriverManager.getDriver().getAppiumDriver());
		PageIOSSetting.clickKodakApp();
		PageIOSSetting.enableNaturalPTZDirection(false);
		
		// Open kodak app
//		driverSetting.setBundleId(kodakBundleId);
//		DriverManager.createWebDriver(driverSetting);
		
		// Check and sign in
		PageBase.switchApp(KODAK_BUNDLE_ID, DriverManager.getDriver().getAppiumDriver());
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.getCameraStatusByName(device.getName());
		PageDashboard.selectDeviceToView(device.getName());
		String streamMode = PageStreamView.getStreamMode(30);
		
		if(streamMode.contains("OK")) {
			boolean naturalPtz = false;
			// Pan-tilt in Landscap mode
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.PORTRAIT);
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.LANDSCAPE);
			PageStreamView.checkPanTilt(naturalPtz,terminal);
			
			// Pan-tilt in portrait mode
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.PORTRAIT);
			PageStreamView.checkPanTilt(naturalPtz,terminal);
		}
	}
	
	@Test(priority = 57, description = "Pan tilt with natural PTZ on")
	public void panTiltWithNaturaPTZOn() {
		final String SETTING_BUNDLE_ID = "com.apple.Preferences";
		final String KODAK_BUNDLE_ID = "com.perimetersafe.kodaksmarthome";
		
		// Disable Natural PTZ direction
		PageBase.switchApp(SETTING_BUNDLE_ID, DriverManager.getDriver().getAppiumDriver());
		PageIOSSetting.clickKodakApp();
		PageIOSSetting.enableNaturalPTZDirection(true);
		
		// Open kodak app
//		driverSetting.setBundleId(kodakBundleId);
//		DriverManager.createWebDriver(driverSetting);
		
		// Check and sign in
		PageBase.switchApp(KODAK_BUNDLE_ID, DriverManager.getDriver().getAppiumDriver());
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.getCameraStatusByName(device.getName());
		PageDashboard.selectDeviceToView(device.getName());
		String streamMode = PageStreamView.getStreamMode(30);
		
		if(streamMode.contains("OK")) {
			boolean naturalPtz = true;
			// Pan-tilt in Landscap mode
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.PORTRAIT);
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.LANDSCAPE);
			PageStreamView.checkPanTilt(naturalPtz,terminal);
			
			// Pan-tilt in portrait mode
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.PORTRAIT);
			PageStreamView.checkPanTilt(naturalPtz,terminal);
		}
	}
	
//	@AfterMethod
//	public void cleanUp() {
//		try {
//			DriverManager.getDriver().getAppiumDriver().terminateApp(settingBundleID);
////			terminal.closePort();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
}
