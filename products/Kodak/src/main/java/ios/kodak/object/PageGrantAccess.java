package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageGrantAccess extends PageBase{

	/*
	 * ELEMENTS
	 */
	
	public static Element getEmptyListUser() {
		String emptyList = "//XCUIElementTypeTable[@name='Empty list']";
		return new Element(By.xpath(emptyList)).setDescription("Empty list user");
	}
	
	public static Element getGrantAccessViaEmail() {
		String viaEmail = "//XCUIElementTypeStaticText[@name='Via Email']";
		return new Element(By.xpath(viaEmail)).setDescription("Grant Access Via Email");
	}
	
	public static Element getGrantAccessViaQRCode() {
		String viaQACode = "//XCUIElementTypeStaticText[@name='Via QR Code']";
		return new Element(By.xpath(viaQACode)).setDescription("Grant Access Via QR Code");
	}
	
	public static Element getGrantAccessEmailField() {
		String email = "//XCUIElementTypeNavigationBar[@name='Add Friend']/following-sibling::XCUIElementTypeOther/XCUIElementTypeTextField";
		return new Element(By.xpath(email)).setDescription("Email");
	}
	
	public static Element getDeviceToShareByName(String deviceName) {
		String deviceCbk = "//XCUIElementTypeStaticText[@name='%s']";
		return new Element(By.xpath(String.format(deviceCbk, deviceName))).setDescription("Device: " + deviceName);
	}
	
	public static Element getAccessRightSwitch() {
		String accessSwitch = "//XCUIElementTypeStaticText[@name='Allow Access Rights']/following-sibling::XCUIElementTypeSwitch";
		return new Element(By.xpath(accessSwitch)).setDescription("Allow Access Rights");
	}
	
	public static Element getEmailGranted(String email) {
		String emailEle = "//XCUIElementTypeStaticText[@value='%s']";
		return new Element(By.xpath(String.format(emailEle, email))).setDescription("Email grant access");
	}
	
	public static Element getDeleteUserBtn() {
		String deleteBtn = "//XCUIElementTypeButton[@name='ic delete']";
		return new Element(By.xpath(deleteBtn)).setDescription("Delete button");
	}
	/*
	 * ACTIONS
	 */
	
	public static void grantAccessToUser(String email, String deviceName, boolean allowAccessRight) {
		clickMiniAddButton();
		getGrantAccessViaEmail().click();
		getGrantAccessEmailField().sendKeys(email);
		getDeviceToShareByName(deviceName).click();
		if(allowAccessRight) {
			getAccessRightSwitch().click();
		}
		clickSaveButton();
	}
	
	public static boolean verifyEmailWasGranted(String email) {
		return getEmailGranted(email).getWebElement() != null;
	}
	
	public static void deleteUserShared(String email) {
		getEmailGranted(email).click();
		getDeleteUserBtn().click();
		clickYesButton();
	}
}
