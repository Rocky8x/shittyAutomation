package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class SIGNUP01_SignUpTest extends TestBaseIOS{
	
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
	
	/* This test must always be run at the very first
	* the user created by this test will be use in the whole test run for all other test cases
	* and will be removed by server automatically at the end of everyday
	* this is a request from server team,
	* PLEASE FOLLOW ALWAYS
	*/
	
	@Test(priority = 0, description = "Verify that user can sign up new account with valid data")
	public void signUpWithValidData() {
		// Go to sign up page and register new account
		PageGetStart.gotoSignUpPage();
		PageSignUp.registerNewAccount(username, firstName, lastName, email, "123123Aa");
		TimeHelper.sleep(10*1000);
		// Get confirm sign up email 
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
		restMailHelper.deleteAllRestMail();
		restMailHelper.confirmSignupEmail();
		// Click confirm email later and verify app stay in dashboard
//		PageSignUp.clickConfirmEmailLaterButton();
		Assert.assertTrue(PageDashboard.isDisplayed(), "Dashboard page should display");
	}
	
	@Test(priority = 1, description = "Verify that user cannot sign up new account with invalid username")
	public void signUpWithInvalidUserName() {
		// Go to sign up page and create new account with special character username
		PageGetStart.gotoSignUpPage();
		PageSignUp.registerNewAccount(StringHelper.randomString("~", 7), firstName, lastName, email, password);
		
		// Get error message and verify with special character username
		String message = PageSignUp.getSignUpErrorMessage();
		Assert.assertEquals(message, PageSignUp.msg_WARN_SPECAIL_CHARACTER_USERNAME, 
				String.format("Message should display with: %s. But found: %s", PageSignUp.msg_WARN_SPECAIL_CHARACTER_USERNAME, message));
		
		// invalid length username
		String[] invalidlengthnames = { "",
									   "-",
				StringHelper.randomString(".", 4),
				StringHelper.randomString("@", 30),
				StringHelper.randomString("", 52)
				};
		for (String name : invalidlengthnames) {
		TimeHelper.sleep(2000);
		PageBase.getOKButton().click();
		PageBase.swipeBottomToTop();
		PageSignUp.getUsernameTextField().sendKeys(name);
		PageSignUp.getSignUpButton().click();
		message = PageSignUp.getSignUpErrorMessage().replaceAll("\\r+|\\n+|\\r+\\n+", " ");
		Assert.assertEquals(message, PageSignUp.msg_WARN_LENGTH_USERNAME, 
				String.format("Message should display with: %s. But found: %s.", PageSignUp.msg_WARN_LENGTH_USERNAME, message));
		
		}
		
		
	}
	
	@Test(priority = 2, description = "Verify that user cannot sign up new account with invalid email")
	public void signUpWithInvalidEmail() {
		// Go to sign up page and create new account with invalid email
		PageGetStart.gotoSignUpPage();
		PageSignUp.registerNewAccount(username, firstName, lastName, "qa", password);
		// Get error message and remove all line breaks and verify
		String message = PageSignUp.getSignUpErrorMessage().replaceAll("\\r+|\\n+|\\r+\\n+|\\n+\\r+", " ").replaceAll("  ", " ");
	
		Assert.assertEquals(message, PageSignUp.msg_WRONG_EMAIL_FORMAT, 
				String.format("Message should display with: %s. But found: %s.", PageSignUp.msg_WRONG_EMAIL_FORMAT, message));
	}
	
	@Test(priority = 3, description = "Verify that user cannot sign up new account with invalid password")
	public void signUpWithInvalidPassword() {
		// Go to sign up page and create new account with invalid password
		PageGetStart.gotoSignUpPage();
		PageSignUp.registerNewAccount(username, firstName, lastName, email, "12");
		// Get error message and remove all line breaks
		String message = PageSignUp.getSignUpErrorMessage().replaceAll("\\r+|\\n+|\\r+\\n+", " ");
	
		Assert.assertEquals(message, PageSignUp.msg_PASSWD_STRENGTH, 
				String.format("Message should display with: %s. But found: %s.", PageSignUp.msg_PASSWD_STRENGTH, message));
	}
	
	@Test(priority = 3, description = "Verify that user cannot sign up new account with invalid firstname")
	public void signUpWithInvalidFirstName() {
		// Go to sign up page and create new account with invalid first name
		PageGetStart.gotoSignUpPage();
		PageSignUp.registerNewAccount(username, firstName, lastName, email, password);
		String[] invalidnames = { "",
				StringHelper.randomString("", 31),
				StringHelper.randomString("", 32),
				StringHelper.randomString("", 52),
				};
		for (String name : invalidnames) {
		TimeHelper.sleep(2000);
		PageBase.getOKButton().click();
		PageBase.swipeBottomToTop();
		PageSignUp.getFirstNameTextField().sendKeys(name);
		PageSignUp.getSignUpButton().click();
		String message = PageSignUp.getSignUpErrorMessage().replaceAll("\\r+|\\n+|\\r+\\n+", " ");
		Assert.assertEquals(message, PageSignUp.msg_WARN_FIRSTNAME_STRENGTH, 
				String.format("Message should display with: %s. But found: %s.", PageSignUp.msg_WARN_FIRSTNAME_STRENGTH, message));
		
		}
	}
	
	@Test(priority = 3, description = "Verify that user cannot sign up new account with invalid lastname")
	public void signUpWithInvalidLastName() {
		// Go to sign up page and create new account with invalid last name
		PageGetStart.gotoSignUpPage();
		PageSignUp.registerNewAccount(username, firstName, lastName, email, password);
		String[] invalidnames = { "",
				StringHelper.randomString("", 31),
				StringHelper.randomString("", 32),
				StringHelper.randomString("", 52),
				};
		for (String name : invalidnames) {
		TimeHelper.sleep(2000);
		PageBase.getOKButton().click();
		PageSignUp.getLastNameTextField().sendKeys(name);
		PageSignUp.getSignUpButton().click();
		String message = PageSignUp.getSignUpErrorMessage().replaceAll("\\r+|\\n+|\\r+\\n+", " ");
		Assert.assertEquals(message, PageSignUp.msg_WARN_LASTNAME_STRENGTH, 
				String.format("Message should display with: %s. But found: %s.", PageSignUp.msg_WARN_LASTNAME_STRENGTH, message));
		
		}
	}
}
