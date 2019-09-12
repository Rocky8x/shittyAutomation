package ios.kodak.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageDeviceOrder extends PageBase{

	/*
	 * ELEMENTS
	 */
	
	public static Element getEditBtn() {
		String editBtn = "//XCUIElementTypeButton[@name='Edit']";
		return new Element(By.xpath(editBtn)).setDescription("Edit button");
	}
	
	public static List<WebElement> getListReOrderButton() {
		String reOrder = "//XCUIElementTypeButton[contains(@name,'Reorder')]";
		return DriverManager.getDriver().findElements(By.xpath(reOrder));
	}
	
	public static Element getPrimaryDevice() {
		String device = "//XCUIElementTypeOther[@name='Devices']/preceding-sibling::XCUIElementTypeCell/XCUIElementTypeStaticText";
		return new Element(By.xpath(device)).setDescription("Primary device");
	}
	
	public static List<WebElement> getListSecondaryDevice(){
		String device = "//XCUIElementTypeOther[@name='Devices']/following-sibling::XCUIElementTypeCell/XCUIElementTypeStaticText";
		return DriverManager.getDriver().findElements(By.xpath(device));
	}
	
	/*
	 * ACTIONS
	 */
	
	public static void clickEditButton() {
		 getEditBtn().click();
	}
	
	public static List<String> getListDeviceOrder(){
		Log.info("Get list device order.");
		List<String> lstDevice = new ArrayList<String>();
		lstDevice.add(getPrimaryDevice().getText());
		for(WebElement ele : getListSecondaryDevice()) {
			Log.info("Device: " + ele.getText());
			lstDevice.add(ele.getText());
		}
		return lstDevice;
	}
	
	public static void changeDeviceOrder() {
		getEditBtn().click();
		// Get random secondary camera
		int random = new Random().nextInt(getListReOrderButton().size());

		// Get location of primary and secondary camera
		int tapX1 = getListReOrderButton().get(random).getLocation().x + getListReOrderButton().get(random).getSize().getWidth()/2;
		int tapY1 = getListReOrderButton().get(random).getLocation().y + getListReOrderButton().get(random).getSize().getHeight()/2;
		int tapX2 = getPrimaryDevice().getLocation().x + getPrimaryDevice().getSize().getWidth()/2;
		int tapY2 = getPrimaryDevice().getLocation().y + getPrimaryDevice().getSize().getHeight()/2;
				
		// Change device order
		DriverManager.getDriver().swipe(tapX1, tapY1, tapX2, tapY2, 2000);
		
		clickDoneButton();
	}
}
