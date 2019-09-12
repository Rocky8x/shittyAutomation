package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageProfile extends PageBase {
	private static final String MARKETING_CONSENT_ON = "ON";
	private static final String MARKETING_CONSENT_OFF = "OFF";
	private static String currentEmail;

	public static String getCurrentEmail() {
		return currentEmail;
	}

	public static void setCurrentEmail(String currentEmail) {
		PageProfile.currentEmail = currentEmail;
	}

	public static Element getEmailTextView() {
//		String tvEmail = "//android.widget.TextView[@text='%s']";
		String id = "account_setting_title";
		return new Element(By.id(id)).setDescription("Email field");
	}

	public static Element getChangeEmailPanel() {
		String pnChangeEmail = "//android.widget.LinearLayout[@resource-id='android:id/parentPanel']";
		return new Element(By.xpath(pnChangeEmail)).setDescription("Change email profile popup");
	}

	public static Element getEmailTextEdit() {
		String id = "text_new_email";
		return new Element(By.id(id)).setDescription("Email textbox");
	}

	public static Element getChangeButton() {
		String btnChange = "//android.widget.Button[@text='CHANGE']";
		return new Element(By.xpath(btnChange)).setDescription("Change button");
	}

	public static Element getChangeEmailConfirmationPanel() {
		String id = "layout_dialog_body";
		return new Element(By.id(id)).setDescription("Confirm change email popup");
	}

	public static Element getOpenEmailButton() {
		String openBtn = "//android.widget.Button[@text='Open Email']";
		return new Element(By.xpath(openBtn)).setDescription("Open email button");
	}

	public static Element getCancelOpenEmailButton() {
		String cancelBtn = "//android.widget.Button[@text='Cancel']";
		return new Element(By.xpath(cancelBtn)).setDescription("Cancel open email");
	}

	public static Element getChangePasswordButton() {
		String pwdBtn = "//android.widget.TextView[contains(@resource-id,'account_setting_title') and @text='Change Password']";
		return new Element(By.xpath(pwdBtn)).setDescription("Change password button");
	}

	public static Element getChangePasswordPanel() {
		String pwdPanel = "//android.widget.TextView[@resource-id='android:id/alertTitle' and @text='Change Password']";
		return new Element(By.xpath(pwdPanel)).setDescription("Change password profile popup");
	}

	public static Element getOldPasswordTextBox() {
		String id = "edittext_old_password";
		return new Element(By.id(id)).setDescription("Old password textbox");
	}

	public static Element getNewPasswordTextBox() {
		String id = "edittext_new_password";
		return new Element(By.id(id)).setDescription("New password textbox");
	}

	public static Element getConfirmPasswordTextBox() {
		String id = "edittext_confirm_password";
		return new Element(By.id(id)).setDescription("Confirm passaword textbox");
	}

	public static Element getConfirmChangePwdButton() {
		String confirmBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(confirmBtn)).setDescription("Change password button");
	}

	public static Element getCancelChangePwdButton() {
		String cancelBtn = "//android.widget.Button[@resource-id='android:id/button2']";
		return new Element(By.xpath(cancelBtn)).setDescription("Cancel change password button");
	}

	public static Element getMarketingConsentSwitch() {
		String id = "account_setting_switch";
		return new Element(By.id(id)).setDescription("Marketing consent switch");
	}

	public static boolean verifyChangeEmailPanelDisplay() {
		return getChangeEmailPanel().isDisplayed();
	}

	public static void updateEmailProfile(String newEmail) {
		getEmailTextView().click();
		if (verifyChangeEmailPanelDisplay()) {
			getEmailTextEdit().sendKeys(newEmail);
			getChangeButton().click();
		}
	}

	public static boolean verifyChangeEmailConfimationPanel() {
		boolean rs = false;
		if (getChangeEmailConfirmationPanel().isDisplayed()) {
			getCancelOpenEmailButton().click();
			rs = true;
		}
		return rs;
	}

	public static boolean verifyEmailProfileUpdated(String email) {
		return getEmailTextView().getText().equals(email);
	}

	public static String getCurrentEmailProfile() {
		String rs = "";
		if (getEmailTextView().getWebElement() != null) {
			rs = getEmailTextView().getText();
		}
		return rs;
	}

	public static void clickChangePassword() {
		getChangePasswordButton().click();
	}

	/**
	 * Change password account in profile page
	 * 
	 * @param oldPwd: current password
	 * @param newPwd: new password
	 */
	public static void changePasswordAccount(String oldPwd, String newPwd) {
		getOldPasswordTextBox().sendKeys(oldPwd);
		getNewPasswordTextBox().sendKeys(newPwd);
		getConfirmPasswordTextBox().sendKeys(newPwd);
		getConfirmChangePwdButton().click();
	}

	public static String getMarketingConsentStatus() {
		String rs = "";
		if (getMarketingConsentSwitch() != null) {
			rs = getMarketingConsentSwitch().getText();
		}
		return rs;
	}
	
	public static boolean verifyMarketingConsentOn() {
		return getMarketingConsentStatus().equalsIgnoreCase(MARKETING_CONSENT_ON);
	}
	
	public static boolean verifyMarketingConsentOFF() {
		return getMarketingConsentStatus().equalsIgnoreCase(MARKETING_CONSENT_OFF);
	}
	
	public static void enableMarketingConsent() {
		if (verifyMarketingConsentOFF()) {
			getMarketingConsentSwitch().click();
		}
	}
	
	public static void disableMarketingConsent() {
		if (verifyMarketingConsentOn()) {
			getMarketingConsentSwitch().click();
		}
	}
}
