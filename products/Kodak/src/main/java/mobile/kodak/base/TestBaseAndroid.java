package mobile.kodak.base;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.driver.DriverAndroid;
import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageDoNotDisturb;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import jssc.SerialPortException;

public class TestBaseAndroid extends TestBase {

	@BeforeSuite
	public void beforeSuiteAndroid() throws IOException {

		cleanUpAppium();
		unlockCameraShell();
		if (!c_quickRun) {
			launchApp();
			PageGetStart.checkAndSignin(c_username, c_password);
			enableShowDebugInfo();
			enableDnd();
			closeApp();
		}
	}

	@BeforeMethod
	public void beforeMethodAndroid(Method method) throws Exception {

		Log.info("====> Nodes: " + TerminalHelper.exeBashCommand("pgrep node").replace('\n', ' '));

		if (TestConstant.appVersion == null) {
			TestConstant.appVersion = TerminalHelper
					.adbGetAppVersion(driverSetting.getDeviceUDID());
		}
	}

	@AfterMethod
	public void afterMethodAndroid(ITestResult result) throws Exception {

		String testName = result.getMethod().getMethodName();

		switch (result.getStatus()) {
		case ITestResult.FAILURE:
			String fullPath = StringHelper.getSystemPath() + "/html/" + testName + ".logcat";
			TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
			break;
		default:
			break;
		}
	}

	public String adbClearAppData() throws Exception {

		String	appPackage	= (String) ((DriverAndroid) DriverManager.getDriver()).cap
				.getCapability("appPackage");
		String	clearAdbCmd	= String.format("adb -s %s shell pm clear %s",
				driverSetting.getDeviceUDID(), appPackage);

		PageBase.resetFlags();
		return TerminalHelper.exeBashCommand(clearAdbCmd);
	}

	public void adbRefreshWifi() {

		try {
			String cmd = String.format("adb -s %s shell svc wifi disable",
					driverSetting.getDeviceUDID());
			TerminalHelper.exeBashCommand(cmd);
			TimeHelper.sleep(500);
			cmd = String.format("adb -s %s shell svc wifi enable", driverSetting.getDeviceUDID());
			TerminalHelper.exeBashCommand(cmd);

			TimeHelper.sleep(15000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void adbReinstallApp() throws Exception {

		String	packageName	= (String) ((DriverAndroid) DriverManager.getDriver()).cap
				.getCapability("appPackage");
		String	apkFile		= (String) ((DriverAndroid) DriverManager.getDriver()).cap
				.getCapability("app");

		// uninstall first
		String cmd = String.format("adb -s %s uninstall %s", driverSetting.getDeviceUDID(),
				packageName);
		TerminalHelper.exeBashCommand(cmd);

		// install app again
		cmd = String.format("adb -s %s install %s", driverSetting.getDeviceUDID(), apkFile);
		TerminalHelper.exeBashCommand(cmd);

		PageBase.resetFlags();
	}

	public void enableShowDebugInfo() {

		PageBase.enableDebugInfo(c_username, c_password);

	}

	public void enableDnd() {

		// enable DND to prevent notification cover app UI -> can't find UI
		// element to interact with
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.getDevicesTextView().click();

		PageHomeMenu.getDoNotDisturbTextBtn().click();

		PageDoNotDisturb.enableDoNotDisturb(true);
		PageDoNotDisturb.enableDndSchedule(false);
		PageBase.exitPage();
	}

	public void unlockCameraShell() {

		try {
			Log.info("Unlock camera shell");
			Terminal t = new Terminal(c_comport);
			t.unlockCameraShell();
			t.closePort();
		} catch (SerialPortException e) {
			Log.fatal(e.getMessage());
		}
	}
}
