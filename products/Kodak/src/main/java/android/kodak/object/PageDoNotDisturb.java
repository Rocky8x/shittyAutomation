package android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageDoNotDisturb extends PageBase {
	public static Element getDoNotDisturbSwitch() {
		String id = "switch_do_not_disturb";
		return new Element(By.id(id)).setDescription("DND switch");
	}
	
	public static Element getNotificationContainer() {
		String id = "layout_device_notification_container";
		return new Element(By.id(id)).setDescription("Notification setting page: Notification container");
	}
	
	public static List<WebElement> getListDeviceSwitch(){
		String id = "switch_device_chosen";
		return DriverManager.getDriver().findElements(By.xpath(id));
	}
	
	public static Element getDndScheduleSwitch() {
		String id = "switch_schedule";
		return new Element(By.id(id));
	}
	
	public static void enableDoNotDisturb(boolean value) {
		getDoNotDisturbSwitch().setValue(value);
	}
	
	public static void enableDndSchedule(boolean value) {
		getDndScheduleSwitch().setValue(value);
	}
	
	public static boolean verifyDoNotDisturbEnable(boolean value) {
		boolean rs = false;
		if(value) {
			rs = getDoNotDisturbSwitch().getAttribute("checked").equals(value + "") && getNotificationContainer().getWebElement() == null;
		}else {
			rs = getDoNotDisturbSwitch().getAttribute("checked").equals(value + "") && getNotificationContainer().getWebElement() != null;
		}
		return rs;
	}
	/**
	 * Duong Nguyen
	 * Enable/disable all device notifications
	 * @param value
	 */
	public static void enableAllDeviceNotification(boolean value) {
		for(WebElement ele : getListDeviceSwitch()) {
			if (Boolean.parseBoolean(ele.getAttribute("checked")) != value) {
				ele.click();
			}
		}
	}
	/**
	 * Duong nguyen
	 * Verify all device notification enable/disable
	 * @param value
	 * @return
	 */
	public static boolean verifyAllDeviceNotificationEnable(boolean value) {
		boolean rs = true;
		for(WebElement ele : getListDeviceSwitch()) {
			if (Boolean.parseBoolean(ele.getAttribute("checked")) != value) {
				rs = false;
			}
		}
		return rs;
	}
	
}
