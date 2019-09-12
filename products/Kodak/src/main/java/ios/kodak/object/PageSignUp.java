package ios.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

import io.appium.java_client.MobileBy;

public class PageSignUp extends PageBase{
	/*
	 * MESSAGE
	 */
	public static final String msg_WRONG_EMAIL_FORMAT = "Invalid email format. Example: aaa@bbb.ccc";
	public static final String msg_PASSWD_STRENGTH = "Password must be between 8-15 characters, including at least one upper-, and lower-case letter, digits.";
	public static final String msg_WARN_LENGTH_USERNAME = "Username must be 6 to 30 characters";
	public static final String msg_WARN_SPECAIL_CHARACTER_USERNAME = "Username should not contain special characters except -.@_";
	
	public static final String msg_WARN_FIRSTNAME_STRENGTH = "First name must be 1 to 30 characters";
	public static final String msg_WARN_LASTNAME_STRENGTH = "Last name must be 1 to 30 characters";
	public static final String msg_PLZ_ACCEPT_TERM = "Please accept to our Terms of Service and Privacy Policy";
	public static final String msg_REGISTRATION_EMAIL_NOT_DELIVERED = "Registration failed. Email is not delivered. Check your email to ensure it is valid.";
	
	/*
	 * ELEMENT
	 */
	
	public static Element getUsernameTextField() {
		String username = "**/XCUIElementTypeOther[$type=='XCUIElementTypeImage' AND name=='ic_user_icon'$]/XCUIElementTypeTextField[1]";
		return new Element(MobileBy.iOSClassChain(username)).setDescription("Username field");
	}
	
	public static Element getFirstNameTextField() {
		String username = "**/XCUIElementTypeOther[$type=='XCUIElementTypeImage' AND name=='ic_user_icon'$]/XCUIElementTypeTextField[2]";
		return new Element(MobileBy.iOSClassChain(username)).setDescription("First name field");
	}
	
	public static Element getLastNameTextField() {
		String username = "**/XCUIElementTypeOther[$type=='XCUIElementTypeImage' AND name=='ic_user_icon'$]/XCUIElementTypeTextField[3]";
		return new Element(MobileBy.iOSClassChain(username)).setDescription("Last name field");
	}
	
	public static List<WebElement> getEmailTextField() {
		String email = "//XCUIElementTypeImage[@name='ic_email_icon']/following-sibling::XCUIElementTypeTextField";
		return DriverManager.getDriver().findElements(By.xpath(email));
	}
	
	public static Element getReEmailTextField() {
		String email = "//XCUIElementTypeTextField[@value='Re-enter email']";
		return new Element(By.xpath(email)).setDescription("Re-enter email field");
	}
	
	public static Element getPasswordField() {
		String pwd = "//XCUIElementTypeSecureTextField[@value='Password']";
		return new Element(By.xpath(pwd)).setDescription("Password field");
	}
	
	public static Element getConfirmPassword() {
		String confirmPwd = "//XCUIElementTypeSecureTextField[@value='Reconfirm Password']";
		return new Element(By.xpath(confirmPwd)).setDescription("Confirm password field");
	}
	
	public static Element getAgreeRadio() {
		String agree = "(//XCUIElementTypeImage[@name='ic_radio_btn_uncheck'])[1]";
		return new Element(By.xpath(agree)).setDescription("Agree terms and service radio button");
	}
	
	public static Element getSignUpButton() {
		String signUp = "//XCUIElementTypeButton[@name='SIGN UP']";
		return new Element(By.xpath(signUp), "SIGN UP").setDescription("Sign Up button");
	}
	
	public static Element getCheckEmailNowButton() {
		String checkMail = "//XCUIElementTypeButton[@name='CHECK EMAIL NOW']";
		return new Element(By.xpath(checkMail)).setDescription("Check Email Now");
	}
	
	public static Element getConfirmLaterButton() {
		String confirm = "//XCUIElementTypeButton[contains(@name,'CONFIRM EMAIL LATER')]";
		return new Element(By.xpath(confirm)).setDescription("Confirm email later button");
	}
	
	public static Element getSignUpErrorMsg() {
		String msg = "//XCUIElementTypeButton[@name='OK']/preceding-sibling::XCUIElementTypeTextView";
		return new Element(By.xpath(msg)).setDescription("Error message");
	}
	
	/*
	 * ACTION
	 */
	
	public static void registerNewAccount(String username, String firstName, String lastName, String email, String password) {
		Log.info(String.format("Register account with data: %s \r\n %s, %s, %s, %s", username, firstName, lastName, email, email, password, password));
		getUsernameTextField().sendKeys(username);
		getFirstNameTextField().sendKeys(firstName);
		getLastNameTextField().sendKeys(lastName);
		// Input email and confirm email
		for(WebElement ele : getEmailTextField()) {
			Log.debug(String.format("send \"%s\" to: %s", email, "Email"));
			ele.clear();
			ele.sendKeys(email);
			DriverManager.getDriver().hideKeyboard();
		}
		getPasswordField().sendKeys(password);
		getConfirmPassword().sendKeys(password);
		getAgreeRadio().click();
		getSignUpButton().click();
	}
	
	public static void clickCheckEmailNowButton() {
		getCheckEmailNowButton().click();
	}
	
	public static void clickConfirmEmailLaterButton() {
		getConfirmLaterButton().click();
	}
	
	public static String getSignUpErrorMessage() {
		return getSignUpErrorMsg().getText();
	}
	
	
}
