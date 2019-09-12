package ios.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageNotification extends PageBase {
	
	/*
	 * ELEMENTS
	 */
	
	public static Element getDoNotDisturbSwitch() {
		String sw = "//XCUIElementTypeSwitch[@name='Do Not Disturb']";
		return new Element(By.xpath(sw)).setDescription("Do not disturb switch");
	}
	
	public static Element getStartTimeTextField() {
		String startTime = "//XCUIElementTypeStaticText[@name='Start Time']";
		return new Element(By.xpath(startTime)).setDescription("Start time lable");
	}
	
	public static Element getStopTimeTextField() {
		String stopTime = "//XCUIElementTypeStaticText[@name='Stop Time']";
		return new Element(By.xpath(stopTime)).setDescription("Stop time lable");
	}
	
	public static List<WebElement> getListDeviceSwitch(){
		String sw = "//XCUIElementTypeOther[contains(@name,'Please select device(s)')]/following-sibling::XCUIElementTypeCell/XCUIElementTypeSwitch";
		return DriverManager.getDriver().findElements(By.xpath(sw));
	}
	
	public static List<WebElement> getListDeviceNameEle(){
		String device = "**/XCUIElementTypeCell[$type=='XCUIElementTypeSwitch' AND !name BEGINSWITH 'Do Not' AND !name BEGINSWITH 'Scheduled'$]/XCUIElementTypeStaticText";
		return DriverManager.getDriver().findElements(MobileBy.iOSClassChain(device));
	}
	
	public static Element getScheduleSwitch() {
		String sw = "type=='XCUIElementTypeSwitch' AND name=='Scheduled'";
		return new Element(MobileBy.iOSNsPredicateString(sw)).setDescription("Schedule switch");
	}
	
	public static Element getScheduleStartTimeValue() {
		String start = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Stop Time'$]/XCUIElementTypeStaticText[$!name BEGINSWITH 'St'$][1]";
		return new Element(MobileBy.iOSClassChain(start)).setDescription("Start time");
	}
	
	public static Element getScheduleStopTimeValue() {
		String stop = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name=='Stop Time'$]/XCUIElementTypeStaticText[$!name BEGINSWITH 'St'$][2]";
		return new Element(MobileBy.iOSClassChain(stop)).setDescription("Stop time");
	}
	
	public static Element getScheduleStartTimeTextEle() {
		String startTime = "type=='XCUIElementTypeStaticText' AND name=='Start Time'";
		return new Element(MobileBy.iOSNsPredicateString(startTime)).setDescription("Start time");
	}
	
	/*
	 * ACTIONS
	 */
	
	public static void enableDoNotDisturb() {
		if(getDoNotDisturbSwitch().getAttribute("value").equals("0")) {
			getDoNotDisturbSwitch().click();
		}
	}
	
	public static void disableDoNotDisturb() {
		if(getDoNotDisturbSwitch().getAttribute("value").equals("1")) {
			getDoNotDisturbSwitch().click();
		}
	}
	
	public static boolean verifyDoNotDisturbEnable() {
		return getDoNotDisturbSwitch().getAttribute("value").equals("1");
	}

	public static void enableNotificationAllDevice() {
		for(WebElement ele : getListDeviceSwitch()) {
			if(ele.getAttribute("value").equals("0")) {
				ele.click();
			}
		}
	}
	
	public static void disableNotificationAllDevice() {
		for(WebElement ele : getListDeviceSwitch()) {
			if (ele.getAttribute("value").equals("1")) {
				ele.click();
			}
		}
	}

	public static boolean verifyNotificationAllDeviceEnable() {
		for(WebElement ele : getListDeviceSwitch()) {
			if(!ele.getAttribute("value").equals("1")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean verifyNotificationAllDeviceDisable() {
		for(WebElement ele : getListDeviceSwitch()) {
			if(!ele.getAttribute("value").equals("0")) {
				return false;
			}
		}
		return true;
	}
	
	public static List<String> getListDeviceName() {
		List<String> lstDevice = new ArrayList<String>();
		for(WebElement ele : getListDeviceNameEle()) {
			lstDevice.add(ele.getText());
		}
		return lstDevice;
	}
	
	public static void enableDNDSchedule(boolean value) {
		if(value) {
			if (getScheduleSwitch().getAttribute("value").equals("0"))
				getScheduleSwitch().click();
		}else {
			if (getScheduleSwitch().getAttribute("value").equals("1"))
				getScheduleSwitch().click();
		}
	}
	
	public static boolean verifyDNDScheduleDisplay() {
		return getStartTimeTextField().getWebElement() != null && getStopTimeTextField().getWebElement() != null;
	}
	
	public static String getScheduleStartTime() {
		return getScheduleStartTimeValue().getText();
	}
	
	public static String getScheduleStopTime() {
		return getScheduleStopTimeValue().getText();
	}
	
	public static void openTimePicker() {
		getScheduleStartTimeTextEle().click();
	}
}
