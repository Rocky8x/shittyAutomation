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

public class DETECT02_SoundDetection extends TestBaseIOS{

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
	
	@Test(priority = 28, description = "Verify that user can change sound detection setting successfully")
	public void verifyUserCanChangeSoundDetection() throws SerialPortException {
		
		String camLog;
		terminal = new Terminal(c_comport);
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Open camera setting and go to detail setting
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_OFF);
		
		// Enable sound detection if it disable
		if (PageDeviceSettings.verifySoundDetection(PageDeviceSettings.MODE_OFF)) {
			terminal.clearTeratermLog();
			PageDeviceSettings.setSoundDetection(PageDeviceSettings.MODE_ON);
			camLog = terminal.getTeratermLog();
			Assert.assertTrue(camLog.contains("req=set_sound_detection&value=1"));
			Assert.assertTrue(PageDeviceSettings.verifySoundDetection(PageDeviceSettings.MODE_ON));
		}
		// Set sound sensitivity Low and verify
		terminal.clearTeratermLog();
		PageDeviceSettings.changeSoundSensitivityLevel(TestConstant.soundLevel.Low);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_sound_detection&value=1&sensitivity=1"));
		terminal.clearTeratermLog();
		
		// Set sound sensitivity High and verify
		PageDeviceSettings.changeSoundSensitivityLevel(TestConstant.soundLevel.High);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_sound_detection&value=1&sensitivity=5"));
		terminal.clearTeratermLog();
		
		// Set sound sensitivity Medium and verify
		PageDeviceSettings.changeSoundSensitivityLevel(TestConstant.soundLevel.Medium);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_sound_detection&value=1&sensitivity=3"));
		terminal.clearTeratermLog();
		
		// Disable sound sensitivity and verify
		PageDeviceSettings.setSoundDetection(PageDeviceSettings.MODE_OFF);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=set_sound_detection&value=0"));
		Assert.assertTrue(PageDeviceSettings.verifySoundDetection(PageDeviceSettings.MODE_OFF));
		
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
