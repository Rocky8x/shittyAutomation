package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class OTA03_OTAByForceUpgrade extends TestBaseIOS{

	Terminal terminal;
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() throws SerialPortException {
		terminal = new Terminal(c_comport);
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 70, description = "FW OTA manually by clicking via camera setting/detail/force upgrade")
	public void forceUpgrateOTA() throws SerialPortException {
		// Downgrade firmware
		terminal.sendCommand("sdcard bu_upgrade", "---------- Success ---------", 10);
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to camera setting
		PageDashboard.swipeBottomToTop();
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();
		
		// Check and update firmware
		Assert.assertTrue(PageDeviceSettings.verifyNewFirmwareAvailableForUpdate());
		PageDeviceSettings.clickUpdateFirmware();
		
		// Verify firmware updated
		Assert.assertTrue(PageDeviceSettings.verifyFirmwareUpdateSuccessful());
		TimeHelper.sleep(60000);
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
