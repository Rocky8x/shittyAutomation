package com.automation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WbDriverManager {

	static WebDriver driver;

	public static WebDriver getDriver() {

		return driver;
	}

	public static void setDriver(WebDriver driver) {

		WbDriverManager.driver = driver;
	}

	public static void newBrowser(String browser) {

		switch (browser.toLowerCase()) {
		case "chrome":
			Log.info("Starting new Chrome driver");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--disable-gpu");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			driver = new ChromeDriver(options);
			break;
		case "firefox":
			Log.info("Starting new Firefox driver");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		default:
			break;
		}
		driver.manage().window().maximize();
	}

	public static void navigateToUrl(String url) {

		Log.info("Navigate to: " + url);
		getDriver().navigate().to(url);
	}

	public static void closeBrowser() {

		try {
			Log.info("Close browser");
			getDriver().quit();
		} catch (Exception e) {
		}
	}

	public static String getCurrentUrl() {

		String url = driver.getCurrentUrl();
		Log.info("Current url: " + url);
		return url;
	}

	public static void waitElement(By by) {

		// By default it will accepts in Seconds
		WebDriverWait wait = new WebDriverWait(driver, 40);

		// Here we will wait until element is not visible, if element is visible
		// then it will return web element
		// or else it will throw exception
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public static List<WbElement> findElements(By by) {

		List<WebElement> webElements = driver.findElements(by);
		List<WbElement> wbElements = new ArrayList<WbElement>();
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

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		// below code will switch to new tab
		driver.switchTo().window(tabs.get(1));
		// perform whatever actions you want in new tab then close it
		driver.close();
		// Switch back to your original tab
		driver.switchTo().window(tabs.get(0));
	}

	public static void switchToNewWindow() {

		Log.info("Navigate to previous page");
		String currentWindowHandle = getDriver().getWindowHandle();
		// Get the list of all window handles
		ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());

		for (String window : windowHandles) {
			if (window != currentWindowHandle) {
				getDriver().switchTo().window(window);
			}
		}
	}

	public static void waitForPageLoad() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Time out after 6s, check every 1s
		for (int i = 0; i < 6; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	public static void takeScreenShot(String filePath) {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(filePath));
			Log.info("Copy: " + scrFile.toString() + "\n" + "to: " + filePath);
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			String path = ("<img src=\"" + filePath.substring(filePath.indexOf("html/") + 5) + "\" alt=\"\"/></img>");
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
