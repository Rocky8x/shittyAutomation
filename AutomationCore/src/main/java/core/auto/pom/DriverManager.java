package core.auto.pom;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.auto.core.config.TestConstant;
import com.auto.core.helpers.StringHelper;
import com.auto.core.utils.Log;

import core.auto.pom.DriverSetting;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class DriverManager {

	private static Map<Long, WebDriver> hashDriver = new ConcurrentHashMap<Long, WebDriver>();

	public static synchronized void storeWebDriver(AppiumDriver<MobileElement> driver) {

		hashDriver.put(Thread.currentThread().getId(), driver);
	}

	public static synchronized WebDriver getDriver() {

		return hashDriver.get(Thread.currentThread().getId());
	}

	public static synchronized void removeWebDriver() {

		hashDriver.remove(Thread.currentThread().getId());
	}
	public static synchronized void createWebDriver(String browserName) {
		
	}
	
	public static synchronized void createMobileDriver(DriverSetting driverSetting) {

		AppiumDriver<MobileElement>	driver	= null;
		DesiredCapabilities			cap		= new DesiredCapabilities();

		cap.setCapability("platformName", driverSetting.getPlatformName());
		cap.setCapability("deviceName", driverSetting.getDeviceName());
		cap.setCapability("udid", driverSetting.getDeviceUDID());
		cap.setCapability("noReset", true);

		if (driverSetting.getPlatformName().toLowerCase().equals("android")) {
			File	appdir	= new File(TestConstant.appFolder);
			File	app		= new File(appdir, driverSetting.getApp());

			cap.setCapability("app", (app.getAbsolutePath()));
			cap.setCapability("newCommandTimeout", 7300);
			cap.setCapability("appPackage", driverSetting.getAppId());
			cap.setCapability("automationName", "uiautomator2");
			cap.setCapability("noSign", true);
			cap.setCapability("skipServerInstallation", true);
			cap.setCapability("appActivity", driverSetting.appActivity());

			AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
			appiumServiceBuilder.withArgument(GeneralServerFlag.ROBOT_ADDRESS,
					driverSetting.getDeviceUDID());

			driver = new AndroidDriver<MobileElement>(appiumServiceBuilder, cap);
		} else if (driverSetting.getPlatformName().toLowerCase().equals("ios")) {
			cap.setCapability("automationName", "XCUITest");
			cap.setCapability("platformVersion", driverSetting.getDeviceVersion());
			cap.setCapability("rotatable", true);
			cap.setCapability("bundleId", driverSetting.getAppId());
			cap.setCapability("showIOSLog", true);
			cap.setCapability("showXcodeLog", false);
			cap.setCapability("fullReset", false);
			cap.setCapability("newCommandTimeout", 600);
			driver = new IOSDriver<MobileElement>(cap);
		}
		if (driver != null) {
			storeWebDriver(driver);
			Log.info("-------------- Appium server started --------------");
		}
	}

	public static void captureScreen(String filename) throws InterruptedException {

		try {
			String	systemPath	= StringHelper.getSystemPath();
			String	fullPath	= systemPath + "/html/" + filename + ".png";
			Log.info("Screenshot saved: " + fullPath);

			new File(fullPath).mkdirs();
			File screen = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			ImageIO.write(ImageIO.read(screen), "png", new File(fullPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void killAllDrivers() {

		Set<Long> keys = hashDriver.keySet();
		for (Long k : keys) { hashDriver.get(k).quit(); }
	}
}
