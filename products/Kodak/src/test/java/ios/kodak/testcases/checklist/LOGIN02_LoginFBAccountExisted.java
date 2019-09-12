package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseIOS;

public class LOGIN02_LoginFBAccountExisted extends TestBaseIOS{

	@Test(priority = 9, description = "Login using FB and this account existed in Kodak server")
	public void loginWithFacebookAccountExistInServer() {
		String fbUser = "qa4290@gmail.com";
		String fbPwd = "123456aA";
		// Go to sign in page
		PageGetStart.gotoSigninPage();
		// Login with Facebook
		PageLogin.clickOnFacebookIcon();
		PageLogin.loginWithFacebook(fbUser, fbPwd);
		PageBase.clickContinueButton();
		// Verify app stay in dashboard
		Assert.assertTrue(PageDashboard.isDisplayed(), "Dashboard page should display");
		PageGetStart.gotoSigninPage();
	}
}
