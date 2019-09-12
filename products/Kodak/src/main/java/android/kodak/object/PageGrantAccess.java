package android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.element.Element;

public class PageGrantAccess extends PageBase{
	
	public static Element getAddUserButton() {
		String id = "menu_add_share_user";
		return new Element(By.id(id)).setDescription("Add user button");
	}
	
	public static Element getViaEmailButton() {
		String btn = "//android.widget.TextView[@text='Via Email']";
		return new Element(By.xpath(btn)).setDescription("Share cameras via email button");
	}
	
	public static Element getViaQrCodeButton() {
		String btn = "//android.widget.TextView[@text='Via QR code']";
		return new Element(By.xpath(btn)).setDescription("Share cameras via QR code button");
	}
	
	public static Element getEmailGrantAccess(String email) {
		String emailTextView = "//android.widget.TextView[contains(@resource-id,'text_email') and @text='%s']";
		return new Element(By.xpath(String.format(emailTextView, email))).setDescription(String.format("Access granted email: %s", email));
	}
	
	public static Element getEmailTextBox() {
		String id = "edittext_email";
		return new Element(By.id(id)).setDescription("Add email to grant access textbox");
	}
	
	public static Element getDeviceToShareByName(String deviceName) {
		String deviceCb = String.format(
				"//android.widget.TextView[contains(@resource-id,'textview_device_chosen') and @text='%s']/../android.widget.CheckBox",
				deviceName);
		return new Element(By.xpath(deviceCb)).setDescription(String.format("Camera to share: %s", deviceName));
	}
	
	public static Element getGrantAccessSwitch() {
		String id = "sw_allow_access_rights";
		return new Element(By.id(id)).setDescription("Grant acess switch");
	}
	
	public static Element getSaveButton() {
		String id = "btn_add_share_user";
		return new Element(By.id(id)).setDescription("Grant access save button");
	}
	
	public static List<WebElement> getListUserShared(){
		String lstUserPanelId = "list_share_user";
		Element userPanel = new Element(By.id(lstUserPanelId));
		String lstUserId = "img_forward";
		return userPanel.findElements(By.id(lstUserId));	
	}
	
	public static Element getRemoveUserIcon() {
		String id = "remove_share_user_menu_item";
		return new Element(By.id(id)).setDescription("remove shared user button");
	}
	
	public static Element getConfirmRemoveButton() {
		String id = "btn_remove";
		return new Element(By.id(id)).setDescription("Confirm button");
	}
	
	public static void clickAddUserButton() {
		getAddUserButton().click();
	}
	
	public static void grantAccessToUser(String email, String deviceName, boolean allowAccessRight) {
		clickAddUserButton();
		getViaEmailButton().click();
		getEmailTextBox().sendKeys(email);
		getDeviceToShareByName(deviceName).click();
		if(allowAccessRight) {
			getGrantAccessSwitch().click();
		}
		if(getSaveButton().isEnabled()) {
			getSaveButton().click();
		}
	}
	
	public static boolean verifyEmailWasGrantAccess(String email) {
		return getEmailGrantAccess(email).getWebElement() != null;
	}
	
	public static void removeAllSharedUser() {
		for (WebElement ele : getListUserShared()) {
			ele.click();
			getRemoveUserIcon().click();
			getConfirmRemoveButton().click();
		}
	}
}
