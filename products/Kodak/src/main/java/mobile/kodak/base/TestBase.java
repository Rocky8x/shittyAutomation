package mobile.kodak.base;

import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

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

import android.kodak.object.PageBase;

public class TestBase {

	public static String	currentLoginUser	= "";
	public static String	c_platform, c_server, c_deviceid, c_devicessid, c_comport, c_wifiname,
			c_wifipassword, c_username, c_password;
	public boolean			c_quickRun			= false; // set to true if you don't want to force
														 // enable DND and show debug on the app

	public static DriverSetting driverSetting;

	public static ApplicationContext	context;
	public static Class<?>				tenant;

	@Parameters({	"platform",
					"server",
					"username",
					"password",
					"deviceid",
					"comport",
					"wifiname",
					"wifipassword",
					"quickRun" })
	@BeforeSuite
	public void beforeSuite(String platform, String server, @Optional String username,
			@Optional String password, @Optional String deviceid, String comport, String wifiname,
			String wifipassword, @Optional String quickRun) throws Exception {

		loadBean();
		loadParam(platform, server, username, password, deviceid, comport, wifiname, wifipassword,
				quickRun);
		driverSetting	= (DriverSetting) context.getBean(c_platform);
		tenant			= TenantObjects.getTenantByAppName(driverSetting.getApp());
		ApiHelperConfig.configServer(tenant, c_server, c_username);
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws Exception {

		Log.info(String.format("------ START: %s: %s ------",
				method.getDeclaringClass().getSimpleName(), method.getName()));
		launchApp();
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

	public void loadParam(String platform, String server, @Optional String username,
			@Optional String password, String deviceid, String comport, String wifiname,
			String wifipassword, @Optional String quickRun) throws Exception {

		c_platform	= platform;
		c_server	= server;
		if (username != null) { c_username = username; }
		if (password != null) { c_password = password; }

		c_comport		= comport;
		c_wifiname		= wifiname;
		c_wifipassword	= wifipassword;
		if (deviceid != null) {
			c_deviceid = deviceid;
		} else {
			Terminal t = new Terminal(c_comport);
			c_deviceid = t.getCameraUdid();
			t.closePort();
		}
		c_devicessid = Device.convertSsidByUuid(c_deviceid);
		if (System.getProperty("suitePlatform") != null) {
			c_platform = System.getProperty("suitePlatform");
		}

		if (quickRun != null)
			c_quickRun = Boolean.parseBoolean(quickRun);
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

	public void loadBean() {

		PageBase.resetFlags();
		context = new ClassPathXmlApplicationContext("Bean.xml");
		String systemPath = StringHelper.getSystemPath();
		try {
			FileHelper.clearFolder(systemPath + "/html/");
			FileHelper.clearFolder(systemPath + "/allure-results/");
			FileHelper.deleteFiles((StringHelper.getSystemPath()), ".xlsx");
		} catch (Exception e) {
			Log.info(String.format("Got exception while clean up previous report: %s",
					e.getMessage()));
		}
	}

	protected void launchApp() {

		DriverManager.createWebDriver(driverSetting);
	}

	protected void closeApp() {

		DriverManager.getDriver().quit();
	}
}
