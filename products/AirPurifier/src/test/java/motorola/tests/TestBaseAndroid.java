package motorola.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.ebn.automation.core.TestHelper;
import com.motorola.ap.android.Objects.PageBase;

import rocky.automation.POM.DriverManager;

public class TestBaseAndroid extends TestBase {

	@BeforeSuite
	public void beforeSuiteAndroid() throws Exception {

		driverSetting.setPlatformName("Android");
		driverSetting.setAppActivity("com.cinatic.demo2.activities.splash.SplashActivity");

		TestHelper.cleanUpAppium();
	}

	@BeforeMethod
	public void beforeMethodAndroid() {

		DriverManager.createMobileDriver(driverSetting);
		new PageBase().checkAndLogin(testParams.get("username"), testParams.get("password"));
	}
}
