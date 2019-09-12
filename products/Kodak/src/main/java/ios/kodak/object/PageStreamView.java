package ios.kodak.object;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;
import com.cinatic.object.Terminal;

import io.appium.java_client.MobileBy;

public class PageStreamView extends PageBase{
	
	public static final String PLAY_CLIP_ERROR_MSG = "Some errors encountered while playback the clip. Please retry.";
	public static final String SDCARD = "sdcard";
	public static final String CLOUD = "cloud";
	public static int locationX;
	public static int locationY;
	public static Point location;
	public static boolean flagAutoTutorialClosed = false;

	/*
	 * ELEMENTS
	 */
	
	public static Element getTemperatureUnit() {
		String temUnit = "//XCUIElementTypeImage[@name='camera_menu_icon_humidity']/preceding-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(temUnit)).setDescription("Temperature");
	}
	
	public static Element getHumidity() {
		String humidity = "//XCUIElementTypeImage[@name='camera_menu_icon_humidity']/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(humidity)).setDescription("Humidity");
	}
	
	public static Element getMoreIcon() {
		return new Element(By.name("ic menu more")).setDescription("More menu icon");
	}
	
	public static Element getNoEventText() {
		String event = "type == 'XCUIElementTypeStaticText' AND name == 'No Event Available'";
		return new Element(MobileBy.iOSNsPredicateString(event)).setDescription("No Event Available");
	}
	
	public static Element getNoEventImage() {
		return new Element(By.name("ic_calender_menubar")).setDescription("No Event Image");
	}
	
	public static Element getEditMotionDetectionSetting() {
		String editBtn = "type == 'XCUIElementTypeButton' AND name == 'Edit Sound/Motion Detection Settings'";
		return new Element(MobileBy.iOSNsPredicateString(editBtn)).setDescription("Edit Sound/Motion Detection Settings");
	}
	
	public static Element getWarningSDCardStorageMessage() {
		return new Element(By.name("Warning"), 10).setDescription("Sd-card warning message");
	}
	
	public static Element getStreamModeEle() {
		String mode = "type=='XCUIElementTypeStaticText' AND name BEGINSWITH 'PS ' OR name BEGINSWITH 'PL '";
		return new Element(MobileBy.iOSNsPredicateString(mode)).setDescription("Stream mode");
		
	}
	
	public static Element getCloudPlayVideoBtn() {
		String btn = "**/XCUIElementTypeTable/XCUIElementTypeCell[$type == 'XCUIElementTypeImage' AND name == 'ic_camera_menu_cloud'$]/XCUIElementTypeButton";
		return new Element(MobileBy.iOSClassChain(btn)).setDescription("Cloud play button");
	}
	
	public static Element getSDCardPlayVideoBtn() {
		String btn = "**/XCUIElementTypeTable/XCUIElementTypeCell[$type == 'XCUIElementTypeImage' AND name == 'ic_camera_menu_card'$]/XCUIElementTypeButton";
		return new Element(MobileBy.iOSClassChain(btn)).setDescription("Sdcard play button");
	}
	
	public static Element getPlayMotionClipErrorMsg() {
		String msg = "//XCUIElementTypeButton[@name='replay icon']/preceding-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(msg)).setDescription("Error message");
	}
	
	public static Element getReplayIcon() {
		return new Element(By.name("replay icon")).setDescription("Replay button");
	}
	
	public static Element getRemainTime() {
		String remainTime = "//XCUIElementTypeSlider/following-sibling::XCUIElementTypeStaticText";
		return new Element(By.xpath(remainTime)).setDescription("Remain Time");
	}
	
	public static Element getConfirmButton() {
		return new Element(By.name("OK"), 5).setDescription("OK button");
	}
	
	public static Element getContinueStreamBtn() {
		return new Element(By.name("Continue View"), 10).setDescription("Continue View");
	}
	
	public static Element getTakePhotoButton() {
		String btn = "ic_cam";
		return new Element(MobileBy.AccessibilityId(btn)).setDescription("Take Photos");
	}
	
	public static Element getStartRecordIcon() {
		String icon = "ic_record";
		return new Element(MobileBy.AccessibilityId(icon)).setDescription("Start Record Icon");
	}
	
	public static Element getMelodyIconOff() {
		String musicIcon = "ic_music_off";
		return new Element(MobileBy.AccessibilityId(musicIcon)).setDescription("Melody Icon Off");
	}
	
	public static Element getMelodyIconOn() {
		String musicIcon = "ic_music_on";
		return new Element(MobileBy.AccessibilityId(musicIcon)).setDescription("Melody Icon On");
	}
	
	public static Element getMelodyByName(int name) {
		String melody = "name=='Melody %s' AND visible==true";
		return new Element(MobileBy.iOSNsPredicateString(String.format(melody, name))).setDescription("Melody: " + name);
	}
	
	public static Element getTalkBackOff() {
		return new Element(MobileBy.AccessibilityId("ic_mic_off")).setDescription("Talk back off");
	}
	
	public static Element getTalkBackOn() {
		return new Element(MobileBy.AccessibilityId("ic_mic_on")).setDescription("Talk back on");
	}
	
	public static Element getStopMelodyBtn() {
		return new Element(By.name("Stop melody")).setDescription("Stop melody");
	}
	
	public static Element getStopRecordingIcon() {
		return new Element(By.name("ic_recording")).setDescription("Stop recording");
	}
	
	public static Element getSpeakerOn() {
		return new Element(MobileBy.AccessibilityId("ic_speaker_on")).setDescription("Speaker on");
	}
	
	public static Element getSpeakerOff() {
		return new Element(MobileBy.AccessibilityId("ic_speaker_off")).setDescription("Speaker off");
	}
	
	public static Element getDownloadButton() {
		return new Element(By.name("ic download"), 10).setDescription("Download button");
	}
	
	public static List<WebElement> getMotionDetectionText() {
		String icon = "//XCUIElementTypeCell/XCUIElementTypeButton[@name='Button' and @visible='true']/preceding-sibling::XCUIElementTypeStaticText[contains(@name, 'Motion detected')]";
		return DriverManager.getDriver().findElements(By.xpath(icon));
	}
	
	public static Element getDownloadMotionMsg() {
		String msg = "Download complete Video saved to Kodak Gallery";
		return new Element(By.name(msg)).setDescription("Download message");
	}
	
	public static Element getZoomInIcon() {
		String icon = "ic_zoom_in";
		return new Element(By.name(icon)).setDescription("Zoom In");
	}
	
	public static Element getZoomOutIcon() {
		String icon = "ic_zoom_out";
		return new Element(By.name(icon)).setDescription("Zoom Out");
	}
	
	public static Element getStreamingScreen() {
		String streamview = "**/XCUIElementTypeScrollView/XCUIElementTypeImage";
		return new Element(MobileBy.iOSClassChain(streamview)).setDescription("Streaming Screen");
	}
	
	/*
	 * ACTIONS
	 */
	
	public static String getTemperatureInStreamView() {
		return StringHelper.getNumberInString(getTemperatureUnit().getText()).get(0).toString();
	}
	
	public static String getHumidityInStreamView() {
		return StringHelper.getNumberInString(getHumidity().getText()).get(0).toString();
	}
	
	public static boolean verifyTemperatureUnitInStreamView(String unit) {
		return getTemperatureUnit().getText().contains(unit);
	}
	
	public static boolean verifyTemperatureSyncWithAppAndCamera(String temp) {
		String temperature = getTemperatureInStreamView();
		Log.info("Temperature in app: " + temperature);
		return temp.contains(temperature);
	}
	
	public static boolean isDisplayed() {
		return getMoreIcon().getWebElement() != null ? true : false;
	}
	
	public static boolean verifyHumiditySyncWithAppAndCamera(String humi) {
		String humidity = getHumidityInStreamView();
		Log.info("Humidity on app: " + humidity);
		return humi.equals(humidity);
	}
	
	public static boolean verifyNoEventAvailable() {
		return getNoEventText().getWebElement() != null || getNoEventImage().getWebElement() != null;
	}
	
	public static void clickEditSoundMotionDetection() {
		getEditMotionDetectionSetting().click();
	}
	
	public static String getStreamMode(int timeOut) {
		Log.info("Get stream mode");
		String mode = "";		
		while (timeOut >= 0) {
			try {
				mode = getStreamModeEle().getText();
			} catch (Exception e) {
				mode = "";
			}
			if (mode.contains("OK"))
				break;
			timeOut--;
//			TimeHelper.sleep(1000);
		}
		return mode;
	}	
	
	/**
	 * Duong Nguyen
	 * Swipe down 10 times and verify video storage in sdcard/cloud exist
	 * @return
	 */
	public static boolean getMotionVideo(String clipType) {
		int count = 10;
		boolean rs = false;
		// Verify page has no event
		if(verifyNoEventAvailable()) {
			return false;
		}
		// Verify cloud motion clip
		if (clipType.equalsIgnoreCase(CLOUD)) {
			while(getCloudPlayVideoBtn().getWebElement() == null && count > 0) {
				swipeTopToBottom();
				count --;
			}

			if(getCloudPlayVideoBtn().getWebElement() != null) {
				rs = true;
			}
		}else {
			// Verify sdcard motion clip
			while(getSDCardPlayVideoBtn().getWebElement() == null && count > 0) {
				swipeTopToBottom();
				count --;
			}

			if(getSDCardPlayVideoBtn().getWebElement() != null) {
				rs = true;
			}
		}
		
		return rs;
	}
	
	public static void playCloudMotionClip() {
		getCloudPlayVideoBtn().click();
	}
	
	public static void playSDcardMotionClip() {
		getSDCardPlayVideoBtn().click();
	}
	
	public static String getRemainTimeAfterPlayClip() {
		return getRemainTime().getText();
	}
	
	public static String verifyPlayMotionClipSuccessfull() {
		if (getPlayMotionClipErrorMsg().getWebElement() != null) {
			if (getPlayMotionClipErrorMsg().getText().equals(PLAY_CLIP_ERROR_MSG)){
				return getPlayMotionClipErrorMsg().getText();
			}
		}else if(!getRemainTime().getText().equals("0:00")) {
			return "Play clip unsuccessful, remain time is: " + getRemainTime().getText();
		}
		return "";
	}
	
	public static void closeWarningCloudStorageUpdate() {
		if(getWarningSDCardStorageMessage().getWebElement() != null) {
			getConfirmButton().click();
		}
	}
	
	public static void continueStreamViewIfAsk() {
		if(getContinueStreamBtn().getWebElement() != null) {
			getContinueStreamBtn().click();
		}
	}
	
	public static void clickMoreMenuIcon() {
		getMoreIcon().click();
		TimeHelper.sleep(5000);
	}
	/*
	 * All icon in stream menu cannot click by Appium, just tap on location
	 */
	public static void openMelody() {
		Log.info("Open melody");
		
		clickMoreMenuIcon();
		TimeHelper.sleep(10000);
		swipeUpMiniMenu();
		
		if(getMelodyIconOff().getWebElement() != null) {
			location = getMelodyIconOff().getLocation();
			DriverManager.getDriver().tap(location);
		}else {
			location = getMelodyIconOff().getLocation();
			DriverManager.getDriver().tap(location);
		}
	}
	
	public static void playMelody(int number) {
		getMelodyByName(number).click();
	}
	/*
	 * All icon in stream menu cannot click by Appium, just tap on location
	 */
	public static void clickMelodyIconOn() {
		if (location.x != 0 & location.y != 0)
			DriverManager.getDriver().tap(location);
		else
			DriverManager.getDriver().tap(getMelodyIconOn().getLocation());
	}
	
	public static void clickOnMelodyIconOff() {
		DriverManager.getDriver().tap(getMelodyIconOff().getLocation());
	}
	
	public static void clickStopMelody() {
		getStopMelodyBtn().click();
	}
	/*
	 * All icon in stream menu cannot click by Appium, just tap on location
	 */
	public static void clickStartRecording(int duration) {
		location = getStartRecordIcon().getLocation();
		DriverManager.getDriver().tap(location);
		TimeHelper.sleep(duration * 1000);
	}
	/*
	 * All icon in stream menu cannot click by Appium, just tap on location
	 */
	public static void clickStopRecording() {
		if(getStopRecordingIcon().getWebElement() != null) {
			DriverManager.getDriver().tap(location);
		}
	}
	/*
	 * All icon in stream menu cannot click by Appium, just tap on location
	 */
	public static void enableSpeaker(boolean value) {
		if (value) {
			if(getSpeakerOff().getWebElement() != null)
				DriverManager.getDriver().tap(getSpeakerOff().getLocation());
		}else {
			if(getSpeakerOn().getWebElement() != null)
				DriverManager.getDriver().tap(getSpeakerOn().getLocation());
		}
	}
	
	public static boolean verifySpeakerOn(boolean value) {
		if (value) {
			return getSpeakerOn().getWebElement() != null;
		}
		return getSpeakerOff().getWebElement() != null;
	}
	
	public static boolean verifyTalkbackEnable(boolean value) {
		if (value) {
			return getTalkBackOn().getWebElement() != null;
		}
		return getTalkBackOff().getWebElement() != null;
	}
	/*
	 * All icon in stream menu cannot click by Appium, just tap on location
	 */
	public static void clickTakePhotoButton() {
		DriverManager.getDriver().tap(getTakePhotoButton().getLocation());
	}
	
	public static void clickDownloadButton() {
		getDownloadButton().click();
		TimeHelper.sleep(20000);
	}
	
	// Cannot get share icon, click to text "Motion Detect... instead
	public static void clickShareFirstMotionClip() {
		Log.info("Click on share icon");
		WebElement ele = getMotionDetectionText().get(0);
		// If element not visible, scroll down
		if(ele.getAttribute("visible").equals("false")) {
			swipeTopToBottom();
		}
		ele.click();
		TimeHelper.sleep(20000);
	}
	
	public static void swipeToTalkBack() {
		Element eleRecord = getStartRecordIcon();
		int tapX1 = eleRecord.getLocation().x + eleRecord.getSize().getWidth() / 2;
		int tapY1 = eleRecord.getLocation().y + eleRecord.getSize().getHeight() / 2;
		
		Element eleMelody = getMelodyIconOn();
		int tapX2 = eleMelody.getLocation().x + eleMelody.getSize().getWidth() / 2;
		int tapY2 = eleMelody.getLocation().y + eleMelody.getSize().getHeight();
		
		DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
	}
	
	public static void swipeToMelody() {
		Element eleStartRecord = getStartRecordIcon();
		int tapX1 = eleStartRecord.getLocation().x + eleStartRecord.getSize().getWidth() / 2;
		int tapY1 = eleStartRecord.getLocation().y + eleStartRecord.getSize().getHeight() / 2;

		Element eleTalkBack = getTalkBackOn();
		int tapX2 = eleTalkBack.getLocation().x + eleTalkBack.getSize().getWidth() / 2;
		int tapY2 = eleTalkBack.getLocation().y;
		
		DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
	}
	
	public static void enableTalkBack(boolean value) {
		if (value) {
			if (getTalkBackOff().getWebElement() != null)
				DriverManager.getDriver().tap(getTalkBackOff().getLocation());
		}else {
			if (getTalkBackOn().getWebElement() != null)
				DriverManager.getDriver().tap(getTalkBackOn().getLocation());
		}
	}
	
	public static void swipeUpMiniMenu() {
		Element eleStartRecord = getStartRecordIcon();
		int tapX1 = eleStartRecord.getLocation().x + eleStartRecord.getSize().getWidth() / 2;
		int tapY1 = eleStartRecord.getLocation().y + eleStartRecord.getSize().getHeight() / 2;

		Element eleTalkBack = getTalkBackOff();
		int tapX2 = eleTalkBack.getLocation().x + eleTalkBack.getSize().getWidth() / 2;
		int tapY2 = eleTalkBack.getLocation().y;
		
		DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
	}
	
	public static void clickZoomIn() {
		if(location == null) {
			location = getZoomInIcon().getLocation();
		}
		DriverManager.getDriver().tap(location);
	}
	
	public static void clickZoomOut() {
		if(location == null) {
			location = getZoomInIcon().getLocation();
		}
		DriverManager.getDriver().tap(location);
	}
	
	public static void checkPanTilt(boolean naturalPtz, Terminal com) {
		String swipeDirection_up = "up";
		String swipeDirection_down = "down";
		String swipeDirection_left = "left";
		String swipeDirection_right = "right";
		if (naturalPtz) {
			swipeDirection_down = "up";
			swipeDirection_up = "down";
			swipeDirection_left = "right";
			swipeDirection_right = "left";
		}


		com.clearTeratermLog();
		panTilt(swipeDirection_left);
		assertTrue(com.getTeratermLog().contains("req=motor_left"),
				String.format("Natural PTZ = %s, swipe %s, camera NOT received command to turn %s", naturalPtz,
						swipeDirection_left, "LEFT"));

		com.clearTeratermLog();
		panTilt(swipeDirection_right);
		assertTrue(com.getTeratermLog().contains("req=motor_right"),
				String.format("Natural PTZ = %s, swipe %s, camera NOT received command to turn %s", naturalPtz,
						swipeDirection_right, "RIGHT"));

		com.clearTeratermLog();
		panTilt(swipeDirection_up);
		assertTrue(com.getTeratermLog().contains("req=motor_up"),
				String.format("Natural PTZ = %s, swipe %s, camera NOT received command to turn %s", naturalPtz,
						swipeDirection_up, "UP"));

		com.clearTeratermLog();
		panTilt(swipeDirection_down);
		assertTrue(com.getTeratermLog().contains("req=motor_down"),
				String.format("Natural PTZ = %s, swipe %s, camera NOT received command to turn %s", naturalPtz,
						swipeDirection_down, "DOWN"));
	}
	
	/**
	 * @param String direction: left, right, up, or down
	 */
	public static void panTilt(String direction) {
		WebElement e = getStreamingScreen().getWebElement();
		
		Dimension size = e.getSize();
		Point root = e.getLocation();
		int swipeTime = 5000;

		Point right = new Point(root.x + size.width * 9/10,
								root.y + size.height / 2);
		Point left = new Point(root.x + size.width * 4/10,
							   right.y);		
		Point high = new Point(root.x + size.width / 2,
							   root.y + size.height / 3); 
		Point low = new Point(high.x,
							  root.y + size.height * 7/12);
		
		switch (direction.toLowerCase()) {
		case "left":
			Log.debug("Panning left");
			DriverManager.getDriver().swipe(right, left, swipeTime);
			break;
		case "right":
			Log.debug("Panning right");
			DriverManager.getDriver().swipe(left, right, swipeTime);
			break;
		case "up":
			Log.debug("Tilt up");
			DriverManager.getDriver().swipe(low, high, swipeTime);
			break;
		case "down":
			Log.debug("Tilt down");
			DriverManager.getDriver().swipe(high, low, swipeTime);
			break;
		default:
			Log.error(String.format("Input direction is %s, expected up, down, left or right only", direction));
			break;
		}
	}	
}
