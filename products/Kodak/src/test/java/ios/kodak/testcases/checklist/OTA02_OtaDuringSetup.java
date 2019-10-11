package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageIOSSetting;
import ios.kodak.object.PageSetup;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class OTA02_OtaDuringSetup extends TestBaseIOS{
	Terminal terminal;
	ApiHelper api;
	Device device;
	String deviceid1, devicessid1, comport1, wepwifiname, wepwifipassword;
	
//	@BeforeMethod
//	public void setUp() throws SerialPortException {
//		terminal = new Terminal(c_comport);
//		api = new ApiHelper();
//		api.userLogin(c_username, c_password);
//		api.getDevices();
//		device = api.getDeviceByDeviceId(c_deviceid);
//	}
	@Parameters({"deviceid1", "devicessid1", "comport1"})
	@BeforeClass
	public void setup(String deviceid1, String devicessid1, String comport1) throws SerialPortException{
		terminal = new Terminal(comport1);
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(deviceid1);
		this.deviceid1 = deviceid1;
		this.devicessid1 = devicessid1;
//		//Check and sign in
//		PageGetStart.checkAndSignin(c_username, c_password);
//				
//		// Open camera setting
//		PageDashboard.openDeviceSetting(device.getName());
//				
//		// Delete camera and verify
//		PageDeviceSettings.DeleteDevice();
//		TimeHelper.sleep(10000);
//		terminal.unlockCameraShell();
				
	}
	
	
	@Test(priority = 71, description = "OTA app during setup")
	public void otaDuringSetup() throws SerialPortException {
		String secureType = "wpa";
		if (c_wifiname.contains("Beisiusiu")){
			secureType = "wep";
		}
		
		// Downgrade firmware
		terminal.sendCommand("sdcard bu_upgrade", "---------- Success ---------", 10);
		
		TimeHelper.sleep(7000);
//		 Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.clickAddNewCamera();
		PageSetup.selectCameraImageByModelName(Device.getModelNameByUuid(deviceid1));
		TimeHelper.sleep(2000);
		PageSetup.clickProceedAnyway();
		TimeHelper.sleep(2000);
		PageSetup.clickOnContinueSettupButton();
		TimeHelper.sleep(2000);
		PageSetup.clickOnContinueSettupButton();
		TimeHelper.sleep(2000);
		PageSetup.clickGotoSettingsButton();
		TimeHelper.sleep(2000);
		PageIOSSetting.clickWifiSettings();
		TimeHelper.sleep(2000);
		PageIOSSetting.clickOnWifiName(devicessid1);
		TimeHelper.sleep(10000);
		PageIOSSetting.backToKodakApp();
		TimeHelper.sleep(10000);
		PageSetup.clickSelectAnotherWifi();
		TimeHelper.sleep(20000);
		PageSetup.selectWifitoConnectRouter(c_wifiname, c_wifipassword, secureType);
		PageSetup.clickProceedButton();
		PageSetup.clickCustomButton();
		PageBase.clickContinueButton();
		PageBase.clickDoneButton();
		TimeHelper.sleep(60000);
		
		// Go to camera setting
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();
		
		// Verify firmware updated
		Assert.assertTrue(PageDeviceSettings.getNewFirmwareVersionText().getWebElement() == null);
		
	}
	
	@AfterMethod
	public void cleanUp() {
		try {
			terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
