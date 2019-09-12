package ios.kodak.object;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageManageCloudStorage extends PageBase{
	
	/*
	 * ELEMENTS
	 */
	
	public static Element getYourPlanField() {
		String yourPlan = "//XCUIElementTypeStaticText[@name='Your plan:']/following-sibling::XCUIElementTypeStaticText[not(contains(@value, 'Cameras'))]";
		return new Element(By.xpath(yourPlan)).setDescription("Your plan");
	}
	
	public static List<WebElement> getListDeviceEle(){
		String device = "//XCUIElementTypeCollectionView//XCUIElementTypeStaticText";
		return DriverManager.getDriver().findElements(By.xpath(device));
	}
	
	/*
	 * ACTIONS
	 */

	/**
	 * get map camera name and storage plan
	 * key - camera name : value - your storage plan
	 * @return map
	 */
	public static HashMap<String, String> getYourCurrentPlan() {
		TimeHelper.sleep(3000);
		HashMap<String, String> yourPlanMap = new HashMap<String, String>();
		for(WebElement ele : getListDeviceEle()) {
			yourPlanMap.put(ele.getText(), getYourPlanField().getText());
		}
		return yourPlanMap;
	}
}
