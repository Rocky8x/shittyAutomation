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

public class LOGIN03_LoginGGAccountNotExisted extends TestBaseIOS{
	@Test(priority = 10, description = "Login using Google and this account not existed in Kodak server")
	public void loginWithGoogleAccountNotExistedInServer() {
		String gmailAccount = "autoqatest01@gmail.com";
		String gmailPassword = "Cinatic123";
		String username = StringHelper.randomString("qaauto_", 6).toLowerCase();
		String email = username + "@restmail.net";
		String password = "Cinatic123";
		String firstName = "Qatest";
		String lastName = StringHelper.randomString("", 5);
		
		// Login with google account
		PageGetStart.gotoSigninPage();
		PageLogin.loginWithGoogleAccount(gmailAccount, gmailPassword);
		// Get message account not exist in server
		String msg = PageLogin.getRegisterNewAccountMessage();
		Assert.assertEquals(msg, PageLogin.MSG_USER_NOT_EXISTED, "Message should display with: " + PageLogin.MSG_USER_NOT_EXISTED);
		PageBase.clickYesButton();
		TimeHelper.sleep(5000);
		// Create nleeew account
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
