package com.cinatic.element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class Element implements WebElement {

	public static final int	longTime	= 30;
	public static final int	mediumTime	= 15;
	public static final int	shortTime	= 5;

	public WebElement	webElement	= null;
	public String		description	= "";
	public By			by;
	public int			timeout		= 5;
	public String		findText;

	public Element() {
	}

	public Element(By by) {

		this.by		= by;
		description	= by.toString();
	}

	public Element(By by, int timeout) {

		this.by			= by;
		description		= by.toString();
		this.timeout	= timeout;
	}

	public Element(By by, String scrollToText) {

		this.by		= by;
		description	= by.toString();
		findText	= scrollToText;
	}

	public Element(By by, String scrollToText, int timeout) {

		this.by		= by;
		description	= by.toString();
		findText	= scrollToText;
	}

	public WebElement getWebElement() {

		Log.debug(String.format("Get element: %s", description));
		return webElement;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {

		return webElement.getScreenshotAs(target);
	}

	@Override
	public void clear() {

		Log.debug(String.format("clear text in: %s", description));
		webElement.clear();
	}

	@Override
	public void click() {

		try {
			Log.debug(String.format("clicking: %s", description));
			webElement.click();
		} catch (NullPointerException e) {
			Log.debug("Couldn't find: " + description);
			throw e;
		}
	}

	public void click(int x, int y) {

		Point p = webElement.getLocation();
		DriverManager.getDriver().tap(p.x + x, p.y + y);
	}

	@Override
	public WebElement findElement(By by) {

		Log.info("Finding: ", description);
		if (webElement == null) {
			String[] stringBy = by.toString().split(":");
			if (stringBy[0].equals("By.id")) {
				try {
					DriverManager.getDriver().scrollToId(stringBy[1].trim());
				} catch (Exception e) {}
			}
		}

		return DriverManager.getDriver().findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {

		return webElement.findElements(by);
	}

	@Override
	public String getAttribute(String name) {

		Log.debug(String.format("get attribute %s = %s of %s", name, webElement.getAttribute(name),
				description));
		return webElement.getAttribute(name);
	}

	@Override
	public String getCssValue(String propertyName) {

		return webElement.getCssValue(propertyName);
	}

	public boolean waitForDisappear(int timeout) {

		WebDriverWait waits = new WebDriverWait(DriverManager.getDriver().getAppiumDriver(),
				timeout);
		try {
			Log.info("Wait for", description, "to be gone");
			waits.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			Log.debug("Tired of waiting for " + description + " to disappear. Move on");
		}
		return false;
	}

	public Element waitForAppear(int timeout) {

		Log.debug("Wait for " + description + " to appear");
		try {

			WebDriverWait waits = new WebDriverWait(DriverManager.getDriver().getAppiumDriver(),
					30);
			webElement = waits.until(ExpectedConditions.presenceOfElementLocated(by));
			return this;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Point getLocation() {

		Log.debug(String.format("get location value \"%s\": %s", webElement.getLocation(),
				description));
		return webElement.getLocation();
	}

	@Override
	public Rectangle getRect() {

		Log.debug(String.format("get rect value \"%s\": %s", webElement.getRect(), description));
		return webElement.getRect();
	}

	@Override
	public Dimension getSize() {

		Log.debug(String.format("get size value \"%s\": %s", webElement.getSize(), description));
		return webElement.getSize();
	}

	@Override
	public String getTagName() {

		Log.debug(String.format("get tag name value \"%s\": %s", webElement.getTagName(),
				description));
		return webElement.getTagName();
	}

	@Override
	public String getText() {

		Log.debug(String.format("get text value \"%s\": %s", webElement.getText(), description));
		return webElement.getText();
	}

	@Override
	public boolean isDisplayed() {

		Log.debug(String.format("get displayed status \"%s\": %s", webElement.isDisplayed(),
				description));
		return webElement.isDisplayed();
	}

	@Override
	public boolean isEnabled() {

		Log.debug(String.format("get enabled status \"%s\": %s", webElement.isEnabled(),
				description));
		return webElement.isEnabled();
	}

	@Override
	public boolean isSelected() {

		Log.debug(String.format("get selected status \"%s\": %s", webElement.isSelected(),
				description));
		return webElement.isSelected();
	}

	public void sendKeysNoHideKb(CharSequence... text) {

		if (text[0] != null && !text[0].equals("N/A")) {
			Log.debug(String.format("send \"%s\" to: %s", text[0], description));
			// clearText();
			webElement.clear();
			webElement.sendKeys(text);
		}
	}

	@Override
	public void sendKeys(CharSequence... text) {

		if (text[0] != null && !text[0].equals("N/A")) {
			Log.debug(String.format("send \"%s\" to: %s", text[0], description));
			// clearText();
			webElement.clear();
			webElement.sendKeys(text);
			DriverManager.getDriver().hideKeyboard();
		}
	}

	public void sendKeys(boolean pressBackspaceKey, CharSequence... text) {

		if (!text[0].equals("N/A")) {
			Log.debug(String.format("send \"%s\" to: %s", text[0], description));
			if (pressBackspaceKey)
				clearText();
			webElement.sendKeys(text);
			DriverManager.getDriver().hideKeyboard();
		}
	}

	/**
	 * Input value without hide key board
	 * 
	 * @param text
	 */
	public void inputValue(CharSequence... text) {

		if (text[0] != null && !text[0].equals("N/A")) {
			Log.debug(String.format("send \"%s\" to: %s", text[0], description));
			// clearText();
			webElement.click();
			webElement.clear();
			webElement.sendKeys(text);
		}
	}

	public void setValue(boolean t) {

		Log.debug(String.format("set value: %s %s", t, description));
		if (Boolean.parseBoolean(webElement.getAttribute("checked")) != t) { webElement.click(); }
	}

	public void clearText() {

		Log.debug(String.format("clear text: %s", description));
		Point	p	= webElement.getLocation();
		int		w	= webElement.getSize().width;
		int		h	= webElement.getSize().height;

		for (int j = 0; j < 3; j++) {
			DriverManager.getDriver().tap(p.x + w / 2, p.y + h / 2);
			for (int i = 0; i < 10; i++) { PressBackspaceKey(); }
		}
	}

	public void longPress() {

		Log.info("Long touch: " + description);
		TouchAction<?>		action	= new TouchAction<>(
				DriverManager.getDriver().getAppiumDriver());
		LongPressOptions	option	= new LongPressOptions()
				.withElement(ElementOption.element(webElement));
		action.longPress(option).perform();
	}

	public void PressBackspaceKey() {

		Log.debug(String.format("press backspace key: %s", description));
		DriverManager.getDriver().pressKey(AndroidKey.BACK);
		;
	}

	public BufferedImage captureScreenshot() {

		Log.debug(String.format("capture element screen: %s", description));
		File			screen		= ((TakesScreenshot) DriverManager.getDriver()
				.getAppiumDriver()).getScreenshotAs(OutputType.FILE);
		int				ImageWidth	= webElement.getSize().getWidth();
		int				ImageHeight	= webElement.getSize().getHeight();
		Point			point		= webElement.getLocation();
		int				xcord		= point.getX();
		int				ycord		= point.getY();
		BufferedImage	img			= null;
		try {
			img = ImageIO.read(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
		return dest;
	}

	@Override
	public void submit() {

		Log.debug(String.format("submit: %s", description));
		webElement.submit();
	}

	public Element setDescription(String description) {

		this.description = description;
		return this;
	}

	public String getDescription() {

		return description;
	}

	public Element waitClickable() {

		WebDriverWait waits = new WebDriverWait(DriverManager.getDriver().getAppiumDriver(), 10);
		waits.until(ExpectedConditions.elementToBeClickable(webElement));
		return this;
	}

	public Element find() {

		if (findText != null) {
			try {

				DriverManager.getDriver().scrollTo(findText);
			} catch (Exception e) {}
		}
		this.webElement = findElement(by);
		return this;
	}
}
