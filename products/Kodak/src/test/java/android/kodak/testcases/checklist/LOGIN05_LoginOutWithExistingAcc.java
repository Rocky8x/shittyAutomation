package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN05_LoginOutWithExistingAcc extends TestBaseAndroid {

	@Test(priority = 19, description = "TC009: Login/Logout with existed user")
	public void loginLogInOutWithExistedUser() {

		try {
			PageDashboard.gotoHomeMenuPage();
		} catch (Exception e) {}
		PageHomeMenu.getSignOutMenuItem().click();
		PageLogin.loginApp(c_username, c_password);
		Assert.assertTrue(PageDashboard.getAddDeviceBigBtn().isEnabled(),
				"App should navigate to dashboard page.");
		PageGetStart.goToSigninPage();
		Assert.assertTrue(PageLogin.getSignInButton().isDisplayed(),
				"App should navigate to signin page.");
	}

	@Test(priority = 13, description = "TC003: Verify that user cannot login with invalid data")
	public void loginWithInvalidData() {

		PageGetStart.goToSigninPage();
		PageLogin.loginApp(c_username, c_password + "1");
		String message = PageLogin.getLoginMsg().getText();

		Assert.assertEquals(message.matches(PageLogin.msg_WRONG_PASSWORD), true,
				"Error: actual result is " + message);
	}
}
