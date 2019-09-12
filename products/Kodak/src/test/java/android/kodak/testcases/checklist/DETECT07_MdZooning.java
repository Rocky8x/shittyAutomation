package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseAndroid;

public class DETECT07_MdZooning extends TestBaseAndroid {
	
	Device device;

	@BeforeMethod
	public void Precondition() throws Exception{
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 13, description = "Zoning setting and apply to camera: add, edit, remove, update")
	public void verifyZoningSetting() {
		String zone1 = "Zone 1";
		String zone2 = "Zone 2";
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.openDeviceSetting(device.getName());
		PageCameraSetting.enableMotionDetection();
		PageCameraSetting.clickOnZoneDetectionMotion();
		PageCameraSetting.removeAllZone();
		Assert.assertTrue(PageCameraSetting.verifyRemoveAllZoneSuccessful(), "All zone should remove successful.");
		PageCameraSetting.addZone(zone1);
		Assert.assertTrue(PageCameraSetting.verifyZoneByName(zone1), String.format("Zone %s should create successful.", zone1));
		PageCameraSetting.editZone(zone1);
		PageCameraSetting.addZone(zone2);
		Assert.assertTrue(PageCameraSetting.verifyZoneByName(zone1, zone2), String.format("Zone %s, %s should create successful.", zone1, zone2));
		PageCameraSetting.clickUpdateZoneToCamera();
		PageCameraSetting.clickOnZoneDetectionMotion();
		PageCameraSetting.clickRefreshZoneButton();
		Assert.assertTrue(PageCameraSetting.verifyZoneByName(zone1, zone2), "Update zone to camera fail.");
	}
}
