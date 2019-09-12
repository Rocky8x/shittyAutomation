package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN02_FbLoginWithExistedEmail extends TestBaseAndroid{
	
	@Test(priority = 18, description = "TC008: Log in with facebook user existed in system")
	public void loginWithFaceBookAccountExisted() {
		String facebookAccount = "qa4290@gmail.com";
		String facebookPassword = "123456aA";
		PageGetStart.goToSigninPage();
		PageLogin.loginWithFacebookAccount(facebookAccount, facebookPassword);
		Assert.assertTrue(PageDashboard.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
	}
}
