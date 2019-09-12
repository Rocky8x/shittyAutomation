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

public class SETTING20_EditDetectionSchedule extends TestBaseIOS{

	ApiHelper api;
	Device device;
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 35, description = "Edit detection schedule")
	public void editDetectionSchedule() {
		String startTimeSetting = "01:15";
		String stopTimeSetting = "03:10";
		String startTimeEdit = "03:25";
		String stopTimeEdit = "04:42";
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Enable schedule motion detection
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_SCHEDULE);
		PageDeviceSettings.openDetectionScheduleSetting();
		
		if(PageDeviceSettings.getEditButton().getWebElement() != null) {
			PageDeviceSettings.clickEditButton();
			PageDeviceSettings.clickDeleteAllButton();
			PageDeviceSettings.clickProceedButton();
		}
		
		// Create new detection schedule
		PageDeviceSettings.clickAddNewDetectionSchedule();
		
		PageTimePicker.configScheduleTime(startTimeSetting, stopTimeSetting);
		PageDeviceSettings.clickDoneButton();
		PageDeviceSettings.clickSaveButton();
		TimeHelper.sleep(5000);
		
		// Verify schedule create successful
		PageDeviceSettings.openDetectionScheduleSetting();
		PageDeviceSettings.clickEditButton();
		PageDeviceSettings.verifyDetectionScheduleExisted(startTimeSetting + " - " + stopTimeSetting); 
		
		// Edit detection schedule
		PageDeviceSettings.clickBackButton();
		PageDeviceSettings.clickExistedSchedule();
		
		PageTimePicker.configScheduleTime(startTimeEdit, stopTimeEdit);
		PageDeviceSettings.clickDoneButton();
		PageDeviceSettings.clickSaveButton();
		TimeHelper.sleep(5000);
		
		// Verify schedule edited successful
		PageDeviceSettings.openDetectionScheduleSetting();
		PageDeviceSettings.clickEditButton();
		PageDeviceSettings.verifyDetectionScheduleExisted(startTimeEdit + " - " + stopTimeEdit); 
	}
}
