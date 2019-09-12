package android.kodak.testcases.checklist;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;

import android.kodak.object.PageBase;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import android.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN03_GoogleLoginWithNotExistedEmail extends TestBaseAndroid{
		
	@Test(priority = 15, description = "TC005: Log in with google user not exist in system")
	public void loginWithGoogleAccountNotExisted() {
		String gmailAccount = "autoqatest01@gmail.com";
		String gmailPassword = "Cinatic123";
		String username = StringHelper.randomString("qaauto_", 10).toLowerCase();
		String email = username + "@restmail.net";
		
		PageGetStart.goToSigninPage();
		PageLogin.loginWithGoogleAccount(gmailAccount, gmailPassword);
		String message = PageLogin.getLoginMessage();
		if(PageLogin.MSG_USER_NOT_EXISTED.equals(message)) {
			PageBase.clickConfirmButton();
		}else {
			Assert.fail("Error: Actual result is: " + message);
		}
		
		// make sure that app will navigate to sign up page
		assertTrue(PageSignUp.getUsernameTextbox().getWebElement()!= null);
		assertTrue(PageSignUp.getEmailTextbox().getWebElement()!= null);
		assertTrue(PageSignUp.getConfirmEmailTextbox().getWebElement()!= null);
		assertTrue(PageSignUp.getPasswordTextbox().getWebElement()!= null);
		assertTrue(PageSignUp.getConfirmPasswordTextbox().getWebElement()!= null);
		assertTrue(PageSignUp.getAgreeCheckbox().getWebElement()!= null);
		assertTrue(PageSignUp.getSignUpButton().getWebElement()!= null);

		
	}
}
