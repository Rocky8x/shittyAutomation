package mobile.kodak.base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class TestBaseIOS extends TestBase{
	
	@BeforeSuite
	public void beforeSuiteIOS () throws Exception {

		driverSetting.setPlatformName("iOS");
	}
	
	@BeforeMethod
	public void beforeMethodIOS() {
		launchApp();
	}
}