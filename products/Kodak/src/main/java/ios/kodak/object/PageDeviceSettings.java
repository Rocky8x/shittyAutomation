package ios.kodak.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageDeviceSettings extends PageBase {
	public static final String CHANGE_DEVICE_NAME_ERROR_MSG = "The device name must be 5 to 30 characters long";
	public static final String _50HZ = "50Hz";
	public static final String _60HZ = "60Hz";
	public static final String UPDATE_CLOUD_STORAGE_PLAN = "Upgrade your cloud storage plan to improve your monitoring experience!";
	public static final String NIGHT_MODE_AUTO = "Auto";
	public static final String MODE_ON = "On";
	public static final String MODE_OFF = "Off";
	public static final String MODE_SCHEDULE = "Schedule";
	public static final String TOP = "TOP";
	public static final String LEFT = "LEFT";
	public static final String BOTTOM = "BOTTOM";
	public static final String RIGHT = "RIGHT";
	public static final String ALTERNATIVE_HELP_MSG = "The alternative SSID is a second SSID stored on the camera. When the camera can not find the original SSID, it will search for this alternative SSID and connect to this one once it is found.";
	
	/*
	 * ELEMENTS
	 */

	public static Element getDetailsTitle() {
		String detailsPath = "//XCUIElementTypeOther[@name='Details']";
		return new Element(By.xpath(detailsPath)).setDescription("Details title");
	}

	public static Element getSettingsTitle() {
		String settings = "//XCUIElementTypeOther[@name='Settings']";
		return new Element(By.xpath(settings)).setDescription("Settings title");
	}

	public static Element getSharedBy() {
		String shareBy = "//XCUIElementTypeStaticText[@name='Shared By']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(shareBy), "Shared By").setDescription("Shared by");
	}

	public static Element getDeleteIcon() {
		String deleteIcon = "//XCUIElementTypeButton[@name='Delete']";
		return new Element(By.xpath(deleteIcon)).setDescription("Delete device icon");
	}

	public static Element getCameraNameEle() {
		String camName = "//XCUIElementTypeStaticText[@name='Change Camera Name']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(camName)).setDescription("Camera name");
	}

	public static Element getCurrentStoragePlanEle() {
		String plan = "//XCUIElementTypeStaticText[@name='Current Storage Plan']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(plan)).setDescription("Current storage plan");
	}

	public static Element getTemperatureSw() {
		String temp = "name BEGINSWITH[c] 'Temperature' AND type=='XCUIElementTypeSwitch'";
		return new Element(MobileBy.iOSNsPredicateString(temp)).setDescription("Temperature Switch");
	}

	public static List<WebElement> getTemperatureThreshold() {
		String temp = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Temperature'$]/XCUIElementTypeTextField";
		return DriverManager.getDriver().findElements(MobileBy.iOSClassChain(temp));
	}
	
	public static List<WebElement> getTempArrowUpIcon(){
		String arrowUp = "**/XCUIElementTypeButton[$name=='ic arrow up'$]";
		return DriverManager.getDriver().findElements(MobileBy.iOSClassChain(arrowUp));
	}
	
	public static List<WebElement> getTempArrowDownIcon(){
		String arrowDown = "**/XCUIElementTypeButton[$name=='ic arrow down'$]";
		return DriverManager.getDriver().findElements(MobileBy.iOSClassChain(arrowDown));
	}

	public static Element getDeviceSettingTitle() {
		String setting = "//XCUIElementTypeNavigationBar[@name='Device Settings']";
		return new Element(By.xpath(setting)).setDescription("Device Setting Title");
	}

	public static Element getDeleteAllEventBtn() {
		String btn = "Delete All Events";
		return new Element(By.name(btn)).setDescription("Delete all event");
	}

	public static Element getDeviceNameEle() {
		String deviceName = "//XCUIElementTypeStaticText[@name='Change Camera Name']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(deviceName)).setDescription("Device Name");
	}

	public static Element getChangeDeviceNameTextField() {
		String textField = "//XCUIElementTypeAlert[@name='Change Camera Name']//XCUIElementTypeTextField";
		return new Element(By.xpath(textField)).setDescription("Change Camera Name Field");
	}

	public static Element getMsgChangeCameraName() {
		String msg = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[@name='Change Camera Name']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(msg)).setDescription("Change camera name message");
	}

	public static Element getSendCameraLogField() {
		String camLog = "//XCUIElementTypeStaticText[@name='Send Camera Log']";
		return new Element(By.xpath(camLog)).setDescription("Send Camera Log");
	}

	public static Element getLightSourceFrequencyTitle() {
		String title = "//XCUIElementTypeStaticText[@name='Light Source Frequency']";
		return new Element(By.xpath(title)).setDescription("Light source Frequency text");
	}

	public static Element getLightSourceFrequencyValue() {
		String scrollTo = "Light Source Frequency";
		String value = "//XCUIElementTypeStaticText[@name='Light Source Frequency']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(value), scrollTo).setDescription("Light source Frequency value");
	}

	/**
	 * When user click change light source frequency, a panel display to choice
	 * a new value of light source
	 */
	public static List<WebElement> getListLightSourceFrequencyAvailable() {
		String xpath = "//XCUIElementTypeSheet[@name='Light Source Frequency']//XCUIElementTypeButton[contains(@name, 'Hz')]";
		return DriverManager.getDriver().findElements(By.xpath(xpath));
	}

	public static Element getTimeZoneValue() {
		String timezone = "//XCUIElementTypeStaticText[@name='Time Zone']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(timezone)).setDescription("Time zone");
	}

	public static Element getSoundSensitiveSlider() {
		String slider = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Sound Sensitivity'$]/XCUIElementTypeSlider";
		return new Element(MobileBy.iOSClassChain(slider)).setDescription("Sound Sensitivity slider");
	}

	public static Element getSoundDetectionSwitch(String mode) {
		String sw = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Sound Detection'$]/XCUIElementTypeSegmentedControl/XCUIElementTypeButton[$name=='%s'$]";
		return new Element(MobileBy.iOSClassChain(String.format(sw, mode))).setDescription("Sound detection " +  mode);
	}

	public static Element getMotionDetectionSwitch(String mode) {
		String sw = "//XCUIElementTypeStaticText[@name='Motion Detection']/following-sibling::XCUIElementTypeSegmentedControl/XCUIElementTypeButton[@name='%s']";
		return new Element(By.xpath(String.format(sw, mode))).setDescription("Motion detection " + mode);
	}

	public static Element getUpdateCloudStoragePlanMsg() {
		String ele = "//XCUIElementTypeStaticText[@name='%s']";
		return new Element(By.xpath(String.format(ele, UPDATE_CLOUD_STORAGE_PLAN)), 5)
				.setDescription("Update cloud storage plan");
	}

	public static Element getMotionSensitiveBtn(String level) {
		String btn = "//XCUIElementTypeButton[@name='%s']";
		return new Element(By.xpath(String.format(btn, level))).setDescription("Sensitivity level: " + level);
	}

	public static Element getCeilingMountSwitch() {
		String scrollTo = "Ceiling Mount";
		String sw = "//XCUIElementTypeSwitch[@name='Ceiling Mount']";
		return new Element(By.xpath(sw), scrollTo).setDescription("Ceiling mount switch");
	}

	public static Element getNightVersion(String mode) {
		String btn = "	//XCUIElementTypeButton[@name='%s']";
		return new Element(By.xpath(String.format(btn, mode))).setDescription("Night mode: " + mode);
	}
	
	public static Element getZoneSettingText() {
		String zone = "//XCUIElementTypeStaticText[@name='Define Zone to Detect Motion']";
		return new Element(By.xpath(zone)).setDescription("Define Zone to Detect Motion");
	}
	
	public static List<WebElement> getListZones(){
		String lstZone = "//XCUIElementTypeScrollView/XCUIElementTypeOther";
		return DriverManager.getDriver().findElements(By.xpath(lstZone));
	}
	
	public static Element getRemoveZoneIcon() {
		String removeIcon = "ic removezone";
		return new Element(By.name(removeIcon)).setDescription("Remove zone");
	}
	
	public static Element getRefreshBtn() {
		String btn = "//XCUIElementTypeButton[@name='Refresh']";
		return new Element(By.xpath(btn)).setDescription("Refresh button");
	}
	
	public static Element getAddNewZoneBtn() {
		String addNew = "//XCUIElementTypeStaticText[@name='Add New Zone']/preceding-sibling::XCUIElementTypeButton";
		return new Element(By.xpath(addNew)).setDescription("Add New Zone");
	}
	
	public static Element getEditZoneBtn() {
		String btn = "//XCUIElementTypeButton[@name='ic editzone']";
		return new Element(By.xpath(btn)).setDescription("Edit Zone");
	}
	
	public static Element getSaveZoneBtn() {
		String saveBtn = "//XCUIElementTypeButton[@name='ic editzone'][@value='1']";
		return new Element(By.xpath(saveBtn)).setDescription("Save Zone");
	}
	
	public static Element getApplyZoneBtn() {
		String btn = "//XCUIElementTypeButton[@name='Apply Zone to Camera']";
		return new Element(By.xpath(btn)).setDescription("Apply Zone");
	}
	
	public static Element getNewFirmwareVersionText() {
		String ele = "//XCUIElementTypeStaticText[contains(@name, 'New firmware version')]";
		return new Element(By.xpath(ele)).setDescription("New Firmware");
	}
	
	public static Element getFirmwareVersionText() {
		String name = "Firmware Version";
		return new Element(By.name(name), 400).setDescription("Firmware version");
	}
	
	public static Element getAlternativeHelpIcon() {
		String helpIcon = "ic hint question";
		return new Element(MobileBy.AccessibilityId(helpIcon)).setDescription("Alternative hint icon");
	}
	
	public static Element getAlternativeHelpMsg() {
		String msg = "**/XCUIElementTypeOther[$type=='XCUIElementTypeStaticText' AND name=='Help'$]/XCUIElementTypeTextView";
		return new Element(MobileBy.iOSClassChain(msg)).setDescription("Help message");
	}
	
	public static Element getAlternativeSSIDText() {
		String alternative = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Alternative SSID'$]/XCUIElementTypeButton[$!name BEGINSWITH 'ic hint'$]";
		return new Element(MobileBy.iOSClassChain(alternative)).setDescription("Alternative");
	}

	public static Element getEditDetectionScheduleText() {
		String schedule = "type='XCUIElementTypeStaticText' AND name='Edit Detection Schedule'";
		return new Element(MobileBy.iOSNsPredicateString(schedule)).setDescription("Edit Detection Schedule");
	}
	
	public static Element getAddScheduleButton() {
		return new Element(MobileBy.name("Add")).setDescription("Add schedule button");
	}
	
	public static List<WebElement> getListScheduleEle(){
		String ele = "**/XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText";
		return DriverManager.getDriver().findElements(MobileBy.iOSClassChain(ele));
	}
	
	public static Element getScheduleCreated() {
		String schedule = "**/XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeOther/XCUIElementTypeOther";
		return new Element(MobileBy.iOSClassChain(schedule)).setDescription("Schedule");
	}
	
	public static Element getGotoScheduleDefinitionBtn() {
		return new Element(MobileBy.name("Goto Schedule Definition"), 5).setDescription("Goto Schedule Definition");
	}
	
	public static Element getRestartDevice() {
		String xpath = "//XCUIElementTypeStaticText[@name='Restart Device']";
		return new Element(By.xpath(xpath), "Restart Device").setDescription("Restart Device");
	}
	
	/*
	 * ACTIONS
	 */

	public static boolean isDisplayed() {
		return getDeviceSettingTitle().getWebElement() != null ? true : false;
	}

	public static void gotoDetailsCamera() {
		getDetailsTitle().click();
	}

	public static void gotoCameraSettingDetail() {
		getSettingsTitle().click();
	}

	public static boolean verifySettingTitleDisplay() {
		return getSettingsTitle().getWebElement() != null;
	}

	public static String getUserShared() {
		return getSharedBy().getText();
	}

	public static void DeleteDevice() {
		getDeleteIcon().click();
		clickOkButton();
	}

	public static String getCameraName() {
		return getCameraNameEle().getText();
	}

	public static String getCurrentStoragePlan() {
		return getCurrentStoragePlanEle().getText();
	}
	
	public static void restartDevice() {
		getRestartDevice().click();
		clickYesButton();
	}

	/**
	 * get map camera name and storage plan (key - camera name : value - current
	 * storage plan)
	 * 
	 * @return map
	 */
	public static HashMap<String, String> getCurrentStorage() {
		HashMap<String, String> storageMap = new HashMap<String, String>();
		for (WebElement ele : PageDashboard.getListCameraSettingIcon()) {
			ele.click();
			gotoDetailsCamera();
			storageMap.put(getCameraName(), getCurrentStoragePlan());
			clickBackButton();
		}

		return storageMap;
	}

	public static void enableTemperatureSetting() {
		if (getTemperatureSw().getWebElement() != null && getTemperatureSw().getAttribute("value").equals("0")) {
			getTemperatureSw().click();
		}
	}
	
	public static void disableTemperatureSetting() {
		if (getTemperatureSw().getWebElement() != null && getTemperatureSw().getAttribute("value").equals("1")) {
			getTemperatureSw().click();
		}
	}

	public static boolean verifyTemperatureSettingEnable(boolean value) {
		if (value) {
			return getTemperatureSw().getAttribute("value").equals("1");
		}else {
			return getTemperatureSw().getAttribute("value").equals("0");
		}
	}
	
	public static boolean verifyTemperatureUnit(String unit) {
		for (WebElement ele : getTemperatureThreshold()) {
			if (!ele.getText().contains(unit))
				return false;
		}
		return true;
	}

	public static void deleteAllEvent() {
		while (getDeleteAllEventBtn().getWebElement() != null) {
			Element ele = getDeleteAllEventBtn();
			if(ele.getAttribute("visible").equals("true")) {
				ele.click();
				break;
			}else {
				swipeTopToBottom();
			}
		}
		
		clickYesButton();
		clickOkButton();
	}

	public static String getDeviceName() {
		return getDeviceNameEle().getText();
	}

	public static void clickOnDeviceName() {
		getDeviceNameEle().click();
	}

	public static void inputCameraName(String name) {
		getChangeDeviceNameTextField().inputValue(name);
	}

	public static void changeCameraName(String value) {
		clickOnDeviceName();
		inputCameraName(value);
		clickOkButton();
	}

	public static String getChangeCameraNameWarningMessage() {
		String msg = getMsgChangeCameraName().getText();
		clickOkButton();
		return msg;
	}

	public static void clickSendCameraLog() {
		getSendCameraLogField().click();
	}

	public static String changeLightSourceFrequency(String currentSource) {
		String source = currentSource;
		getLightSourceFrequencyTitle().click();
		for (WebElement ele : getListLightSourceFrequencyAvailable()) {
			source = ele.getText();
			if (!source.equalsIgnoreCase(currentSource)) {
				ele.click();
				TimeHelper.sleep(5000);
				break;
			}
		}
		return source;
	}

	public static String getCurrentLightSourceFrequency() {
		return getLightSourceFrequencyValue().getText();
	}

	public static float getTimeZoneSystem() {
		return Float.parseFloat(getTimeZoneValue().getText());
	}

	/**
	 * Rule: iOffset > - 4 && iOffset < 9 --> 50Hz, else --> 60Hz
	 * 
	 * @param timeZone
	 * @param iOffset
	 * @return
	 */
	public static boolean verifyLightSourceFrequencyByTimeZone(float timeZone, String iOffset) {
		if (timeZone > -4 && timeZone < 9) {
			return _50HZ.equals(iOffset);
		} else {
			return _60HZ.equals(iOffset);
		}
	}

	/**
	 * Get current sound sensitivity level
	 * 
	 * @param ele
	 * @return location of sound sensitivity level (low - medium - high)
	 */
	private static int getCurrentSoundSensitivityLevel(Element ele) {
		int startX = 0;
		if (ele.getAttribute("value").equals("0%")) {
			startX = ele.getLocation().x + 5;
		} else if (ele.getAttribute("value").equals("50%")) {
			startX = ele.getLocation().x + ele.getSize().getWidth() / 2;
		} else if (ele.getAttribute("value").equals("100%")) {
			startX = ele.getLocation().x + ele.getSize().getWidth() - 5;
		}
		return startX;
	}

	public static void changeSoundSensitivityLevel(TestConstant.soundLevel level) {
		Element ele = getSoundSensitiveSlider();
		int startX = getCurrentSoundSensitivityLevel(ele);
		int startY = ele.getLocation().y + ele.getSize().getHeight() / 2;
		int endX = 0;
		if (level == TestConstant.soundLevel.Low) {
			endX = ele.getLocation().x + 5;
		} else if (level == TestConstant.soundLevel.Medium) {
			endX = ele.getLocation().x + ele.getSize().getWidth() / 2;
		} else if (level == TestConstant.soundLevel.High) {
			endX = ele.getLocation().x + ele.getSize().getWidth() - 5;
		}

		DriverManager.getDriver().swipe(startX, startY, endX, startY, 2000);
	}

	public static void setMotionDetection(String value) {
		getMotionDetectionSwitch(value).click();
		if (!value.equals(MODE_OFF)) {
			if(value.equals(MODE_SCHEDULE)) {
				clickGotoScheduleDefinition();
				if(getDoneBtn().getWebElement() != null) {
					clickDoneButton();
				}
			}
			closeUpdateCloudStoragePlan();
		}
	}

	public static boolean verifyMotionDetectionEnable(String value) {
		return "1".equals(getMotionDetectionSwitch(value).getAttribute("value"));
	}
	
	public static boolean verifyMotionDetectionDisable(String value) {
		return !"1".equals(getMotionDetectionSwitch(value).getAttribute("value"));
	}

	public static boolean verifySoundDetection(String value) {
		return "1".equals(getSoundDetectionSwitch(value).getAttribute("value"));
	}
	
	public static boolean verifySoundDetectionDisable(String value) {
		return !"1".equals(getSoundDetectionSwitch(value).getAttribute("value"));
	}

	public static void setSoundDetection(String value) {
		getSoundDetectionSwitch(value).click();
	}

	public static void closeUpdateCloudStoragePlan() {
		if (getUpdateCloudStoragePlanMsg().getWebElement() != null) {
			clickClose();
		}
	}

	public static void changeMotionSensitivityLevel(TestConstant.soundLevel level) {
		getMotionSensitiveBtn(level.toString()).click();
	}

	public static void enableCeilingMount(boolean value) {
		String enableMode = "1";
		if (value)
			enableMode = "0";

		if (getCeilingMountSwitch().getAttribute("value").equals(enableMode)) {
			getCeilingMountSwitch().click();
		}
	}

	public static boolean verifyCeilingMoungEnable(boolean value) {
		String enableMode = "0";
		if (value)
			enableMode = "1";

		if (!getCeilingMountSwitch().getAttribute("value").equals(enableMode))
			return false;

		return true;
	}

	/**
	 * Enable / disable night mode
	 * 
	 * @param mode: Auto - On - Off
	 * @param enable: true - Enable / false - Disable
	 */
	public static void enableNightMode(String mode, boolean enable) {
		String enableMode = "1";
		if (enable) {
			if (!enableMode.equals(getNightVersion(mode).getAttribute("value"))) {
				getNightVersion(mode).click();
			}
		} else {
			if (enableMode.equals(getNightVersion(mode).getAttribute("value"))) {
				getNightVersion(mode).click();
			}
		}
	}

	/**
	 * Verify Night mode enable
	 * 
	 * @param mode: Auto - On - Off
	 * @param value: true - Enable / false - Disable
	 * @return
	 */
	public static boolean verifyNightModeEnable(String mode, boolean value) {
		String enableMode = "1";
		if (value) {
			return enableMode.equals(getNightVersion(mode).getAttribute("value"));
		}else {
			return !enableMode.equals(getNightVersion(mode).getAttribute("value"));
		}
	}
	
	public static void clickDefineZone() {
		getZoneSettingText().click();
	}

	public static int getListZoneDefine() {
		return getListZones().size();
	}
	
	public static void removeAllZones() {
		int size = getListZoneDefine();
		
		for(int i = 0; i < size; i ++) {
			getRemoveZoneIcon().click();
			clickOkButton();
			clickRefreshButton();
		}
	}
	
	public static void clickRefreshButton() {
		getRefreshBtn().click();
	}
	
	public static void clickAddNewZone() {
		getAddNewZoneBtn().click();
	}
	
	public static void reSizeZone() {
		WebElement lastZone = getListZones().get(getListZones().size()-1);
		
		Dimension d = lastZone.getSize();
		WebElement topRight = lastZone.findElements(By.xpath("//XCUIElementTypeOther")).get(2);
		
		int tapX1 = topRight.getLocation().x + topRight.getSize().getWidth() / 2;
		int tapY1 = topRight.getLocation().y + topRight.getSize().getHeight() / 2;
				
		int tapX2 = lastZone.getLocation().x + lastZone.getSize().getWidth() / 2;
		int tapY2 = lastZone.getLocation().y + lastZone.getSize().getHeight() / 2;
		
		DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 1000);
		clickRefreshButton();
		
	}
	
	public static void moveZone(String location) {
		WebElement lastZone = getListZones().get(getListZones().size()-1);
		
		Dimension d = lastZone.getSize();
		List<WebElement> lstCorner = lastZone.findElements(By.xpath("//XCUIElementTypeOther"));
		
		int tapX1 = lstCorner.get(0).getLocation().x + ((lstCorner.get(1).getLocation().x - lstCorner.get(0).getLocation().x) / 2);
		int tapY1 = lstCorner.get(0).getLocation().y + ((lstCorner.get(1).getLocation().y - lstCorner.get(0).getLocation().y) / 2);
		int tapX2, tapY2;
		switch (location) {
		case TOP:
			tapX2 = lstCorner.get(0).getLocation().x + ((lstCorner.get(1).getLocation().x - lstCorner.get(0).getLocation().x) / 2);
			tapY2 = lstCorner.get(0).getLocation().y - ((lstCorner.get(1).getLocation().y - lstCorner.get(0).getLocation().y) * 2);
			break;

		case LEFT:
			tapX2 = lstCorner.get(0).getLocation().x - ((lstCorner.get(1).getLocation().x - lstCorner.get(0).getLocation().x) * 2);
			tapY2 = lstCorner.get(0).getLocation().y + ((lstCorner.get(1).getLocation().y - lstCorner.get(0).getLocation().y) / 2);
			break;
		
		case RIGHT:
			tapX2 = lstCorner.get(0).getLocation().x + ((lstCorner.get(1).getLocation().x - lstCorner.get(0).getLocation().x) * 2);
			tapY2 = lstCorner.get(0).getLocation().y + ((lstCorner.get(1).getLocation().y - lstCorner.get(0).getLocation().y) / 2);
			break;
			
		case BOTTOM:
			tapX2 = lstCorner.get(0).getLocation().x + ((lstCorner.get(1).getLocation().x - lstCorner.get(0).getLocation().x) / 2);
			tapY2 = lstCorner.get(0).getLocation().y + ((lstCorner.get(1).getLocation().y - lstCorner.get(0).getLocation().y) * 2);
			break;
			
		default:
			tapX2 = lstCorner.get(0).getLocation().x - ((lstCorner.get(1).getLocation().x - lstCorner.get(0).getLocation().x) * 2);
			tapY2 = lstCorner.get(0).getLocation().y + ((lstCorner.get(1).getLocation().y - lstCorner.get(0).getLocation().y) / 2);
			break;
		}
		
		DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 1000);
	}
	
	public static void clickSaveZone() {
		getSaveZoneBtn().click();
	}
	
	public static void clickApplyZone() {
		getApplyZoneBtn().click();
	}
	
	public static void clickEditZone() {
		getEditZoneBtn().click();
	}
	
	public static boolean verifyNewFirmwareAvailableForUpdate() {
		return getNewFirmwareVersionText().getWebElement() != null;
	}
	
	public static void clickUpdateFirmware() {
		getNewFirmwareVersionText().click();
	}
	
	public static boolean verifyFirmwareUpdateSuccessful() {
		return getFirmwareVersionText().getWebElement() != null;
	}
	
	public static boolean verifyTemperatureSetting(String lowTemp, String highTemp) {
		List<WebElement> lstTemp = getTemperatureThreshold();
		if (!lstTemp.get(0).getText().contains(lowTemp))
			return false;
		if (!lstTemp.get(1).getText().contains(highTemp))
			return false;
		
		return true;
	}
	
	private static void clickArrowIcon(WebElement ele, int timesClick) {
		for(int i = 0; i < timesClick; i++) {
			ele.click();
		}
	}
	
	/**
	 * Verify Maximum temperature setting
	 * @param minLowTemp: Maximum value expected in Low Temperature
	 * @param minHighTemp: Maximum value expected in High Temperature
	 */
	public static boolean verifyMaximumTempSetting(String maxLowTemp, String maxHighTemp) {
		List<WebElement> lstTemp = getTemperatureThreshold();
		List<WebElement> listArrowUpIcon = getTempArrowUpIcon();
		
		int currentLowTemp = Integer.parseInt(lstTemp.get(0).getText().substring(0, 2));
		clickArrowIcon(listArrowUpIcon.get(0), Integer.parseInt(maxHighTemp) - currentLowTemp + 1);
		
		int currentHighTemp = Integer.parseInt(lstTemp.get(1).getText().substring(0,  2));
		clickArrowIcon(listArrowUpIcon.get(1), Integer.parseInt(maxHighTemp) - currentHighTemp + 1);
		
		return verifyTemperatureSetting(maxLowTemp, maxHighTemp);
	}
	/**
	 * Verify Minimum temperature setting
	 * @param minLowTemp: Minimum value expected in Low Temperature
	 * @param minHighTemp: Minimum value expected in High Temperature
	 */
	public static boolean verifyMinimumTempSetting(String minLowTemp, String minHighTemp) {
		List<WebElement> lstTemp = getTemperatureThreshold();
		List<WebElement> listArrowDownIcon = getTempArrowDownIcon();
		
		int currentLowTemp = Integer.parseInt(lstTemp.get(0).getText().substring(0, 2));
		clickArrowIcon(listArrowDownIcon.get(0), currentLowTemp - Integer.parseInt(minLowTemp) + 1);
		
		int currentHighTemp = Integer.parseInt(lstTemp.get(1).getText().substring(0, 2));
		clickArrowIcon(listArrowDownIcon.get(1), currentHighTemp - Integer.parseInt(minHighTemp) + 1);
		
		return verifyTemperatureSetting(minLowTemp, minHighTemp);
	}
	
	public static void clickAlternativeHelpIcon() {
		getAlternativeHelpIcon().click();
	}
	
	public static String getAlternativeHelpMessage() {
		return getAlternativeHelpMsg().getText();
	}
	
	public static void clickChangeAlternativeSSID() {
		getAlternativeSSIDText().click();
	}
	
	public static Element getAlternativeSSID() {
		String ssid = "**/XCUIElementTypeTextField";
		return new Element(MobileBy.iOSClassChain(ssid)).setDescription("SSID");
	}
	
	public static Element getAlternativeSSIdPwd() {
		String ssidPwd = "**/XCUIElementTypeSecureTextField";
		return new Element(MobileBy.iOSClassChain(ssidPwd));
	}
	
	public static void ChangeAltSSID(String ssid, String pwd) {
		clickChangeAlternativeSSID();
		getAlternativeSSID().sendKeys(ssid);
		getAlternativeSSIdPwd().sendKeys(pwd);
		clickProceedButton();
	}
	
	public static void openDetectionScheduleSetting() {
		getEditDetectionScheduleText().click();
	}
	
	public static void clickAddNewDetectionSchedule() {
		getAddScheduleButton().click();
	}
	
	public static List<String> getListDetectionSchedule() {
		List<String> lstSchedule = new ArrayList<String>();
		for(WebElement ele : getListScheduleEle()) {
			lstSchedule.add(ele.getText());
		}
		
		return lstSchedule;
	}
	
	public static boolean verifyDetectionScheduleExisted(String schedule) {
		for(WebElement ele : getListScheduleEle()) {
			if (ele.getText().contains(schedule)) {
				return true;
			}
		}
		return false;
	}
	
	public static void clickExistedSchedule() {
		DriverManager.getDriver().tap(getScheduleCreated().getLocation());
	}
	
	public static void clickGotoScheduleDefinition() {
		if (getGotoScheduleDefinitionBtn().getWebElement() != null) {
			getGotoScheduleDefinitionBtn().click();
		}
	}
}
