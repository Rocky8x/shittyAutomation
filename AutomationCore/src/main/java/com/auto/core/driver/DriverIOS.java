package com.auto.core.driver;

import java.net.MalformedURLException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

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
}