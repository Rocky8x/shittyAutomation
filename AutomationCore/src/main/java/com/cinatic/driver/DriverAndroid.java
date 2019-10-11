package com.cinatic.driver;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.cinatic.config.TestConstant;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class DriverAndroid extends DriverBase {
	public DesiredCapabilities cap;
	public DriverAndroid(DriverSetting driverSetting) throws MalformedURLException {
		super(driverSetting);
		cap = new DesiredCapabilities();
		File appdir = new File(TestConstant.appFolder);
		File app = new File(appdir, driverSetting.getApp());
		
		cap.setCapability("platformName", driverSetting.getPlatformName());
		cap.setCapability("deviceName", driverSetting.getDeviceName());
		cap.setCapability("app", (app.getAbsolutePath()));
		cap.setCapability("noReset", true);
		cap.setCapability("newCommandTimeout", 7300);
		cap.setCapability("udid", driverSetting.getDeviceUDID());
		cap.setCapability("appPackage", driverSetting.getAppId());
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("noSign", true);
		cap.setCapability("skipServerInstallation", true);
		cap.setCapability("appActivity", driverSetting.appActivity());
		cap.setCapability("appPackage","com.perimetersafe.kodaksmarthome");
		
		AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
		appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, TestConstant.logLevel);
		appiumServiceBuilder.usingAnyFreePort();	
		appiumServiceBuilder.withArgument(GeneralServerFlag.ROBOT_ADDRESS, driverSetting.getDeviceUDID());
		this.setAppiumDriver(new AndroidDriver<WebElement>(appiumServiceBuilder, cap));
	}

}