package ios.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.TimeHelper;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageNotification;
import ios.kodak.object.PageTimePicker;
import mobile.kodak.base.TestBaseIOS;

public class DND01_DoNotDisturb extends TestBaseIOS{

	@Test(priority = 33, description = "Verify user can turn on - off do not disturb") 
	public void verifyDoNotDisturbUI() {
		// Login app
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Get list camera in dashboard
		List<String> listCam = PageDashboard.getListDeviceNameInDashboard();
		
		// Enable do not disturb
		PageDashboard.gotoDoNotDisturbPage();
		PageNotification.disableDoNotDisturb();
		TimeHelper.sleep(2000);
		
		// Get list camera in notification page and verify
		List<String> lstDevice = PageNotification.getListDeviceName();
		boolean rs = lstDevice.containsAll(listCam) && lstDevice.size() == listCam.size();
		Assert.assertTrue(rs, "List device should consistent in dashboard and notification page.");
		
		// Enable Do not disturb and disable schedule
		PageNotification.enableDoNotDisturb();
		PageNotification.enableDNDSchedule(false);
		TimeHelper.sleep(2000);
		lstDevice = PageNotification.getListDeviceName();
		Assert.assertEquals(lstDevice.size(), 0, "List device should disappear");
		
		// Enable DND schedule
		PageNotification.enableDNDSchedule(true);
		TimeHelper.sleep(2000);
		lstDevice = PageNotification.getListDeviceName();
		Assert.assertTrue(PageNotification.verifyDNDScheduleDisplay());
		rs = lstDevice.containsAll(listCam) && lstDevice.size() == listCam.size();
		Assert.assertTrue(rs, "List device should consistent in dashboard and notification page.");
	}
	
	@Test(priority = 33, description = "Verify Do Not Disturb Schedule setup")
	public void verifyDNDScheduleSetup() {
		String timeFormat = "hh:mm";
		
		// Login app
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Enable do not disturb
		PageDashboard.gotoDoNotDisturbPage();
		PageNotification.enableDoNotDisturb();
		PageNotification.enableDNDSchedule(true);

		if(PageNotification.getScheduleStartTime().contains("M")) {
			timeFormat = "h:mm";
		}
		// Generate random start time and stop time
		String startTimeSetup = TimeHelper.getRandomTime(timeFormat);
		String stopTimeSetup = TimeHelper.getRandomTime(timeFormat);
		
		// Config start time and stop time 
		PageNotification.openTimePicker();
		PageTimePicker.configScheduleTime(startTimeSetup, stopTimeSetup);
		PageNotification.clickOkButton();
		
		// Verify start time and stop time after setup
		String currentStartTime = PageNotification.getScheduleStartTime();
		Assert.assertTrue(startTimeSetup.contains(currentStartTime.split(" ")[0]));
		
		String currentStopTime = PageNotification.getScheduleStopTime();
		Assert.assertTrue(stopTimeSetup.contains(currentStopTime.split(" ")[0]));
		
	}
}
