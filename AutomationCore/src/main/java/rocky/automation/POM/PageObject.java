package rocky.automation.POM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cinatic.log.Log;

import io.appium.java_client.MobileElement;

public class PageObject {

	private WebDriver			driver;
	protected WebDriverWait		waits;
	private static final int	WAIT_TIME	= 5;

	protected MobileElement find(By elementInfo) {

		return waitForAppear(elementInfo, WAIT_TIME);
	}

	public WebDriverWait getWaits() {

		return waits;
	}

	public void setWaits(WebDriverWait waits) {

		this.waits = waits;
	}

	public WebDriver getDriver() {

		return driver;
	}

	public void setDriver(WebDriver driver) {

		this.driver	= driver;
		this.waits	= new WebDriverWait(driver, WAIT_TIME);
		PageFactory.initElements(driver, this);

	}

	public PageObject() {

	}

	public PageObject(WebDriver driver) {

		setDriver(driver);
	}

	public MobileElement waitForAppear(By by, int timeout) {

		MobileElement el = null;
		try {

			Log.info("Wait appear:", by.toString());
			el = (MobileElement) waits.withTimeout(Duration.ofSeconds(timeout))
					.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {}
		return el;
	}

	public MobileElement waitClickable(By by) {

		Log.info("Wait clickable:", by.toString());
		return (MobileElement) waits.until(ExpectedConditions.elementToBeClickable(by));
	}
}
