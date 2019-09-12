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

public class SETTING15_RemoveCamera extends TestBaseIOS{
	
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
	
	@Test(priority = 100, description = "Verify that user can remove camera")
	public void deleteCamera() throws SerialPortException {
		String camLog;
		terminal = new Terminal(c_comport);
		
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Open camera setting
		PageDashboard.openDeviceSetting(device.getName());
		
		// Delete camera and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.DeleteDevice();
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("reset_factory"), "Reset factory command should executed.");
		
		// Verify camera deleted in dashboard
		Assert.assertTrue(!PageDashboard.getListDeviceNameInDashboard().contains(device.getName()), "Camera should remove from dashboard.");
		
	}

	@AfterMethod
	public void cleanUp() throws SerialPortException {
		try {
			TimeHelper.sleep(90000);
			terminal.sendCommand("shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN");
			terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
