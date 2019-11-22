package motorola.tests;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.cinatic.ApiHelperConfig;
import com.cinatic.FileHelper;
import com.cinatic.StringHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.ebn.automation.core.CoreTestBase;
import com.ebn.automation.core.TestHelper;

import rocky.automation.POM.DriverManager;
import rocky.automation.POM.DriverSetting;

public class TestBase extends CoreTestBase {

	public static DriverSetting	driverSetting;
	public static Class<?>		tenant;

	@BeforeSuite
	public void beforeSuiteBase(ITestContext context) {

		testParams = TestHelper.loadTestParams(context);

		String systemPath = StringHelper.getSystemPath();
		try {
			FileHelper.clearFolder(systemPath + "/html/");
			FileHelper.clearFolder(systemPath + "/allure-results/");
		} catch (Exception e) {
			Log.info(String.format("Got exception while clean up previous report: %s",
					e.getMessage()));
		}

		driverSetting = new DriverSetting();
		driverSetting.setApp(String.valueOf(testParams.get("app")));
		driverSetting.setDeviceName(String.valueOf(testParams.get("deviceName")));
		driverSetting.setDeviceUDID(String.valueOf(testParams.get("deviceUDID")));
		driverSetting.setRemoteURL(String.valueOf(testParams.get("remoteURL")));

		try {

			driverSetting.setAppId("com.perimetersafe.airpurifier");
			tenant = TenantObjects.getTenantByAppName(driverSetting.getAppId());
			ApiHelperConfig.configServer(tenant, testParams.get("server"),
					testParams.get("userName"));
		} catch (Exception e) {}
	}

	@AfterMethod
	public void afterMethodBase(ITestResult result) {

		String testName = result.getMethod().getMethodName();
		try {

			DriverManager.captureScreen(testName);
		} catch (Exception e) {}
		DriverManager.getDriver().quit();
	}

	protected void launchApp() {

		// Do not call this function from within this class
		// since driverSetting is not yet fully configured in this class
		// there's more configuration need to be done in TestBaseAndroid and TestBaseIOS
		DriverManager.createMobileDriver(driverSetting);
	}
}
