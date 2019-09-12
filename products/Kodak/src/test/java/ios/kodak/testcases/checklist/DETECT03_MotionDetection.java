package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class DETECT03_MotionDetection extends TestBaseIOS{

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
	
	@Test(priority = 29, description = "Verify that user can change motion detection setting successfully")
	public void verifyUserCanChangeMotionDetection() throws SerialPortException {
		String camLog;
		terminal = new Terminal(c_comport);
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Open camera setting and go to detail setting
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		if(PageDeviceSettings.verifyMotionDetectionEnable(PageDeviceSettings.MODE_OFF)) {	
			terminal.clearTeratermLog();
			PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_ON);
			camLog = terminal.getTeratermLog();
			Assert.assertTrue(camLog.contains("req=set_motion_source&value=1"));
			Assert.assertTrue(PageDeviceSettings.verifyMotionDetectionEnable(PageDeviceSettings.MODE_ON));
		}
		
		// Set motion sensitivity Low and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.changeMotionSensitivityLevel(TestConstant.soundLevel.Low);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_motion_sensitivity&value=1"));
		terminal.clearTeratermLog();
		
		// Set motion sensitivity High and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.changeMotionSensitivityLevel(TestConstant.soundLevel.High);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_motion_sensitivity&value=5"));
		terminal.clearTeratermLog();
				
		// Set motion sensitivity Medium and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.changeMotionSensitivityLevel(TestConstant.soundLevel.Medium);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_motion_sensitivity&value=3"));
		terminal.clearTeratermLog();
		
		// Disable motion detection and verify
		PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_OFF);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_motion_source&value=0"));
		Assert.assertTrue(PageDeviceSettings.verifyMotionDetectionEnable(PageDeviceSettings.MODE_OFF));
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
