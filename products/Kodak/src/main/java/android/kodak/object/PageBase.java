package android.kodak.object;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageBase {
// All permission request dialog has the same id
// I just create these dummy variables so it would be easier to understand the
// test case
	public static final String	ANDROID_PERMISSION_MEDIA		= "media";
	public static final String	ANDROID_PERMISSION_LOCATION		= "location";
	public static final String	ANDROID_PERMISSION_MICROPHONE	= "mic";
	protected static boolean	flagPermissionMediaGranted		= false;
	protected static boolean	flagPermissionLocationGranted	= false;
	protected static boolean	flagPermissionMicrophoneGranted	= false;
	protected static int		ELEMENT_TIMEOUT_SHORT			= 5;

	// Android permission request dialog
	public static Element getLocationPermissionProcessBtn() {
		String btnLocationPermissionProcess = String
				.format("//android.widget.Button[@resource-id='android:id/button1']");
		return new Element(By.xpath(btnLocationPermissionProcess), ELEMENT_TIMEOUT_SHORT)
				.setDescription("Location permission Allow button");
	};

	public static Element getAndroidPermissionAllowBtn(String permission) {
		String xpathLocator = String.format(
				"//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']");
		return new Element(By.xpath(xpathLocator), ELEMENT_TIMEOUT_SHORT)
				.setDescription("Android " + permission + " permission Allow button");
	};

	public static Element getBackButton() {
		String id = "imgMain";
		return new Element(By.id(id)).setDescription("Back button");
	}

	/**
	 * using for confirm popup: (Ok, Yes, Confirm...) button
	 * 
	 * @return
	 */
	public static Element getConfirmButton() {
		String id = "btn_confirm";
		return new Element(By.id(id)).setDescription("Confirm button");
	}

	/**
	 * Using for confirm popup: (No, Cancel...) button
	 * 
	 * @return
	 */
	public Element getCancelButton() {
		String id = "btn_cancel";
		return new Element(By.id(id)).setDescription("Cancel buton");
	}

	public static Element getGmailApp() {
		String gmailIcon = "//android.widget.TextView[@text='Gmail']/..//android.widget.ImageView[@resource-id='android:id/icon']";
		return new Element(By.xpath(gmailIcon), 120).setDescription("Share via Gmail button");
	}

	/**
	 * @param permission
	 * PageBase.ANDROID_PERMISSION_LOCATION
	 * PageBase.ANDROID_PERMISSION_MEDIA
	 * PageBase.ANDROID_PERMISSION_MICROPHONE
	 */
	public static void allowAndroidPermissionIfAsked(String permission) {

		switch (permission) {
		case ANDROID_PERMISSION_LOCATION:
			if (flagPermissionLocationGranted) {
				Log.info("Location permission is already granted");
			} else if (getLocationPermissionProcessBtn().getWebElement() != null) {
				getLocationPermissionProcessBtn().click();
				getAndroidPermissionAllowBtn(permission).click();
			}
			flagPermissionLocationGranted = true;
			break;
		case ANDROID_PERMISSION_MEDIA:
			if (flagPermissionMediaGranted) {
				Log.info("Media access permission is already granted");
			} else if (getAndroidPermissionAllowBtn(permission).getWebElement() != null) {
				getAndroidPermissionAllowBtn(permission).click();
			}
			flagPermissionMediaGranted = true;
			break;
		case ANDROID_PERMISSION_MICROPHONE:
			if (flagPermissionMicrophoneGranted) {
				Log.info("Record Audio permission is already granted");
			} else if (getAndroidPermissionAllowBtn(permission).getWebElement() != null) {
				getAndroidPermissionAllowBtn(permission).click();

			}
			flagPermissionMicrophoneGranted = true;
			break;
		default:
			break;
		}
	}

	// Tutorial - appears when hit (?) button
	public static Element getTutorialNextDoneBtn() {
		String id = "text_action_bottom";
		return new Element(By.id(id), 5).setDescription("Tutorial Next/Done button");
	}

	protected static boolean closeAnyTutorial(boolean flagPage) {
		if (flagPage) {
			Log.info("Tutorial should be closed already");
			return flagPage;
		}
		// click Next/done 3 time to close the tutorial
		if (getTutorialNextDoneBtn().getWebElement() != null) {
			Log.info("Closing tutorial");
			for (int i = 0; i < 3; i++) {
				getTutorialNextDoneBtn().click();
				TimeHelper.sleep(200);
			}
			return true;
		}
		return false;
	}

	// Dashboard upgrade cloud tip
	// This only appears in PageDashboard and PageCameraSetting when turn on
	// motion detection
	public static Element getCloudUpgradeTipCloseBtn() {
		// this button is NAF
		String id = "btn_close";
		return new Element(By.id(id), 10).setDescription("Close \"Want to see more\" hint button");
	}

	// 3 button at the bottom of dashboard page.
	public static Element getTimelineIcon() {
		String timelineIcon = "//android.widget.HorizontalScrollView[contains(@resource-id,'tab_layout_bottom')]/android.widget.LinearLayout/*[3]";
		// android.widget.LinearLayout
		return new Element(By.xpath(timelineIcon)).setDescription("Timeline tab button");
	}

	public static Element getGalleryIcon() {
		String galleryIcon = "//android.widget.HorizontalScrollView[contains(@resource-id,'tab_layout_bottom')]/android.widget.LinearLayout/*[2]";
		return new Element(By.xpath(galleryIcon)).setDescription("Gallery tab button");
	}

	public static Element getDashBoardIcon() {
		String dashboardIcon = "//android.widget.HorizontalScrollView[contains(@resource-id,'tab_layout_bottom')]/android.widget.LinearLayout/*[1]";
		return new Element(By.xpath(dashboardIcon)).setDescription("Dashboard tab button");
	}

	// Empty event image exited in page stream view and Timeline page
	public static Element getEmptyEventTextView() {
		String id = "tv_empty_event";
		return new Element(By.id(id)).setDescription("Empty event image exited in page stream view and Timeline page");
	}

	/*
	 * Close "Want to see more" top
	 */
	public static void closeCouldUpgradeTipIfAsked() {
		try {
			getCloudUpgradeTipCloseBtn().click();
			Log.info("Clould uprade hint has just been closed");
		} catch (Exception e) {
			Log.info("Cloud upgrade hint does not show up");
		}
	}

	// There are many page using this method, so move on PageBase
	public static void exitPage() {
		getBackButton().click();
	}

	/**
	 * Swipe from top to bottom
	 */
	public static void swipeTopToBottom() {
		Dimension	dim			= DriverManager.getDriver().getAppiumDriver().manage().window().getSize();
		int			height		= dim.getHeight();
		int			width		= dim.getWidth();
		int			x			= width / 2;
		int			top_y		= (int) (height * 0.80);
		int			bottom_y	= (int) (height * 0.20);
		DriverManager.getDriver().swipe(x, top_y, x, bottom_y, 2000);
	}

	public static void swipeBottomToTop() {
		Dimension	dim		= DriverManager.getDriver().getAppiumDriver().manage().window().getSize();
		int			height	= dim.getHeight();
		int			width	= dim.getWidth();

		Point	top		= new Point(width / 2, (int) (height * 0.2));
		Point	bottom	= new Point(top.x, (int) (height * 0.8));

		DriverManager.getDriver().swipe(top, bottom, 2000);
	}

// public boolean waitForDisplayElement(Element ele) {
// boolean isElementPresent = false;
// try{
// WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), 10);
// wait.until(ExpectedConditions.visibilityOf(ele));
// isElementPresent = ele.isDisplayed();
// return isElementPresent;
// }catch(Exception e){
// isElementPresent = false;
// System.out.println(e.getMessage());
// return isElementPresent;
// }
// }

	/**
	 * using for confirm popup: (Ok, Yes, Confirm...) button
	 * 
	 * @return
	 */
	public static void clickConfirmButton() {
		getConfirmButton().click();
	}

	/**
	 * Using for confirm popup: (No, Cancel...) button
	 * 
	 * @return
	 */
	public void clickCancelButton() {
		getCancelButton().click();
	}

	public static PageTimeline gotoToTimelinePage() {
		getTimelineIcon().click();
		return new PageTimeline();
	}

	public static void navigateToDashBoardPage() {
		getDashBoardIcon().click();
	}

	public static void navigateToGalleryPage() {
		getGalleryIcon().click();
	}

	// There are many page using this method, so move on PageBase
	public static boolean verifyEmptyEvent() {
		return getEmptyEventTextView().getWebElement() != null;
	}

	public static Element getOkButton(String description) {
		String xpathLocator = "//android.widget.Button[@text='OK']";
		return new Element(By.xpath(xpathLocator)).setDescription(description);
	}

	public static void resetFlags() {
		PageBase.flagPermissionMediaGranted				= false;
		PageBase.flagPermissionLocationGranted			= false;
		PageBase.flagPermissionMicrophoneGranted		= false;
		PageDashboard.flagAutoTutorialClosed			= false;
		PageDashboard.flagCloudUpgradeSuggestionClosed	= false;
		PageStreamView.flagAutoTutorialClosed			= false;
	}

	public static void openSystemQuickSetting() {
		Point	high	= new Point(10, 0);
		Point	low		= new Point(10, 500);
		DriverManager.getDriver().swipe(high, low, 0);
	}

	public static void openSystemWifiSetting() {
		openSystemQuickSetting();
		PageAndroidQuickSetting.openWifiSetting();
	}

	public static void enableDebugInfo(String username, String passwd) {
		PageGetStart.checkAndSignin(username, passwd);

		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.enableDebug();
		PageHomeMenu.gotoHomeSetingPage();
		PageHomeSetting.enableShowDebugInfo();
		PageBase.exitPage();
	}
}
