package ios.kodak.testcases.checklist;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
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

public class SETTING21_DeleteDetectionSchedule extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 35, description = "Verify user can delete detection schedule")
	public void deleteSpecificDetectionSchedule() {
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Enable schedule motion detection
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoCameraSettingDetail();
		PageDeviceSettings.setMotionDetection(PageDeviceSettings.MODE_SCHEDULE);
		
		
		// Create Detection schedule
		String startTimeSetting;
		String stopTimeSetting;
		for(int i = 1 ; i <= 6; i++) {
			startTimeSetting = "0" + i + ":00";
			stopTimeSetting = "0" + (i+1) + ":25";
			i+=1;
			PageDeviceSettings.openDetectionScheduleSetting();
			PageDeviceSettings.clickAddNewDetectionSchedule();
			PageTimePicker.configScheduleTime(startTimeSetting, stopTimeSetting);
			PageDeviceSettings.clickDoneButton();
			PageDeviceSettings.clickSaveButton();
			TimeHelper.sleep(5000);
		}
					
		String scheduleName = "";
		PageDeviceSettings.openDetectionScheduleSetting();
		PageDeviceSettings.clickEditButton();
		List<WebElement> lstSchedule = PageDeviceSettings.getListScheduleEle();
		if (lstSchedule.size() < 1) {
			Assert.fail("There is no detection schedule");
		}
		for(int i = 0; i < lstSchedule.size(); i++) {
			// Delete detection schedule
			scheduleName = lstSchedule.get(0).getText();
			lstSchedule.get(0).click();
			PageDeviceSettings.clickDeleteButton();
			
			// After delete final detection schedule, app navigate to setting page --> verify edit button disappear
			if(i == lstSchedule.size()-1) {
				PageDeviceSettings.clickProceedButton();
				TimeHelper.sleep(3000);
				Assert.assertTrue(PageDeviceSettings.getEditButton().getWebElement() == null, "Edit button should disappear.");
				break;
			}
			// Verify detection schedule disappear
			TimeHelper.sleep(3000);
			Assert.assertFalse(PageDeviceSettings.verifyDetectionScheduleExisted(scheduleName), "Detection schedule should disappear.");
		}
	}
}
