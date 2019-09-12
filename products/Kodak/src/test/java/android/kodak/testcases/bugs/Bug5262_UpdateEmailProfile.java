package android.kodak.testcases.bugs;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageProfile;
import mobile.kodak.base.TestBaseAndroid;
import net.restmail.KodakRestMailHelper;

public class Bug5262_UpdateEmailProfile extends TestBaseAndroid {
	@Test(priority = 1, description = "TC001: App should update profile after change new email successfully")
	public void TC001_UpdateEmailProfile() {
		String newEmail = "";
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeProfilePage();
		String currentEmail = PageProfile.getCurrentEmailProfile();
		
		PageProfile.getCurrentEmailProfile();
		newEmail = StringHelper.randomString(currentEmail.split("@")[0],3).toLowerCase() + "@restmail.net";
		
		PageProfile.updateEmailProfile(newEmail);
		Assert.assertTrue(PageProfile.verifyChangeEmailConfimationPanel(), "Change email confirmation panel should display.");
		PageBase.exitPage();
		
		KodakRestMailHelper currentEmailHelper = new KodakRestMailHelper(currentEmail);
		KodakRestMailHelper newEmailHelper = new KodakRestMailHelper(newEmail);		
		currentEmailHelper.confirmChangeEmail(newEmailHelper);

		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeProfilePage();
		Assert.assertTrue(
				PageProfile.verifyEmailProfileUpdated(newEmail),
				String.format("Email profile should update, Expected: %s. Actual: %s", newEmail, currentEmail));
	}
}
