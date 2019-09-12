package android.kodak.object;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;
import com.cinatic.object.Terminal;

public class PageStreamView extends PageBase{
	public static boolean flagAutoTutorialClosed = false;
	
	/***ELEMENT***/		
	//COMMON
	public static Element getStreamLoadingImg(){
		String id = "img_stream_loading";
		return new Element(By.id(id), 3).setDescription("Stream loading image"); 
	}
	
	public static Element getP2pStatusLabel(){ 
		String id = "text_p2p_status";
		return new Element(By.id(id), 3).setDescription("P2P status"); 
	}
	
	public static Element getLiveStreamStatusLabel(){ 
		String id = "text_live_stream_status";
		return new Element(By.id(id), 3).setDescription("Live stream status"); 
	}
	
	public static Element getBackButtonInStreamView(){
		String id = "imgMain";
		return new Element(By.id(id)).setDescription("Back button in Stream view"); 
	}
	
	public static Element getCameraSettingBtn() {
		String id = "menu_setting";
		return new Element(By.id(id)).setDescription("Camera setting button in Stream view");
	}
	
	//MENU
	public static Element getMenuImage() {
		String id = "img_menu";
		return new Element(By.id(id)).setDescription("Mini memu button in Stream view");
	};
	
	public static Element getTalkBackDisabledImg() {
		String imgTalkBackDisable = "//android.widget.ImageView[@content-desc='disable talkback']";
		return new Element(By.xpath(imgTalkBackDisable)).setDescription("Talkback disabled button"); 
	};
	
	public static Element getTalkBackEnableImg() {
		String imgTalkBackEnable = "//android.widget.ImageView[@content-desc='enable talkback']";
		return new Element(By.xpath(imgTalkBackEnable)).setDescription("Talkback enabled button"); 
	};
	
	public static Element getCaptureImage() {
		String imgCapture = "//android.widget.ImageView[@content-desc='capture image']";
		return new Element(By.xpath(imgCapture), 5).setDescription("Capture image button"); 
	};
	
	public static Element getStartRecordVideoBtn() {
		String xpathLocator = "//android.widget.ImageView[@content-desc='start record video']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Start video record button"); 
	}
	
	public static Element getStopRecordVideoImage() {
		String xpathLocator = "//android.widget.ImageView[@content-desc='stop record video']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Stop video record button"); 
	}
	
	public static Element getMelodyImage() { 
		String xpathLocator = "//android.widget.ImageView[@content-desc='disable melody']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Melody menu button"); 
	}
	
	public static Element getMelodyEnabledImage() {
		String xpathLocator = "//android.widget.ImageView[@content-desc='enable melody']";
		return new Element(By.xpath(xpathLocator), 5).setDescription("Melody enabled image"); 
	}
	
	public static Element getSoundImageBtn() { 
		String imgSound = "//android.widget.ImageView[contains(@content-desc,' sound')]";
		return new Element(By.xpath(imgSound)).setDescription("Mute/unmute sound button"); 
	};
	
	public static Element getMuteSoundImage() { 
		String imgMuteSound = "//android.widget.ImageView[@content-desc='mute sound']";
		return new Element(By.xpath(imgMuteSound)).setDescription("Mute sound button"); 
	};
	
	public static Element getUnMuteSoundImage() {
		String imgUnMuteSound = "//android.widget.ImageView[@content-desc='unmute sound']";
		return new Element(By.xpath(imgUnMuteSound)).setDescription("Unmute sound button"); 
	};
	
	//MELODY
	public static Element getMelodyByName(int number) { 
		String lblMelody = "//android.widget.TextView[contains(@resource-id,'text_melody') and @text='Melody %s']";
		return new Element(By.xpath(String.format(lblMelody, number))).setDescription(String.format("Melody number: %s", number)); 
	};
	
	public static Element getStopMelody() {
		String lblStopMelody = "//android.widget.TextView[contains(@resource-id,'text_melody') and @text='Stop Melody']";
		return new Element(By.xpath(lblStopMelody)).setDescription("Stop melody button");
	};
	
	//EVENT
	public static Element get1stMDClipPlayButton() { 
		String btnFirstMotionDetection = "(//android.widget.ImageView[contains(@resource-id,'imageview_event_item_play')])[1]";
		return new Element(By.xpath(btnFirstMotionDetection)).setDescription("First MD clip in stream view event"); 
	};

	public static Element get2ndMDClipPlayButton() { 
		String btnFirstMotionDetection = "(//android.widget.ImageView[contains(@resource-id,'imageview_event_item_play')])[2]";
		return new Element(By.xpath(btnFirstMotionDetection)).setDescription("First MD clip in stream view event"); 
	};
	
	//VIDEO PLAY
	public static Element getVideoPlayImage() {
		String id = "img_video_play";
		return new Element(By.id(id)).setDescription("MD clip play button"); 
	};
	
	public static Element getVideoLoading() {
		String id = "img_loading";
		return new Element(By.id(id), 3).setDescription("MD clip loading image"); 
	};
	
	public static Element getVideoPlaySeekbar() { 
		String id = "seekbar_progress";
		return new Element(By.id(id)).setDescription("MD clip player seekbar"); 
	};
	
	public static Element getCancelMelodyButton() {
		String cancelBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(cancelBtn)).setDescription("Melody menu cancel button");
	}
	
	public static Element getEditSoundMotionSettingButton() {
		String id = "btn_edit_motion_setting";
		return new Element(By.id(id)).setDescription("Big red Edit Sound/Motion setting button");
	}
	
	public static Element getHumidityTextView() {
		String id = "text_humidity";
		return new Element(By.id(id)).setDescription("Stream view: Humidity value");
	}
	
	public static Element getTemperatureTextView() {
		String id = "text_temperature";
		return new Element(By.id(id));
	}
	
	public static Element getDetectionEventTime() {
		String id = "textview_event_item_time";
		return new Element(By.id(id));
	}
	
	public static Element getZoomInButton() {
		String xpathLocator="//android.widget.ImageView[@content-desc='zoom in']";
		return new Element(By.xpath(xpathLocator)).setDescription("Zoom in button");		
	}
	
	public static Element getZoomOutButton() {
		String xpathLocator="//android.widget.ImageView[@content-desc='zoom out']";
		return new Element(By.xpath(xpathLocator)).setDescription("Zoom out button");		
	}
	
	/***ACTION***/
	public static String getStreamMode(int timeOut) {
		Log.info("Get stream mode");
		String mode = "";		
		while (timeOut >= 0) {
			try {
				mode = getP2pStatusLabel().getText();
			} catch (Exception e) {
				mode = "";
			}
			if (mode.contains("OK"))
				break;
			timeOut--;
			TimeHelper.sleep(1000);
		}
		return mode;
	}		
	
	public static void exitToDashboard() {
		Log.info("Exit page");
		exitPage();
		TimeHelper.sleep(5000);
		// handle new tip added in 1.0.4 (40) when back to dashboard
		try {
			PageDashboard.getGrandAccessDashboardTipCloseBtn().click();
			Log.info("Additional tip about preview and Grant access feature has just been close");
		} catch (Exception e) {
			Log.info("Additional tip about preview and Grant access feature does not show");
		}
	}
	
	public static void enableTalkBack() {
		Log.info("Enable talk back");
		getMenuImage().click();
		getTalkBackDisabledImg().click();
		allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MICROPHONE);
		TimeHelper.sleep(5000);
	}
	
	public static void disableTalkBack() {
		Log.info("Disable talk back");
		try {
			getTalkBackEnableImg().click();
			TimeHelper.sleep(5000);
		} catch (Exception e) {
			
		}
	}
	
	public static void muteSound() {
		Log.info("Mute sound");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getMuteSoundImage().getWebElement() != null) {
			getMuteSoundImage().click();
			TimeHelper.sleep(5000);
		}
	}
	
	public static void unMuteSound() {
		Log.info("Unmute sound");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getUnMuteSoundImage().getWebElement() != null) {
			getUnMuteSoundImage().click();
			TimeHelper.sleep(5000);
		}
	}
	
	public static void captureImage() {
		Log.info("Capture image");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null) {
			getMenuImage().click();
		}

		getCaptureImage().click();
		TimeHelper.sleep(5000);		
	}
	
	public static void startRecordVideo(int duration) {
		Log.info("Start record video in: " + duration);
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getStartRecordVideoBtn().getWebElement() != null) {
			getStartRecordVideoBtn().click();
			TimeHelper.sleep(duration * 1000);
		}
	}
	
	public static void stopRecordVideo() {
		Log.info("Stop record video");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();

		if (getStopRecordVideoImage().getWebElement() != null) {
			getStopRecordVideoImage().click();
			TimeHelper.sleep(5000);
		}
	}
	
	public static void swipeUpMiniMenu() {
		Log.info("Swipe up the mini menu");
		if (getCaptureImage().getWebElement() == null && getMelodyImage().getWebElement() == null)
			getMenuImage().click();
		
//		if(getMelodyImage().getWebElement() == null)
//		{
			Element element1 = getCaptureImage();
			int tapX1 = element1.getLocation().x + element1.getSize().getWidth() / 2;
			int tapY1 = element1.getLocation().y + element1.getSize().getHeight() / 2;

			Element element2 = getStartRecordVideoBtn();
			int tapX2 = element2.getLocation().x + element2.getSize().getWidth() / 2;
			int tapY2 = element2.getLocation().y + element2.getSize().getHeight() / 2;

			DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
//		}
	}
	
	public static void openMelody() {
		Log.info("Open melody");
		swipeUpMiniMenu();
		getMelodyImage().click();
		TimeHelper.sleep(1500);
	}
	
	public static void playMelody(int number) {
		getMelodyByName(number).click();
		TimeHelper.sleep(5000);
	}
	
	public static void playStopMelody() {
		Log.info("Play stop melody");
		getStopMelody().click();
		TimeHelper.sleep(5000);
	}
	
	public static boolean verifyTalkBackEnable() {
		Log.info("Verify talk back enable");
		return getTalkBackEnableImg().getWebElement() != null;
	}
	
	public static boolean verifyTalkBackDisable() {
		Log.info("Verify talk back disable");
		return getTalkBackDisabledImg().getWebElement() != null;
	}
	
	public static void clickCancelOnPlayMelodyPanel() {
		Log.info("Click cancel on play medody panel");
		getCancelMelodyButton().click();
	}
	
	public static void clickEditSoundMotionSetting() {
		Log.info("Click edit sound motion setting");
		getEditSoundMotionSettingButton().click();
	}
	
	public static boolean verifyStreamViewPage() {
		Log.info("Verify stream view page");
		return getMenuImage().getWebElement() != null;
	}
	
	public static String getHumidityInApp() {
		Log.info("Get humidity in app");
		return getHumidityTextView().getText();
	}
	
	public static boolean verifyEmptyEventInStreamPage() {
		return getEditSoundMotionSettingButton().getWebElement() != null;
	}
	
	public static boolean verifyTemperatureUnitInStreamPage(String unit) {
		return getTemperatureTextView().getText().toUpperCase().endsWith(unit.toUpperCase());
	}
	
	public static void gotoCameraSettingPage() {
		getCameraSettingBtn().click();
	}
	
	public static String getMotionEventTime() {
		String rs = "";
		if(getDetectionEventTime().getWebElement() != null) {
			rs = getDetectionEventTime().getText().replace("\n", " ");
		}
		return rs;
	}
	
	public static Element getStreamingScreen() {
		String id= "imageview_device_inner";
		return new Element(By.id(id)).setDescription("Streaming screen");
	}
	
	/**
	 * @param String direction: left, right, up, or down
	 */
	public static void panTilt(String direction) {
		
		WebElement e = getStreamingScreen().getWebElement();
		Dimension size = e.getSize();
		Point root = e.getLocation();
		int swipeTime = 3000;

		Point right = new Point(root.x + size.width * 9/10,
								root.y + size.height / 2);
		Point left = new Point(root.x + size.width * 3/10,
							   right.y);		
		Point high = new Point(root.x + size.width / 2,
							   root.y + size.height / 3); 
		Point low = new Point(high.x,
							  root.y + size.height * 2/3);
		
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

		PageStreamView cameraStreamView = new PageStreamView();

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
	
	public static void exitPage() {
		getBackButton().click();
		try {
			PageDashboard.getGrandAccessDashboardTipCloseBtn().click();
		} catch (Exception e) {
		}
	}
	
	public static void closeTutorial() {
		flagAutoTutorialClosed = PageBase.closeAnyTutorial(flagAutoTutorialClosed);
	}
	
	public static class SdcardNearFullWarning {
		
		public static final String MESSAGE = "SD Card storage space on device is nearly full. Your oldest clips will be overwritten for newer clips soon. If you want to retain the older clips, copy out the files in the SD Card.";
		public static Element getOkButton() {
			String id = "btn_confirm";
			return new Element(By.id(id),2).setDescription("Ok button");
		}
		
		public static Element getMessageTextView() {
			String id = "text_dialog_msg";
			return new Element(By.id(id)).setDescription("Sdcard low capacity warning");
		}
	}
}