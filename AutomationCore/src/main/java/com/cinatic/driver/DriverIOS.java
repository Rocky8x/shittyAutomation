package com.cinatic.driver;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.cinatic.element.Element;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class DriverIOS extends DriverBase {

	public DriverIOS(DriverSetting driverSetting) throws MalformedURLException {
		super(driverSetting);
		if (driverSetting.getPlatformName().equalsIgnoreCase("ios")){
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("automationName", "XCUITest");
			cap.setCapability("platformName", driverSetting.getPlatformName());
			cap.setCapability("deviceName", driverSetting.getDeviceName());
			cap.setCapability("platformVersion", driverSetting.getDeviceVersion());
			cap.setCapability("rotatable", true);
			cap.setCapability("bundleId", driverSetting.getAppId());
			cap.setCapability("showIOSLog",true );
			cap.setCapability("showXcodeLog", false);
			cap.setCapability("udid", driverSetting.getDeviceUDID());
			cap.setCapability("noReset", true);
			cap.setCapability("fullReset", false);
			cap.setCapability("newCommandTimeout", 600);
			this.setAppiumDriver(new IOSDriver<WebElement>(cap));
		}
	}
	
	@Override
	public WebElement scrollTo(String name) {
		String predicateString = "name == '" + name + "'";
		Element element = new Element(MobileBy.iOSNsPredicateString(predicateString));
		JavascriptExecutor js = appiumDriver;
		HashMap<String, String> scrollObj = new HashMap<String, String>();
		scrollObj.put("predicateString", predicateString);
		scrollObj.put("toVisible", "not an empty string");
		while (element.getWebElement() != null) {
			element = new Element(MobileBy.iOSNsPredicateString(predicateString));
			if(element.getAttribute("visible").equals("true")) {
				break;
			}else {
				js.executeScript("mobile: scroll", scrollObj);
			}
		}
        return element;
	}
}