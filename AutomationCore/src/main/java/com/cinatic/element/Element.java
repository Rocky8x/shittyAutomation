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

import com.cinatic.config.TestConstant;
import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class Element implements WebElement {
	private WebElement webElement = null;
	private String description = "";
	private By by;

	public Element(By by, boolean find) {
		this.by = by;
		description = by.toString();
		if (find == true)
			webElement = DriverManager.getDriver().findElement(by, TestConstant.longTime);		
	}

	public Element(By by) {
		this.by = by;
		description = by.toString();
		webElement = DriverManager.getDriver().findElement(by, TestConstant.mediumTime);

		if (webElement == null) {
			String[] stringBy = by.toString().split(":");
			if (stringBy[0].equals("By.id")) {
				try {
					DriverManager.getDriver().scrollToId(stringBy[1].trim());
				} catch (Exception e) {}
				webElement = DriverManager.getDriver().findElement(by, TestConstant.shortTime);
			}
		}
	}

	public Element(By by, int timeout) {
		this.by = by;
		description = by.toString();
		webElement = DriverManager.getDriver().findElement(by, timeout);
	}

	public Element(By by, String scrollToText) {
		try {
			DriverManager.getDriver().scrollTo(scrollToText);
		} catch (Exception ex) {
			Log.error(ex.getMessage());
		}
		this.by = by;
		description = by.toString();
		webElement = DriverManager.getDriver().findElement(by, TestConstant.longTime);
	}

	public Element(By by, String scrollToText, int timeout) {
		try {
			DriverManager.getDriver().scrollTo(scrollToText);
		} catch (Exception ex) {

		}
		this.by = by;
		description = by.toString();
		webElement = DriverManager.getDriver().findElement(by, timeout);
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
		return webElement.findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return webElement.findElements(by);
	}

	@Override
	public String getAttribute(String name) {
		Log.debug(String.format("get attribute %s = %s of %s",name , webElement.getAttribute(name), description));
		return webElement.getAttribute(name);
	}

	@Override
	public String getCssValue(String propertyName) {
		return webElement.getCssValue(propertyName);
	}

	public boolean waitForDisappear(int timeout) {
		int t = timeout + 1;
		Log.debug("Wait for "+ description+ " to disappear");
		while (timeout > 0) {
			if (DriverManager.getDriver().findElement(by, 1) == null) {
				t = t - timeout;
				Log.debug("Oh great! It's gone after "+ t +" try");
				return true;
			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			timeout--;
		}
		Log.debug("Tired of waiting for "+ description+ " to disappear. Move on");
		return false;
	}

	public boolean waitForAppear(int timeout) {
		Log.debug("Wait for "+ description+ " to appear");
		while (timeout > 0) {
			if (DriverManager.getDriver().findElement(by, 1) != null) {
				return true;
			}
			timeout--;
		}
		return false;
	}

	@Override
	public Point getLocation() {
		Log.debug(String.format("get location value \"%s\": %s", webElement.getLocation(), description));
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
		Log.debug(String.format("get tag name value \"%s\": %s", webElement.getTagName(), description));
		return webElement.getTagName();
	}

	@Override
	public String getText() {
		Log.debug(String.format("get text value \"%s\": %s", webElement.getText(), description));
		return webElement.getText();
	}

	@Override
	public boolean isDisplayed() {
		Log.debug(String.format("get displayed status \"%s\": %s", webElement.isDisplayed(), description));
		return webElement.isDisplayed();
	}

	@Override
	public boolean isEnabled() {
		Log.debug(String.format("get enabled status \"%s\": %s", webElement.isEnabled(), description));
		return webElement.isEnabled();
	}

	@Override
	public boolean isSelected() {
		Log.debug(String.format("get selected status \"%s\": %s", webElement.isSelected(), description));
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
		if (Boolean.parseBoolean(webElement.getAttribute("checked")) != t) {
			webElement.click();
		}
	}

	public void clearText() {
		Log.debug(String.format("clear text: %s", description));
		Point p = webElement.getLocation();
		int w = webElement.getSize().width;
		int h = webElement.getSize().height;

		for (int j = 0; j < 3; j++) {
			DriverManager.getDriver().tap(p.x + w / 2, p.y + h / 2);
			for (int i = 0; i < 10; i++) {
				PressBackspaceKey();
			}
		}
	}

	public void longPress() {
		Log.info("Long touch: " + description);
		TouchAction<?> action = new TouchAction<>(DriverManager.getDriver().getAppiumDriver());
		LongPressOptions option = new LongPressOptions().withElement(ElementOption.element(webElement));
		action.longPress(option).perform();
	}

	public void PressBackspaceKey() {
		Log.debug(String.format("press backspace key: %s", description));
		DriverManager.getDriver().pressKeyCode(67);
	}

	public BufferedImage captureScreenshot() {
		Log.debug(String.format("capture element screen: %s", description));
		File screen = ((TakesScreenshot) DriverManager.getDriver().getAppiumDriver())
				.getScreenshotAs(OutputType.FILE);
		int ImageWidth = webElement.getSize().getWidth();
		int ImageHeight = webElement.getSize().getHeight();
		Point point = webElement.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();
		BufferedImage img = null;
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
}
