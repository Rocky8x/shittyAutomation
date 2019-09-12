package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class HUMTMP01_VerifyHumidity extends TestBaseAndroid{
	private Device device;
	private Terminal terminal;
	
	@BeforeMethod
	public void preCondition() throws SerialPortException {
		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		terminal = new Terminal(c_comport);
	}
	
	@Test(priority = 99, description = "Verify humidity same on App and server")
	public void verifyHumiditySameOnAppAndCamera() throws SerialPortException {
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.getStreamMode(60);
		
		// Get humidity from camera
		terminal.clearTeratermLog();
		terminal.sendCommand("atecmd get_humi");
		String camLog = terminal.getTeratermLog();		
		int i = camLog.indexOf("Humidity = ") + 11;
		float humidityFromCamlog = Float.parseFloat(camLog.substring(i,i+4));	
		
		// Get humidity in app
		float humidityFromApp = Float.parseFloat(StringHelper.getNumberInString(PageStreamView.getHumidityInApp()).get(0));		
		float tolerance	= Math.abs(humidityFromApp - humidityFromCamlog);
		
		Assert.assertTrue(tolerance <= 2,
				String.format("Humidity tolerance between app and server is  > 2. App: %s, cam log: %s.",
						humidityFromApp,
						humidityFromCamlog));
	}

	@AfterMethod
	public void tearDown() throws SerialPortException {
		terminal.closePort();
	}
}
