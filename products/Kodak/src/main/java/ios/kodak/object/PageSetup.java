package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageSetup extends PageBase{

	public static Element getSelectAnotherWifiLink() {
		String link = "name=='Select Another Wi-Fi' AND visible==true";
		return new Element(MobileBy.iOSNsPredicateString(link)).setDescription("Select Another Wi-Fi");
	}
	
	public static Element getWifiPasswordField() {
		String pwdField = "**/XCUIElementTypeOther[$type=='XCUIElementTypeButton' AND name=='ic open eyes'$]/XCUIElementTypeTextField";
		return new Element(MobileBy.iOSClassChain(pwdField)).setDescription("Password field");
	}
	
	public static Element getOtherNetworkBtn() {
		String networkBtn = "**/XCUIElementTypeCell[$type=='XCUIElementTypeButton' AND name=='ic add camera sign'$]/XCUIElementTypeStaticText[$name=='Other Network'$]";
		return new Element(MobileBy.iOSClassChain(networkBtn)).setDescription("Other Network button");
	}
	
	public static Element getBabySeriesImage() {
		String babyImage = "Baby Series";
		return new Element(MobileBy.AccessibilityId(babyImage)).setDescription("Baby series image");
	}

	public static Element getHomeSeriesImage() {
		String homeImage = "Home Series";
		return new Element(MobileBy.AccessibilityId(homeImage)).setDescription("Home series image");
	}

	public static Element getDoorbellSeriesImage() {
		String doorbellImage = "Doorbell Series";
		return new Element(MobileBy.AccessibilityId(doorbellImage)).setDescription("Doorbell series image");
	}
	
	public static Element getDeviceModelLabel(String deviceName) {
		String deviceModel = "KODAK %s";
		return new Element(MobileBy.AccessibilityId(String.format(deviceModel, deviceName)))
				.setDescription(String.format(deviceModel, deviceName));
	}

	public static Element getGoToWifiSettingsLink() {
		String settingLink = "//XCUIElementTypeButton[@name='Goto Wi-Fi Settings']";
		return new Element(By.xpath(settingLink)).setDescription("Goto Wifi Settings link");
	}

	public static Element getProceedAnyWayLink() {
		String proceedLink = "//XCUIElementTypeButton[@name='Proceed Anyway']";
		return new Element(By.xpath(proceedLink), 10).setDescription("Proceed Anyway link");
	}

	public static Element getGotoSettingsBtn() {
		String settingBtn = "//XCUIElementTypeButton[@name='Go To Settings']";
		return new Element(By.xpath(settingBtn)).setDescription("Goto Settings button");
	}
	
	public static Element getCustomBtn() {
		return new Element(By.name("Custom"), 600).setDescription("Custom");
	}
	
	// -----------Setup another wifi element--------------
	public static Element getWifiNameField() {
		String name = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Name'$]/XCUIElementTypeTextField";
		return new Element(MobileBy.iOSClassChain(name)).setDescription("Wifi Name");
	}
	
	public static Element getWifiPwdField() {
		String pwdWifi = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Password'$]/XCUIElementTypeSecureTextField";
		return new Element(MobileBy.iOSClassChain(pwdWifi)).setDescription("Wifi Password");
	}
	
	public static Element getSecurityTypeField() {
		String securityType = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Security'$]/XCUIElementTypeStaticText[$name!='Security'$]";
		return new Element(MobileBy.iOSClassChain(securityType)).setDescription("Security type");
	}
	
	public static Element getSecurityTypeByName(String security) {
		String secure = "type == 'XCUIElementTypeStaticText' AND value BEGINSWITH[c] '%s'";
		return new Element(MobileBy.iOSNsPredicateString(String.format(secure, security))).setDescription("Security type: " + security);
	}
	
	// --------------------------------------
	
	
	/*
	 * ACTIONS
	 */
	
	public static void selectCameraImageByModelName(String model) {
		if (model.equals("C520")) {
			PageSetup.clickBabySeriesModel();
		}else {
			PageSetup.clickHomeSeriesModel();
		}
		PageSetup.clickOnDeviceModel(model);
	}
	
	public static void clickBabySeriesModel() {
		getBabySeriesImage().click();
	}

	public static void clickHomeSeriesModel() {
		getHomeSeriesImage().click();
	}

	public static void clickDoorbellSeriesModel() {
		getDoorbellSeriesImage().click();
	}
	
	public static void setupWifiSecurity(String securityType) {
		getSecurityTypeField().click();
		getSecurityTypeByName(securityType).click();
		clickBackButton();
	}
	public static void setupAnotherWifi(String wifiName, String wifiPwd, String securityType) {
		getWifiNameField().sendKeys(wifiName);
		getWifiPwdField().sendKeys(wifiPwd);
		setupWifiSecurity(securityType);
		clickDoneButton();
	}
	
	public static void clickSelectAnotherWifi() {
		getSelectAnotherWifiLink().click();
	}
	
	public static void selectWifitoConnectRouter(String wifiName, String pwd, String secureType) {
		if(PageIOSSetting.getWifiByName(wifiName).getWebElement() != null) {
			PageIOSSetting.clickOnWifiName(wifiName);
			inputWifiPassword(pwd);
		}else {
			clickOtherNetworkButton();
			setupAnotherWifi(wifiName, pwd, secureType);
		}
		clickContinueButton();
	}
	
	public static void inputWifiPassword(String pwd) {
		getWifiPasswordField().sendKeys(pwd);
	}
	
	public static void clickOtherNetworkButton() {
		while (getOtherNetworkBtn().getWebElement() != null) {
			if(getOtherNetworkBtn().getAttribute("visible").equals("true")) {
				getOtherNetworkBtn().click();
			}else {
				swipeTopToBottom();
			}
		}
	}
	
	public static void clickOnDeviceModel(String deviceName) {
		getDeviceModelLabel(deviceName).click();
	}

	public static void clickOnContinueSettupButton() {
		getContinueButton().click();
	}

	public static void clickProceedAnyway() {
		if (getProceedAnyWayLink().getWebElement() != null)
			getProceedAnyWayLink().click();
		else {
			PageSetup.clickOnContinueSettupButton();
			if (getProceedAnyWayLink().getWebElement() != null)
				getProceedAnyWayLink().click();
		}
	}

	public static void clickGotoSettingsButton() {
		if(getContinueButton().getWebElement() != null)
			clickContinueButton();
		getGotoSettingsBtn().click();
	}
	
	public static void clickCustomButton() {
		getCustomBtn().click();
	}
	
}