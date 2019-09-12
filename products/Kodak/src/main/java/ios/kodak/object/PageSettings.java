package ios.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;

public class PageSettings extends PageBase {

	/*
	 * ELEMENTS
	 */
	
	public static Element getFahrenheitUnit() {
		String unit = "//XCUIElementTypeButton[@name='°F']";
		return new Element(By.xpath(unit)).setDescription("Fahrenheit Unit");
	}
	
	public static Element getCelsiusUnit() {
		String unit = "//XCUIElementTypeButton[@name='°C']";
		return new Element(By.xpath(unit)).setDescription("Celsius Unit");
	}
	
	public static Element getBAMSwitch() {
		String sw = "type=='XCUIElementTypeSwitch' AND name=='Background Audio Monitoring'";
		return new Element(MobileBy.iOSNsPredicateString(sw)).setDescription("BAM switch");
	}
	
	public static Element getBAMText() {
		String ele = "type=='XCUIElementTypeStaticText' AND name=='Background Audio Monitoring'";
		return new Element(MobileBy.iOSNsPredicateString(ele)).setDescription("BAM text");
	}
	
	/*
	 * ACTIONS
	 */
	
	public static void selectFahrenheitUnit() {
		if(!"1".equals(getFahrenheitUnit().getAttribute("value"))) {
			getFahrenheitUnit().click();
		}
	}
	
	public static void selectCelsiusUnit() {
		if(!"1".equals(getCelsiusUnit().getAttribute("value")))
			getCelsiusUnit().click();
	}
	
	public static boolean verifyFahrenheitUnitSelected() {
		return "1".equals(getFahrenheitUnit().getAttribute("value"));
	}
	
	public static boolean verifyCelsiusUnitSelected() {
		return "1".equals(getCelsiusUnit().getAttribute("value"));
	}
	
	public static void enableBAMSwitch(boolean value) {
		if(value) {
			if (getBAMSwitch().getAttribute("value").equals("0"))
				getBAMSwitch().click();
		}else {
			if (getBAMSwitch().getAttribute("value").equals("1"))
				getBAMSwitch().click();
		}
	}
	
	public static boolean verifyBAMSwitchEnable(boolean value) {
		if (value) {
			return getBAMSwitch().getAttribute("value").equals("1");
		}else {
			return getBAMSwitch().getAttribute("value").equals("0");
		}
	}
	
	public static void openBAMSettingDetail() {
		getBAMText().click();
	}
	
	public static class BackGroundAudioMonitoring{
		/*
		 * ELEMENT
		 */
		
		public static Element getBAMSwitchInside() {
			String sw = "**/XCUIElementTypeOther[$type=='XCUIElementTypeStaticText' AND name=='Off'$]/XCUIElementTypeSwitch";
			return new Element(MobileBy.iOSClassChain(sw)).setDescription("BAM switch");
		}
		
		public static Element getArrowUpIcon() {
			String icon = "ic arrow up";
			return new Element(MobileBy.name(icon)).setDescription("Arrow up icon");
		}
		
		public static Element getArrowDownIcon() {
			String icon = "ic arrow down";
			return new Element(MobileBy.name(icon)).setDescription("Arrow down icon");
		}
		
		public static Element getBAMTimeEle() {
			String bamTime = "**/XCUIElementTypeOther[$type=='XCUIElementTypeButton' AND name=='ic arrow up'$]/XCUIElementTypeTextField";
			return new Element(MobileBy.iOSClassChain(bamTime)).setDescription("BAM time");
		}
		
		/*
		 * ACTIONS
		 */
		
		public static void enableInsideBAMSwitch(boolean value) {
			if(value) {
				if (getBAMSwitchInside().getAttribute("value").equals("0"))
					getBAMSwitchInside().click();
			}else {
				if (getBAMSwitchInside().getAttribute("value").equals("1"))
					getBAMSwitchInside().click();
			}
		}
		
		public static boolean verifyInsideBAMSwithEnable(boolean value) {
			if (value) {
				return getBAMSwitchInside().getAttribute("value").equals("1");
			}else {
				return getBAMSwitchInside().getAttribute("value").equals("0");
			}
		}
		
		public static String getBAMTime() {
			return getBAMTimeEle().getText();
		}
	
		public static void increaseTime() {
			getArrowUpIcon().click();
		}
		
		public static void decreaseTime() {
			getArrowDownIcon().click();
		}
	}
}
