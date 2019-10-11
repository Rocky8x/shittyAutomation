package com.ebn.automation.core;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cinatic.log.Log;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WbDriver implements WebDriver {

	private WebDriver		driver;
	private WebDriverWait	wait;
	static int				WAIT_TIME_OUT	= 30;

	public WbDriver(String browser) {

		// configDriverPath();

		Log.info("Starting new driver:", browser);
		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--disable-gpu");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			driver = new ChromeDriver(options);
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		default:
			break;
		}
		wait = new WebDriverWait(driver, WAIT_TIME_OUT);
	}

	public WebDriverWait waits() {

		return wait;
	}

	public WebDriver driver() {

		return driver;
	}

	@Override
	public void get(String url) {

		driver.get(url);
	}

	@Override
	public String getCurrentUrl() {

		return driver.getCurrentUrl();
	}

	@Override
	public String getTitle() {

		return driver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {

		return driver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {

		return driver.findElement(by);
	}

	@Override
	public String getPageSource() {

		return driver.getPageSource();
	}

	@Override
	public void close() {

		driver.close();
	}

	@Override
	public void quit() {

		driver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {

		return driver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {

		return driver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {

		return driver.switchTo();
	}

	@Override
	public Navigation navigate() {

		return driver.navigate();
	}

	@Override
	public Options manage() {

		return driver.manage();
	}

}
