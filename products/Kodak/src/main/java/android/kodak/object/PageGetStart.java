package android.kodak.object;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import com.cinatic.element.Element;
import com.cinatic.log.Log;

import mobile.kodak.base.TestBaseAndroid;

/**
 * @author Thach Nguyen
 * Intro page has 2 buttons Sign In and Sign up
 * with demo and some commercial 
 */
public class PageGetStart extends PageBase{
	/***ELEMENT***/
	public static Element getSignUpBtn(){
		String id = "textview_introduction_register";
		return new Element(By.id(id), 5).setDescription("Get Start: Sign up button"); 
	}
	
	
	public static Element getSigninBtn(){ 
		String id = "textview_introduction_login";
		return new Element(By.id(id), 5).setDescription("Get Start: Signin button"); 
	}
	
	/***ACTION***/
	public static void checkAndSignin(String username, String passwd) {
		
		if (TestBaseAndroid.currentLoginUser.equals(username) && PageDashboard.isDisplayed()) {
			Log.info("Logged in user : " + TestBaseAndroid.currentLoginUser);
			Log.info("User to login: " + username);
			return ;
		}
		
		if (PageDashboard.isDisplayed()) {
			PageDashboard.handlePermissionsAndHintsWhenPageOpen();
			/*
			*  if Dashboard is showing, means that app already login,
			* check if it's the same user, if not, log out and login to desired account
			*/
			// Open menu and check get current username and check
			PageDashboard.getMenuButton().click();
			// current username is match, return
			if (PageDashboard.getAccountTextViewInMenu().getText().equals(username)) {
				PageDashboard.getHomeIcon().click();
				return;
			}
			// not match, goto PageLogin.
			PageHomeMenu.getSignOutMenuItem().click();
		} else if (isDisplayed()) {
			// if PageGetStart is displayed, click Signin button to go to PageLogin
			getSigninBtn().click();
		} 	/*
			* if already at login page then proceed to login
			* nothing else to do, just list here to know that
			* the case is covered
			*/ 
		
		// login with provide username & passwd
		PageLogin.loginApp(username, passwd);
		TestBaseAndroid.currentLoginUser = username;
		return;
	}
	
	public static void goToSigninPage() {
		if (getSigninBtn().getWebElement() != null) {
			getSigninBtn().click();			
		} else if ( PageDashboard.getMenuButton().getWebElement() != null) {
			PageDashboard.signOut();
		} else if (PageLogin.getLoginBackButton().getWebElement() == null) {
			getSigninBtn().click();
		}
	}
	
	public static PageSignUp goToSignUpPage() {
		if (getSignUpBtn().getWebElement() != null) {
			getSignUpBtn().click();			
		} else if (PageDashboard.getMenuButton().getWebElement() != null) {
			PageDashboard.signOut();
			PageLogin.getLoginBackButton().click();
			getSignUpBtn().click();
		} else if (PageLogin.getLoginBackButton().getWebElement() == null) {
			getSignUpBtn().click();
		}
		return new PageSignUp();
	}
		
	public static boolean isDisplayed() {
		if (getSigninBtn().getWebElement() != null) return true;
		return false;
	}
	
	public static OfflineModeDialog inConnectionErrorDialog() {
		return new OfflineModeDialog();
	}
	
	public static class OfflineModeDialog {
		static int timeout = 180;
		static final String TITLE = "Connection Error";
//		final String MESSAGE = "There are some problems while contacting to the server. Please try again later.";
		static final String MESSAGE = "There are some problems while contacting to the server. Click here for detail. \r\n" + 
				"Switch to offline mode?";
		public static Element getTitle() {
			String id = "text_dialog_title";
			return new Element(By.id(id),timeout).setDescription("Connection error dialog title");
		}
		
		public static Element getMessage() {
			String id = "text_dialog_msg";
			return new Element(By.id(id),timeout).setDescription("Connection error Message");
		}
		
		public static Element getGoOfflineBtn() {
			String xpath = "//android.widget.Button[@text='Go Offline']";
			return new Element(By.xpath(xpath),timeout).setDescription("Go Offline button");
		}
		
		public static Element getCancelButon() {
			String xpath = "//android.widget.Button[@text='Cancel']";
			return new Element(By.xpath(xpath),timeout).setDescription("Cancel button");
		}
		
		public static void verifyDialogTitleAndMessage() {
			assertEquals(getTitle().getText(), TITLE);
			assertEquals(getMessage().getText(), MESSAGE);
		}
	}
}
