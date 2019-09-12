package ios.kodak.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.openqa.selenium.By;

import com.cinatic.TimeHelper;
import com.cinatic.element.Element;

public class PageFeedback extends PageBase{
	private static Random random = new Random();
	/*
	 * ELEMENTS
	 */
	
	public static Element getOptionByName(String option) {
		String optionEl = "//XCUIElementTypeStaticText[@name='%s']";
		return new Element(By.xpath(String.format(optionEl, option))).setDescription(option);
	}
	
	public static Element getDescriptionEle() {
		String descript = "//XCUIElementTypeOther[@name='Short Description']/following-sibling::XCUIElementTypeCell/XCUIElementTypeTextView";
		return new Element(By.xpath(descript)).setDescription("Description");
	}
	
	public static Element getSelectCameraIssueField() {
		String scrollTo = "1000";
		String ele = "//XCUIElementTypeStaticText[@name='Select Camera with issue']";
		return new Element(By.xpath(ele), scrollTo).setDescription("Select Camera with issue");
	}
	
	public static Element getCameraIssueByName(String cameraName) {
		String cam = "//XCUIElementTypeStaticText[@name='%s']";
		return new Element(By.xpath(String.format(cam, cameraName))).setDescription("Camera: " + cameraName);
	}
	
	public static Element getComposeButton() {
		String btn = "//XCUIElementTypeButton[@name='Compose']";
		return new Element(By.xpath(btn)).setDescription("Compose button");
	}
	
	/*
	 * ACTIONS
	 */
	/**
	 * Select random issue to report
	 * return issue happen
	 */
	public static String selectRandomWhatIsIssue() {
		ArrayList<String> listIssue = new ArrayList<String>(
			    Arrays.asList("Setup", "Streaming", "Offline", "Motion Detection", "App related", "Others/Feedback"));
		int index = random.nextInt(listIssue.size());
		getOptionByName(listIssue.get(index)).click();
		return listIssue.get(index);
	}
	/**
	 * Select random a time when issue happened
	 * return time happen
	 */
	public static String selectRandomWhenIssueHappened() {
		ArrayList<String> listTime = new ArrayList<String>(
			    Arrays.asList("Last 5 min", "5 to 30 min ago", "30 to 60 min ago", "1 day ago", "Not Applicable"));
		int index = random.nextInt(listTime.size());
		getOptionByName(listTime.get(index)).click();
		return listTime.get(index);
	}
	
	public static void inputDescription(String description) {
		getDescriptionEle().sendKeys(description);
	}
	
	public static void selectCameraIssue(String camName) {
		getSelectCameraIssueField().click();
		getCameraIssueByName(camName).click();
		clickBackButton();
	}
	
	public static void clickComposeEmail() {
		getComposeButton().click();
		TimeHelper.sleep(30 * 1000);
	}
}
