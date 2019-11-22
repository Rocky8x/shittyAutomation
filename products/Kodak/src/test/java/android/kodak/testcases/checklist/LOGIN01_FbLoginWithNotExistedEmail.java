package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import android.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN01_FbLoginWithNotExistedEmail extends TestBaseAndroid {

	@Test(priority = 17, description = "TC007: Log in with Facebook user not exist in system")
	public void loginWithFacebookAccountNotExisted() {
		String facebookAccount = "ebn_ben@restmail.net";
		String facebookPassword = "123123Aa";
		
		PageGetStart.goToSigninPage();
		PageLogin.loginWithFacebookAccount(facebookAccount, facebookPassword);
		String message = PageLogin.getLoginMessage();
		if(PageLogin.MSG_USER_NOT_EXISTED.equals(message)) {
			PageBase.clickConfirmButton();
		}else {
			Assert.fail("Error: Actual result is: " + message);
		}
		String email = PageSignUp.getEmailTextbox().getText();
		Assert.assertEquals(facebookAccount, email );
	}	
}