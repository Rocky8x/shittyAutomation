package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;
import com.cinatic.object.LocalPC;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING12_AlternativeSSID_UI_test extends TestBaseAndroid {
	private String cameraName;
	Terminal terminal;

	@BeforeClass
	public void getCameraName() throws SerialPortException {
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		cameraName = api.getDeviceByDeviceId(c_deviceid).getName();
		terminal = new Terminal(c_comport);
	}

	@Parameters({ "wepwifiname", "wepwifipassword" })
	@Test(priority = 11)
	public void alternativeSsidUiNFunctionTest(String wepwifiname, String wepwifipassword) throws Exception {
		String altSSID = "";
		PageDashboard.getCameraSettingButton(cameraName).click();

		try {
			altSSID = PageCameraSetting.getAlternativeSsidText().getWebElement().getText();
			String msg = "Alternative SSID was already set as: " + altSSID;
			Log.debug(msg);
		} catch (Exception e) {
		}

		// add a fake SSID
		String fakeSSID = "Fake non-existed SSID";
		String fakePasswd = StringHelper.randomString("F@k3", 5);
		changeAndCheckAltSsid(fakeSSID, fakePasswd);

		// verify help message
		PageCameraSetting.getAlternativeSsidHelpBtn().click();
		PageCameraSetting.verifyAltSsidHelpMsg();

		/* FUNCTIONAL verifying in firmware side */

		// add a real SSID
		changeAndCheckAltSsid(wepwifiname, wepwifipassword);
		DriverManager.getDriver().quit();
		DriverManager.removeWebDriver();
		LocalPC.disableHotspot();
		int waitTime = 90 * 1000; // seconds
		TimeHelper.sleep(waitTime);
		LocalPC.enableHotspot();

		adbRefreshWifi();
		TimeHelper.sleep(5000);

		DriverManager.createWebDriver(driverSetting);

		boolean checkResult = terminal.sendCommand("config", "ROUTER_SSID : \\[c\\d+\\] \\'" + wepwifiname + "\\'", 5);
		Assert.assertTrue(checkResult, "Camera did not change to Alternative SSID");

		PageDashboard.openDeviceSetting(cameraName);
		Assert.assertEquals(PageCameraSetting.getCurrentSsidTextbox().getText(), wepwifiname);

		// reboot camera. make sure it will connect back to primary ssid
		terminal.sendCommand("reboot");
		TimeHelper.sleep(60000);
		PageBase.exitPage();
		PageDashboard.openDeviceSetting(cameraName);
		checkResult = terminal.sendCommand("config", "ROUTER_SSID : \\[c\\d+\\] \\'" + c_wifiname + "\\'", 5);
		Assert.assertTrue(checkResult, "Camera did not connect back to primary SSID");
	}

	public void changeAndCheckAltSsid(String ssid, String passwd) throws SerialPortException {
		Log.info("Update alt SSID to: " + ssid);
		boolean checkAltSsidChanged = false;
		PageCameraSetting.getAlternativeSsidArrowBtn().click();
		PageCameraSetting.getAlternativeSsidEditText().sendKeys(ssid);
		PageCameraSetting.getAlternativeSsidPasswordInputBox().sendKeys(passwd);
		PageCameraSetting.getAlternativeSsidProceedBtn().click();
		TimeHelper.sleep(3000);
		checkAltSsidChanged = terminal.sendCommand("config", "ROUTER_SSID_BACKUP : a\\[\\d\\]\\'" + ssid + "\\'", 10);
		Assert.assertTrue(checkAltSsidChanged, "Alternative SSID change was not applied to camera");
	}

	@AfterClass
	public void cleanup() {
		try {
			terminal.closePort();
		} catch (Exception e) {
		}
	}
}
