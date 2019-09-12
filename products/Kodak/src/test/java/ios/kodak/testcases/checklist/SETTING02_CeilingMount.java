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

public class SETTING02_CeilingMount extends TestBaseIOS{

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
	
	@Test(priority = 31, description = "Verify that user can change ceiling mount setting successfully")
	public void verifyUserCanChangeCeilingMount() throws SerialPortException {
		String camLog;
		terminal = new Terminal(c_comport);
		
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Open camera setting and go to detail camera setting
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		
		// Enable ceiling mount and verify
		if(PageDeviceSettings.verifyCeilingMoungEnable(false)) {
			terminal.clearTeratermLog();
			PageDeviceSettings.enableCeilingMount(true);
			camLog = terminal.getTeratermLog();
			Assert.assertTrue(camLog.contains("req=set_flipup&value=1"));
			Assert.assertTrue(PageDeviceSettings.verifyCeilingMoungEnable(true));
		}
		
		// Disable ceiling mount and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.enableCeilingMount(false);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_flipup&value=0"));
		Assert.assertTrue(PageDeviceSettings.verifyCeilingMoungEnable(false));
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
