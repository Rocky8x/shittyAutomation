package android.kodak.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageDevicesOrder extends PageBase{
	
	public static Element getEditButton() {
		String id = "text_action_edit";
		return new Element(By.id(id)).setDescription("Edit order button");
	}
	
	public static Element getDoneButton() {
		String id = "text_action_done";
		return new Element(By.id(id)).setDescription("Edit order done button");
	}
	
	public static Element getPrimaryDevice() {
		String primary = "//android.widget.TextView[@text='Devices']/../../preceding-sibling::android.widget.LinearLayout//android.widget.TextView[contains(@resource-id,'textview_device_chosen')]";
		return new Element(By.xpath(primary)).setDescription("Device order: Primary camera");
	}
	
	// List off devices in secondary section ("Devices" section of non primary devices)
	public static List<WebElement> getListSecondaryDevices(){
		String secondary = "//android.widget.TextView[@text='Devices']/../../following-sibling::android.widget.LinearLayout//android.widget.TextView[contains(@resource-id,'textview_device_chosen')]";
		return DriverManager.getDriver().findElements(By.xpath(secondary));
	}

	// Parent view which contains both Primary and Devices section
	public static Element getDeviceOrderListEle() {
		String id = "device_order_list";
		return new Element(By.id(id)).setDescription("Device Order: Whole Primary & Devices section");
	}
	
	// Get a list off all Elements which shows as camera entry in this page
	public static List<WebElement> getListDevicesEle(){
		String id = "textview_device_chosen";
		return DriverManager.getDriver().findElements(By.id(id));
	}
	/**
	 * Duong Nguyen
	 * Get Name of device in device order page
	 * @return list name of device
	 */
	public static List<String> getListDeviceName(){
		List<String> lstDevices = new ArrayList<>();
		for(WebElement ele : getListDevicesEle()) {
			lstDevices.add(ele.getText());
		}
		return lstDevices;
	}
	
	public static void clickEditButton() {
		getEditButton().click();
	}
	
	public static void clickDoneButton() {
		getDoneButton().click();
	}
	/**
	 * Duong Nguyen
	 * Change primary device with random secondary device
	 */
	public static void changeDevicesOrder() {
		clickEditButton();
		// Get primary device location
		int tapX1 = getPrimaryDevice().getLocation().x + getPrimaryDevice().getSize().getWidth() / 2;
		int tapY1 = getPrimaryDevice().getLocation().y + getPrimaryDevice().getSize().getHeight() / 2;
		// Get random device to change with primary device
		int rnd = new Random().nextInt(getListSecondaryDevices().size());
		// Get secondary device location
		int tapX2 = getListSecondaryDevices().get(rnd).getLocation().x + getListSecondaryDevices().get(rnd).getSize().getWidth() / 2;
		int tapY2 = getListSecondaryDevices().get(rnd).getLocation().y + getListSecondaryDevices().get(rnd).getSize().getHeight();
		// Swipe from primary device to secondary device
		DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
		clickDoneButton();
	}
	
}
