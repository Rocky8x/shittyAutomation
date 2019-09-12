package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import ios.kodak.object.PageProfiles;
import mobile.kodak.base.TestBaseIOS;

public class SETTING16_ChangePasswordAccount extends TestBaseIOS{

ApiHelper apiHelper;
	
	@Test(priority = 15, description = "Verify that user can change password account")
	public void changePasswordProfile() {
		String newPwd = "Cinatic1234";
		try {
			//Login app
			PageGetStart.checkAndSignin(c_username, c_password);
			// Go to user profile and change password
			PageDashboard.gotoUserProfilePage();
			PageProfiles.clickChangePassword();
			PageProfiles.changePasswordProfile(c_password, newPwd);
			// Get change password message and verify
			String message = PageProfiles.getChangePasswordStatus();
			Assert.assertEquals(message, "Success");
			// Sign out and sign in again with new password
			PageBase.clickOkButton();
			PageBase.clickBackButton();
			PageDashboard.signOut();
			PageLogin.loginApp(c_username, newPwd);
			Assert.assertTrue(PageDashboard.isDisplayed(), "Dashboard page should display.");
		}finally {
			// Recovery password
			apiHelper = new ApiHelper();
			apiHelper.userLogin(c_username, newPwd);
			apiHelper.changePassword(newPwd, c_password, c_password);
			PageGetStart.gotoSigninPage();
			PageLogin.loginApp(c_username, c_password);
		}
	}
}
