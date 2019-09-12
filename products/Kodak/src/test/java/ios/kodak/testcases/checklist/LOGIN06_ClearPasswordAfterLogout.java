package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseIOS;

public class LOGIN06_ClearPasswordAfterLogout extends TestBaseIOS{

	@Test(priority = 7, description = "After logout, app should clear password field")
	public void clearPasswordAfterLogout() {
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Logout
		PageDashboard.getMenuButton().click();
		PageDashboard.signOut();
		
		// Verify password field is empty
		Assert.assertEquals(PageLogin.getUsernameTextBox().getText(), c_username);
		Assert.assertEquals(PageLogin.getPasswordTextBox().getText(), "Password");
	}
}
