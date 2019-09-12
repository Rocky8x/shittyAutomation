package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import ios.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class LOGIN01_LoginFaceBookAccountNotExisted extends TestBaseIOS{

	@Test(priority = 5, description = "Login with facebook user that is not existed in server")
	public void loginFacebookNotExisted() {
		String fbUser = "drleo407@gmail.com";
		String fbPwd = "songnhi407";
		String username = StringHelper.randomString("qaauto_", 6).toLowerCase();
		String email = username + "@restmail.net";
		String password = "Cinatic123";
		String firstName = "Qatest";
		String lastName = StringHelper.randomString("", 5);
		
		// Go to sign in page
		PageGetStart.gotoSigninPage();
		
		// Login with Facebook
		PageLogin.clickOnFacebookIcon();
		PageLogin.loginWithFacebook(fbUser, fbPwd);
		PageLogin.clickFabookContinueLogin();
		// Get message account not exist in server
		String msg = PageLogin.getRegisterNewAccountMessage();
		Assert.assertEquals(msg, PageLogin.MSG_USER_NOT_EXISTED, "Message should display with: " + PageLogin.MSG_USER_NOT_EXISTED);
		PageBase.clickYesButton();
		TimeHelper.sleep(5000);
		
		// Create new account
		PageSignUp.registerNewAccount(username, firstName, lastName, email, password);
		
		TimeHelper.sleep(10*1000);
		// Get confirm sign up email 
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
		restMailHelper.deleteAllRestMail();
		restMailHelper.confirmSignupEmail();
		// Click confirm email later and verify app stay in dashboard
		Assert.assertTrue(PageDashboard.isDisplayed(), "Dashboard page should display.");
		PageGetStart.gotoSigninPage();
	}
}
