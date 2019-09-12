package android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.cinatic.TimeHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

import io.qameta.allure.Step;

public class PageCameraSetting extends PageBase {
	private static final String _50HZ = "50Hz";
	private static final String _60HZ = "60Hz";

	/*** ELEMENT ***/
	// DETAILS

	public static Element getCameraNameValue() {
		String xpathLocator = "//android.widget.TextView[@text='Camera Name']";
		return new Element(By.xpath(xpathLocator)).setDescription("Camera name");
	}

	public static Element getModelNameValue() {
		String id = "textview_device_setting_model";
		return new Element(By.id(id)).setDescription("Camera model");
	}

	public static Element getMacAddressValue() {
		String id = "textview_device_setting_mac_address";
		return new Element(By.id(id)).setDescription("MAC address");
	}

	public static Element getFirmwareVersion() {
		String id = "textview_firmware_label";
		return new Element(By.id(id)).setDescription("Camera FW ver");
	}

	public static Element PUVersion() {
		String xpathLocator = "//android.widget.TextView[@resource-id='PU Version']";
		return new Element(By.xpath(xpathLocator)).setDescription("PU fw ver");
	}

	public static Element getCurrentPlanValue() {
		String id = "textview_device_setting_current_plan";
		return new Element(By.id(id)).setDescription("Curent plan value");
	}

	public static Element getTimeZoneValue() {
		String id = "textview_device_setting_timezone";
		return new Element(By.id(id)).setDescription("Time zone value");
	}

	public static Element getWifiSignalStrengthValue() {
		String id = "textview_device_setting_wifi_network_strength";
		return new Element(By.id(id)).setDescription("Wifi strengh value");
	}

	public static Element getBatteryLevelValue() {
		String id = "textview_device_setting_battery_level";
		return new Element(By.id(id)).setDescription("Camera battery value");
	}
	
	public static Element getBatteryStateImg() {
		String id = "textview_device_setting_battery_level";
		return new Element(By.id(id)).setDescription("Camera battery state full");
	}
	
	public static Element getBatteryGeneralInfo() {
		String xpathLocator = "//android.widget.RelativeLayout[contains(@resource-id,'layout_device_setting_battery_state')]/*";
		return new Element(By.xpath(xpathLocator)).setDescription("Camera battery general info");
	}
	
	public static Element getSendDeviceLog() {
		String scrollToString = "Send Camera Log";
		String id = "text_send_device_log";
		return new Element(By.id(id),scrollToString).setDescription("Send camera log button");
	}

	// CHANGE CAMERA NAME
	
	public static Element getRenameTextbox() {
		String txtRename = "//android.widget.EditText[@index='0']";
		return new Element(By.xpath(txtRename)).setDescription("Rename camera textbox");
	}

	public static Element getDoneButton() {
		String id = "btn_confirm";
		return new Element(By.id(id)).setDescription("Cam rename done button");
	}

	public static Element Cancel_button() {
		String id = "btn_cancel";
		return new Element(By.id(id)).setDescription("Cam rename cancel button");
	}

	// SETTINGS
	public static Element getNightVisionAutoBtn() {
		String id = "textview_device_setting_night_vision_auto";
		return new Element(By.id(id), "Night Vision").setDescription("Auto night vision button");
	}

	public static Element getNightVisionOnBtn() {
		String id = "textview_device_setting_night_vision_on";
		return new Element(By.id(id), "Night Vision").setDescription("Night vision button on");
	}

	public static Element getNightVisionOffBtn() {
		String id = "textview_device_setting_night_vision_off";
		return new Element(By.id(id), "Night Vision").setDescription("Night vision button off");
	}

	public static Element getCeilingMountSwitch() {
		String id = "sw_ceiling_mount";
		return new Element(By.id(id), "Ceiling Mount").setDescription("Ceiling mount sw");
	}

	public static Element getMotionDetectionOnButton() {
		String swMotionDetection = "//android.widget.LinearLayout[contains(@resource-id,'motion_options_view')]/android.widget.TextView[@text='On']";
		return new Element(By.xpath(swMotionDetection), "Motion Detection").setDescription("MD on button");
	}
	
	public static Element getMotionDetectionOffButton() {
		String swMotionDetection = "//android.widget.LinearLayout[contains(@resource-id,'motion_options_view')]/android.widget.TextView[@text='Off']";
		return new Element(By.xpath(swMotionDetection), "Motion Detection").setDescription("MD off button");
	}
	
	public static Element getMdSdCardStorageBtn() {
		String xpath = "//android.widget.TextView[@text='SD Card']";
		return new Element(By.xpath(xpath)).setDescription("Motion detection, SD card storage button");
	}
	
	public static Element getMdCloudStorageBtn() {
		String xpath = "//android.widget.TextView[@text='Cloud']";
		return new Element(By.xpath(xpath)).setDescription("Motion detection, Cloud storage button");
	}

	public static Element getSoundDetectionOnButton() {
		String swSoundDetection = "//android.widget.LinearLayout[contains(@resource-id,'sound_options_view')]/android.widget.TextView[@text='On']";
		return new Element(By.xpath(swSoundDetection), "Sound Detection").setDescription("Sound detection On button");
	}
	
	public static Element getSoundDetectionOffButton() {
		String swSoundDetection = "//android.widget.LinearLayout[contains(@resource-id,'sound_options_view')]/android.widget.TextView[@text='Off']";
		return new Element(By.xpath(swSoundDetection), "Sound Detection").setDescription("Sound detection off button");
	}

	public static Element getTemperatureSwitch() {
		String id = "swTemperatureEnable";
		return new Element(By.id(id), "Temperature").setDescription("Temp detection switch");
	}

	public static Element getVideoResolutionLabel() {
		String id = "tvTitle";
		return new Element(By.id(id), "Video Resolution").setDescription("Video resolution label");
	}

	public static Element getMotionSensitiveSeekbar() {
		String id = "seekbar_motion_sensitive";
		return new Element(By.id(id), "Motion Sensitivity").setDescription("Motion seitivity setting");
	};

	public static Element getSoundSensitiveSeekbar() {
		String id = "seekbar_sound_sensitive";
		return new Element(By.id(id), "Sound Sensitivity").setDescription("Sound sensitive setting");
	};

	public static Element getLightSourceFrequencyTextBox() {
		String xpath = "//android.widget.TextView[contains(@resource-id,'tvDetail') and contains(@text, 'Hz')]";
		return new Element(By.xpath(xpath), "Light Source Frequency",  10).setDescription("Light source frequency setting value");
	}

	public static Element getFrequencySelected() {
		String xpath = "//android.widget.RadioButton[@checked='true']/../android.widget.TextView";
		return new Element(By.xpath(xpath)).setDescription("Selected light source frequency");
	}

	public static Element getFrequencyNotSelected() {
		String xpath = "//android.widget.RadioButton[@checked='false']/../android.widget.TextView";
		return new Element(By.xpath(xpath)).setDescription("Not selected light source frequency");
	}

	public static Element getFrequencyPanel() {
		String panel = "//android.widget.TextView[contains(@resource-id,'alertTitle') and @text='Light Source Frequency']";
		return new Element(By.xpath(panel)).setDescription("Light source frequency setting panel");
	}

	public static Element getZoneDetectionMotion() {
		String id = "zone_detection";
		return new Element(By.id(id), "Define Zone to Detect Motion");
	}

	public static Element getAddNewZoneButton() {
		String id = "add_zone";
		return new Element(By.id(id)).setDescription("Add new zone button");
	}

	public static Element getUpdateZoneToCameraButton() {
		String id = "btn_update_zone";
		return new Element(By.id(id)).setDescription("Update zone to camera button");
	}

	public static Element getEditZoneIcon(String zoneName) {
		String editIcon = String.format(
				"//android.widget.TextView[contains(@resource-id,'zone_name') and @text = '%s']/../android.widget.ImageView[2]",
				zoneName);
		return new Element(By.xpath(editIcon)).setDescription("Edit zone button");
	}

	public static Element getZoneByName(String zoneName) {
		String zoneTextView = String.format(
				"//android.widget.TextView[contains(@resource-id,'zone_name') and @text = '%s']",
				zoneName);
		return new Element(By.xpath(zoneTextView)).setDescription("Zone name");
	}
	
	public static Element getZoneDefault() {
		String defaultZone = "//android.widget.FrameLayout[contains(@resource-id,'zone_img_container')]/android.view.View";
		return new Element(By.xpath(defaultZone)).setDescription("Defaut zone");
	}

	public static Element getZoneDefaultEditView() {
		String id = "editable_image";
		return new Element(By.id(id)).setDescription("Edit default zone");
	}
	
	public static Element getZoneListPanel() {
		String id = "zone_list";
		return new Element(By.id(id)).setDescription("Zone list panel");
	}
	
	public static Element getZoneContainer() {
		String id = "zone_img_container";
		return new Element(By.id(id)).setDescription("Zone container");
	}
	
	public static Element getRefreshZoneButton() {
		String id = "menu_refresh";
		return new Element(By.id(id)).setDescription("Refresh zone button");
	}

	/*** ACTION ***/
	public static Element getSharedByTextView() {
		String id = "text_device_owner";
		return new Element(By.id(id),"Shared By").setDescription("Camera owner info text");
	}
	
	public static Element getCameraSettingTitle() {
		String id = "setting_title";
		return new Element(By.id(id),"Motion Detection").setDescription("SETTING section of camera setting");
	}
	
	public static Element getConfirmYesBtn() {
		String okBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(okBtn)).setDescription("Yes button");
	}
	
	public static Element getDeleteAllEventsTextView() {
		String scrollToText = "Delete All Events";
		String id = "layout_delete_all_events_top";
		return new Element(By.id(id),scrollToText).setDescription("Delete all event button");
	}
	
	public static Element getDeleteCamIcon() {
		String id = "remove_device_setting_menu_item";
		return new Element(By.id(id)).setDescription("Delete camera button");
	}
	
	public static Element getDeviceSettingPageTitle() {
		String id = "textview_title_main";
		return new Element(By.id(id)).setDescription("Device setting title");
	}
	
	public static Element getHighTemperatureThreshold() {
		String scrollToText = "Video Resolution";
		String hightTem = "//android.widget.LinearLayout[contains(@resource-id,'seekbar_high_temperature')]//android.widget.TextView[contains(@resource-id,'number_picker_value')]";
		return new Element(By.xpath(hightTem), scrollToText).setDescription("High Temperature Threshold");
	}
	
	public static Element getLowTemperatureThreshold() {
		String scrollToText = "Low Temperature";
		String lowTem = "//android.widget.LinearLayout[contains(@resource-id,'seekbar_low_temperature')]//android.widget.TextView[contains(@resource-id,'number_picker_value')]";
		return new Element(By.xpath(lowTem), scrollToText).setDescription("Low Temperature Threshold");
	}
	public static Element getConfirmFirmwareUpgrade() {
		String id = "btn_confirm";
		return new Element(By.id(id), 400).setDescription("Confirm upgrade firmware successful.");
	}
	
	public static Element getFirmwareUpdateStatus() {
		String id = "text_dialog_msg";
		return new Element(By.id(id)).setDescription("Firmware update message status");
	}
	
	// Alternative SSID feature
	public static Element getAlternativeSsidText() {
		String id = "textview_alternative_ssid_name";
		return new Element(By.id(id)).setDescription("Alternative SSID text");
	}
	
	public static Element getAlternativeSsidArrowBtn() {
		String id = "img_wifi_arrow";
		return new Element(By.id(id)).setDescription("Edit alternative SSID arrow button");
	}
	
	public static Element getAlternativeSsidHelpBtn() {
		String id = "img_ssid_help";
		return new Element(By.id(id)).setDescription("Alternative SSID help button");
	}
	
	public static Element getAlternativeSsidHelpOkBtn() {
		String id = "btn_confirm";
		return new Element(By.id(id)).setDescription("Alternative SSID help OK button");
	}
	
	public static Element getAlternativeSsidEditText() {
		String id = "edittext_ssid";
		return new Element(By.id(id)).setDescription("Alternative SSID edit text box");
	}
	
	public static Element getAlternativeSsidPasswordInputBox() {
		String id = "edittext_password";
		return new Element(By.id(id)).setDescription("Alternative SSID password input box");
	}
	
	public static Element getAlternativeSsidCancelBtn() {
		String xpathLocator = "//android.widget.Button[@text='CANCEL']";
		return new Element(By.xpath(xpathLocator)).setDescription("Edit Alternative SSID Cancel button");
	}
	
	public static Element getAlternativeSsidProceedBtn() {
		String xpathLocator = "//android.widget.Button[@text='PROCEED']";
		return new Element(By.xpath(xpathLocator)).setDescription("Edit Alternative SSID Proceed button");
	}
	
	public static Element getAltSsidHelpTextView() {
		String id = "text_dialog_msg";
		return new Element(By.id(id)).setDescription("Alternative SSID help textbox");
	}
	
	public static Element getCurrentSsidTextbox() {
		String id = "textview_device_setting_wifi_network_name";
		return new Element(By.id(id)).setDescription("Current SSID textbox");
	}
	
	public static Element getReStartDeviceText() {
		String id = "text_restart_device";
		return new Element(By.id(id),"Restart Device").setDescription("Restart Device text");
	}
	
	
	
	/***ACTION***/
	@Step("Change Camera name")
	public static void changeCameraName(String name) {
		getCameraNameValue().click();
		getRenameTextbox().sendKeys(name);
		getDoneButton().click();
	}

	@Step("Enable Ceiling Mount")
	public static void enableCeilingMount() {
		getCeilingMountSwitch().setValue(true);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Ceiling Mount")
	public static void disableCeilingMount() {
		getCeilingMountSwitch().setValue(false);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Enable Motion Detection")
	public static void enableMotionDetection() {
		getMotionDetectionOnButton().click();
		closeCouldUpgradeTipIfAsked();
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Motion Detection")
	public static void disableMotionDetection() {
		getMotionDetectionOffButton().click();;
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Enable Sound Detection")
	public static void enableSoundDetection() {
		getSoundDetectionOnButton().setValue(true);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Sound Detection")
	public static void disableSoundDetection() {
		getSoundDetectionOffButton().setValue(true);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Enable Temperature")
	public static void enableTemperature() {
		getTemperatureSwitch().setValue(true);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Disable Temperature")
	public static void disableTemperature() {
		getTemperatureSwitch().setValue(false);
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Set Night Vision Auto")
	public static void setNightVisionAuto() {
		getNightVisionAutoBtn().click();
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Set Night Vision On")
	public static void setNightVisionOn() {
		getNightVisionOnBtn().click();
		TimeHelper.sleep(3 * 1000);
	}

	@Step("Set Night Vision Off")
	public static void setNightVisionOff() {
		getNightVisionOffBtn().click();
		TimeHelper.sleep(3 * 1000);
	}

	public static void changeMotionSensitivityLevel(TestConstant.motionLevel level) {
		Element element = getMotionSensitiveSeekbar();
		int tapX = 0;
		int tapY = element.getLocation().y + element.getSize().getHeight() / 2;
		if (level == TestConstant.motionLevel.Low)
			tapX = element.getLocation().x + 5;
		else if (level == TestConstant.motionLevel.Medium)
			tapX = element.getLocation().x + element.getSize().getWidth() / 2;
		else if (level == TestConstant.motionLevel.High)
			tapX = element.getLocation().x + element.getSize().getWidth() - 5;
		DriverManager.getDriver().tap(tapX, tapY);
		TimeHelper.sleep(3 * 1000);
	}

	public static void changeSoundSensitivityLevel(TestConstant.soundLevel level) {
		Element element = getSoundSensitiveSeekbar();
		int tapX = 0;
		int tapY = element.getLocation().y + element.getSize().getHeight() / 2;
		if (level == TestConstant.soundLevel.Low)
			tapX = element.getLocation().x + 5;
		else if (level == TestConstant.soundLevel.Medium)
			tapX = element.getLocation().x + element.getSize().getWidth() / 2;
		else if (level == TestConstant.soundLevel.High)
			tapX = element.getLocation().x + element.getSize().getWidth() - 5;
		DriverManager.getDriver().tap(tapX, tapY);
		TimeHelper.sleep(3 * 1000);
	}

	public static float getTimeZoneSystem() {
		return Float.parseFloat(getTimeZoneValue().getText());
	}

	/**
	 * Rule: iOffset > -4 && iOffset < 9 --> 50Hz, else --> 60Hz
	 * 
	 * @param timeZone
	 * @param hz
	 * @return
	 */
	public static boolean verifyLightSourceFrequencyByTimeZone(float timeZone, String hz) {
		if (timeZone > -4 && timeZone < 9) {
			return _50HZ.equals(hz);
		} else {
			return _60HZ.equals(hz);
		}
	}

	/**
	 * Try to get Light source frequency, if element not existed, swipe to
	 * bottom until it display.
	 * 
	 * @return
	 */
	public static String getLightSourceFrequencyValue() {
		if (getLightSourceFrequencyTextBox().getWebElement() != null) {
			return getLightSourceFrequencyTextBox().getText();
		}
		return "";
	}

	public static void changeLightSourceFrequency() {
		getLightSourceFrequencyTextBox().click();
		if (getFrequencyPanel().getWebElement() != null) {
			getFrequencyNotSelected().click();
		}
	}

	public static boolean verifyFrequencySelected(String hz) {
		if (getFrequencySelected().getWebElement() != null) {
			return getFrequencySelected().getText().equals(hz);
		}
		return false;
	}

	public static void clickOnZoneDetectionMotion() {
		getZoneDetectionMotion().click();
	}

	public static void clickAddNewZone() {
		getAddNewZoneButton().click();
	}

	public static void addZone(String zoneName) {
		clickAddNewZone();
		getEditZoneIcon(zoneName).click();
	}

	public static void editZone(String zoneName) {
		Dimension d = getZoneDefault().getSize();
		getEditZoneIcon(zoneName).click();
		dragAndDropZone();
		getEditZoneIcon(zoneName).click();
	}

	/**
	 * Hard code, because cannot get editable zone
	 */
	public static void dragAndDropZone() {
		DriverManager.getDriver().swipe(100, 558, 600, 558, 2000);
	}

	public static void clickRefreshZoneButton() {
		getRefreshZoneButton().click();
	}
	
	public static void removeAllZone() {
		String id = "delete_button";
		List<WebElement> lstDeleteIcon = getZoneListPanel().findElements(By.id(id));
		if (lstDeleteIcon.size() > 0) {
			for (WebElement ele : lstDeleteIcon) {
				// ele variable not use because after delete a zone, dom was refresh, but ele not update.
				getZoneListPanel().findElement(By.id(id)).click();
				clickConfirmButton();
			}
		}
	}
	
	public static boolean verifyZoneByName(String... zoneName) {
		String zoneInView = "//android.view.View";
		List<WebElement> listZoneInView = getZoneContainer().findElements(By.xpath(zoneInView));
		if(zoneName.length != listZoneInView.size()) {
			return false;
		}
		for(String zone : zoneName) {
			if(getZoneByName(zone).getWebElement() == null) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean verifyRemoveAllZoneSuccessful() {
		return getZoneListPanel().getWebElement() == null;
	}
	
	public static void clickUpdateZoneToCamera() {
		getUpdateZoneToCameraButton().click();
	}
	public static boolean verifyDeviceSharedBy(String owner) {
		return getSharedByTextView().getText().trim().equals(owner);
	}
	
	public static boolean verifySettingTitleDisplay() {
		return getCameraSettingTitle().getWebElement() != null;
	}
	
	public static void sendCameraLog() {
		getSendDeviceLog().click();
		getConfirmYesBtn().click();
		TimeHelper.sleep(1000);
		getGmailApp().click();
	}
	
	public static void deleteAllEvents() {
		getDeleteAllEventsTextView().click();
		// Confirm Delete events or not
		getConfirmYesBtn().click();
		// Confirm Events were deleted
		getConfirmYesBtn().click();
	}
	
	public static void removeCamera() {
		getDeleteCamIcon().click();
		getConfirmYesBtn().click();
	}
	
	public static boolean verifyDeviceSettingPage() {
		return getDeviceSettingPageTitle().getWebElement() != null;
	}
	
	public static String getCurrentStoragePlan() {
		return getCurrentPlanValue().getText();
	}
	
	public static boolean verifyTemperatureUnitInDeviceSetting(String unit) {
		return getHighTemperatureThreshold().getText().toUpperCase().endsWith(unit.toUpperCase()) &&
				getLowTemperatureThreshold().getText().toUpperCase().endsWith(unit.toUpperCase());
	}
	
	public static boolean verifyNewFirmwareAlreadyToUpdate() {
		return getFirmwareVersion().getText().contains("New firmware version");
	}
	
	public static boolean verifyLatestFirmwareVersion() {
		return getFirmwareVersion().getText().equals("Firmware Version");
	}
	
	/**
	 * Duong Nguyen
	 * Waiting for message Firmware upgrade is successful display.
	 * @return
	 */
	public static boolean verifyFirmwareUpgradeSuccessful() {
		boolean rs = false;
		if(getConfirmFirmwareUpgrade().getWebElement() != null && getFirmwareUpdateStatus().getText().equals("Firmware upgrade is successful.")) {
			clickConfirmButton();
			rs = verifyLatestFirmwareVersion();
		}
		return rs;
	}
	
	/**
	 * Manual update firmware by clicking via camera setting/detail/force upgrade
	 * @return
	 */
	public static boolean manualUpdateFirmware() {
		// Click on update new firmware
		getFirmwareVersion().click();
		// Click confirm button
		clickConfirmButton();
		// Verify firmware update successful
		return verifyFirmwareUpgradeSuccessful();
	}
	
	/**
	 * Thach Nguyen
	 * verify alternative SSID the help message content
	 * must click on the help button first
	 */
	public static void verifyAltSsidHelpMsg () {
		String expectedMsg = "The alternative SSID is a second SSID stored on the camera. When the camera can not find the original SSID, it will search for this alternative SSID and connect to this one once it is found.";
		String msg = getAltSsidHelpTextView().getText();
		getAlternativeSsidHelpOkBtn().click();
		Assert.assertEquals(expectedMsg,msg);
	}
	
	public static void refresh() {
		Point start = getCameraNameValue().getLocation();
		DriverManager.getDriver().swipe(start.x+10, start.y, start.x+10, start.y + 600, 1000);
	}
	
	public static void restartDevice() {
		getReStartDeviceText().click();
		clickConfirmButton();
	}
}