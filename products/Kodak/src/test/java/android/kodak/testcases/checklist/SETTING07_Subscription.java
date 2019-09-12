	package android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageManageCloudStorage;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING07_Subscription extends TestBaseAndroid {
		
	@Test(priority = 11, description = "Verify free cloud storage plan")
	public void verifyFreeCloudStorage() {
		
		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		Device device = api.getDeviceByDeviceId(c_deviceid);
		// Get current storage plan in setting page
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.openDeviceSetting(device.getName());
		String settingStorage = PageCameraSetting.getCurrentStoragePlan();
		
		PageBase.exitPage();
		PageDashboard.gotoHomeMenuPage();
		// Get Storage plan in Manage Storage Plan page
		PageHomeMenu.gotoDevices_ManageCloudStoragePage();
		String storagePlan = PageManageCloudStorage.getYourCurrentStoragePlan();
		Assert.assertEquals(storagePlan, settingStorage, String.format("Current storage plan is: %s, but found: %s.", settingStorage, storagePlan));
		// Verify device in current storage plan
		List<String> devices = PageManageCloudStorage.getListDevicesName();
		Assert.assertTrue(devices.contains(device.getName()), "your device should stay in list device of current storage plan.");
		
	}
	
}