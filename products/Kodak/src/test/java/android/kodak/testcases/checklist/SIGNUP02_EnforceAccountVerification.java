package android.kodak.testcases.checklist;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.cinatic.StringHelper;

import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import android.kodak.object.PageLogin.LoginErrorDialog;
import android.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseAndroid;

public class SIGNUP02_EnforceAccountVerification extends TestBaseAndroid {
	
	@Test(priority = 14, description = "TC004: Log in with not active user")
	public void loginWithNotActiveUser() {
		String username = StringHelper.randomString("qaauto_", 8).toLowerCase();
		String firstname = StringHelper.randomString("qaauto_", 8).toLowerCase();
		String lastname = StringHelper.randomString("qaauto_", 8).toLowerCase();
		String email = username + "@restmail.net";
		
		PageGetStart.goToSignUpPage();
		PageSignUp.registerAccount(username, firstname, lastname, email, email, c_password, c_password);
		PageSignUp.exitPage();
		
		PageGetStart.getSigninBtn().click();
		PageLogin.loginApp(username, c_password); 
		String dialogTitle = PageLogin.LoginErrorDialog.getTitle().getText();
		String dialogContent = PageLogin.LoginErrorDialog.getCheckVerificationEmailMessage().getText();
		
		PageLogin.LoginErrorDialog.getNoButton().click();
		
		assertTrue(dialogTitle.equals(LoginErrorDialog.TITLE));
		assertTrue(dialogContent.equals(LoginErrorDialog.MSG_VERIFICATION));
	}
}