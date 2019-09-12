package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class OTA03_ForceOTA extends TestBaseAndroid {
	Device device;
	ApiHelper api;
	Terminal terminal;
	String deviceId1;
	String devicessid1;
	
	@BeforeMethod
	@Parameters({"comport1"})
	public void Precondition(String comport1) throws SerialPortException{
		terminal = new Terminal(comport1);
		deviceId1 = deviceId1==null ? terminal.getCameraUdid():deviceId1;
		devicessid1 = devicessid1==null ? Device.convertSsidByUuid(deviceId1):devicessid1;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(deviceId1);
	}
	
	@Test(priority = 50, description = "FW OTA manually by clicking via camera setting/detail/force upgrade")
	public void verifyOTAManualForceUpgrade() throws SerialPortException {
		// Downgrade camera firmware version
		terminal.sendCommand("sdcard bu_upgrade", "---------- Success ---------", 10);
		
		PageGetStart.goToSigninPage();
		PageLogin.loginApp(c_username, c_password);		
		
		//Wait for device online
		PageDashboard.waitForDeviceOnline(device.getName(), 30);
		
		// Go to setting and update firmware		
		PageDashboard.openDeviceSetting(device.getName());
		Assert.assertTrue(PageCameraSetting.verifyNewFirmwareAlreadyToUpdate(), "New firmware version should already to update.");
		
		// Upgrade new firmware and verify
		boolean rs = PageCameraSetting.manualUpdateFirmware();
		Assert.assertTrue(rs, "Firmware setup");
	}
	
	@AfterMethod
	public void cleanup() {
		try {
			terminal.closePort();
		} catch (Exception e) {	}
	}
}
