package android.kodak.testcases.checklist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.log.Log;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import android.kodak.object.PageSignUp;
import mobile.kodak.base.TestBaseAndroid;
import net.restmail.KodakRestMailHelper;

public class SIGNUP01_SignUp extends TestBaseAndroid {
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String email;

	@BeforeClass
	public void Precondition() {
		this.username = StringHelper.randomString("qaauto_", 6).toLowerCase();
		this.firstname = StringHelper.randomString("qaauto_", 6).toLowerCase();
		this.lastname = StringHelper.randomString("qaauto_", 6).toLowerCase();
		this.password = StringHelper.randomStringMustHaveNumber("Pw", 15);
		this.email = this.username + "@restmail.net";
	}
	
	@Test(priority = 11, description="TC001: Verify that user can signup new account with valid data")
	public void TC_001_SignUpWithValidData() {
		
		PageGetStart.goToSignUpPage();
		PageSignUp.registerAccount(username, firstname, lastname, email, email, password, password);
		TimeHelper.sleep(5 * 1000);

		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(username);
		restMailHelper.confirmSignupEmail();
		
//		button removed since 1.0.24(73)
//		pageSignUp.getVerifyEmailLaterBtn().click();
		
		PageSignUp.exitPage();
		
		// make sure that app login to new account automatically navigate to Dashboard page, able to see add button
		
		PageGetStart.goToSigninPage();
		PageLogin.loginApp(username, password);
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		PageDashboard.getAddDeviceBigBtn().getWebElement();
		
		// Log in back to previous account
		PageDashboard.signOut();
		PageLogin.loginApp(c_username, c_password);
	}
	
	@Test(priority = 12, description = "TC002: Verify that user cannot signup new account with invalid username")
	public void TC_002_SignUpWithInvalidUsername() throws InterruptedException, IOException {
		/* users.error.username_format=Your username must be 6 to 30 characters long.
		 *  Only digit, alphabet and these special characters -.@_ are supported
		*/
		PageGetStart.goToSignUpPage();
		String invalidUser= "qa" ;
		PageSignUp.registerAccount(invalidUser,firstname, lastname, email, email, password, password);
		PageSignUp.confirmSignupErrorDialog();
		String message;

		Log.info("Check username with special char");

		char[] notAcceptChar = new String("`~#$%^&*()+=[]{}|\\;:'\"/?<>,") .toCharArray();
		for (char c : notAcceptChar) {
			
			invalidUser= "qaauto_" + c;
			PageSignUp.getUsernameTextbox().sendKeys(invalidUser);
			PageSignUp.getSignUpButton().click();
			message = PageSignUp.getUsernameConstraintMessage().getText();
			PageSignUp.confirmSignupErrorDialog();
			Assert.assertEquals(message, PageSignUp.msg_WARN_USERNAME_SPECIAL_CHARS,
					"Error: actual result is " + message);
		}
		
		Log.info("short username test");
		String[] shortUsernames = { "",
									StringHelper.randomString("", 1),
									StringHelper.randomString("", 2),
									StringHelper.randomString("", 3),
									StringHelper.randomString("", 4),
									StringHelper.randomString("", 5)};
		// too long username
		String[] longUsernames = {  StringHelper.randomString("A", 32), // 33 chars
									StringHelper.randomString("A", 52)}; // 53 chars
		// mix the 2 arrays
		String[] inputNames = ArrayUtils.addAll(shortUsernames, longUsernames);
		
		// check too short and too long username
		for (String name : inputNames) {
			
			PageSignUp.getUsernameTextbox().sendKeys(name);
			PageSignUp.getSignUpButton().click();
			message = PageSignUp.getUsernameConstraintMessage().getText();
			PageSignUp.confirmSignupErrorDialog();
			Assert.assertEquals(message, PageSignUp.msg_WARN_USERNAME_LENGTH,
					"Error: actual result is " + message);
		}
	}
	
	@Test(priority = 13, description = "TC003: Verify that user cannot signup new account with invalid email")
	public void TC_003_SignUpWithInvalidEmail() throws InterruptedException, IOException {
		
		Random random = new Random();
		String[] invalidEmails = { 
				StringHelper.randomString("", random.nextInt(9)) + "@" + StringHelper.randomString("", random.nextInt(9)) + "." + StringHelper.randomString("", 1),
				StringHelper.randomString("", 1) + "@" + "." + StringHelper.randomString("", 9),
				StringHelper.randomString("", 1) + "@" + ".." + StringHelper.randomString("", 9),
				StringHelper.randomString("", 1) + "@@" + "." + StringHelper.randomString("", 9)
				};
		PageGetStart.goToSignUpPage();
		PageSignUp.registerAccount(StringHelper.randomString("", 6), StringHelper.randomString("", 6),StringHelper.randomString("", 6), "AA", "AA", password, password);
		PageSignUp.confirmSignupErrorDialog();
		
		for (String email : invalidEmails) {
			PageSignUp.getEmailTextbox().sendKeys(email);
			PageSignUp.getSignUpButton().click();
			String message = PageSignUp.getEmailConstraintMessage().getText();
			PageSignUp.confirmSignupErrorDialog();
			
			Assert.assertEquals(message, PageSignUp.msg_WRONG_EMAIL_FORMAT,
					"Error: actual result is " + message);
		}		
	}
	
	@Test(priority = 14, description = "TC004: Verify that user cannot signup new account with invalid password")
	public void TC_004_SignUpWithInvalidPassword() throws InterruptedException, IOException {
		/*users.error.password_format=Your password must be 8 to 30 characters long
		 *  and must have at least one Capital letter, one small letter and one number.
		 *   Space is not allowed
		 */
		
		ArrayList<String> passwordLowerCase = new ArrayList<String>();
		passwordLowerCase.add(StringHelper.randomStringNoNumber("", 8).toLowerCase());
		passwordLowerCase.add(StringHelper.randomStringNoNumber("", 30).toLowerCase());
		
		ArrayList<String> passwordUpperCase = new ArrayList<String>();
		passwordUpperCase.add(StringHelper.randomStringMustHaveNumber("", 8).toUpperCase());
		passwordUpperCase.add(StringHelper.randomStringMustHaveNumber("", 30).toUpperCase());
		passwordUpperCase.add(StringHelper.randomNumber("", 8));

			
		ArrayList<String> passwordNoDigit = new ArrayList<String>();
		passwordNoDigit.add(StringHelper.randomStringNoNumber("Aa", 6));
		passwordNoDigit.add(StringHelper.randomStringNoNumber("Aa", 28));

		ArrayList<String> passwordLengthTest =  new ArrayList<String>();
		passwordLengthTest.add("");
		passwordLengthTest.add(StringHelper.randomString("", 7));
		passwordLengthTest.add(StringHelper.randomString("", 6));
		passwordLengthTest.add(StringHelper.randomString("", 5));
		passwordLengthTest.add(StringHelper.randomString("", 4));
		passwordLengthTest.add(StringHelper.randomString("", 3));
		passwordLengthTest.add(StringHelper.randomString("", 2));
		passwordLengthTest.add(StringHelper.randomString("", 1));
		passwordLengthTest.add(StringHelper.randomString("", 31));
		passwordLengthTest.add(StringHelper.randomString("", 60));
		passwordLengthTest.add(StringHelper.randomString("", 41));
		
		
		Map<String, ArrayList<String>> testData = new HashMap<String, ArrayList<String>>();
		testData.put(PageSignUp.msg_PASSWD_STRENGTH, passwordLengthTest);
		testData.put(PageSignUp.msg_PASSWD_NEED_LOWWER_CASE_CHAR, passwordUpperCase);
		testData.put(PageSignUp.msg_PASSWD_NEED_UPPER_CASE_CHAR, passwordLowerCase);
		testData.put(PageSignUp.msg_PASSWD_NEED_DIGIT, passwordNoDigit);

		Set<String> cases = testData.keySet();
		
		PageGetStart.goToSignUpPage();
		PageSignUp.registerAccount(StringHelper.randomString("", 6), StringHelper.randomString("", 6), StringHelper.randomString("", 6), email, email, "12","12");
		PageSignUp.confirmSignupErrorDialog();
		
		for (String MSG: cases) {
			
			Log.info("Check message: " + MSG);
			ArrayList<String> passwordCase = testData.get(MSG);
			
			for (String password : passwordCase) {
				PageSignUp.getPasswordTextbox().sendKeys(password);
				PageSignUp.getSignUpButton().click();
				String message = PageSignUp.getPasswordConstraintMessage().getText();
				PageSignUp.confirmSignupErrorDialog();
				Assert.assertEquals(message, MSG,
						"Error: actual result is " + message);
			}
		}		
	}
	
	@Test(priority = 12, description = "TC005: Verify that user cannot signup new account with invalid firstname")
	public void TC_005_SignUpWithInvalidFirstname() throws InterruptedException, IOException {
		/* users.error.firstname_format=Your first name must be 1 to 30 characters long
		*/
		PageGetStart.goToSignUpPage();
		PageSignUp.registerAccount(StringHelper.randomString("", 6),"AA", StringHelper.randomString("", 6), email, email, password, password);
		PageSignUp.confirmSignupErrorDialog();
		String message;
		
		Log.info("short invalid firstname test");
		String[] invalidFirstnames = { "",
									StringHelper.randomString("A", 30), // 31 chars
									StringHelper.randomString("A", 31), // 32 chars
									StringHelper.randomString("", 53)};
	
		
		// check too short and too long firstname
		for (String name : invalidFirstnames) {
			
			PageSignUp.getFirstnameTextbox().sendKeys(name);
			PageSignUp.getSignUpButton().click();
			message = PageSignUp.getUsernameConstraintMessage().getText();
			PageSignUp.confirmSignupErrorDialog();
			Assert.assertEquals(message, PageSignUp.msg_WARN_FIRSTNAME_LENGTH,
					"Error: actual result is " + message);
		}
	}
	
	@Test(priority = 12, description = "TC006: Verify that user cannot signup new account with invalid lastname")
	public void TC_006_SignUpWithInvalidLastname() throws InterruptedException, IOException {
		/* users.error.lasstname_format=Your last name must be 1 to 30 characters long
		*/
		PageGetStart.goToSignUpPage();
		PageSignUp.registerAccount(StringHelper.randomString("", 6),"AA", StringHelper.randomString("", 6), email, email, password, password);
		PageSignUp.confirmSignupErrorDialog();
		String message;
		
		Log.info("short invalid laststname test");
		String[] invalidLastnames = { "",
									StringHelper.randomString("A", 30), // 31 chars
									StringHelper.randomString("A", 31), // 32 chars
									StringHelper.randomString("", 53)};
	
		
		// check too short and too long lastname
		for (String name : invalidLastnames) {
			
			PageSignUp.getLastnameTextbox().sendKeys(name);
			PageSignUp.getSignUpButton().click();
			message = PageSignUp.getUsernameConstraintMessage().getText();
			PageSignUp.confirmSignupErrorDialog();
			Assert.assertEquals(message, PageSignUp.msg_WARN_LASTNAME_LENGTH,
					"Error: actual result is " + message);
		}
	}
	
	@AfterMethod
	public void cleanup() {

	}
}