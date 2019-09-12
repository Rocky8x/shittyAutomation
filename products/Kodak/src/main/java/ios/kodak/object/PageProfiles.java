package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageProfiles extends PageBase{
	/*
	 * ELEMENT
	 */
	public static Element getChangePasswordTextField() {
		String changePwd = "//XCUIElementTypeStaticText[@name='Change Password']";
		return new Element(By.xpath(changePwd)).setDescription("Change Password");
	}
	
	public static Element getCurrentPasswordField() {
		String currentPwd = "//XCUIElementTypeSecureTextField[@value='Current Password']";
		return new Element(By.xpath(currentPwd)).setDescription("Current Password");
	}
	
	public static Element getNewPasswordField() {
		String newPwd = "//XCUIElementTypeSecureTextField[@value='New Password']";
		return new Element(By.xpath(newPwd)).setDescription("New Password");
	}
	
	public static Element getConfirmPasswordField() {
		String confirmPwd = "//XCUIElementTypeSecureTextField[@value='Confirm Password']";
		return new Element(By.xpath(confirmPwd)).setDescription("Confirm Password");
	}
	
	public static Element getChangePasswordMsg() {
		String msg = "//XCUIElementTypeStaticText[@name='Change Password'][@visible='true']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(msg)).setDescription("Change password status");
	}
	
	public static Element getMarketingConsentSwitch() {
		String marketingSw = "//XCUIElementTypeSwitch[@name='Marketing Consent']";
		return new Element(By.xpath(marketingSw)).setDescription("Marketing Consent switch");
	}
	
	public static Element getChangeMarketingConsentMsg() {
		String msg = "//XCUIElementTypeStaticText[@name='Change Marketing Consent']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(msg)).setDescription("Change marketing consent status");
	}
	
	public static Element getChangeBtn() {
		return new Element(MobileBy.name("Change")).setDescription("Change button");
	}
	
	public static Element getFirstNameField() {
		String firstName = "**/XCUIElementTypeCell[$type='XCUIElementTypeImage' AND name='ic_user_icon'$]/XCUIElementTypeStaticText[1]";
		return new Element(MobileBy.iOSClassChain(firstName)).setDescription("First Name");
	}
	
	public static Element getLastNameField() {
		String lastName = "**/XCUIElementTypeCell[$type='XCUIElementTypeImage' AND name='ic_user_icon'$]/XCUIElementTypeStaticText[2]";
		return new Element(MobileBy.iOSClassChain(lastName)).setDescription("Last Name");
	}
	
	public static Element getNameTextField() {
		String nameField = "type='XCUIElementTypeTextField'";
		return new Element(MobileBy.iOSNsPredicateString(nameField)).setDescription("Name field");
	}
	
	/*
	 * ACTIONS
	 */
	
	public static void clickChangePassword() {
		getChangePasswordTextField().click();
	}
	
	public static String getChangePasswordStatus() {
		return getChangePasswordMsg().getText();
	}
	
	public static void changePasswordProfile(String oldPwd, String newPwd) {
		getCurrentPasswordField().sendKeys(oldPwd);
		getNewPasswordField().sendKeys(newPwd);
		getConfirmPasswordField().sendKeys(newPwd);
		clickOkButton();
	}
	
	public static void enableMarketingConsent() {
		if(getMarketingConsentSwitch().getAttribute("value").equals("0")) {
			getMarketingConsentSwitch().click();
			clickOkButton();
		}
	}
	
	public static void disableMarketingConsent() {
		if(getMarketingConsentSwitch().getAttribute("value").equals("1")) {
			getMarketingConsentSwitch().click();
			clickOkButton();
		}
	}
	
	/**
	 * Verify Marketing consent swith enable or disable
	 * @return true: enable
	 * 			false: disable
	 */
	public static boolean verifyMarketingConsentEnable() {
		return getMarketingConsentSwitch().getAttribute("value").equals("1");
	}
	
	public String getChangeMarketingConsentStatus() {
		return getMarketingConsentSwitch().getText();
	}
	
	public static void clickChangeButton() {
		getChangeBtn().click();
	}
	
	public static boolean verifyChangeButtonEnable(boolean value) {
		if(value) {
			return getChangeBtn().getAttribute("enabled").equals("true");
		}else {
			return getChangeBtn().getAttribute("enabled").equals("false");
		}
	}
	
	public static String getFirstNameValue() {
		return getFirstNameField().getText();
	}
	
	public static String getLastNameValue() {
		return getLastNameField().getText();
	}
	
	public static boolean verifyFirstNameLastNameUpdated(String firstName, String lastName) {
		return getFirstNameValue().equals(firstName) && getLastNameValue().equals(lastName);
	}
	
	public static void updateFirstName(String firstName) {
		getFirstNameField().click();
		getNameTextField().inputValue(firstName);
	}
	
	public static void updateLastName(String lastName) {
		getLastNameField().click();
		getNameTextField().inputValue(lastName);
	}
}
