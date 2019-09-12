package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseIOS;

public class LOGIN05_LoginWithExistedAccount extends TestBaseIOS{

	@Test(priority = 6, description = "Login/Logout with existed user")
	public void loginLogoutWithExistedUser() {
		// Go to sign in page and login app
		PageGetStart.gotoSigninPage();
		PageLogin.loginApp(c_username, c_password);
		Assert.assertTrue(PageDashboard.isDisplayed(), "Dashboard page should display");
		// Log out and login again
		PageGetStart.gotoSigninPage();
		Assert.assertTrue(PageBase.getBackButton().isDisplayed(), "App should navigate to Sign In page.");
	}
}
