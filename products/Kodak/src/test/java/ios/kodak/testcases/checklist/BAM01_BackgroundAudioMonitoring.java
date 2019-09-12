package ios.kodak.testcases.checklist;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.TimeHelper;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageSettings;
import mobile.kodak.base.TestBaseIOS;

public class BAM01_BackgroundAudioMonitoring extends TestBaseIOS{

	// Bug 7150 - cannot switch to local mode
	
	@Test( description = "Turn on BAM from setting page then check inside BAM setting page and vice-versa, the option must be on in both case", priority = 30)
	public void verifyConsistentBetweenOutsideAndInsideSwitch() {
		// Check and signin
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.gotoHomeMenuSettingPage();
		
		// Enable BAM in setting page
		// Go to BAM setting page and check inside switch must also enabled
		PageSettings.enableBAMSwitch(true);
		PageSettings.openBAMSettingDetail();
		boolean rs = PageSettings.BackGroundAudioMonitoring.verifyInsideBAMSwithEnable(true);
		Assert.assertTrue(rs, "BAM inside BAM setting page must be enabled when enabled BAM in setting page");
		
		// go to BAM setting page, disable inside switch,
		// go back to setting page and check outside switch must also be disabled
		PageSettings.BackGroundAudioMonitoring.enableInsideBAMSwitch(false);
		PageSettings.clickBackButton();
		TimeHelper.sleep(1000);
		rs = PageSettings.verifyBAMSwitchEnable(false);
		Assert.assertTrue(rs, "BAM in Setting page must be disabled when the option is disabled inside BAM setting page");

		// go to BAM setting page, enable inside switch,
		// go back to setting page and check outside switch must also be enabled
		PageSettings.openBAMSettingDetail();
		PageSettings.BackGroundAudioMonitoring.enableInsideBAMSwitch(true);
		PageSettings.clickBackButton();
		TimeHelper.sleep(1000);
		rs = PageSettings.verifyBAMSwitchEnable(true);
		Assert.assertTrue(rs, "BAM in Setting page must be enabled when the option is enabled inside BAM setting page");
		
		// Enable BAM in setting page
		// Go to BAM setting page and check inside switch must also enabled
		PageSettings.enableBAMSwitch(false);
		PageSettings.openBAMSettingDetail();
		rs = PageSettings.BackGroundAudioMonitoring.verifyInsideBAMSwithEnable(false);
		Assert.assertTrue(rs, "BAM inside BAM setting page must be disabled when disabled BAM in setting page");
	}
	
	@Test(priority = 30, description = "Check time setting")
	public void bamTimeSetting() {
		// Check and sign in
		ArrayList<String> timeOptions = new ArrayList<>();
		timeOptions.add("8 hours");
		timeOptions.add("4 hours");
		timeOptions.add("2 hours");
		timeOptions.add("1 hours");
		
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.gotoHomeMenuSettingPage();
		PageSettings.enableBAMSwitch(true);
		PageSettings.openBAMSettingDetail();
		
		String currentTime = PageSettings.BackGroundAudioMonitoring.getBAMTime();
		int index = timeOptions.indexOf(currentTime);
		// Increase BAM setting time
			for (int i = index; i >= 0; i --) {
				if (i == 0) {
					// Check boundary value
					PageSettings.BackGroundAudioMonitoring.increaseTime();
					Assert.assertEquals(PageSettings.BackGroundAudioMonitoring.getBAMTime(), timeOptions.get(i));
				}else {
					PageSettings.BackGroundAudioMonitoring.increaseTime();
					Assert.assertEquals(PageSettings.BackGroundAudioMonitoring.getBAMTime(), timeOptions.get(i-1));
				}
		}
		
		// Decrease BAM setting time
		currentTime = PageSettings.BackGroundAudioMonitoring.getBAMTime();
		index = timeOptions.indexOf(currentTime);
			for(int i = index; i < timeOptions.size(); i ++) {
				if (i == timeOptions.size() - 1) {
					// Check boundary value
					PageSettings.BackGroundAudioMonitoring.decreaseTime();
					Assert.assertEquals(PageSettings.BackGroundAudioMonitoring.getBAMTime(), timeOptions.get(i));
				}else {
					PageSettings.BackGroundAudioMonitoring.decreaseTime();
					Assert.assertEquals(PageSettings.BackGroundAudioMonitoring.getBAMTime(), timeOptions.get(i + 1));
				}
		}
	}
}
