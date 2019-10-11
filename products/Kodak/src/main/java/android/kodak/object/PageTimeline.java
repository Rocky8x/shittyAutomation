package android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageTimeline extends PageBase {

	public static final String CLOUD_STORAGE = "cloud";

	public static Element getBackToTopElement() {

		String ele = "//android.widget.TextView[@text='Back To Top']";
		return new Element(By.xpath(ele), 5).setDescription("Back to Top Icon");
	}

	public static Element getPlayClipIcon() {

		String id = "imageview_event_item_play";
		return new Element(By.id(id), 5).setDescription("Play Video Icon");
	}

	public static Element getDownloadMotionIcon() {

		String id = "img_download";
		return new Element(By.id(id)).setDescription("Download Motion Icon");
	}

	public static Element getFilterButton() {

		String id = "filter_event_menu_item";
		return new Element(By.id(id)).setDescription("Filter Button");
	}

	public static Element getListDevicePanel() {

		String id = "recyclerview_event_setting_devices";
		return new Element(By.id(id)).setDescription("List device panel in filter");
	}

	public static Element getCheckBoxDeviceByName(String deviceName) {

		String cbEle = "//android.widget.TextView[contains(@resource-id,'textview_device_chosen') and @text='%s']/../android.widget.CheckBox";
		return new Element(By.xpath(String.format(cbEle, deviceName)))
				.setDescription("Device checkbox to filter");
	}

	public static Element getClearButton() {

		String id = "textview_title_clear";
		return new Element(By.id(id)).setDescription("Clear button");
	}

	public static Element getDoneButton() {

		String id = "done_event_setting_menu_item";
		return new Element(By.id(id)).setDescription("Done button");
	}

	public static Element getListEventsPanel() {

		String id = "recyclerview_home_event";
		return new Element(By.id(id)).setDescription("List events panel in timeline page");
	}

	public static List<WebElement> getListEvents() {

		String id = "textview_event_item_name";
		return getListEventsPanel().findElements(By.id(id));
	}

	public static Element getShareIcon() {

		String id = "img_event_share_video";
		return new Element(By.id(id)).setDescription("Share icon");
	}

	public static Element getShareEventPanel() {

		String id = "parentPanel";
		return new Element(By.id(id)).setDescription("Share event panel");
	}

	public static Element getDownloadClipButton() {

		String clipBtn = "//android.widget.Button[@resource-id='android:id/button2']";
		return new Element(By.xpath(clipBtn))
				.setDescription("Download clip button in confirm popup");
	}

	public static Element getDownloadSnapButton() {

		String snapBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(snapBtn))
				.setDescription("Download snap button in confirm popup");
	}

	public static Element getPlayVideoErrorMessageEle() {

		String id = "text_load_fail";
		return new Element(By.id(id)).setDescription("Play video error message");
	}

	public static Element getReplayIcon() {

		String id = "img_refresh";
		return new Element(By.id(id), 40).setDescription("Replay / Refresh icon");
	}

	public static Element getPlayVideoIconStorageInCloud() {

		String playBtn = "//android.widget.ImageView[contains(@resource-id,'img_event_share_video')]/../../..//android.widget.ImageView[contains(@resource-id,'imageview_event_item_play')]";
		return new Element(By.xpath(playBtn), 5).setDescription("Play button of cloud MD clip");
	}

	public static Element getPlayVideoIconStorageInSDCard() {

		String playBtn = "//android.widget.ImageView[contains(@resource-id,'img_event_video_location')][not(../android.widget.ImageView[contains(@resource-id,'img_event_share_video')])]/../../..//android.widget.ImageView[contains(@resource-id,'imageview_event_item_play')]";
		return new Element(By.xpath(playBtn), 5).setDescription("Play button of SD-card MD clip");
	}

	public static List<WebElement> getAllTimeLabels() {

		return DriverManager.getDriver().findElements(By.id("textview_event_item_time"));
	}

	/**
	 * Swipe to end of timeline page and verify video storage in cloud exist
	 * 
	 * @return
	 */
	public static boolean findVideoStorageInCloud() {

		if (verifyEmptyEvent()) { return false; }

		List<WebElement>	timeLabels;
		String				beforeScrollLastEventTime	= "before";
		String				afterScrollLastEventTime	= "after";

		while (getPlayVideoIconStorageInCloud().getWebElement() == null
				&& getBackToTopElement().getWebElement() == null
				&& !beforeScrollLastEventTime.equals(afterScrollLastEventTime)) {

			timeLabels					= getAllTimeLabels();
			beforeScrollLastEventTime	= timeLabels.get(timeLabels.size() - 1).getText();
			swipeTopToBottom();
			timeLabels					= getAllTimeLabels();
			afterScrollLastEventTime	= timeLabels.get(timeLabels.size() - 1).getText();
		}
		if (getPlayVideoIconStorageInCloud().getWebElement() != null) { return true; }
		return false;
	}

	/**
	 * Swipe to end of timeline page and verify video storage in sd-card exist
	 * 
	 * @return
	 */
	public static boolean findVideoStorageInSDCard() {

		if (verifyEmptyEvent()) { return false; }

		List<WebElement>	timeLabels;
		String				beforeScrollLastEventTime	= "before";
		String				afterScrollLastEventTime	= "after";

		while (getPlayVideoIconStorageInCloud().getWebElement() == null
				&& getBackToTopElement().getWebElement() == null
				&& !beforeScrollLastEventTime.equals(afterScrollLastEventTime)) {

			timeLabels					= getAllTimeLabels();
			beforeScrollLastEventTime	= timeLabels.get(timeLabels.size() - 1).getText();
			swipeTopToBottom();
			timeLabels					= getAllTimeLabels();
			afterScrollLastEventTime	= timeLabels.get(timeLabels.size() - 1).getText();
		}
		
		if (getPlayVideoIconStorageInSDCard().getWebElement() != null) { return true; }
		return false;
	}

	/**
	 * Duong Nguyen
	 * Playback video and return number of failure
	 * 
	 * @param storagePlace: places storage video
	 * @param playTimes:    number times playback video
	 * 
	 * @return number playback failure
	 */
	public static int verifyPlayBackVideoFunction(String storagePlace, int playTimes) {

		int countFail = 0;
		if (storagePlace.equalsIgnoreCase(CLOUD_STORAGE)) {
			getPlayVideoIconStorageInCloud().click();
		} else {
			getPlayVideoIconStorageInSDCard().click();
		}
		for (int i = 0; i < playTimes; i++) {
			getReplayIcon().click();
			if (!(getReplayIcon().getWebElement() != null
					&& getPlayVideoErrorMessageEle().getWebElement() == null)) {
				countFail++;
			}
		}
		return countFail;
	}

	public static boolean clickOnPlayMotionVideo() {

		if (verifyEmptyEvent()) { return false; }
		boolean isScrollable = getListEventsPanel().getAttribute("scrollable").equals("true");
		while (getPlayClipIcon().getWebElement() == null
				&& getBackToTopElement().getWebElement() == null) {
			if (!isScrollable)
				break;
			swipeTopToBottom();
		}
		if (getPlayClipIcon().getWebElement() != null) {
			getPlayClipIcon().click();
			return true;
		}
		return false;
	}

	public static boolean donwloadMotionVideo() {

		boolean rs = false;
		if (clickOnPlayMotionVideo()) {
			getDownloadMotionIcon().click();
			TimeHelper.sleep(5000);
			DriverManager.getDriver().getAppiumDriver().navigate().back();
			rs = true;
		}
		return rs;
	}

	public static void clickFilterButton() {

		getFilterButton().click();
	}

	public static List<String> getListDevices() {

		String			id			= "textview_device_chosen";
		List<String>	lstDevices	= new ArrayList<>();
		for (WebElement ele : getListDevicePanel().findElements(By.id(id))) {
			lstDevices.add(ele.getText());
		}
		return lstDevices;
	}

	public static void selectDeviceFilterByName(String deviceName) {

		getCheckBoxDeviceByName(deviceName).setValue(true);
	}

	public static void clickClearFilter() {

		getClearButton().click();
	}

	public static void clickDoneFilter() {

		getDoneButton().click();
	}

	public static boolean verifyDeviceSeleted(String deviceName) {

		return getCheckBoxDeviceByName(deviceName).getAttribute("checked").equals("true");
	}

	/**
	 * Swipe in timeline and verify events by camera name
	 * 
	 * @param deviceName
	 * @return
	 */
	public static boolean verifyDetectionByDeviceName(String deviceName) {

		List<WebElement>	eles			= new ArrayList<WebElement>();
		boolean				isScrollable	= getListEventsPanel().getAttribute("scrollable")
				.equals("true");
		while (getBackToTopElement().getWebElement() == null) {
			eles = getListEvents();
			for (WebElement ele : eles) {
				if (!ele.getText().contains(deviceName)) {
					Log.info(String.format("Event: %s not in device: %s.", ele.getText(),
							deviceName));
					return false;
				}
			}
			if (!isScrollable) { break; }
			swipeTopToBottom();
		}
		return true;
	}

	/**
	 * share motion video in time line, stream view page
	 */
	public static void shareMotionVideo() {

		int count = 0;
		// Swipe until share icon display (maximum 5 times)
		while (count < 5) {
			try {
				getShareIcon().click();
				count = 10;
			} catch (Exception e) {
				swipeTopToBottom();
				count++;
			}
		}
		// If pop up confirm download clip or snap display, select clip
		if (getShareEventPanel().getWebElement() != null) { getDownloadClipButton().click(); }
		getGmailApp().click();
	}

	public static void filterByDeviceName(String deviceName) {

		clickFilterButton();
		clickClearFilter();
		selectDeviceFilterByName(deviceName);
		clickDoneFilter();
	}
}
