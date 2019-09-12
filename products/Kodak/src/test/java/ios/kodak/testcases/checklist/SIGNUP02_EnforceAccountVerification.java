package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import ios.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class SIGNUP02_EnforceAccountVerification extends TestBaseIOS{
	
	private String username;
	private String password;
	private String email;
	String firstName = "Qatest";
	String lastName = StringHelper.randomString("", 5);
	
	@BeforeClass
	public void Precondition() {
		this.username = StringHelper.randomString("qaauto_", 6).toLowerCase();
		this.password = "Aaaa1111";
		this.email = this.username + "@restmail.net";
		
	}

	@Test(priority = 4, description = "Enforce account verification")
	public void verifyUserEnforceAccountVerification() {
		// Go to sign up page and register new account
		PageGetStart.gotoSignUpPage();
		PageSignUp.registerNewAccount(username, firstName, lastName, email, password);
		TimeHelper.sleep(10*1000);

		// Kill app and open app again
		DriverManager.getDriver().closeApp();
		DriverManager.createWebDriver(driverSetting);
		String errMsg = PageLogin.getLoginErrorMessageText().replaceAll("\\r+|\\n+|\\r+\\n+|\\n+\\r+", " ").replaceAll("  ", " ");
		Assert.assertEquals(errMsg, PageLogin.MSG_NOT_ACTIVE_ACCOUNT);
		
		// Get confirm sign up email 
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
		restMailHelper.deleteAllRestMail();
		restMailHelper.confirmSignupEmail();
		
		// Verify app stay in dashboard
		PageBase.clickNoButton();
		PageLogin.getLoginButton().click();
		Assert.assertTrue(PageDashboard.isDisplayed(), "Dashboard page should display");
	}
}
