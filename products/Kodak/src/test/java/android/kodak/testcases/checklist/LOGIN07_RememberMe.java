package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.driver.DriverManager;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN07_RememberMe extends TestBaseAndroid {
	
	@Test(priority = 12, description = "TC002: Verify that user can login with remember me successfully")
	public void loginWithRememberMe() {
		PageGetStart.checkAndSignin(c_username, c_password);		
		Assert.assertEquals(PageDashboard.getAddDeviceBigBtn().isEnabled(), true);
		
		DriverManager.getDriver().quit();	
		DriverManager.createWebDriver(driverSetting);
		Assert.assertEquals(PageDashboard.getAddDeviceBigBtn().isEnabled(), true);
	}
}
