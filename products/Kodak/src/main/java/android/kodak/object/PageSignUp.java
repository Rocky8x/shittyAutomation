package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;
import com.cinatic.log.Log;

import mobile.kodak.base.TestBaseAndroid;

public class PageSignUp extends PageBase{
	/*** Message ***/
	public static final String msg_WRONG_EMAIL_FORMAT = "Invalid email format. Example: aaa@bbb.ccc";
	public static final String msg_PASSWD_STRENGTH = "Password must be between 8–30 characters, including at least one upper-, and lower-case letter, digits.";
	public static final String msg_PASSWD_NEED_LOWWER_CASE_CHAR= "Password must have one or more lowercase letters.";
	public static final String msg_PASSWD_NEED_UPPER_CASE_CHAR= "Password must have one or more uppercase letters.";
	public static final String msg_PASSWD_NEED_DIGIT= "Password must have one or more digits.";
	
	public static final String msg_WARN_USERNAME_LENGTH = "Username must be 6 to 30 characters.";
	public static final String msg_WARN_FIRSTNAME_LENGTH = "First name must be 1 to 30 characters.";
	public static final String msg_WARN_LASTNAME_LENGTH = "Last name must be 1 to 30 characters.";


	public static final String msg_WARN_USERNAME_SPECIAL_CHARS= "Username should not contain special characters except -.@_";
	
	public static final String msg_PLZ_ACCEPT_TERM = "Please accept to our Terms of Service and Privacy Policy";
	public static final String msg_REGISTRATION_EMAIL_NOT_DELIVERED = "Registration failed. Email is not delivered. Check your email to ensure it is valid.";
	
	/***ELEMENT***/
	public static Element getUsernameTextbox(){ 
		String id = "username_register";
		return new Element(By.id(id)).setDescription("Username textbox"); }
	
	public static Element getFirstnameTextbox(){ 
		String id = "_first_name_register";
		return new Element(By.id(id)).setDescription("Firstname textbox"); }
	
	public static Element getLastnameTextbox(){ 
		String id = "last_name_register";
		return new Element(By.id(id)).setDescription("Lastname textbox"); }
	
	private static String lblConstraintHintId = "text_dialog_msg";

	public static Element getUsernameConstraintMessage() {
		return new Element(By.id(lblConstraintHintId)).setDescription("Username constraint message");
	}
	
	public static Element getEmailTextbox() { 
		String id = "email_register";
		return new Element(By.id(id)).setDescription("Email textbox"); }
	
	public static Element getConfirmEmailTextbox() { 
		String id = "email_confirm_register";
		return new Element(By.id(id)).setDescription("Confirm email textbox"); }
	
	public static Element getEmailConstraintMessage() { 
		String lblEmail = lblConstraintHintId;
		return new Element(By.id(lblEmail)).setDescription("Email constraint message"); }
	
	public static Element getPasswordTextbox() { 
		String id = "password_register";
		return new Element(By.id(id)).setDescription("Password textbox"); }
	
	public static Element getPasswordConstraintMessage() { 
		String lblPassword = lblConstraintHintId;
		return new Element(By.id(lblPassword)).setDescription("Password constraint message"); }
	
	public static Element getConfirmPasswordTextbox() { 
		String id = "confirm_password_register";
		return new Element(By.id(id)).setDescription("Confirm password textbox"); }
	
	public static Element getAgreeCheckbox() { 
		String id = "textview_register_agree";
		return new Element(By.id(id)).setDescription("Agree term and condition checkbox"); }
	
	public static Element getSignUpButton() { 
		String id = "textview_register_create";
		return new Element(By.id(id),"SIGN UP").setDescription("Sign up button"); }
		
	public static Element getBackButton() { 
		String id = "img_back";
		return new Element(By.id(id)).setDescription("Back button"); }
	
	//MARKETING CONSENT
	public static Element getYesButton() { 
		String btnYes = "//android.widget.Button[@text='YES']";
		return new Element(By.xpath(btnYes)).setDescription("Yes button"); }
	
	public static Element getNoButton() { 
		String btnNo = "//android.widget.Button[@text='NO']";
		return new Element(By.xpath(btnNo)).setDescription("No button"); }
	
	// "Verify Your Account" page with 2 button
	public static Element getVerifyEmailLaterBtn() {
		String id = "btn_proceed";
		return new Element(By.xpath(id)).setDescription("\"Verify email later\" button");
	}
	
	public static void fillData(String name, String firstname, String lastname, String email,String confirmEmail, String password, String confirmPasswd){
		Log.info(String.format("FILL DATA: %s %s %s %s %s", name, email, confirmEmail, password, confirmPasswd));
		getUsernameTextbox().sendKeys(name);
		getFirstnameTextbox().sendKeys(name);
		getLastnameTextbox().sendKeys(name);
		getEmailTextbox().sendKeys(email);
		getConfirmEmailTextbox().sendKeys(email);
		getPasswordTextbox().sendKeys(password);
		getConfirmPasswordTextbox().sendKeys(password);		
	}

	public static void registerAccount(String name, String firstname, String lastname, String email, String confirmEmail, String password, String confirmPasswd) {
		Log.info(String.format("REGISTER ACCOUNT: %s %s %s", name, email, password));
		fillData(name, firstname, lastname, email, confirmEmail, password, confirmPasswd);
		getAgreeCheckbox().setValue(true);
		getSignUpButton().click();
		TestBaseAndroid.currentLoginUser = name;
		if (getNoButton().getWebElement() != null)
			getNoButton().click();
	}
	
	public static PageGetStart backToGetStartedPage() {
		getBackButton().click();
		return new PageGetStart();
	}
	
	public static void confirmSignupErrorDialog() {
		PageBase.getOkButton("Sign up fail OK button").click();
	}
	
	public static void exitPage() {
		getBackButton().click();
	}
}