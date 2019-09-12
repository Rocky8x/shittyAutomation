package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseIOS;

public class LOGIN04_LoginGGAccountExisted extends TestBaseIOS{

	@Test(priority = 11, description = "Login using Google and this account existed in Kodak server")
	public void loginWithGoogleAccountExistedInServer() {
		String gmailAccount = "qa0855@gmail.com";
		String gmailPassword = "Cinatic123";
		// Login with google account 
		PageGetStart.gotoSigninPage();
		PageLogin.loginWithGoogleAccount(gmailAccount, gmailPassword);
		// Verify app stay in dashboard
		Assert.assertTrue(PageDashboard.isDisplayed(), "Dashboard page should display.");
		PageGetStart.gotoSigninPage();
	}
}
