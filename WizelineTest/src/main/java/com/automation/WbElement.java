package com.automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class WbElement implements WebElement {

	public WebElement	element		= null;
	public String		description	= "";

	public WbElement() {

	}

	public WbElement(By by) {

		description = by.toString();
		Log.info("Finding: " + by);
		try {
			element = WbDriverManager.getDriver().findElement(by);
		} catch (NoSuchElementException e) {
			Log.fatal("Can't find the element");
			throw e;
		}
	}

	public WbElement(By by, String description) {

		this.description = description;
		Log.info("Finding: " + description);
		try {
			element = WbDriverManager.getDriver().findElement(by);
		} catch (NoSuchElementException e) {
			Log.fatal("Can't find the element");
			throw e;
		}
	}

	public WbElement(WebElement e, String description) {

		Log.info("Finding: " + description);
		element				= e;
		this.description	= description;
	}
	
	public WbElement(WebElement e) {

		element				= e;
	}

	public String getDescription() {

		return description;
	}

	public WebElement getElement() {

		return element;
	}

	@Override
	public void submit() {

		element.submit();
	}

	@Override
	public void clear() {

		element.clear();
	}

	@Override
	public WebElement findElement(By by) {

		return WbDriverManager.getDriver().findElement(by);
	}

	@Override
	public void click() {

		Log.info("Clicking : " + description);
		try {
			element.click();
			Thread.sleep(300);
		} catch (Exception e) {
			Log.fatal("Cannot click");
		}

	}

	@Override
	public List<WebElement> findElements(By by) {

		return WbDriverManager.getDriver().findElements(by);
	}

	@Override
	public String getAttribute(String name) {

		return element.getAttribute(name);
	}

	@Override
	public String getCssValue(String propertyName) {

		return element.getCssValue(propertyName);
	}

	@Override
	public Point getLocation() {

		return element.getLocation();
	}

	@Override
	public Rectangle getRect() {

		return element.getRect();
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {

		return element.getScreenshotAs(target);
	}

	@Override
	public Dimension getSize() {

		return element.getSize();
	}

	@Override
	public String getTagName() {

		return element.getTagName();
	}

	@Override
	public String getText() {

		return element.getText();
	}

	@Override
	public boolean isDisplayed() {

		boolean isDisplay = element.isDisplayed();
		Log.info(description + " is display ? " + isDisplay);
		return isDisplay;
	}

	@Override
	public boolean isEnabled() {

		return element.isEnabled();
	}

	@Override
	public boolean isSelected() {

		return element.isSelected();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {

		String s = "";
		for (CharSequence charSequence : keysToSend) { s += charSequence.toString().trim(); }
		Log.info("Send string " + s + " to " + description);
		element.sendKeys(keysToSend);
	}

	public WbElement scrollToElement() {

		((JavascriptExecutor) WbDriverManager.getDriver())
				.executeScript("arguments[0].scrollIntoView(true);", element);
		return this;
	}

	public WebElement hoverOnElement() {

		Log.info("Hovering to: " + description);
		Actions a = new Actions(WbDriverManager.getDriver());
		a.moveToElement(element);
		a.build().perform();
		return this;
	}

	public WebElement clickByJavaScripts() {

		JavascriptExecutor executor = (JavascriptExecutor) WbDriverManager.getDriver();
		executor.executeScript("arguments[0].click();", element);
		return this;
	}

	public String getHref() {

		return element.getAttribute("href");
	}

	public WbElement findElement(By by, String desc) {

		return new WbElement(WbDriverManager.getDriver().findElement(by), desc);
	}

	public WbElement findSubElement(By by, String desc) {

		Log.info("Finding: " + desc);
		WbElement we = new WbElement();
		we.element		= element.findElement(by);
		we.description	= desc;
		return we;
	}
}
