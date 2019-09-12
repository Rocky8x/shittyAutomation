package ios.kodak.testcases.checklist;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageTimePicker;
import mobile.kodak.base.TestBaseIOS;

public class SETTING19_AddDetectionSchedule extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 35, description = "Create new detection schedule")
	public void addNewDetectionSchedule() {
		String startTimeSetting = "01:15";
		String stopTimeSetting = "03:25";
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Enable schedule motion detection
		PageDashboard.openDeviceSetting(device.getName());
		
		PageDeviceSettings.gotoCameraSettingDetail();
		PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_SCHEDULE);
		PageDeviceSettings.openDetectionScheduleSetting();
		
		// Delete all schedule if any
		if (PageDeviceSettings.getEditButton().getWebElement() != null) {
			PageDeviceSettings.clickEditButton();
			PageDeviceSettings.clickDeleteAllButton();
			PageDeviceSettings.clickProceedButton();
		}
		
		PageDeviceSettings.clickAddNewDetectionSchedule();
		
		PageTimePicker.configScheduleTime(startTimeSetting, stopTimeSetting);
		PageDeviceSettings.clickDoneButton();
		PageDeviceSettings.clickSaveButton();
		TimeHelper.sleep(5000);
		
		PageDeviceSettings.openDetectionScheduleSetting();
		PageDeviceSettings.clickEditButton();
		PageDeviceSettings.verifyDetectionScheduleExisted(startTimeSetting + " - " + stopTimeSetting); 
	}
}
