package mobile.kodak.base;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.cinatic.ApiHelperConfig;
import com.cinatic.FileHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.driver.DriverManager;
import com.cinatic.driver.DriverSetting;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;
import com.ebn.automation.core.TestHelper;

import android.kodak.object.PageBase;

public class TestBase {

	public static String currentLoginUser = "";

	// Still using two ways to obtain test params, one is via static "c_" var (old
	// way), the other is via static Map "testParams" (new way)
	// the reason for using both method is for backward compatible with old test
	// cases
	// refactor all these tests will take lots of time, hence the current situation
	// here.

	// Using testParam map will be easier when add or remove test parameters:
	// - from Jenkins: just add/remove java VM args "-DtestArg.[test param name]"
	// - from xml suite file: add/remove <parameter> tag
	// then use testParams.get("[test param name]") in java test class
	//
	public static String				c_server, c_deviceid, c_devicessid, c_comport, c_wifiname,
			c_wifipassword, c_username, c_password;
	public static Map<String, String>	testParams;
	/* ------------------------------------------------------------ */

	public static DriverSetting driverSetting;

	public static Class<?> tenant;

	@BeforeSuite
	public void beforeSuite(ITestContext icontext) throws Exception {

		testParams = TestHelper.loadTestParams(icontext);

		PageBase.resetFlags();
		String systemPath = StringHelper.getSystemPath();
		try {
			FileHelper.clearFolder(systemPath + "/html/");
			FileHelper.clearFolder(systemPath + "/allure-results/");
			FileHelper.deleteFiles((StringHelper.getSystemPath()), ".xlsx");
		} catch (Exception e) {
			Log.info(String.format("Got exception while clean up previous report: %s",
					e.getMessage()));
		}

		c_server		= testParams.get("server");
		c_username		= testParams.get("username");
		c_password		= testParams.get("password");
		c_comport		= testParams.get("comport");
		c_wifiname		= testParams.get("wifiname");
		c_wifipassword	= testParams.get("wifipassword");
		if (testParams.get("deviceid") != null) {
			c_deviceid = testParams.get("deviceid");
		} else {
			Terminal t = new Terminal(c_comport);
			c_deviceid = t.getCameraUdid();
			t.closePort();
		}
		c_devicessid = Device.convertSsidByUuid(c_deviceid);

		driverSetting = new DriverSetting();
		driverSetting.setApp(String.valueOf(testParams.get("app")));
		driverSetting.setDeviceName(String.valueOf(testParams.get("deviceName")));
		driverSetting.setDeviceUDID(String.valueOf(testParams.get("deviceUDID")));
		driverSetting.setRemoteURL(String.valueOf(testParams.get("remoteURL")));

		try {

			driverSetting.setAppId("com.perimetersafe.kodaksmarthome");
			tenant = TenantObjects.getTenantByAppName(driverSetting.getAppId());
			ApiHelperConfig.configServer(tenant, c_server, c_username);
		} catch (Exception e) {}
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws Exception {

		Log.info(String.format("------ START: %s: %s ------",
				method.getDeclaringClass().getSimpleName(), method.getName()));
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception {

		Log.info("---------------------- END: " + result.getMethod().getMethodName()
				+ " ----------------------");
		String testName = result.getMethod().getMethodName();
		DriverManager.captureScreen(testName);
		closeApp();
		if (result.getStatus() != 1) {
			Log.info("----- Trace: ");
			result.getThrowable().printStackTrace();
		}

		String status = "UNKNOWN";
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "SUCCESS";
			break;
		case ITestResult.FAILURE:
			status = "FAILURE";
			break;
		case ITestResult.SKIP:
			status = "SKIPPED";
			break;
		default:
			break;
		}
		Log.info("  | Result : " + status);
		Log.info(result.getInstanceName());
	}

	public void cleanUpAppium() {

		// kill all orphan Appium server
		try {
			String pidList = TerminalHelper.exeBashCommand("pgrep node").trim();
			if (!pidList.isEmpty()) {
				TerminalHelper.exeBashCommand(String.format("kill %s", pidList.replace("\n", " ")));
			}
		} catch (Exception e) {}
	}

	protected void launchApp() {

		// Do not call this function from within this class
		// since driverSetting is not yet fully configured in this class
		// there's more configuration need to be done in TestBaseAndroid and TestBaseIOS
		DriverManager.createWebDriver(driverSetting);
	}

	protected void closeApp() {

		DriverManager.getDriver().quit();
	}
}
