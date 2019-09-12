package android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.element.Element;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

public class PageDashboard extends PageBase {

	protected static boolean	flagAutoTutorialClosed				= false;
	protected static boolean	flagCloudUpgradeSuggestionClosed	= false;

	/*** ELEMENT ***/
	// MENU
	public static Element getMenuButton() {

		String id = "imgMain";
		return new Element(By.id(id), 15).setDescription("Menu button on dashboard");
	}

	// Dashboard additional tip about grant access and preview
	public static Element getGrandAccessDashboardTipCloseBtn() {

		String id = "btn_close_dashboard_additional_tips";
		return new Element(By.id(id), ELEMENT_TIMEOUT_SHORT)
				.setDescription("Grant access tip close button");
	}

	public static Element getGrantAccessFeatureBtn() {

		String id = "button_grant_access_setting";
		return new Element(By.id(id)).setDescription("Grant access setting in home menu");
	}

	public static Element getDashboardSettingBtn() {

		String id = "button_preview_setting";
		return new Element(By.id(id)).setDescription("Dashboard setting button in home menu");
	}

	// Primary camera click for live view hint
	public static Element getClickForLiveViewButton() {

		String id = "btn_press_for_live_view";
		return new Element(By.id(id)).setDescription("Click here for live view hint");
	}

	// account name text view
	public static Element getAccountTextViewInMenu() {

		String id = "tv_account_name";
		return new Element(By.id(id)).setDescription("Account name in home menu");
	}

	// Dashboard tab
	public static Element getCameraByName(String name) {

		String layoutDevice = "//android.widget.TextView[contains(@resource-id,'_name') and @text='%s']/../..";
		return new Element(By.xpath(String.format(layoutDevice, name)))
				.setDescription(String.format("Camera %s", name));
	}

	public static Element getCameraNameLabel(String name) {

		String lblDeviceName = "//android.widget.TextView[contains(@resource-id,'_name') and @text='%s']";
		return new Element(By.xpath(String.format(lblDeviceName, name)))
				.setDescription(String.format("Camera label %s", name));
	}

	public static Element getCameraStatusLabel(String name) {

		String lblDeviceStatus = "//android.widget.TextView[@text='%s']/../following-sibling::android.widget.TextView[contains(@resource-id, 'status') and not(contains(@resource-id, 'p2p'))]";
		return new Element(By.xpath(String.format(lblDeviceStatus, name)), 3)
				.setDescription(String.format("Camera %s status", name));
	}

	public static Element getCameraSettingButton(String name) {

		String btnDeviceSetting = "//android.widget.TextView[contains(@resource-id,'_name') and @text='%s']/../../..//android.widget.ImageView[contains(@resource-id,'_setting')]";
		return new Element(By.xpath(String.format(btnDeviceSetting, name)))
				.setDescription("Camera setting button");
	}

	public static Element getAddDeviceBigBtn() {

		String id = "ic_add_1";
		return new Element(By.id(id)).setDescription("Add camera big button");
	}

	public static Element getAddDeviceMenuBtn() {

		String id = "menu_add_device";
		return new Element(By.id(id)).setDescription("Add camera small button");
	}

	public static Element getDashboardLabel() {

		String id = "textview_title_main";
		return new Element(By.id(id)).setDescription("Dashboard label");
	}

	// SETUP
	public static Element getBabySeriesImage() {

		String id = "img_baby_series";
		return new Element(By.id(id)).setDescription("Baby series image");
	};

	public static Element getHomeSeriesImage() {

		String id = "img_frontier_series";
		return new Element(By.id(id)).setDescription("Home series image");
	};

	public static void handlePermissionsAndHintsWhenPageOpen() {

		// Handle media permission request at first time app launch
		allowAndroidPermissionIfAsked(ANDROID_PERMISSION_MEDIA);

		// Handle Cloud upgrade ("Want to see more") hint at first launch
		closeCloudUpgradeSuggestion();

		// Check and close the tutorial if appears, memorize it
		closeTutorial();
	}

	public static Element getDeviceModelLabel(String name) {

		String lblDeviceModel = "//android.widget.TextView[contains(@resource-id,'text_device_model') and contains(@text,'%s')]";
		return new Element(By.xpath(String.format(lblDeviceModel, name)))
				.setDescription("Camera model" + name + " label");
	};

	public static Element getModel685Label() {

		String imgF685 = "//android.widget.TextView['@text=KODAK F685']";
		return new Element(By.xpath(String.format(imgF685))).setDescription("F685 image");
	};

	public static Element getContinueButton() {

		String id = "btn_continue";
		return new Element(By.id(id)).setDescription("Continue button");
	};

	public static Element getSSIDLabel(String name) {

		String lblSSID = "//android.widget.TextView[contains(@resource-id,'textview_access_point_item_name') and contains(@text,'%s')]";
		return new Element(By.xpath(String.format(lblSSID, name)), name)
				.setDescription("Camera SSID for pairing");
	};

	public static Element getSSIDRefreshImage() {

		String id = "imageview_select_wifi_refresh";
		return new Element(By.xpath(id)).setDescription("Pairing: Refresh camera SSID list");
	};

	public static Element getWifiSettingButton() {

		String btnWifiSetting = "//android.widget.Button[@text='WI-FI SETTINGS']";
		return new Element(By.xpath(String.format(btnWifiSetting)))
				.setDescription("Pairing: wifi setting button");
	};

	public static Element getWifiNameLabel(String name) {

		String lblWiFiName = "//android.widget.TextView[@resource-id='android:id/title' and @text='%s']";
		return new Element(By.xpath(String.format(lblWiFiName, name)))
				.setDescription("Pairing: Wifi name label");
	};

	public static Element getWifiPasswordTextbox() {

		String txtWifiPassword = "//android.widget.EditText[@resource-id='com.android.settings:id/password']";
		return new Element(By.xpath(String.format(txtWifiPassword)))
				.setDescription("Pairing: wifi password");
	};

	public static Element getWifiConnectButton() {

		String btnWifiConnect = "//android.widget.Button[@resource-id='android:id/button1' and @text='CONNECT']";
		return new Element(By.xpath(String.format(btnWifiConnect)))
				.setDescription("Pairing: Wifi connect button");
	};

	public static Element getWifiConnectedLabel(String name) {

		String lblWifiConnected = "//android.widget.TextView[@resource-id='android:id/title' and @text='%s']/..//android.widget.TextView[@resource-id='android:id/summary' and @text='Connected']";
		return new Element(By.xpath(String.format(lblWifiConnected, name)))
				.setDescription("Wifi connect label");
	};

	public static Element getCurrentWifiLabel() {

		String id = "text_current_wifi";
		return new Element(By.id(id)).setDescription("Current wifi name");
	};

	public static Element getTextWifiPasswordTextbox() {

		String id = "text_wifi_password";
		return new Element(By.id(id)).setDescription("Pairing: wifi password input");
	};

	public static Element getSelectAnotherWifiLabel() {

		String id = "tvSelectAnotherWifi";
		return new Element(By.id(id)).setDescription("Select Another Wi-Fi button");
	};

	public static Element getMainProgressbar() {

		String id = "progressbar_main";
		return new Element(By.id(id)).setDescription("Pairing progress bar");
	};

	public static Element getCustomButton() {

		String id = "btn_custom";
		return new Element(By.id(id), 400).setDescription("Paring: set custom name button");
	};

	public static Element getDoneButton() {

		String btnDone = "//android.widget.Button[contains(@resource-id,'btn_continue') and @text='Done']";
		return new Element(By.xpath(btnDone)).setDescription("Paring: Done buton");
	};

	public static Element getMobileDataContinueButton() {

		String btnMobileDataContinue = "//android.widget.Button[@resource-id='android:id/button1' and @text='CONTINUE']";
		return new Element(By.xpath(btnMobileDataContinue))
				.setDescription("Confirm using mobile data button");
	};

	public static Element getWifi5gProcessAnywayButton() {

		String btnWifi5gProcessAnyway = "//android.widget.Button[@resource-id='android:id/button2']";
		return new Element(By.xpath(btnWifi5gProcessAnyway))
				.setDescription("Process using 5Ghz Wifi button");
	};

	public static Element getSameSsidProcessAnywayButton() {

		String btnWifi5gProcessAnyway = "//android.widget.Button[@text='PROCEED ANYWAY']";
		return new Element(By.xpath(btnWifi5gProcessAnyway)).setDescription(
				"Process using current wifi despite of existing cam was paired to another wifi");
	};

	public static Element getHomeIcon() {

		String xpathLocator = "//android.widget.ImageView[contains(@resource-id,'account_setting_home')]";
		return new Element(By.xpath(xpathLocator)).setDescription("Home button in home menu");
	}
	// TAB: 3 tab, dashboard, gallerya and Timeline
	// Already define in PageBase

	// IN VIDEO GALLERY TAB
	public static Element getFirstVideoGalleryTime() {

		String timeFirstVideoGallery = "//android.widget.RelativeLayout[contains(@resource-id,'rootView')]//android.widget.TextView[contains(@resource-id,'timeTextView')]";
		return new Element(By.xpath(timeFirstVideoGallery))
				.setDescription("Recorded time of first item in Gallery");
	};

	public static Element getAccountNotActivateYetIcon() {

		String id = "imgNotify";
		return new Element(By.id(id)).setDescription("Your account not yet active icon");
	}

	public static Element getEmptyDeviceImage() {

		String id = "img_empty_device";
		return new Element(By.id(id)).setDescription("Image appear when no camera is paired");
	}

	public static List<WebElement> getListCameraInDashBoard() {

		String id = "swiperefreshlayout_device";
		return new Element(By.id(id)).setDescription("List of cameras in dashboard")
				.findElements(By.xpath("//android.widget.TextView[contains(@resource-id,'name')]"));
	}

	public static Element getPassWordWifiTextBox() {

		String id = "edittext_diaglog_access_point_key";
		return new Element(By.id(id)).setDescription("Pairing: Another wifi: Wifi passwd input");
	}

	public static Element getConnectButton() {

		String id = "textview_diaglog_access_point_connect";
		return new Element(By.id(id))
				.setDescription("Pairing: Another wifi: Wifi passwd input: Connect button");
	}

	public static Element getSSIDNameTextBox() {

		String id = "edtWifiName";
		return new Element(By.id(id))
				.setDescription("Pairing: Another wifi: Other: Input wifi SSID name");
	}

	public static Element getSecurityWifiSpinner() {

		String id = "spnWifiSecurityType";
		return new Element(By.id(id)).setDescription("Wifi security dropdown list");
	}

	public static Element getSecurityTypeByName(String securityType) {

		String secure = "//android.widget.CheckedTextView[@text='%s']";
		return new Element(By.xpath(String.format(secure, securityType)))
				.setDescription("Wifi Security type");
	}

	public static Element getSSIDPasswordTextBox() {

		String id = "edtWifiPassword";
		return new Element(By.id(id))
				.setDescription("Pairing: Another wifi: Other: Input Wifi passwd");
	}

	public static Element getDialogTitleUpdateFirmware() {

		String id = "text_dialog_title";
		return new Element(By.id(id)).setDescription("Firmware upgrade confirm dialog");
	}

	public static Element getDeviceNameTextView() {

		String id = "text_device_name";
		return new Element(By.id(id)).setDescription("Device name text view");
	}

	public static Element getProceedUpgradeButton() {

		String processBtn = "//android.widget.Button[@text='Proceed']";
		return new Element(By.xpath(processBtn), 400).setDescription("Proceed button");
	}

	public static Element getPrimaryCamera() {

		String id = "layout_banner_preview";
		return new Element(By.id(id)).setDescription("Primary camera");
	}

	/*** ACTION ***/
	public static void signOut() {

		getMenuButton().click();
		TimeHelper.sleep(1500);
		PageHomeMenu.getSignOutMenuItem().click();
	}

	public static String waitForDeviceOnline(String name, int timeOut) {

		String status = "";
		while (timeOut >= 0) {
			try {
				status = getCameraStatusLabel(name).getText();
			} catch (Exception e) {
				status = "";
			}
			if (status.equals("Online"))
				break;
			swipeBottomToTop();
			timeOut--;
			TimeHelper.sleep(1000);
		}
		return status;
	}

	public static boolean isCameraOnline(String name) {

		int checkCount = 30;
		while (checkCount > 0) {
			if (getCameraStatusLabel(name).getText().equals("Online")) { return true; }
			TimeHelper.sleep(1000);
			checkCount--;
		}
		return false;
	}

	public static void selectDeviceToView(String name) {

		getCameraByName(name).click(); // go to stream view page
		closeAnyTutorial(PageStreamView.flagAutoTutorialClosed);
	}

	public static PageCameraSetting openDeviceSetting(String name) {

		getCameraSettingButton(name).click();
		return new PageCameraSetting();
	}

	public static void openVideoGalleryTab() {

		getGalleryIcon().click();
	}

	public static String getFirstVieoGalleryInfo() {

		return getFirstVideoGalleryTime().getText();
	}

	public static void gotoAddNewDevice() {

		getAddDeviceBigBtn().click();
		allowAndroidPermissionIfAsked(ANDROID_PERMISSION_LOCATION);
	}

	public static void gotoHomeMenuPage() {

		getMenuButton().click();
	}

	/**
	 * Verify "Press here for live view" button display
	 * 
	 * @param isDisplay: verify display or not
	 * @return
	 */
	public static boolean verifyPressForLiveViewButtonDisplay(boolean isDisplay) {

		boolean rs = false;
		if (isDisplay) {
			rs = getClickForLiveViewButton().getWebElement().isDisplayed();
		} else {
			rs = getClickForLiveViewButton().getWebElement() == null;
		}
		return rs;
	}

	public static boolean verifyInActiveIconDisplay() {

		Log.info("Verify inactve user icon '!' display on top left dashboard page");
		return getAccountNotActivateYetIcon().isDisplayed();
	}

	public static boolean verifyEmptyDevice() {

		return getEmptyDeviceImage().getWebElement() != null;
	}

	public static boolean verifyDeviceExisted(String cameraName) {

		for (WebElement ele : getListCameraInDashBoard()) {
			if (ele.getText().equals(cameraName))
				return true;
		}
		return false;
	}

	public static List<String> getListCameraNameInDashBoard() {

		List<String> lstCam = new ArrayList<>();
		for (WebElement ele : getListCameraInDashBoard()) { lstCam.add(ele.getText()); }
		return lstCam;
	}

	/**
	 * Duong Nguyen
	 * Ignore wifi setting when device connect to Wifi 5G by proceed anyway
	 */
	public static void proceedAnyway5GWifiIfAsk() {

		if (getWifi5gProcessAnywayButton().getWebElement() != null) {
			getWifi5gProcessAnywayButton().click();
		}
	}

	/**
	 * Duong Nguyen
	 * Config wifi for camera while setup
	 * 
	 * Steps:
	 * If current wifi connect as expected, input password and continue
	 * Else click Select Another Wifi
	 * If list wifi display contains expected wifi --> select wifi name --> input
	 * password and Connect
	 * Else scroll to the end, select Other --> input ssid, security type, password
	 * and continue
	 * 
	 * @param wifiName
	 * @param security
	 * @param wifiPwd
	 */
	public static void configWifiForCamera(String wifiName, String security, String wifiPwd) {

		if (!getCurrentWifiLabel().getText().equals(wifiName)) {
			getSelectAnotherWifiLabel().click();
			if (getSSIDLabel(wifiName).getWebElement() != null) {
				getSSIDLabel(wifiName).click();
				getPassWordWifiTextBox().sendKeys(wifiPwd);
				getConnectButton().click();
			} else {
				getSSIDLabel("Other").click();
				configNetWorkWithNewWifi(wifiName, security, wifiPwd);
				getContinueButton().click();
			}
		} else {
			getTextWifiPasswordTextbox().sendKeys(wifiPwd);
			getContinueButton().click();
		}

	}

	public static void setupNewCamera(String wifiName, String wifiSecurityType, String wifiPasswd,
			String deviceid, String comport) throws Exception {

		String	cameraModel	= Device.getModelNameByUuid(deviceid);
		String	cameraSSID	= Device.convertSsidByUuid(deviceid);
		PageDashboard.getAddDeviceMenuBtn().click();
		PageDashboard.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
		PageDashboard.chooseCameraSeriesByModel(cameraModel);

		try {
			getSameSsidProcessAnywayButton().click();
		} catch (Exception e) {}
		try {
			getWifi5gProcessAnywayButton().click();
		} catch (Exception e) {}

		PageDashboard.getDeviceModelLabel(cameraModel).click();

		PageDashboard.getContinueButton().click();
		Terminal t = new Terminal(comport);
		t.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.getContinueButton().click();
		PageDashboard.getSSIDLabel(cameraSSID).click();
		PageDashboard.configWifiForCamera(wifiName, wifiSecurityType, wifiPasswd);
		PageDashboard.getCustomButton().click();
		PageDashboard.getContinueButton().click();
		PageDashboard.getDoneButton().click();
	}

	/**
	 * Config new wifi for camera
	 * 
	 * @param wifiName
	 * @param security
	 * @param wifiPwd
	 */
	private static void configNetWorkWithNewWifi(String wifiName, String security, String wifiPwd) {

		getSSIDNameTextBox().sendKeys(wifiName);
		getSecurityWifiSpinner().click();
		getSecurityTypeByName(security).click();
		getSSIDPasswordTextBox().sendKeys(wifiPwd);
	}

	public static void closeCloudUpgradeSuggestion() {

		if (flagCloudUpgradeSuggestionClosed)
			return;
		try {
			getCloudUpgradeTipCloseBtn().click();
		} catch (Exception e) {}
		flagCloudUpgradeSuggestionClosed = true;
	}

	public static boolean isDisplayed() {

		if (getDashboardLabel().getWebElement() != null)
			return true;
		return false;
	}

	/**
	 * Duong Nguyen
	 * 
	 * Verify new firmware available to upgrade
	 * 
	 * @return
	 */
	public static boolean verifyNewFirmwareAvailable() {

		boolean rs = false;
		// Verify dialog confirm upgrade firmware version
		if (getProceedUpgradeButton().getWebElement() != null) {
			rs = getDialogTitleUpdateFirmware().getText().equals("New Firmware Detected");
		}
		// Click proceed to upgrade firmware
		getProceedUpgradeButton().click();
		return rs;
	}

	public static void inputCameraName(String name) {

		getDeviceNameTextView().sendKeys(name);
	}

	public static void tryCloseGrantAccessAndPreviewTip() {

		try {
			getGrandAccessDashboardTipCloseBtn().click();
			Log.info("Additional tip about preview and Grant access feature has just been close");
		} catch (Exception e) {
			Log.info("Additional tip about preview and Grant access feature does not show");
		}
	}

	public static void closeTutorial() {

		flagAutoTutorialClosed = PageBase.closeAnyTutorial(flagAutoTutorialClosed);
	}

	public static void chooseCameraSeriesByModel(String model) {

		switch (model.toCharArray()[0]) {
		case 'C':
			getBabySeriesImage().click();
			break;
		case 'F':
			getHomeSeriesImage().click();
			break;
		default:
			break;
		}
	}
}
