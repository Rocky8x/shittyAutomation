package com.cinatic.driver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import com.cinatic.StringHelper;
import com.cinatic.log.Log;

public class DriverManager {

	private static Map<Long, Driver> hashDriver = new ConcurrentHashMap<Long, Driver>();

	public static synchronized void storeWebDriver(Driver driver) {
		hashDriver.put(Thread.currentThread().getId(), driver);
	}

	public static synchronized Driver getDriver() {
		return hashDriver.get(Thread.currentThread().getId());
	}

	public static synchronized void removeWebDriver() {
		hashDriver.remove(Thread.currentThread().getId());
	}

	public static synchronized void createWebDriver(DriverSetting driverSetting) {
		Driver driver = null;
		if (driverSetting.getPlatformName().toLowerCase().equals("android")) {
			try {
				driver = new DriverAndroid(driverSetting);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (driverSetting.getPlatformName().toLowerCase().equals("ios")) {
			try {
				driver = new DriverIOS(driverSetting);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		if (driver != null) {
			storeWebDriver(driver);
			Log.info("-------------- Appium server started --------------");
		}
	}

	public static void captureScreen(String filename) throws InterruptedException {
		try {
			String systemPath = StringHelper.getSystemPath();			
			String fullPath = systemPath + "/html/" + filename + ".png";
			Log.info("Screenshot saved: " + fullPath);

			new File(fullPath).mkdirs();
			ImageIO.write(getDriver().captureScreenshot(), "png", new File(fullPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void killAllDrivers() {
		Set<Long> keys = hashDriver.keySet();		
		for (Long k : keys) {
			hashDriver.get(k).quit();
		}
	}
}
