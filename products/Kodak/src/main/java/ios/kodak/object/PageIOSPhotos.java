package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageIOSPhotos extends PageBase{

	/*
	 * ELEMENTS
	 */
	
	public static Element getSelectButton() {
		String selectBtn = "//XCUIElementTypeButton[@name='Select']";
		return new Element(By.xpath(selectBtn)).setDescription("Select button");
	}
	
	public static Element getSelectButtonByToday() {
		String selectBtn = "//XCUIElementTypeButton[@name='Select, Today']";
		return new Element(By.xpath(selectBtn)).setDescription("Select today");
	}
	
	public static Element getDeletePhotoBtn() {
		String btn = "name BEGINSWITH 'Delete' AND visible=true";
		return new Element(MobileBy.iOSNsPredicateString(btn)).setDescription("Delete Photo");
	}
	
	public static Element getTodayLable() {
		String scrollTo = "Photos";
		String lable = "//XCUIElementTypeOther[@name='Today']";
		return new Element(By.xpath(lable), scrollTo).setDescription("Today");
	}
	/*
	 * ACTIONS
	 */

	public static void deleteAllImageOnToday() {
		getSelectButton().click();
		getSelectButtonByToday().click();
		clickDeleteButton();
		getDeletePhotoBtn().click();
	}
	
	public static void closeAllowAccessPhoto() {
		if(getOKButton().getWebElement() != null ) {
			getOKButton().click();
		}
	}
	
	public static boolean verifyTodayImageExisted() {
		return getTodayLable().getWebElement() != null;
	}
	
	
}
