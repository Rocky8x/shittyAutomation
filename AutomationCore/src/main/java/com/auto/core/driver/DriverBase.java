package com.auto.core.driver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.Response;
import org.springframework.util.StopWatch;

import com.auto.core.helpers.TimeHelper;
import com.auto.core.utils.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class DriverBase implements Driver {
	protected AppiumDriver<WebElement> appiumDriver;
	private DriverSetting driverSetting;

	@Override
	public AppiumDriver<WebElement> getAppiumDriver() {
		return appiumDriver;
	}

	public void setAppiumDriver(AppiumDriver<WebElement> appiumDriver) {
		this.appiumDriver = appiumDriver;
	}

	@Override
	public DriverSetting getDriverSetting() {
		return driverSetting;
	}

	public void setDriverSetting(DriverSetting driverSetting) {
		this.driverSetting = driverSetting;
	}

	public DriverBase(DriverSetting driverSetting) {
		setDriverSetting(driverSetting);
	}

	@Override
	public void get(String url) {
		appiumDriver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		logStart("get current url");
		return appiumDriver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		return appiumDriver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
//		logStart(String.format("find elements: %s", by));
		List<WebElement> elements = appiumDriver.findElements(by);
		return elements;
	}

	@Override
	public WebElement findElement(By by) {
//		logStart(String.format("find element: %s", by));
		WebElement element = appiumDriver.findElement(by);
		return element;
	}

	@Override
	public WebElement findElement(By by, int timeout) {
//		logStart(String.format("find element: %s", by));
		WebElement element = null;
		StopWatch tw = new StopWatch();
		if (timeout > 0) {
			try {
				tw.start();
				element = appiumDriver.findElement(by);
			} catch (Exception ex) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tw.stop();
				timeout = (int) (timeout - tw.getTotalTimeSeconds());
				element = findElement(by, timeout);
			}
		}
		return element;
	}

	@Override
	public String getPageSource() {
		logStart("get page source");
		return appiumDriver.getPageSource();
	}

	@Override
	public void close() {
		logStart("close driver");
		appiumDriver.close();
	}

	@Override
	public void quit() {
		logStart("quit driver");
		appiumDriver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		logStart("get window handles");
		return appiumDriver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		logStart("get window handle");
		return appiumDriver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Navigation navigate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Options manage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver context(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getContextHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void performMultiTouchAction(MultiTouchAction arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	@SuppressWarnings("rawtypes")
	public TouchAction performTouchAction(TouchAction arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hideKeyboard() {
		try {
			logStart("hide keyboard");
			appiumDriver.hideKeyboard();
		} catch (Exception ex) {

		}
	}

	@Override
	public void tap(int x, int y) {
		logStart(String.format("tap: %s %s", x, y));
		new TouchAction<>(appiumDriver).tap(PointOption.point(x, y)).perform();
	}
	
	@Override
	public void tap(Point point) {
		logStart(String.format("tap: %s %s", point.x, point.y));
		new TouchAction<>(appiumDriver).tap(PointOption.point(point.x, point.y)).perform();
	}

	@Override
	public BufferedImage captureScreenshot() {
		logStart("capture screenshot");
		File screen = ((TakesScreenshot) DriverManager.getDriver().getAppiumDriver())
				.getScreenshotAs(OutputType.FILE);
		try {
			return ImageIO.read(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BufferedImage captureScreenshot(int x, int y, int w, int h) {
		logStart(String.format("capture screenshot: %s %s %s %s", x, y, w, h));
		File screen = ((TakesScreenshot) DriverManager.getDriver().getAppiumDriver())
				.getScreenshotAs(OutputType.FILE);

		BufferedImage img = null;
		try {
			img = ImageIO.read(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage dest = img.getSubimage(x, y, w, h);
		return dest;
	}

	@Override
	public ScreenOrientation getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rotate(ScreenOrientation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public WebElement findElementByAccessibilityId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByAccessibilityId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location location() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocation(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipe(int tapX1, int tapY1, int tapX2, int tapY2, int i) {
		logStart(String.format("swipe: %s %s %s %s %s", tapX1, tapY1, tapX2, tapY2, i));
		new TouchAction<>(appiumDriver).press(PointOption.point(tapX1, tapY1))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(i))).moveTo(PointOption.point(tapX2, tapY2))
				.release().perform();
	}

	@Override
	public void swipe(Point p1, Point p2, int wait) {
		logStart(String.format("swipe Point to Point: %s %s %s", p1.toString(), p2.toString(), wait));
		new TouchAction<>(appiumDriver).press(PointOption.point(p1.x, p1.y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(p2.x, p2.y))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(wait))).release().perform();
	}

	@Override
	public byte[] pullFile(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] pullFolder(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeApp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void installApp(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAppInstalled(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void launchApp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetApp() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAppStrings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAppStrings(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByClassName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByCssSelector(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementById(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByPartialLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByTagName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElementByXPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByClassName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByCssSelector(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsById(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByPartialLinkText(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByTagName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElementsByXPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeviceTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAppStringMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAppStringMap(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAppStringMap(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement scrollTo(String text) {
		return ((AndroidDriver<WebElement>) appiumDriver).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ text + "\").instance(0))");
	}
	
	@Override
	public WebElement scrollToId(String id) {
		return ((AndroidDriver<WebElement>) appiumDriver).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\".*id/"
						+ id + "\").instance(0))");
	}

	@Override
	public Response execute(String driverCommand, Map<String, ?> parameters) {
		return null;
	}

	private void logStart(String msg) {
		Log.debug(String.format("- [{%s}]", msg));
	}

//	private void logEnd(String msg) {		
//		Log.debug(String.format("+ [{%s}]", msg));
//	}

	@Override
	public void rotate(DeviceRotation rotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public DeviceRotation rotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response execute(String driverCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement findElement(String by, String using) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElements(String by, String using) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebElement scrollToExact(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pressKey(AndroidKey keyEvent) {
		logStart(String.format("press key event: %s", keyEvent));
		((AndroidDriver<WebElement>) appiumDriver).pressKey(new KeyEvent(keyEvent));	
	}
	
	@Override
	public void switchRecentApp() {
		pressKey(AndroidKey.APP_SWITCH);
		TimeHelper.sleep(300);
		pressKey(AndroidKey.APP_SWITCH);

	}
}