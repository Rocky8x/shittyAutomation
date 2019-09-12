package ios.kodak.object;

import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

/*
 * Page time picker support configure time for schedule when enable detection schedule or do not disturb schedule
 * 
 */
public class PageTimePicker extends PageBase{
	
	/*
	 * ELEMENTS
	 */
	
	public static Element getMinutesPicker() {
		String picker = "type = 'XCUIElementTypePickerWheel' AND value ENDSWITH 'minutes' AND visible == true";
		return new Element(MobileBy.iOSNsPredicateString(picker)).setDescription("Minute picker");
	}
	
	public static Element getHourPicker() {
		String picker = "type = 'XCUIElementTypePickerWheel' AND value ENDSWITH 'o’clock' AND visible == true";
		return new Element(MobileBy.iOSNsPredicateString(picker)).setDescription("Hour picker");
	}
	
	public static Element getTimeUnitPicker() {
		String picker = "type = 'XCUIElementTypePickerWheel' AND value ENDSWITH 'M' AND visible == true";
		return new Element(MobileBy.iOSNsPredicateString(picker)).setDescription("Time unit picker");
	}
	
	public static Element getStartTimeExpandIcon() {
		String icon = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND value =='Start Time'$]/XCUIElementTypeButton";
		return new Element(MobileBy.iOSClassChain(icon)).setDescription("Start Time Expand Icon");
	}
	
	public static Element getStopTimeExpandIcon() {
		String icon = "**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND value =='Stop Time'$]/XCUIElementTypeButton";
		return new Element(MobileBy.iOSClassChain(icon)).setDescription("Stop Time Expand Icon");
	}
	
	/*
	 * ACTIONS
	 */
	
	/**
	 * Config Schedule Time. Format input HH:MM (HH:MM:AM/PM)
	 * @param startTime
	 * @param stopTime
	 * @param is24Hrs
	 * 
	 */
	public static void configScheduleTime(String startTime, String stopTime) {
		getStartTimeExpandIcon().click();
		setTimeForSchedule(startTime);
		getStopTimeExpandIcon().click();
		setTimeForSchedule(stopTime);
	}
	
	public static void setTimeForSchedule(String timeInput) {
		String [] times = timeInput.split(":");
		getHourPicker().getWebElement().sendKeys(times[0]);
		getMinutesPicker().getWebElement().sendKeys(times[1]);
	}
}
