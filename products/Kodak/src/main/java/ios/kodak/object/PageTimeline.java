

package ios.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

import io.appium.java_client.MobileBy;

public class PageTimeline extends PageBase{

	/*
	 * ELEMENTS
	 */
	
	public static Element getFilterBtn() {
		String filterBtn = "ic filter event";
		return new Element(By.name(filterBtn)).setDescription("Filter button");
	}
	
	public static List<WebElement> getListCheckBoxDeviceFilter(){
		String device = "**/XCUIElementTypeCell[$type=='XCUIElementTypeImage' AND name=='ic_check'$]/XCUIElementTypeStaticText";
		return DriverManager.getDriver().findElements(MobileBy.iOSClassChain(device));
	}
	
	public static Element getDeviceByName(String deviceName) {
		String device = "type=='XCUIElementTypeStaticText' AND name =='%s'";
		return new Element(MobileBy.iOSNsPredicateString(String.format(device, deviceName))).setDescription("Device: " + deviceName);
	}
	
	public static Element getNoEventMessage() {
		String msg = "You have no events";
		return new Element(By.name(msg)).setDescription("No event message");
	}
	
	public static List<WebElement> getListShareIcon(){
		String icon = "//XCUIElementTypeCell/XCUIElementTypeButton[@name='Button' and @visible='true']/preceding-sibling::XCUIElementTypeStaticText[contains(@name, 'Motion Detected')]";
		return DriverManager.getDriver().findElements(By.xpath(icon));
	}
	
	public static List<WebElement> getListEvents(){
		String events = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name ENDSWITH[c] 'M'$]/XCUIElementTypeStaticText[$!name ENDSWITH[c] 'M' AND name!='Button'$]";
		return DriverManager.getDriver().findElements(MobileBy.iOSClassChain(events));
	}
	
	/*
	 * ACTIONS
	 */
	
	public static void clearAllDeviceFilter() {
		for (WebElement ele : getListCheckBoxDeviceFilter()) {
			ele.click();
		}
	}
	public static void filterEventByDevice(String deviceName) {
		getFilterBtn().click();
		clearAllDeviceFilter();
		getDeviceByName(deviceName).click();
		clickDoneButton();
	}
	
	public static boolean vefifyNoEventInTimelinePage() {
		return getNoEventMessage().getWebElement() != null;
	}
	
	public static void clickShareFirstMotionClip() {
		Log.info("Click share icon");
		WebElement ele = getListShareIcon().get(0);
		// If element not visible, scroll down
		if(ele.getAttribute("visible").equals("false"))
			swipeTopToBottom();
		ele.click();
		TimeHelper.sleep(10000);
	}
	
	public static boolean verifyFilterEventByDeviceName(String deviceName) {
		for(WebElement ele : getListEvents()) {
			if(!ele.getText().contains(deviceName)) {
				Log.info(String.format("Filter by: %s, but found event: %s", deviceName, ele.getText()));
				return false;
			}
		}
		return true;
	}
}
