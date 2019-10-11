package ios.kodak.testcases.checklist;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;

import ios.kodak.object.PageIOSSetting;
import mobile.kodak.base.TestBaseIOS;

public class TM000_EnableStreamingInfo extends TestBaseIOS{

	@BeforeMethod
	@Override
	public void beforeMethod(Method method) {
		Log.info(String.format("---------------------- START: %s ----------------------", method.getName()));
		String appleSettingBundleID = "com.apple.Preferences";
		String kodakBundleId = driverSetting.getAppId(); 

		driverSetting.setAppId(appleSettingBundleID);
		DriverManager.createWebDriver(driverSetting);
		driverSetting.setAppId(kodakBundleId);
	}
	
	@Test(priority = 0, description = "Enable debug info")
	public void enableStreamingInfo() {
		// Disable Natural PTZ direction
		PageIOSSetting.clickKodakApp();
		PageIOSSetting.enableStreamingInfo(true);
	}
}
