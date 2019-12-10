package com.cinatic.driver;

import java.awt.image.BufferedImage;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.nativekey.AndroidKey;

public interface Driver extends MobileDriver<WebElement> {

	AppiumDriver<?> getAppiumDriver();

	DriverSetting getDriverSetting();

	/**
	 * @param by By
	 * @param timeout in second
	 * @return 
	 */
	WebElement findElement(By by, int timeout);

	void tap(int x, int y);
	void tap(Point point);

	WebElement scrollTo(String text);
	
	WebElement scrollToId(String id);

	BufferedImage captureScreenshot();

	BufferedImage captureScreenshot(int x, int y, int w, int h);

	WebElement scrollToExact(String text);

	String getAppStrings();

	String getAppStrings(String text);

	void swipe(int tapX1, int tapY1, int tapX2, int tapY2, int i);
	
	void swipe(Point p1, Point p2, int wait);
	void switchRecentApp();
	void pressKey(AndroidKey keyEvent);
}
