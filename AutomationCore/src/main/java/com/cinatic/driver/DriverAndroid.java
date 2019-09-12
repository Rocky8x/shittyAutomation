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
//		cap.setCapability("deviceVersion", driverSetting.getDeviceVersion());
		cap.setCapability("app", (app.getAbsolutePath()));
		cap.setCapability("noReset", true);
		cap.setCapability("newCommandTimeout", 7300);
		cap.setCapability("udid", driverSetting.getDeviceUDID());
		cap.setCapability("appActivity", "com.your.activity");
		cap.setCapability("appPackage", "com.your.package");
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("noSign", true);
		cap.setCapability("skipServerInstallation", true);
		cap.setCapability("appActivity", "com.cinatic.demo2.activities.splash.SplashActivity");

		if (app.getName().contains("kodak")) {
			cap.setCapability("appPackage","com.perimetersafe.kodaksmarthome");
		} else
		if (app.getName().contains("alecto")) {
			cap.setCapability("appPackage","com.perimetersafe.alecto");
		}
		
		TestConstant.appPackage = cap.getCapability("appPackage").toString();
		
		AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
		appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, TestConstant.logLevel);
		appiumServiceBuilder.usingAnyFreePort();	
		appiumServiceBuilder.withArgument(GeneralServerFlag.ROBOT_ADDRESS, driverSetting.getDeviceUDID());
		this.setAppiumDriver(new AndroidDriver<WebElement>(appiumServiceBuilder, cap));
		
//		this.setAppiumDriver(new AndroidDriver<WebElement>(new URL(driverSetting.getRemoteURL()), cap));
//		this.setAppiumDriver(new AndroidDriver<WebElement>(cap));
	}

}