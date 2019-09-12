package ios.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.StringHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageDashboard extends PageBase {
	public static boolean flagAutoTutorialClosed = false;
	public static boolean flagCloudUpgradeSuggestionClosed = false;
	public static boolean flagSubcriptionPopUpClose = false;
	/*
	 * ELEMENT
	 */

	public static Element getTimelineEle() {
		String timeline = "//XCUIElementTypeTabBar/XCUIElementTypeButton[3]";
		return new Element(By.xpath(timeline)).setDescription("Timeline page");
	}
	
	public static Element getGalleryEle() {
		String timeline = "//XCUIElementTypeTabBar/XCUIElementTypeButton[2]";
		return new Element(By.xpath(timeline)).setDescription("Gallery page");
	}
	
	public static Element getHomeEle() {
		String timeline = "//XCUIElementTypeTabBar/XCUIElementTypeButton[1]";
		return new Element(By.xpath(timeline)).setDescription("Home page");
	}
	
	public static Element getMenuButton() {
		String menuBtn = "ic menu";
		return new Element(By.name(menuBtn), 15).setDescription("Menu button on dashboard");
	}

	public static Element getDashboardLabel() {
		String label = "name=='Dashboard'";
		return new Element(MobileBy.iOSNsPredicateString(label), 120).setDescription("Dashboard label");
	}

	public static Element getAccountTextViewInMenu() {
		String accEle = "**/XCUIElementTypeOther[$type=='XCUIElementTypeImage' AND name=='ic_default_avatar'$]/XCUIElementTypeStaticText";
		return new Element(MobileBy.iOSClassChain(accEle)).setDescription("Account name in home menu");
	}

	public static Element getSignOutButton() {
		String signOutBtn = "name=='Sign Out' AND visible==true";
		return new Element(MobileBy.iOSNsPredicateString(signOutBtn), "Sign Out").setDescription("Sign out button");
	}

	public static Element getAddNewDeviceButton() {
		String addBtn = "//XCUIElementTypeImage[@name='ic_add_camera_sign']";
		return new Element(By.xpath(addBtn)).setDescription("Add new device button");
	}

	public static Element getHomeButton() {
		return new Element(By.name("ic cameras menubar")).setDescription("Home button");
	}

	public static Element getAddNewCameraBtn() {
		String addBtn = "Add New Device ...";
		return new Element(MobileBy.AccessibilityId(addBtn)).setDescription("Add new camera icon");
	}

	public static Element getUserProfileTextField() {
		return new Element(MobileBy.name("User Profile")).setDescription("User Profile");
	}

	public static Element getCameraSettingByCameraName(String cameraName) {
		String settingBtn = "**/XCUIElementTypeOther[$type='XCUIElementTypeStaticText' AND name=='%s'$]/XCUIElementTypeButton[$name=='ic settings'$]";
		return new Element(MobileBy.iOSClassChain(String.format(settingBtn, cameraName))).setDescription("Setting");
	}

	public static Element getDevicesTextField() {
//		String devices = "//XCUIElementTypeStaticText[@name='Devices']";
		return new Element(By.name("Devices")).setDescription("Devices");
	}

	public static Element getGrantAccessTextField() {
		String grantAccess = "//XCUIElementTypeStaticText[@name='Grant Access']";
		return new Element(By.xpath(grantAccess)).setDescription("Grant Access");
	}

	public static Element getNotActiveAccountIcon() {
		String warningIcon = "//XCUIElementTypeImage[@name='ic_warning']";
		return new Element(By.xpath(warningIcon)).setDescription("Warning Icon");
	}

	public static Element getDoNotDisturbField() {
		String ele = "//XCUIElementTypeStaticText[@name='Do Not Disturb']";
		return new Element(By.xpath(ele)).setDescription("Do Not Disturb");
	}

	public static List<WebElement> getListCameraSettingIcon() {
		String icon = "//XCUIElementTypeButton[@name='ic settings']";
		return DriverManager.getDriver().findElements(By.xpath(icon));
	}

	public static Element getDevice_ManageCloudStorageField() {
		String storage = "//XCUIElementTypeStaticText[@name='Manage Cloud Storage']";
		return new Element(By.xpath(storage)).setDescription("Manage Cloud Storage");
	}

	public static Element getDeviceOrderField() {
		String deviceOrder = "//XCUIElementTypeStaticText[@name='Device Order']";
		return new Element(By.xpath(deviceOrder)).setDescription("Device Order");
	}

	public static Element getHomeMenuDashboardField() {
		String dashboard = "//XCUIElementTypeStaticText[@name='Dashboard']";
		return new Element(By.xpath(dashboard)).setDescription("Dashboard");
	}

	public static List<WebElement> getListCameraNameOnDashboard() {
		String camName = "//XCUIElementTypeOther/XCUIElementTypeImage/preceding-sibling::XCUIElementTypeStaticText";
		return DriverManager.getDriver().findElements(By.xpath(camName));
	}

	public static Element getPreviewInMobileSw() {
		String sw = "//XCUIElementTypeSwitch[@name='Preview In Mobile Network']";
		return new Element(By.xpath(sw)).setDescription("Preview in mobile network");
	}

	public static Element getPreviewInWifiSw() {
		String sw = "//XCUIElementTypeSwitch[@name='Preview In Wi-Fi']";
		return new Element(By.xpath(sw)).setDescription("Preview in Wi-Fi");
	}
	
	public static Element getHomeMenuSettingField() {
		return new Element(MobileBy.name("Settings")).setDescription("Settings");
	}
	
	public static Element getCameraByName(String name) {
		String cam = "type=='XCUIElementTypeStaticText' AND name=='%s'";
		return new Element(MobileBy.iOSNsPredicateString(String.format(cam, name))).setDescription("Camera name: " + name);
	}
	
	public static Element getHelpAndSupportField() {
		String field = "//XCUIElementTypeStaticText[@name='Help & Support']";
		return new Element(By.xpath(field)).setDescription("Help & Support");
	}
	
	public static Element getReportIssueField() {
		String field = "//XCUIElementTypeStaticText[@name='Report Issue/Feedback']";
		return new Element(By.xpath(field)).setDescription("Report Issue/Feedback");
	}
	
	public static Element getAppVersionEle() {
		String scrollTo = "KODAK Smart Home";
		String ele = "type=='XCUIElementTypeStaticText' AND name BEGINSWITH 'KODAK Smart Home - Version'";
		return new Element(MobileBy.iOSNsPredicateString(ele)).setDescription("App version");
	}
	
	public static Element getCameraStatusInDashBoardEle(String camName) {
		String ele = "**/XCUIElementTypeOther[$type=='XCUIElementTypeStaticText' AND name=='%s'$]/XCUIElementTypeStaticText[2]";
		return new Element(MobileBy.iOSClassChain(String.format(ele, camName))).setDescription("Camera status");
	}
	
	public static Element getDoNotShowAgainCbk() {
		String checkBox = "type=='XCUIElementTypeButton' AND name=='ic uncheck'";
		return new Element(MobileBy.iOSNsPredicateString(checkBox)).setDescription("Do not show again check box");
	}
	
	/*
	 * ACTION
	 */

	
	public static void gotoHomePage() {
		getHomeEle().click();
	}
	
	public static void gotoGalleryPage() {
		getGalleryEle().click();
	}
	
	public static void gotoTimelinePage() {
		getTimelineEle().click();
	}
	
	public static void clickOnHomeMenuButton() {
		getMenuButton().click();
	}

	public static boolean isDisplayed() {
		return getDashboardLabel().getWebElement() != null ? true : false;
	}

	public static void signOut() {
		getSignOutButton().click();
		clickOkButton();
	}

	public static boolean verifyAddNewDeviceButtonDisplayed() {
		return getAddNewDeviceButton().getWebElement() != null;
	}

	public static void clickHomeButton() {
		getHomeButton().click();
	}

	public static void clickAddNewCamera() {
		getAddNewCameraBtn().click();
	}

	public static void gotoUserProfilePage() {
		clickOnHomeMenuButton();
		getUserProfileTextField().click();
	}

	public static void openDeviceSetting(String deviceName) {
		getCameraSettingByCameraName(deviceName).click();
	}

	public static void gotoGrantAccessPage() {
		clickOnHomeMenuButton();
		getDevicesTextField().click();
		getGrantAccessTextField().click();
	}

	public static boolean verifyNotActiveAccountWarningIcon() {
		return getNotActiveAccountIcon().getWebElement() != null;
	}

	public static void gotoDoNotDisturbPage() {
		clickOnHomeMenuButton();
		getDevicesTextField().click();
		getDoNotDisturbField().click();
	}

	public static void gotoDevice_ManageCloudStorage() {
		clickOnHomeMenuButton();
		getDevicesTextField().click();
		getDevice_ManageCloudStorageField().click();
	}

	public static void gotoDeviceOrderPage() {
		clickOnHomeMenuButton();
		getHomeMenuDashboardField().click();
		getDeviceOrderField().click();
	}

	public static List<String> getListDeviceNameInDashboard() {
		List<String> lstCam = new ArrayList<String>();
		for (WebElement ele : getListCameraNameOnDashboard()) {
			lstCam.add(ele.getText());
		}
		return lstCam;
	}

	public static void gotoHomeMenuDashboard() {
		clickOnHomeMenuButton();
		getHomeMenuDashboardField().click();
	}

	public static boolean verifyAllPreviewModeEnable(boolean value) {
		String enableMode = "0";
		if(value)
			enableMode = "1";
		
		if (!getPreviewInMobileSw().getAttribute("value").equals(enableMode)) {
			return false;
		}
		if (!getPreviewInWifiSw().getAttribute("value").equals(enableMode)) {
			return false;
		}
		return true;
	}

	public static void enableAllPreviewMode(boolean value) {
		String enableMode = "1";
		if (value)
			enableMode = "0";

		if (getPreviewInMobileSw().getAttribute("value").equals(enableMode)) {
			getPreviewInMobileSw().click();
		}
		if (getPreviewInWifiSw().getAttribute("value").equals(enableMode)) {
			getPreviewInWifiSw().click();
		}
	}
	
	public static void gotoHomeMenuSettingPage() {
		clickOnHomeMenuButton();
		getHomeMenuSettingField().click();
	}
	
	public static void selectDeviceToView(String deviceName) {
		getCameraByName(deviceName).click();
		allowIosPermissionIfAsker(IOS_PERMISSION_MICROPHONE);
		PageStreamView.flagAutoTutorialClosed = closeAnyTutorial(PageStreamView.flagAutoTutorialClosed);
	}
	
	public static void gotoReportIssuePage() {
		clickOnHomeMenuButton();
		getHelpAndSupportField().click();
		getReportIssueField().click();
	}
	
	public static String getCameraStatusByName(String camName) {
		return getCameraStatusInDashBoardEle(camName).getText();
	}
	
	public static String getCurrentAppVersion() {
		clickOnHomeMenuButton();
		String appVersion = getAppVersionEle().getText();
		return StringHelper.getStringByString(appVersion, "Version ", "", true);
	}
	
	public static void handlePermissionsAndHintsWhenPageOpen() {
		// Handle media permission request at first time app launch
		allowIosPermissionIfAsker(IOS_PERMISSION_MICROPHONE);
		
		// Check and close the tutorial if appears, memorize it
		flagAutoTutorialClosed = closeAnyTutorial(flagAutoTutorialClosed);
	}
	
	public static void closeSubcriptionPopup() {
		if(!flagSubcriptionPopUpClose) {
			if(getDoNotShowAgainCbk().getWebElement() != null) {
				getDoNotShowAgainCbk().click();
				clickClose();
				flagSubcriptionPopUpClose = true;
			}
		}
	}
}