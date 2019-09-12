package ios.kodak.object;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

public class PageBase {
	
	public static final String IOS_PERMISSION_LOCATION = "location";
	public static final String IOS_PERMISSION_MEDIA = "media";
	public static final String IOS_PERMISSION_MICROPHONE = "mic";
	public static final String IOS_PERMISSION_NOTIFICATION = "notify";
	public static boolean flagPermissionLocationGranted = false;
	public static boolean flagPermissionMediaGranted = false;
	public static boolean flagPermissionMicroPhoneGranted = false;
	public static boolean flagPermissionNotificationGranted = false;
	
	
	/*
	 * ELEMENT
	 */
	public static Element getOKButton() {
		return new Element(By.name("OK"), 10).setDescription("OK button");
	}
	
	public static Element getNOButton() {
		String btn = "NO";
		return new Element(By.name(btn), 10).setDescription("NO button");
	}
	
	public static Element getYesButton() {
		return new Element(By.name("Yes"), 10).setDescription("Yes button");
	}
	
	public static Element getCancelButton() {
		return new Element(MobileBy.name("Cancel")).setDescription("Cancel button");
	}
	
	public static Element getNextBtn() {
		return new Element(MobileBy.name("Next"), 10).setDescription("Next");
	}
	
	public static Element getDoneBtn() {
		return new Element(MobileBy.name("Done"), 10).setDescription("Done");
	}
	
	public static Element getAllowPermissionBtn() {
		return new Element(By.name("Allow"), 10).setDescription("Allow permission button");
	}
	
	public static Element getBackButton() {
		String backBtn = "ic arrow back";
		return new Element(By.name(backBtn)).setDescription("Back button");
	}
	
	public static Element getContinueButton() {
		return new Element(MobileBy.AccessibilityId("Continue")).setDescription("Continue button");
	}
	
	public static Element getMiniAddIcon() {
		return new Element(MobileBy.name("ic add camera mini"), 10).setDescription("Mini Add button");
	}
	
	public static Element getSaveButton() {
		return new Element(MobileBy.name("Save"), 10).setDescription("Save button");
	}
	
	public static Element getEditButton() {
		return new Element(MobileBy.name("Edit"), 10).setDescription("Edit button");
	}
	
	public static Element getCloseIcon() {
		return new Element(MobileBy.name("ic remove"), 10).setDescription("Close");
	}
	
	public static Element getDeleteButton() {
		return new Element(MobileBy.name("Delete"), 10).setDescription("Delete button");
	}
	
	public static Element getProceedBtn() {
		return new Element(MobileBy.iOSNsPredicateString("name=='Proceed'"), 300).setDescription("Proceed button");
	}
	
	public static Element getDeleteAllButton() {
		return new Element(MobileBy.name("Delete All")).setDescription("Delete All button");
	}
	
	/*
	 * ACTION
	 */
	
	public static void clickOkButton() {
		getOKButton().click();
	}
	
	public static void clickCancelButton() {
		getCancelButton().click();
	}
	
	public static void clickContinueButton() {
		getContinueButton().click();
	}
	
	public static void clickNextButton() {
		getNextBtn().click();
	}
	
	public static void clickYesButton() {
		getYesButton().click();
	}
	
	public static void clickNoButton() {
		getNOButton().click();
	}
	
	public static void clickEditButton() {
		getEditButton().click();
	}
	
	public static void clickDeleteAllButton() {
		getDeleteAllButton().click();
	}
	
	public static void allowIosPermissionIfAsker(String permission) {
		switch (permission) {
		case IOS_PERMISSION_LOCATION:
			if(flagPermissionLocationGranted) {
				Log.info("Location permission is already granted.");
				break;
			}
			if(getAllowPermissionBtn().getWebElement() != null) {
				getAllowPermissionBtn().click();
				flagPermissionLocationGranted = true;
			}
			break;

		case IOS_PERMISSION_MEDIA:
			if(flagPermissionMediaGranted) {
				Log.info("Media permission is already granted.");
				break;
			}
			if(getAllowPermissionBtn().getWebElement() != null) {
				getAllowPermissionBtn().click();
				flagPermissionMediaGranted = true;
			}
			break;
			
		case IOS_PERMISSION_MICROPHONE:
			if (flagPermissionMicroPhoneGranted){
				Log.info("Micro phone permission is already granted.");
				break;
			}
			if(getOKButton().getWebElement() != null) {
				getOKButton().click();
				flagPermissionMicroPhoneGranted = true;
			}
			break;
			
		case IOS_PERMISSION_NOTIFICATION:
			if (flagPermissionNotificationGranted) {
				Log.info("Notification permission is already granted.");
				break;
			}
			if (getAllowPermissionBtn().getWebElement() != null) {
				getAllowPermissionBtn().click();
				flagPermissionNotificationGranted = true;
			}
			break;
		}
	}
	
	public static boolean closeAnyTutorial(boolean flag) {
		if (flag) {
			Log.info("No tutorial shows");
			return flag;
		}
		if(getNextBtn().getWebElement() != null) {
			for (int i = 1; i < 3; i ++) {
				if(getNextBtn().getWebElement() != null) {
					getNextBtn().click();
				}
			}
		}
		if (getDoneBtn().getWebElement() != null) {
			getDoneBtn().click();
			return true;
		}
		return false;
	}
	
	public static void clickBackButton() {
		getBackButton().click();
	}
	
	public static void clickMiniAddButton() {
		getMiniAddIcon().click();
	}
	
	public static void clickSaveButton() {
		getSaveButton().click();
	}
	
	public static void clickDoneButton() {
		getDoneBtn().click();
	}
	
	public static void clickClose() {
		getCloseIcon().click();
	}
	
	public static void clickDeleteButton() {
		getDeleteButton().click();
	}
	
	/**
	 * Swipe from top to bottom
	 */
	public static void swipeTopToBottom(){
		Dimension dim = DriverManager.getDriver().getAppiumDriver().manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		int x = width/2;
		int top_y = (int)(height*0.80);
		int bottom_y = (int)(height*0.20);
		DriverManager.getDriver().swipe(x, top_y, x, bottom_y, 2000);
	}
	
	/**
	 * Swipe from top to bottom
	 */
	public static void swipeBottomToTop(){
		Dimension dim = DriverManager.getDriver().getAppiumDriver().manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		int x = width/2;
		int top_y = (int)(height*0.80);
		int bottom_y = (int)(height*0.20);
		DriverManager.getDriver().swipe(x, bottom_y, x, top_y, 2000);
	}
	
	public static void switchApp(String bundleID, AppiumDriver<?> driver) {
		HashMap<String, Object> args = new HashMap<>();
		args.put("bundleId", bundleID);
		driver.executeScript("mobile: launchApp", args);
	}
	
	public static void clickProceedButton() {
		getProceedBtn().click();
	}
}
