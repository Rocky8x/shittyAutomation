package android.kodak.object;
import org.openqa.selenium.By;

import com.cinatic.TimeHelper;
import com.cinatic.element.Element;

import io.qameta.allure.Step;
import mobile.kodak.base.TestBaseAndroid;

public class PageLogin extends PageBase{
	public static final String msg_WRONG_PASSWORD = "You entered an incorrect username or password. Check your sign in details and try again. Take note your account will be locked after next \\d failed attempt\\(s\\). Then you have to wait for 30 minutes to try again.";
	public static final String MSG_USER_NOT_EXISTED = "Looks like you don't have an account with us, do you want to register a new account now?";
	
	/***ELEMENT***/
	public static Element getSignInButton(){ 
		String id= "button_login";
		return new Element(By.id(id)).setDescription("Sign in button"); 
	}
	
	public static Element getLoginBackButton(){ 
		String id = "imageview_login_back";
		return new Element(By.id(id), 3).setDescription("Back button in login page");
	}
	
	public static Element getUserNameTextBox(){ 
		String id = "username_login";
		return new Element(By.id(id)).setDescription("Username field"); 
	}
	
	public static Element getPasswordTextBox(){ 
		String id = "password_login";
		return new Element(By.id(id)).setDescription("Password field"); 
	}
	
	public static Element getPasswordRevealButton() {
		String id = "ic_show_password";
		return new Element(By.id(id)).setDescription("Reveal password button");
	}
	
	public static Element getLoginMsg() { 
		String id = "text_dialog_msg";
		return new Element(By.id(id)).setDescription("Login message dialog"); 
	};
	
	public static Element getGoogleIcon() {
		String id = "img_google_login";
		return new Element(By.id(id)).setDescription("Login with Google button");
	}
	
	public static Element getFacebookIcon() {
		String id = "img_facebook_login";
		return new Element(By.id(id)).setDescription("Login with Facebook button");
	}
	/**
	 * Panel contains list account login before (accounts were cached)
	 * @return
	 */
	public static Element getGoogleAccPanel() {
		String ggPanel = "//android.widget.TextView[@resource-id='com.google.android.gms:id/main_title']";
		return new Element(By.xpath(ggPanel)).setDescription("Login with google panel");
	}
	
	public static Element getUseAnotherAccountLink() {
		String anotherLink = "//android.widget.TextView[@resource-id='com.google.android.gms:id/add_account_chip_title']";
		return new Element(By.xpath(anotherLink), 10).setDescription("Use another account button");
	}
	
	public static Element getGoogleLoginForm() {
		String ggLoginForm = "//android.view.View[@resource-id='view_container']";
		return new Element(By.xpath(ggLoginForm)).setDescription("Google login form");
	}
	/**
	 * Email or Phone google text box
	 * @return
	 */
	public static Element getEmailOrPhoneTextBox() {
		String textBox = "//android.widget.EditText[@resource-id='identifierId']";
		return new Element(By.xpath(textBox)).setDescription("Google login: email/phone");
	}
	/**
	 * Next button in Input email google
	 * @return
	 */
	public static Element getGGEmailNextButton() {
		String nextBtn = "//android.widget.Button[@resource-id='identifierNext']";
		return new Element(By.xpath(nextBtn)).setDescription("Google login: next button");
	}
	/**
	 * Password google text box
	 * @return
	 */
	public static Element getGGPasswordTextBox() {
		String pwdTextEdit = "//android.view.View[@resource-id='password']//android.widget.EditText";
		return new Element(By.xpath(pwdTextEdit)).setDescription("Google login: password textbox");
	}
	/**
	 * Next button in Input password google
	 * @return
	 */
	public static Element getGGPwdNextButton() {
		String nextBtn = "//android.widget.Button[@resource-id='passwordNext']";
		return new Element(By.xpath(nextBtn)).setDescription("Google login: Next button @ password page");
	}
	
	public static Element getExistedGoogleAccount(String email) {
		String emailAccount = "//android.widget.TextView[@resource-id='com.google.android.gms:id/account_name' and @text='%s']";
		return new Element(By.xpath(String.format(emailAccount, email)), 10).setDescription(String.format("Exiting google email: %s", email));
	}
	
	/**
	 * In the first time login with google, Terms of service and privacy policy page display
	 * Click I agree button to navigate to Google service page
	 * @return
	 */
	public static Element getIAgreeButton() {
		String agreeBtn = "//android.widget.Button[@resource-id='signinconsentNext']";
		return new Element(By.xpath(agreeBtn)).setDescription("Google login: aggree ToS button");
	}
	
	public static Element getGGServiceTitle() {
		String title = "//android.widget.TextView[@resource-id='com.google.android.gms:id/suw_layout_title']";
		return new Element(By.xpath(title)).setDescription("Google login: title");
	}
	
	public static Element getGGAcceptButton() {
		String acceptBtn = "//android.widget.Button[@resource-id='com.google.android.gms:id/next_button']";
		return new Element(By.xpath(acceptBtn));
	}
	
	public static Element getEmailFbTextEdit() {
		String emailFb = "//android.widget.EditText[@resource-id='m_login_email']";
		return new Element(By.xpath(emailFb)).setDescription("FB login: email textbox");
	}
	
	public static Element getPwdFbTextEdit() {
		String pwdFb = "//android.widget.EditText[@resource-id='m_login_password']";
		return new Element(By.xpath(pwdFb)).setDescription("FB login: password textbox");
	}
	
	public static Element getFbLoginButton() {
		String loginBtn = "//android.widget.Button[@resource-id='u_0_5']";
		return new Element(By.xpath(loginBtn)).setDescription("FB login button");
	}
	/**
	 * Continue button from second time login kodak app with facebook
	 * @return
	 */
	public static Element getFbContinueButton() {
		String continueBtn = "//android.widget.Button[@text='Continue']";
		return new Element(By.xpath(continueBtn)).setDescription("FB login: continue button");
	}
	/**
	 * Continue button with the first time login kodak app with facebook
	 * @return
	 */
	public static Element getFbContinueWithName() {
		String continueBtn = "//android.widget.Button[@resource-id='u_0_9']";
		return new Element(By.xpath(continueBtn)).setDescription("FB login: continue with name");
	}
	
	/***ACTION***/
	@Step("Login app")
	public static void loginApp(String username, String password) {
		getUserNameTextBox().sendKeys(username);
		getPasswordTextBox().sendKeys(password);
		getSignInButton().click();
		TimeHelper.sleep(5 * 1000);
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
	}
	
	public static void clickGoogleIcon() {
		getGoogleIcon().click();
	}
	
	public static void clickFacebookIcon() {
		getFacebookIcon().click();
	}
	
	/**
	 * This situation have 3 case:
	 * 1. There is no logged in Gmail--> Click google icon --> login Gmail form
	 * 2. Have some Gmail logged in, but not match with gmail variable --> click google icon --> click 'Use another account' --> login Gmail form 
	 * 3. Have Gmail account login before and contains gmail variable --> click google icon -- click existed gmail
	 * @param email
	 * @param password
	 */
	public static void loginWithGoogleAccount(String email, String password) {
		clickGoogleIcon();
		if(getGoogleAccPanel().getWebElement() != null) {
			// Account existed in
			if(verifyAccountExisted(email)) {
				getExistedGoogleAccount(email).click();
			}else {
				// Some account login before but not contains account define
				clickUseAnotherAccount();
				inputDataIntoGoogleLoginForm(email, password);
				getIAgreeButton().click();
			}
		}else {
			// There is no account login before --> move to login form
			inputDataIntoGoogleLoginForm(email, password);
			getIAgreeButton().click();
			if(getGGServiceTitle().getWebElement() != null) {
				clickGoogleAcceptButton();
			}
		}
		TestBaseAndroid.currentLoginUser = email;
	}
	/**
	 * Swipe to the bottom and click on accept button
	 */
	public static void clickGoogleAcceptButton() {
		int count = 0;
		while(count < 5 && getGGAcceptButton().getWebElement() == null) {
			swipeTopToBottom();
			count++;
		}
		if(getGGAcceptButton().getWebElement() != null) {
			// Issue: Accept button must be click 2 time
			getGGAcceptButton().click();
			getGGAcceptButton().click();
		}
	}
	/**
	 * Input email and password to login gmail
	 * @param email
	 * @param password
	 */
	public static void inputDataIntoGoogleLoginForm(String email, String password) {
		if(getGoogleLoginForm().getWebElement() != null) {
			getEmailOrPhoneTextBox().sendKeys(email);
			getGGEmailNextButton().click();
			getGGPasswordTextBox().sendKeys(password);
			getGGPwdNextButton().click();
		}
	}
	
	/**
	 * Swipe until 'Use another account' link appear --> click this link
	 */
	public static void clickUseAnotherAccount() {
		int count = 0;
		while(count < 10 && getUseAnotherAccountLink().getWebElement() == null) {
			swipeTopToBottom();
			count++;
		}
		if(getUseAnotherAccountLink().getWebElement() != null) {
			getUseAnotherAccountLink().click();
		}
	}
	/**
	 * Verify gmail account login before existed
	 * @param email
	 * @return
	 */
	public static boolean verifyAccountExisted(String email) {
		boolean rs = false;
		int count = 1;
		while(count <= 10 && getExistedGoogleAccount(email).getWebElement() == null && getUseAnotherAccountLink().getWebElement() == null) {
			swipeTopToBottom();
			count++;
		}
		if (getExistedGoogleAccount(email).getWebElement() != null) {
			rs = true;
		}
		return rs;
	}
	/**
	 * Get Message of login popup
	 * @return
	 */
	public static String getLoginMessage() {
		if(getLoginMsg().getWebElement() != null) {
			return getLoginMsg().getText();
		}
		return "Cannot get message, please check login message popup.";
	}
	
	/**
	 * Login Kodak app using Facebook account
	 * @param email
	 * @param password
	 */
	public static void loginWithFacebookAccount(String email, String password) {
		clickFacebookIcon();
		getEmailFbTextEdit().sendKeys(email);
		getPwdFbTextEdit().sendKeys(password);
		getFbLoginButton().click();
		clickFbContinueButton();
	}
	/**
	 * Click on continue button
	 * If login with the first time, click on "Continue with [Account name]"
	 * Else click on continue button
	 */
	public static void clickFbContinueButton() {
		if(getFbContinueButton().getWebElement() != null) {
			getFbContinueButton().click();
		}else {
			getFbContinueWithName().click();
		}
	}
	
	/**
	 * Thach Nguyen
	 * check if the page is display base on sign in button appearance.
	 * @return boolean
	 */
	public static boolean isDisplayed() {
		if (getSignInButton().getWebElement() != null ) return true;
		return false;
	}
	
	public static class LoginErrorDialog{
		
		public static final String TITLE = "Login Error";
		public static final String MSG_VERIFICATION = "*Please check your spam/bulk/clutter folder if you haven't received your activation email within 10 minutes.\n" + 
				" Please check your email to complete the verification process.\n" + 
				" Alternatively, would you like to resend the verification email?";
		public static Element getTitle() {
			String id = "text_dialog_title";
			return new Element(By.id(id)).setDescription("Login error dialog title");
		}
		
		public static Element getCheckVerificationEmailMessage() {
			String id = "text_dialog_msg";
			return new Element(By.id(id)).setDescription("Login error message content");
		}
		
		public static Element getYesButton() {
			String xpath = "//android.widget.Button[@text='Yes']";
			return new Element(By.xpath(xpath)).setDescription("Yes button");
		}
		
		public static Element getNoButton() {
			String xpath = "//android.widget.Button[@text='No']";
			return new Element(By.xpath(xpath)).setDescription("No button");
		}
	}
	
	public static LoginErrorDialog getLoginErrorDialog() {
		return new LoginErrorDialog();
	}
}
