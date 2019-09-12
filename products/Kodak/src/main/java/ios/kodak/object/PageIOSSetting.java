package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageIOSSetting extends PageBase{
	/*
	 * ELEMENT
	 */
	
	public static Element getWifiSetting() {
		String wifiEle = "name=='Wi-Fi'";
		return new Element(MobileBy.iOSNsPredicateString(wifiEle)).setDescription("Wifi setting");
	}
	
	public static Element getWifiByName(String wifiName) {
		String wifipath = "name==\"%s\"";
		return new Element(MobileBy.iOSNsPredicateString(String.format(wifipath, wifiName))).setDescription("Wifi: " + wifiName);
		
	}
	
	public static Element getBreakcumBtn() {
		String btn = "name=='breadcrumb'";
		return new Element(MobileBy.iOSNsPredicateString(btn)).setDescription("Back to Kodak App");
	}

	public static Element getKodakApp() {
		return new Element(By.name("KODAK Smart Home")).setDescription("KODAK Smart Home App");
	}
	
	public static Element getNaturalPTZSwitch() {
		String sw = "type=='XCUIElementTypeSwitch' AND name BEGINSWITH[c] 'Natural PTZ'";
		return new Element(MobileBy.iOSNsPredicateString(sw)).setDescription("Natural PTZ switch");
	}
	
	public static Element getStreamingInfosSw() {
		String sw = "type=='XCUIElementTypeSwitch' AND name BEGINSWITH[c] 'Streaming Infos'";
		return new Element(MobileBy.iOSNsPredicateString(sw)).setDescription("Streaming info");
	}
	/*
	 * ACTION
	 */
	
	public static void clickWifiSettings() {
		getWifiSetting().click();
	}
	
	public static void clickOnWifiName(String wifiName) {
		while (getWifiByName(wifiName).getWebElement() != null) {
			Element ele = getWifiByName(wifiName);
			if(ele.getAttribute("visible").equals("true")) {
				ele.click();
				break;
			}else {
				swipeTopToBottom();
			}
		}
	}
	
	public static void backToKodakApp() {
		getBreakcumBtn().click();
	}
	
	public static void clickKodakApp() {
		while (getKodakApp().getWebElement() != null) {
			Element ele = getKodakApp();
			if(ele.getAttribute("visible").equals("true")) {
				ele.click();
				break;
			}else {
				swipeTopToBottom();
			}
		}
	}
	
	public static void enableStreamingInfo(boolean value) {
		if(value) {
			if (getStreamingInfosSw().getAttribute("value").equals("0"))
				getStreamingInfosSw().click();
		}else {
			if (getStreamingInfosSw().getAttribute("value").equals("1"))
				getStreamingInfosSw().click();
		}
	}
	
	public static void enableNaturalPTZDirection(boolean value) {
		if (value) {
			if (getNaturalPTZSwitch().getAttribute("value").equals("0"))
				getNaturalPTZSwitch().click();
		}else {
			if (getNaturalPTZSwitch().getAttribute("value").equals("1"))
				getNaturalPTZSwitch().click();
		}
	}
}
