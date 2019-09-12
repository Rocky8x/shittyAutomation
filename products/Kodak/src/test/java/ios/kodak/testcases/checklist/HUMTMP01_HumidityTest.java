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

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class HUMTMP01_HumidityTest extends TestBaseIOS{
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
	
	@Test(priority = 20, description= "Verify humidity same on app and server")
	public void verifyHumidityOnApp() throws SerialPortException {
	 // Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to Stream view and verify temperature unit
		PageDashboard.selectDeviceToView(device.getName());
		
		// Get humidity in camera console
		terminal.clearTeratermLog();
		terminal.sendCommand("atecmd get_humi");
		String cameraTemp = terminal.getTeratermLog();

		// Verify Humidity sync between app and camera
		String humi = StringHelper.getStringByString(cameraTemp, "Humidity = ", " percent", false);
		Log.info("Humidity on terminal: " + humi);
		Assert.assertTrue(PageStreamView.verifyHumiditySyncWithAppAndCamera(humi), "Temperature should sync between app and camera.");
		
	}

	@AfterMethod
	public void cleanUp() throws SerialPortException {
		terminal.closePort();
	}

}
