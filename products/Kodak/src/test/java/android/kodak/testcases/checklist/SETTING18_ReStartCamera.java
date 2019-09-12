package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.MqttObject;
import com.cinatic.object.Terminal;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING18_ReStartCamera extends TestBaseAndroid {
	private Device device;
	Terminal terminal;
	
	@BeforeMethod
	public void Precondition() throws Exception{
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		MqttObject o = api.registerApp();
		terminal = new Terminal(c_comport);
	}
	
	@Test(priority = 999, description = "Verify user can restart camera on Setting page")
	public void verifyUserCanRestartCamera() throws SerialPortException{
		PageGetStart.checkAndSignin(c_username, c_password);		
		// Go to setting and restart camera
		PageDashboard.openDeviceSetting(device.getName());
		terminal.clearTeratermLog();
		PageCameraSetting.restartDevice();;
		// Get camera log and verify restart camera command
		String camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("System reboot after 3 seconds"), "Reboot sytem is ecxuted on camera");
		
	}
	
	@AfterMethod
	public void closeComPort() {
		try {
			terminal.closePort();
		} catch (Exception e) {	}
	}
}
