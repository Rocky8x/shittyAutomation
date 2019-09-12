package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

import io.appium.java_client.android.nativekey.AndroidKey;

public class PageAndroidQuickSetting {
	public static Element getWifiSwitch() {
		String xpath = "//android.widget.Switch[contains(@content-desc,'Wi-Fi')]";
		return new Element(By.xpath(xpath)).setDescription("System wifi switch");
	}
	
	public static Element getMobileDataSw() {
		String xpath = "//android.widget.Switch[contains(@content-desc,'Mobile data')]";
		return new Element(By.xpath(xpath)).setDescription("System mobile data switch");
	}
	
	public static void openWifiSetting() {
		getWifiSwitch().longPress();
	}
	
	public static boolean isWifiEnabled() {
		return getWifiSwitch().getAttribute("checked") == "true" ? true:false;
	}
	
	public static void exitPage() {
		DriverManager.getDriver().pressKey(AndroidKey.BACK);
	}
}
