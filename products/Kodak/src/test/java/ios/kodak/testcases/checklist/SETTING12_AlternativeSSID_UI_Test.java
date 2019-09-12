package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class SETTING12_AlternativeSSID_UI_Test extends TestBaseIOS{

	Terminal terminal;
	ApiHelper api;
	Device device;
	
	@BeforeClass
	public void setup() throws SerialPortException {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		
		terminal = new Terminal(c_comport);
	}
	
	@Test(priority = 44, description = "Verify UI Alternative ssid")
	public void alternativeUITest() throws SerialPortException {
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();
		
		// Verify help message
		PageDeviceSettings.clickAlternativeHelpIcon();
		String msg = PageDeviceSettings.getAlternativeHelpMessage();
		Assert.assertEquals(msg, PageDeviceSettings.ALTERNATIVE_HELP_MSG);
		PageDeviceSettings.clickOkButton();
		
		// Verify alternative with fake ssid
		String fakeSSID = "Fake non-existed SSID";
		String fakePasswd = StringHelper.randomString("F@k3", 5);
		
		PageDeviceSettings.ChangeAltSSID(fakeSSID, fakePasswd);
		
		TimeHelper.sleep(3000);
		boolean rs = terminal.sendCommand("config", "ROUTER_SSID_BACKUP : a\\[\\d\\]\\'" + fakeSSID + "\\'", 10);
		Assert.assertTrue(rs, "Alternative SSID change was not applied to camera");
	}
	
	@AfterClass
	public void cleanup() {
		try {
			terminal.closePort();
		} catch (Exception e) {
		}
	}
}
