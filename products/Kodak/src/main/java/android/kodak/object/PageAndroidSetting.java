package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;
import com.cinatic.log.Log;

public class PageAndroidSetting {
	public static Element getConnectedWifi() {
		String xpathConnectedWifi = "//android.widget.TextView[@resource-id='android:id/title']/following-sibling::android.widget.TextView[@text='Connected']";
		return new Element(By.xpath(xpathConnectedWifi)).setDescription("Connected wifi entry");
	}
	
	public static Element getWifinameEntry(String name) {
		String xpathWifiName = "//android.widget.TextView[@resource-id='android:id/title' and @text='" + name + "']";
		return new Element(By.xpath(xpathWifiName),name).setDescription("Wifi entry: " + name);
	}
	
	public static Element getPasswordBox() {
		String idPasswdBox = "com.android.settings:id/password";
		return new Element(By.id(idPasswdBox)).setDescription("Wifi passwd box");
	}
	
	public static Element getForgetNetworkButton() {
		String xpath = "//android.widget.TextView[@resource-id = 'android:id/title' and @text = 'Forget network']";
		return new Element(By.xpath(xpath)).setDescription("forget network button");
	}
	
	public static void clickConnectBtn() {
		String xpathString = "//android.widget.Button[@text='CONNECT']";
		new Element(By.xpath(xpathString)).setDescription("Connect button").click();
	}
	
	public static void clickCancelBtn() {
		new Element(By.linkText("CANCEL")).click();
	}
	
	public static void connectWifi(String name, String passwd) {
		getWifinameEntry(name).click();
		getPasswordBox().sendKeysNoHideKb(passwd);
		clickConnectBtn();
		waitTillWifiConnected();
		Log.info("Connected to wifi");
	}
	
	public static void connectWifi(String name) {
		getWifinameEntry(name).click();
		waitTillWifiConnected();
		Log.info("Connected to wifi");
	}
	
	public static void forgetOtherWifi(String name) {
		getWifinameEntry(name).longPress();
		getForgetNetworkButton().click();
	}
	
	public static void forgetConnectedWifi(String name) {
		getWifinameEntry(name).click();
		getForgetNetworkButton().click();
	}
	
	public static boolean isConnectedToWifi() {
		return getConnectedWifi().getText().equals("Connected") ? true:false;
	}
	
	private static boolean waitTillWifiConnected () {
		int maxTry = 5;
		while (maxTry > 0) {
			if(isConnectedToWifi()) {
				return true;
			}
			maxTry--;
		}
		return false;
	}
}
