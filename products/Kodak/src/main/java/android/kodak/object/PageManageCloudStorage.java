package android.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

public class PageManageCloudStorage extends PageBase{
	
	public static Element getYourCurrentPlan() {
		String id = "text_sub_plan_name";
		return new Element(By.id(id),15).setDescription("Current storage plan");
	}
	
	public static List<WebElement> getListDevicesEle(){
		String id = "textview_device_chosen";
		return DriverManager.getDriver().findElements(By.id(id));
	}
	
	public static String getYourCurrentStoragePlan() {
		return getYourCurrentPlan().getText();
	}
	
	public static List<String> getListDevicesName(){
		List<String> lstDevices = new ArrayList<>();
		for (WebElement ele : getListDevicesEle()) {
			lstDevices.add(ele.getText());
		}
		return lstDevices;
	}
}
