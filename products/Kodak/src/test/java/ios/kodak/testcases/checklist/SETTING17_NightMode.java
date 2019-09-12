package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class SETTING17_NightMode extends TestBaseIOS{
	
	ApiHelper api;
	Device device;
	Terminal terminal;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}

	@Test(priority = 24, description = "Verify that user can change night version setting successfully")
	public void verifyUserCanChangeNightVersion() throws SerialPortException {
		String camLog;
		terminal = new Terminal(c_comport);
		
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Open camera setting and go to detail camera setting
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		
		// Change night mode to On and verify
		if(PageDeviceSettings.verifyNightModeEnable(PageDeviceSettings.MODE_ON, false)) {
			terminal.clearTeratermLog();
			PageDeviceSettings.enableNightMode(PageDeviceSettings.MODE_ON, true);
			camLog = terminal.getTeratermLog();
			Assert.assertTrue(camLog.contains("req=set_night_vision&value=1"));
			Assert.assertTrue(PageDeviceSettings.verifyNightModeEnable(PageDeviceSettings.MODE_ON, true));
		}
		
		// Change night mode to OFF and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.enableNightMode(PageDeviceSettings.MODE_OFF, true);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_night_vision&value=2"));
		Assert.assertTrue(PageDeviceSettings.verifyNightModeEnable(PageDeviceSettings.MODE_OFF, true));
		
		// Change night mode to Auto and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.enableNightMode(PageDeviceSettings.NIGHT_MODE_AUTO, true);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_night_vision&value=0"));
		Assert.assertTrue(PageDeviceSettings.verifyNightModeEnable(PageDeviceSettings.NIGHT_MODE_AUTO, true));
	}
	
	@AfterMethod
	public void cleanUp() throws SerialPortException {
		try {
			terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
