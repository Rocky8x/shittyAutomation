package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN04_GoogleLoginWithExistedEmail extends TestBaseAndroid{
			
	@Test(priority = 16, description = "TC006: Log in with google user existed in system")
	public void loginWithGoogleAccountExisted() {
		String gmailAccount = "qa0855@gmail.com";
		String gmailPassword = "Cinatic123";
		PageGetStart.goToSigninPage();
		PageLogin.loginWithGoogleAccount(gmailAccount, gmailPassword);
		Assert.assertTrue(PageDashboard.getAddDeviceBigBtn().isEnabled(), "App should navigate to dashboard page.");
	}
}
