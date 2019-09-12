package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageLogin;
import android.kodak.object.PageProfile;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING16_ChangeAccountPassword extends TestBaseAndroid {
	ApiHelper api;
	
	@Test (priority = 11, description = "User can change password of account")
	public void verifyUserCanChangePasswordAccount() {
		String newPassword = StringHelper.randomStringMustHaveNumber("pW", 10);
		try {
			PageGetStart.checkAndSignin(c_username, c_password);	
			Assert.assertEquals(PageDashboard.getAddDeviceBigBtn().isEnabled(), true);
			
			PageDashboard.gotoHomeMenuPage();
			PageHomeMenu.gotoHomeProfilePage();
			PageProfile.clickChangePassword();
			PageProfile.changePasswordAccount(c_password, newPassword);
			PageBase.exitPage();
			PageGetStart.goToSigninPage();
			PageLogin.loginApp(c_username, newPassword);
			Assert.assertEquals(PageDashboard.getAddDeviceBigBtn().isEnabled(), true);
			PageGetStart.goToSigninPage();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				api = new ApiHelper();
				api.userLogin(c_username, newPassword);
				api.changePassword(newPassword, c_password, c_password);
			} catch (Exception e2) {	}
			throw e;
		} 
		try {
			api = new ApiHelper();
			api.userLogin(c_username, newPassword);
			api.changePassword(newPassword, c_password, c_password);
		} catch (Exception e2) {	}
		PageLogin.loginApp(c_username, c_password);
	}
}
