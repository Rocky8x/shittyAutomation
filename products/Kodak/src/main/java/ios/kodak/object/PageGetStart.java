package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageGetStart extends PageBase{
	public static boolean flagAutoTutorialClosed = false;
	/*
	 * ELEMENT
	 */
	
	public static Element getSignInButton() {
		String signInBtn = "//XCUIElementTypeButton[@name='LOGIN']";
		return new Element(By.xpath(signInBtn)).setDescription("Login button");
	}
	
	public static Element getSignUpButton() {
		String signUpBtn = "//XCUIElementTypeButton[@name='SIGN UP']";
		return new Element(By.xpath(signUpBtn)).setDescription("Sign Up button");
	}
	
	/*
	 * ACTION
	 */
	
	public static void clickOnSignInButton() {
		getSignInButton().click();
	}
	
	public static boolean isDisplayed() {
		return getSignInButton().getWebElement() != null ? true : false;
	}
	
	public static void checkAndSignin(String username, String password) {
		if(PageDashboard.isDisplayed()) {
			PageDashboard.clickOnHomeMenuButton();
			if (PageDashboard.getAccountTextViewInMenu().getText().equals(username)) {
				PageDashboard.clickHomeButton();
				return;
			}else {
				/*
				 * User login not match with current user --> sign out
				 */
				PageDashboard.signOut();
			}
			
		}else if(isDisplayed()) {
			// If get start page is displayed, click on sign in button
			clickOnSignInButton();
		}
		// Check error when login with inactive account
		else if(getNOButton().getWebElement() != null)
			clickNoButton();
		else if(getOKButton().getWebElement() != null)
			clickOkButton();
		// Login with provide username and password
		PageLogin.loginApp(username, password);
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		PageDashboard.closeSubcriptionPopup();
		return;
	}
	/**
	 * Navigate to Sign in page
	 */
	public static void gotoSigninPage() {
		// User stay in Dash board page
		if (PageDashboard.getMenuButton().getWebElement() != null) {
			PageDashboard.getMenuButton().click();
			PageDashboard.signOut();
		}
		// User stay in Login page
		else if (PageLogin.getFacebookIcon().getWebElement() != null) {
			return;
		}
		// User stay in Get start page
		else if(getSignUpButton().getWebElement() != null) {
			getSignInButton().click();
		}
		// Check error when login with inactive account
		else if(getNOButton().getWebElement() != null)
			clickNoButton();
		else if(getOKButton().getWebElement() != null)
			clickOkButton();
	}
	
	/**
	 * Navigate to Sign up page
	 */
	public static void gotoSignUpPage() {
		// User stay in Dash board page
		if (PageDashboard.getMenuButton().getWebElement() != null) {
			PageDashboard.getMenuButton().click();
			PageDashboard.signOut();
			PageBase.getBackButton().click();
			getSignUpButton().click();
		}
		// User stay in Login page
		else if (PageLogin.getFacebookIcon().getWebElement() != null) {
			PageBase.getBackButton().click();
			getSignUpButton().click();
		}
		// User stay in Get start page
		else if(getSignUpButton().getWebElement() != null) {
			getSignUpButton().click();
		}
		// Check error when login with inactive account
		else if(getNOButton().getWebElement() != null)
			clickNoButton();
		else if(getOKButton().getWebElement() != null)
			clickOkButton();
	}
}


