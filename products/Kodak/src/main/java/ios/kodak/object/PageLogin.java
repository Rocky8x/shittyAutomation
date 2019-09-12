package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.TimeHelper;
import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageLogin extends PageBase{
	public static final String MSG_WRONG_PASSWORD_LOGIN = "You entered an incorrect username or password. Check your sign in details and try again. Take note your account will be locked after next \\d failed attempt\\(s\\). Then you have to wait for 30 minutes to try again.";
	public static final String MSG_USER_NOT_EXISTED = "Looks like you don't have an account with us, do you want to register a new account now?";
	public static final String MSG_NOT_ACTIVE_USER = "Your email address is not verified yet. Account may expire after 30 day(s). Refer to your email to activate the account";
	public static final String MSG_NOT_ACTIVE_ACCOUNT = "*Please check your spam/bulk/clutter folder if you haven't received your activation email within 10 minutes. Please check your email to complete the verification process. Alternatively, would you like to resend the verification email?";
	
	/*
	 * ELEMENT
	 */
	
	public static Element getUsernameTextBox() {
		String usernameTxt = "//XCUIElementTypeImage[@name='ic_user_icon']/following-sibling::XCUIElementTypeTextField";
		return new Element(By.xpath(usernameTxt)).setDescription("Username text box");
	}
	
	public static Element getPasswordTextBox() {
		String pwdTxt = "//XCUIElementTypeImage[@name= 'ic_password_icon']/following-sibling::XCUIElementTypeSecureTextField";
		return new Element(By.xpath(pwdTxt)).setDescription("Password text box");
	}
	
	public static Element getLoginButton() {
		String loginBtn = "//XCUIElementTypeButton[@name='LOGIN']";
		return new Element(By.xpath(loginBtn)).setDescription("LOGIN buton");
	}
	
	public static Element getLoginErrorMsg() {
		String errMsg = "//XCUIElementTypeStaticText[@name='Login Error']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(errMsg)).setDescription("Login Error message");
	}
	
	public static Element getFacebookIcon() {
		String fbIcon = "name=='ic facebook login' AND visible==true";
		return new Element(MobileBy.iOSNsPredicateString(fbIcon)).setDescription("Facebook Icon");
	}
	
	public static Element getGoogleIcon() {
		String ggIcon = "name=='ic google login' AND visible==true";
		return new Element(MobileBy.iOSNsPredicateString(ggIcon)).setDescription("Google Icon");
	}
	
	public static Element getFacebookUsername() {
		String fbUser = "//XCUIElementTypeOther[@name='main']/XCUIElementTypeTextField";
		return new Element(By.xpath(fbUser)).setDescription("Facebook user name");
	}
	
	public static Element getFacebookPassword() {
		String fbPwd = "//XCUIElementTypeOther[@name='main']/XCUIElementTypeSecureTextField";
		return new Element(By.xpath(fbPwd)).setDescription("Facebook password");
	}
	
	public static Element getFacebookLoginBtn() {
		String loginBtn = "//XCUIElementTypeOther[@name='main']/XCUIElementTypeButton";
		return new Element(By.xpath(loginBtn)).setDescription("Login Facebook button");
	}
	
	public static Element getExistedGoogleAccount(String email) {
		String ggAcc = "//XCUIElementTypeStaticText[@name='%s']";
		return new Element(By.xpath(String.format(ggAcc, email))).setDescription("Google account with email: " + email);
	}
	
	public static Element getUserAnotherAccount() {
		String anotherAcc = "//XCUIElementTypeStaticText[@name='Use another account']";
		return new Element(By.xpath(anotherAcc)).setDescription("Use another account");
	}
	
	public static Element getEmailOrPhoneTextField() {
		String emailField = "//XCUIElementTypeOther[@name='Sign in – Google accounts']//XCUIElementTypeTextField";
		return new Element(By.xpath(emailField)).setDescription("Email or phone number");
	}

	public static Element getGooglePasswordField() {
		String pwd = "//XCUIElementTypeOther[@name='Sign in – Google accounts']//XCUIElementTypeSecureTextField";
		return new Element(By.xpath(pwd)).setDescription("Password");
	}
	
	public static Element getRegisterNewAccountMsg() {
		String msg = "//XCUIElementTypeStaticText[@visible='true']";
		return new Element(By.xpath(msg)).setDescription("Register new account message");
	}
	
	public static Element getCountinueButton() {
		String xpath = "(//XCUIElementTypeOther[@name='main']//XCUIElementTypeButton)[1]";
		return new Element(By.xpath(xpath)).setDescription("Continue button");
	}
	
	/*
	 * ACTION
	 */
	/**
	 * Login Kodak application with username and password
	 * @param username
	 * @param password
	 * @return
	 */
	public static void loginApp(String username, String password) {
		if(getUsernameTextBox().getWebElement() != null) {
			if(PageBase.getOKButton().getWebElement() != null) {
				PageBase.getOKButton().getWebElement().click();
			}
			getUsernameTextBox().sendKeys(username);
			getPasswordTextBox().sendKeys(password);
			getLoginButton().click();
		}
	}
	
	public static String getLoginErrorMessageText() {
		return getLoginErrorMsg().getText();
	}
	
	public static void clickOnFacebookIcon() {
		getFacebookIcon().click();
	}
	
	public static void clickOnGoogleIcon() {
		getGoogleIcon().click();
	}
	
	public static void loginWithFacebook(String username, String password) {
		getFacebookUsername().inputValue(username);
		getFacebookPassword().inputValue(password);
		getFacebookLoginBtn().click();
	}
	
	
	public static void continueUsingGoogleLoginIfAsk() {
		if(getContinueButton().getWebElement() != null) {
			getContinueButton().click();
		}
	}
	
	public static void loginWithGoogleAccount(String email, String password) {
		clickOnGoogleIcon();
		continueUsingGoogleLoginIfAsk();
		// Account login before
		if (getExistedGoogleAccount(email).getWebElement() != null) {
			getExistedGoogleAccount(email).click();
			if(getGooglePasswordField().getWebElement() != null) {
				getGooglePasswordField().sendKeys(password);
			}
		}else {
			// New account login
			getUserAnotherAccount().click();
			getEmailOrPhoneTextField().sendKeys(email);
			clickNextButton();
			getGooglePasswordField().sendKeys(password);
			clickNextButton();
		}
	}
	
	public static String getRegisterNewAccountMessage() {
		return getRegisterNewAccountMsg().getText();
	}
	
	public static void clickFabookContinueLogin() {
		getCountinueButton().click();
		TimeHelper.sleep(5000);
	}
	
	public static boolean verifyUnActiveAccount() {
		return false;
	}
}