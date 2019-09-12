package ios.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageGallery extends PageBase{
	/*
	 * ELEMENTS
	 */
	
	public static Element getSelectButton() {
		return new Element(By.name("Select")).setDescription("Select button");
	}
	
	public static Element getSelectAllButton() {
		return new Element(By.name("Select all")).setDescription("Select all");
	}
	
	public static Element getDeSelectAllButton() {
		return new Element(By.name("Deselect all")).setDescription("Deselect all");
	}
	
	public static Element getDeleteButton() {
		return new Element(By.name("Delete")).setDescription("Delete button");
	}
	
	public static List<WebElement> getListVideoInGallery(){
		String xpath = "//XCUIElementTypeImage[@name='ic_play_button']/ancestor::XCUIElementTypeCell";
		return DriverManager.getDriver().findElements(By.xpath(xpath));
	}
	
	public static List<WebElement> getListVideoSelected(){
		String xpath = "//XCUIElementTypeButton[@name='ic check yellow']/ancestor::XCUIElementTypeCell";
		return DriverManager.getDriver().findElements(By.xpath(xpath));
	}
	
	public static Element getShareButton() {
		String btn = "//XCUIElementTypeButton[@name='Delete']/preceding-sibling::XCUIElementTypeButton";
		return new Element(By.xpath(btn)).setDescription("Share button");
	}
	
	public static Element getEmailIcon() {
		return new Element(By.name("Mail"), 60).setDescription("Email icon");
//		String xpath = "//XCUIElementTypeButton[@name='Mail']";
//		return new Element(By.xpath(xpath)).setDescription("Email icon");
	}
	
	/*
	 * ACTIONS
	 */
	
	public static void deleteAllVideo() {
		if(getSelectButton().getWebElement() != null) {
			getSelectButton().click();
			getSelectAllButton().click();
			getDeleteButton().click();
			clickYesButton();
			TimeHelper.sleep(2000);
		}
	}
	
	public static boolean verifyNumberCameraInGallery(int number) {
		return number == getListVideoInGallery().size();
	}
	
	public static void selectVideos(int number) {
		getSelectButton().click();
		TimeHelper.sleep(2000);
		for(int i = 0; i < number ; i ++) {
			getListVideoInGallery().get(i).click();
		}
	}
	
	public static void deSelectVideos(int number) {
		for(int i = 0; i < number; i ++) {
			getListVideoSelected().get(i).click();
		}
	}
	
	public static void selectAllVideo() {
		if(getSelectButton().getWebElement() != null)
			getSelectButton().click();
		getSelectAllButton().click();
	}
	
	public static boolean verifyNumberVideoSelected(int number) {
		TimeHelper.sleep(2000);
		return getListVideoSelected().size() == number;
	}
	
	public static int getNumberVideoInGallery() {
		return getListVideoInGallery().size();
	}
	
	public static void clickDeselectAllVideo() {
		getDeSelectAllButton().click();
	}
	
	public static void clickShareButton() {
		getShareButton().click();
		TimeHelper.sleep(5000);
	}
	
	public static void clickOnEmailIcon() {
		getEmailIcon().click();
	}
	
	public static void deleteVideo() {
		clickDeleteButton();
		clickYesButton();
		TimeHelper.sleep(2000);
	}
	
}
