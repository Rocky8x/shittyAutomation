package com.cinatic.driver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.util.StopWatch;

import com.cinatic.TimeHelper;
import com.cinatic.log.Log;

public class WebDriverHelper {

	public WebDriver	driver;
	public String		browser	= "";

	public WebDriverHelper() {

		configDriverPath();
		driver = new ChromeDriver();
	}

	public WebDriverHelper(String browser) {

		configDriverPath();
		this.browser = browser;
		switch (browser) {
		case "chrome":
			driver = new ChromeDriver();
			break;

		case "firefox":
			driver = new FirefoxDriver();
			break;
		}

	}

	public WebElement findWebElement(By by, int timeout) {

		WebElement	element	= null;
		StopWatch	tw		= new StopWatch();
		if (timeout > 0) {
			try {
				tw.start();
				element = driver.findElement(by);
			} catch (Exception ex) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tw.stop();
				timeout	= (int) (timeout - tw.getTotalTimeSeconds());
				element	= findWebElement(by, timeout);
			}
		}
		return element;
	}

	public WebElement findElement(By by) {

		return findWebElement(by, 30);
	}

	public void clearCookieAndRefress() {

		driver.manage().deleteAllCookies();
		TimeHelper.sleep(2 * 1000);
		driver.navigate().refresh();
	}

	public void closeBrowser() {

		driver.close();
		if (!browser.equals("firefox")) { driver.quit(); }
	}

	public void openBrowserAndNavigatetoUrl(String url) {

		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private void configDriverPath() {

		String	webDriverLibPath	= ".." + File.separatorChar + "lib" + File.separatorChar;
		String	driverChromeDriver	= webDriverLibPath + "chromedriver.";
		String	driverGeckoDriver	= webDriverLibPath + "geckodriver.";
		if (System.getProperty("os.name").equals("Linux")) {
			driverChromeDriver	+= "linux";
			driverGeckoDriver	+= "linux";
		} else if (System.getProperty("os.name").contains("Windows")) {
			driverChromeDriver	+= "exe";
			driverGeckoDriver	+= "exe";
		} else {
			driverChromeDriver	+= "mac";
			driverGeckoDriver	+= "mac";
		}

		System.setProperty("webdriver.chrome.driver", driverChromeDriver);
		Log.debug("Config Chrome web driver: ", driverChromeDriver);

		System.setProperty("webdriver.gecko.driver", driverGeckoDriver);
		Log.debug("Config Firefoxweb driver: ", driverGeckoDriver);
	}
}
