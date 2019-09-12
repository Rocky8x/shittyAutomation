package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.driver.DriverManager;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseIOS;

public class LOGIN07_LoginWithRememberMe extends TestBaseIOS{

	@Test(priority = 8, description = "Login with remember me")
	public void loginWithRememberMe() {
		// Login app
		PageGetStart.checkAndSignin(c_username, c_password);
		Assert.assertTrue(PageDashboard.verifyAddNewDeviceButtonDisplayed(), "Dashboard page should display.");
		// Kill app and open app again
		DriverManager.getDriver().closeApp();
		DriverManager.createWebDriver(driverSetting);
		// Verify app stay in dashboard
		Assert.assertTrue(PageDashboard.verifyAddNewDeviceButtonDisplayed(), "Dashboard page should display.");
	}
	
	@Test(priority = 12, description = "Verify that user cannot login with invalid data")
	public void loginWithInvalidData() {
		PageGetStart.gotoSigninPage();
		// Login with invalid password
		PageLogin.loginApp(c_username, c_password + "1");
		String msg = PageLogin.getLoginErrorMessageText();
		// Verify error message display
		Assert.assertEquals(msg.matches(PageLogin.MSG_WRONG_PASSWORD_LOGIN), true, "Error: Actual result is:" + msg);
	}
}
