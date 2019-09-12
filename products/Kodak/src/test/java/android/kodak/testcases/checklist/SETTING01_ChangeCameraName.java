package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING01_ChangeCameraName extends TestBaseAndroid {
	private String username;
	private String password;
	private String cameraname;
	private Device device;
	ApiHelper api;

	@BeforeMethod
	public void Precondition() {
		this.cameraname = StringHelper.randomNumber("cam", 5);
		this.username = c_username;
		this.password = c_password;

		if (c_server.equals("production")) {
			api = new ApiHelper();
			api.userLogin(c_username, c_password);
			api.getDevices();
			device = api.getDeviceByDeviceId(c_deviceid);
		}
	}

	@Test(priority = 11, description = "TC001: Camera's details info must be shown")
	public void TC001_CameraDetailsInfo() {
		PageGetStart.checkAndSignin(username, password);
		PageDashboard.openDeviceSetting(device.getName());
		
		Assert.assertTrue(PageCameraSetting.getCameraNameValue().isDisplayed(), "Camera name is not shown");
		Assert.assertTrue(PageCameraSetting.getModelNameValue().isDisplayed(), "Model name is not shown");
		Assert.assertTrue(PageCameraSetting.getMacAddressValue().isDisplayed(), "MAC address is not shown");
		Assert.assertTrue(PageCameraSetting.getFirmwareVersion().isDisplayed(), "Firmware version is not shown");
		Assert.assertTrue(PageCameraSetting.getCurrentPlanValue().isDisplayed(), "Current plan is not shown");
		Assert.assertTrue(PageCameraSetting.getTimeZoneValue().isDisplayed(), "Current time zone is not shown");
		Assert.assertTrue(PageCameraSetting.getWifiSignalStrengthValue().isDisplayed(), "Wi-fi signal strength is not shown");
		Assert.assertTrue(PageCameraSetting.getBatteryGeneralInfo().isDisplayed(), "Battery info is not shown");
		Assert.assertTrue(PageCameraSetting.getSendDeviceLog().isDisplayed(), "Send camera log is not shown");
	}
	
	@Test(priority = 11, description = "TC002: Verify that user can change camera name successfully")
	public void TC002_ChangeCameraName() {
		PageGetStart.checkAndSignin(username, password);		
		PageDashboard.openDeviceSetting(device.getName());
		PageCameraSetting.changeCameraName(cameraname);
		
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		Assert.assertEquals(device.getName(), cameraname);		
	}
	
}
