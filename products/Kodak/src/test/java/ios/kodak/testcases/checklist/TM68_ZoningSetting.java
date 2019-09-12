package ios.kodak.testcases.checklist;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import mobile.kodak.base.TestBaseIOS;

public class TM68_ZoningSetting extends TestBaseIOS{

	ApiHelper api;
	Device device;
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 15, description = "Verify that user can add new zone", enabled = false)
	public void addAndEditZone() {
		
		// Check and sign in
//		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		
//		PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_ON);
		
		// Delete all zone
		PageDeviceSettings.clickDefineZone();
		PageDeviceSettings.clickEditZone();
//		PageDeviceSettings.removeAllZones();
//		Assert.assertEquals(PageDeviceSettings.getListZoneDefine(), 0);
//		
//		// Add new first zone
//		PageDeviceSettings.clickAddNewZone();
//		PageDeviceSettings.clickSaveZone();
//		PageDeviceSettings.clickApplyZone();
//		PageDeviceSettings.clickDefineZone();
		
		// Edit Zone
		PageDeviceSettings.reSizeZone();
//		PageDeviceSettings.moveZone(PageDeviceSettings.LEFT);
		PageDeviceSettings.moveZone(PageDeviceSettings.TOP);
		PageDeviceSettings.clickSaveZone();
		
		// Add a second zone
		PageDeviceSettings.clickAddNewZone();
		PageDeviceSettings.reSizeZone();
		PageDeviceSettings.moveZone(PageDeviceSettings.RIGHT);
		PageDeviceSettings.moveZone(PageDeviceSettings.BOTTOM);
		PageDeviceSettings.clickSaveZone();
		
		TimeHelper.sleep(20000);
	}
	
	@Test(priority = 14, description = "Verify that user can delete zone", enabled = false)
	public void deleteZone() {
		
	}
}
