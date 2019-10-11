package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class OTA02_OtaDuringSetupCamera extends TestBaseAndroid {
	Device device;
	ApiHelper api;
	Terminal terminal;
	String deviceId1;
	String devicessid1;
	
	@BeforeMethod
	public void Precondition() throws SerialPortException{

		terminal = new Terminal(testParams.get("comport1"));
		deviceId1 = deviceId1==null ? terminal.getCameraUdid():deviceId1;
		devicessid1 = devicessid1==null ? Device.convertSsidByUuid(deviceId1):devicessid1;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(deviceId1);
	}
		
	@Test(priority = 51, description = "FW OTA during App setup")
	public void verifyOTADuringSetup() throws SerialPortException {
		// Downgrade camera firmware version
		terminal.sendCommand("sdcard bu_upgrade", "---------- Success ---------", 10);
		
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MEDIA);		
		PageDashboard.getAddDeviceBigBtn().click();
		
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
		// Pair camera with app
		PageDashboard.getHomeSeriesImage().click();
		PageDashboard.proceedAnyway5GWifiIfAsk();
		PageDashboard.getDeviceModelLabel("670").click();
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.getContinueButton().click();
		PageDashboard.getContinueButton().click();
		
		// Click on device name
		if (PageDashboard.getSSIDLabel(devicessid1).getWebElement() != null) {
			PageDashboard.getSSIDLabel(devicessid1).click();
		} else {
			PageDashboard.getSSIDRefreshImage().click();
			PageDashboard.getSSIDLabel(devicessid1).click();
		}
		
		// Config wifi for camera
		PageDashboard.configWifiForCamera(c_wifiname, "WPA2", c_wifipassword);
		
		// Verify new firmware available
		boolean rs = PageDashboard.verifyNewFirmwareAvailable();
		Assert.assertTrue(rs, "New firmware should available.");
		
		if(PageDashboard.getMobileDataContinueButton().getWebElement()!=null) {
			PageDashboard.getMobileDataContinueButton().click();
		}
		
		PageDashboard.getCustomButton().click();
		PageDashboard.inputCameraName(device.getName());
		PageDashboard.getContinueButton().click();
		PageDashboard.getDoneButton().click();
		
		// Verify camera in dashboard after settup
		Assert.assertTrue(PageDashboard.getListCameraNameInDashBoard().contains(device.getName()),
				String.format("Camera: %s should display in dashboard.", device.getName()));
		//Wait for device online
		PageDashboard.waitForDeviceOnline(device.getName(), 30);
		
		// Open setting page and verify latest firmware already.
		PageDashboard.openDeviceSetting(device.getName());
		rs = PageCameraSetting.verifyLatestFirmwareVersion();
		Assert.assertTrue(rs, "New firmware already to update.");
	}
	
	@AfterMethod
	public void cleanUp() throws SerialPortException {
		terminal.closePort();
	}
}
