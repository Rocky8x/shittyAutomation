package com.ebn.automation.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

import com.cinatic.log.Log;

public class WbDriverManager {

	private static Map<Long, WbDriver>	driverMap	= new ConcurrentHashMap<Long, WbDriver>();

	public static synchronized void putDriver(WbDriver driver) {

		driverMap.put(Thread.currentThread().getId(), driver);
	}

	public static synchronized WbDriver getDriver() {

		return driverMap.get(Thread.currentThread().getId());
	}

	public synchronized static void newBrowser(String browser) {

		putDriver(new WbDriver(browser));
	}

	public static void navigateToUrl(String url) {

		Log.info("Navigate to: " + url);
		getDriver().navigate().to(url);
	}

	public static void closeBrowser() {

		try {
			Log.info("Close browser");
			getDriver().quit();
		} catch (Exception e) {}
	}

	public static String getCurrentUrl() {

		return getDriver().getCurrentUrl();
	}

	public static void waitElement(By by) {


		// Here we will wait until element is not visible, if element is visible
		// then it will return web element
		// or else it will throw exception
		getDriver().waits().until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public static List<WbElement> findElements(By by) {

		List<WebElement>	webElements	= getDriver().findElements(by);
		List<WbElement>		wbElements	= new ArrayList<WbElement>();
		for (WebElement webElement : webElements) {
			wbElements.add(new WbElement(webElement, by.toString()));
		}
		return wbElements;
	}

	public static void backPreviousPage() {

		Log.info("Navigate to previous page");
		getDriver().navigate().back();
	}

	public static void closeCurrentTab() {

		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
		// below code will switch to new tab
		getDriver().switchTo().window(tabs.get(1));
		// perform whatever actions you want in new tab then close it
		getDriver().close();
		// Switch back to your original tab
		getDriver().switchTo().window(tabs.get(0));
	}

	public static void switchToNewWindow() {

		Log.info("Navigate to previous page");
		String currentWindowHandle = getDriver().getWindowHandle();
		// Get the list of all window handles
		ArrayList<String> windowHandles = new ArrayList<String>(getDriver().getWindowHandles());

		for (String window : windowHandles) {
			if (window != currentWindowHandle) { getDriver().switchTo().window(window); }
		}
	}

	public static void waitForPageLoad() {

		JavascriptExecutor js = (JavascriptExecutor) getDriver().driver();
		// Time out after 6s, check every 1s
		for (int i = 0; i < 6; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	public static void takeScreenShot(String filePath) {

		File scrFile = ((TakesScreenshot) getDriver().driver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(filePath));
			Log.info("Copy: " + scrFile.toString() + "\n" + "to: " + filePath);
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			String path = ("<img src=\"" + filePath.substring(filePath.indexOf("html/") + 5)
					+ "\" alt=\"\"/></img>");
			Reporter.log(path);

		} catch (Exception e) {
			Log.fatal("Cannot take screenshot");
			Log.fatal(e.getMessage());
		}
	}

	public static void acceptAlert() {

		getDriver().switchTo().alert().accept();
	}
}