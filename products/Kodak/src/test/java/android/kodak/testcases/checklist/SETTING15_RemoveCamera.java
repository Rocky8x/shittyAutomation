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

public class SETTING15_RemoveCamera extends TestBaseAndroid {
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
	
	@Test(priority = 999, description = "Verify user can remove camera")
	public void verifyUserCanRemoveCamera() throws SerialPortException{
		PageGetStart.checkAndSignin(c_username, c_password);		
		// Go to setting and remove camera
		PageDashboard.openDeviceSetting(device.getName());
		terminal.clearTeratermLog();
		PageCameraSetting.removeCamera();
		// Get camera log and verify reset factory command
		String camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("reset_factory"), "Reset factory command should executed.");
		// Verify camera deleted on dashboard 
		if(!PageDashboard.verifyEmptyDevice()) {
			Assert.assertFalse(PageDashboard.verifyDeviceExisted(device.getName()), "Camera should remove from device.");
		}
	}
	
	@AfterMethod
	public void closeComPort() {
		try {
			terminal.closePort();
		} catch (Exception e) {	}
	}
}
