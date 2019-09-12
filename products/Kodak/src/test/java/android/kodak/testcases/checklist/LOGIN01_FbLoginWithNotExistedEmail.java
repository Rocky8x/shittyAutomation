package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import android.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN01_FbLoginWithNotExistedEmail extends TestBaseAndroid {
	
	@Test(priority = 17, description = "TC007: Log in with Facebook user not exist in system")
	public void loginWithFacebookAccountNotExisted() {
		String facebookAccount = "ebnqa@restmail.net";
		String facebookPassword = "123123Aa";
		String username = StringHelper.randomString("qatest", 10);
		String firstname = StringHelper.randomString("qatest", 10);
		String lastname = StringHelper.randomString("qatest", 10);

		String email = username + "@restmail.net";
		
		PageGetStart.goToSigninPage();
		PageLogin.loginWithFacebookAccount(facebookAccount, facebookPassword);
		String message = PageLogin.getLoginMessage();
		if(PageLogin.MSG_USER_NOT_EXISTED.equals(message)) {
			PageBase.clickConfirmButton();
		}else {
			Assert.fail("Error: Actual result is: " + message);
		}
		PageSignUp.registerAccount(username, firstname, lastname, email, email, c_password, c_password);
		PageSignUp.getVerifyEmailLaterBtn().click();
		Assert.assertTrue(PageDashboard.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
	}
	
	
}