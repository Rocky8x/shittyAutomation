package android.kodak.testcases.checklist;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.driver.DriverManager;

import android.kodak.object.PageBAMSetting;
import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageHomeSetting;
import mobile.kodak.base.TestBaseAndroid;

public class BAM01_BackgroundAudioMonitoring extends TestBaseAndroid {

	@BeforeMethod
	public void removeThenReinstallApp() throws Exception {
		DriverManager.getDriver().quit();
		adbReinstallApp();
		DriverManager.removeWebDriver();
		DriverManager.createWebDriver(driverSetting);
		PageGetStart.checkAndSignin(c_username, c_password);

		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
	}

	@Test(description = "Turn on Background Audio Monitoring but not turn off battery optimization for the app", priority = 90)
	public void enableBamButKeepBattOptimizeOn() {

		// Turn on the option
		PageHomeSetting.getBgAudioMonitoringSwitch().setValue(true);

		// verify the dialog title and message
		String dialogTitle = PageHomeSetting.AudioMonitoringDialog.getTitle().getText();
		String dialogMessage = PageHomeSetting.AudioMonitoringDialog.getMessage().getText();
		assertEquals(dialogTitle, PageHomeSetting.AudioMonitoringDialog.TITLE);
		assertEquals(dialogMessage, PageHomeSetting.AudioMonitoringDialog.MESSAGE);

		// click 'Not now' the option must be disable
		PageHomeSetting.AudioMonitoringDialog.getNotNowBtn().click();
		assertTrue(PageHomeSetting.getBgAudioMonitoringSwitch().getAttribute("checked").equals("false"),
				"Background Audio Monitoring must be disalbe");

		// Turn it on again, but tap OK at this time then click Deny.
		PageHomeSetting.getBgAudioMonitoringSwitch().setValue(true);
		PageHomeSetting.AudioMonitoringDialog.getOkBtn().click();
		PageHomeSetting.getDenyBtn().click();
		String isBamChecked = PageHomeSetting.getBgAudioMonitoringSwitch().getAttribute("checked");
		PageBase.exitPage();

		assertFalse(isBamChecked.equals("false"),
				"Not disalbe battery optimize, background Audio Monitoring must be disalbe");
	}

	@Test(description = "Turn on Background Audio Monitoring and turn off battery optimization for the app", priority = 91)
	public void enableBamAndTurnOffBattOptimize() {

		// Turn on the option
		PageHomeSetting.getBgAudioMonitoringSwitch().setValue(true);

		// verify the dialog title and message
		String dialogTitle = PageHomeSetting.AudioMonitoringDialog.getTitle().getText();
		String dialogMessage = PageHomeSetting.AudioMonitoringDialog.getMessage().getText();
		assertEquals(dialogTitle, PageHomeSetting.AudioMonitoringDialog.TITLE);
		assertEquals(dialogMessage, PageHomeSetting.AudioMonitoringDialog.MESSAGE);

		// Click OK then Allow to disable battery optimization
		PageHomeSetting.AudioMonitoringDialog.getOkBtn().click();
		PageHomeSetting.getAllowBtn().click();
		String isBamChecked = PageHomeSetting.getBgAudioMonitoringSwitch().getAttribute("checked");
		PageBase.exitPage();

		assertTrue(isBamChecked.equals("true"), "Background Audio Monitoring must be enable");
	}

	@Test( description = "Turn on BAM from setting page then check inside BAM setting page and vice-versa, the option must be on in both case", priority = 92)
	public void consistentBetweenOutsideAndInsideSwitch() {
		// Turn on the option in setting page
		PageHomeSetting.getBgAudioMonitoringSwitch().setValue(true);
		// Click OK then Allow to disable battery optimization
		PageHomeSetting.AudioMonitoringDialog.getOkBtn().click();
		PageHomeSetting.getAllowBtn().click();

		// go to BAM setting page, disable inside switch,
		// go back to setting page and check outside switch must also be
		// disabled
		PageHomeSetting.getBackgroundAudioMonitoringArrowBtn().click();
		PageBAMSetting.disableBAM();
		PageBAMSetting.exitPage();
		String isBamChecked1 = PageHomeSetting.getBgAudioMonitoringSwitch().getAttribute("checked");

		// go back to BAM setting page, enable inside switch,
		// go back to setting page and check outside switch must also be enable
		PageHomeSetting.getBackgroundAudioMonitoringArrowBtn().click();
		PageBAMSetting.enableBAM();
		PageBAMSetting.exitPage();
		String isBamChecked2 = PageHomeSetting.getBgAudioMonitoringSwitch().getAttribute("checked");

		PageBAMSetting.exitPage();

		assertTrue(isBamChecked1.equals("false"),
				"BAM in Setting page must be disabled when the option is disabled inside BAM setting page");

		assertTrue(isBamChecked2.equals("true"),
				"BAM in Setting page must be enabled when the option is enabled inside BAM setting page");
	}

	@Test( description = "Check time setting", priority = 93)
	public void bamTimeSetting() {
		ArrayList<String> timeOptions = new ArrayList<>();
		timeOptions.add("8 hours");
		timeOptions.add("4 hours");
		timeOptions.add("2 hours");
		timeOptions.add("1 hour");

		PageHomeSetting.getBackgroundAudioMonitoringArrowBtn().click();
		PageBAMSetting.enableBAM();
		PageHomeSetting.AudioMonitoringDialog.getOkBtn().click();
		PageHomeSetting.getAllowBtn().click();

		String wrongOptionDecreaseCheck = "";
		for (String option : timeOptions) {
			String currentTime = PageBAMSetting.getCurrentTimeSetting();
			if (!currentTime.equals(option)) {
				wrongOptionDecreaseCheck = currentTime;
				break;
			}
			PageBAMSetting.decreaseTime();
		}

		PageBAMSetting.decreaseTime();
		String time1h = PageBAMSetting.getCurrentTimeSetting();

		String wrongOptionIncreaseCheck = "";
		for (int i = timeOptions.size()-1; i > 0; i--) {
			String currentTime = PageBAMSetting.getCurrentTimeSetting();
			if (!currentTime.equals(timeOptions.get(i))) {
				wrongOptionIncreaseCheck = currentTime;
				break;
			}
			PageBAMSetting.increaseTime();
		}

		PageBAMSetting.increaseTime();
		String time8h = PageBAMSetting.getCurrentTimeSetting();
		PageBAMSetting.exitPage();
		PageBase.exitPage();

		assertTrue(wrongOptionDecreaseCheck.isEmpty(),
				String.format("Time decrease test failed. Option \'%s\' is wrong, must be one of the following options only: %s",
						wrongOptionDecreaseCheck, timeOptions.toString()));
		assertTrue(wrongOptionIncreaseCheck.isEmpty(),
				String.format("Time increase test failed. Option \'%s\' is wrong, must be one of the following options only: %s",
						wrongOptionIncreaseCheck, timeOptions.toString()));
		assertEquals(time1h, "1 hour", "Min time must be 1h");
		assertEquals(time8h, "8 hours", "Max time must be 8h");
	}

	@AfterMethod
	public void enableDebugInfoAgain() {
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.enableDebug();
		PageHomeMenu.gotoHomeSetingPage();
		PageHomeSetting.enableShowDebugInfo();
	}
}
