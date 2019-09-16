package com.automation.object;

import org.openqa.selenium.By;

import com.automation.WbElement;

public class PagePersonal {

	public static String URL = "https://www.utest.com/signup/personal";

	public WbElement getFirstNameTxt() {
		return new WbElement(By.id("firstName"), "First name text box");
	}

	public WbElement getLastNameTxt() {
		return new WbElement(By.id("lastName"), "Last name text box");
	}

	public WbElement getEmailAddrTxt() {
		return new WbElement(By.id("email"), "Email text box");
	}

	public WbElement getMonthBox() {
		return new WbElement(By.xpath("//span[text()='Month']/../span[1]"), "Month select box");
	}

	public WbElement getDayBox() {
		return new WbElement(By.xpath("//span[text()='Day']/../span[1]"), "Day select box");

	}

	public WbElement getYearBox() {
		return new WbElement(By.xpath("//span[text()='Year']/../span[1]"), "Year select box");

	}

	public WbElement getGenderBox() {
		return new WbElement(By.xpath("//span[text()='Select a gender']/../span[1]"), "Gender select box");
	}

	public WbElement getNextBtn() {
		return new WbElement(By.xpath("//a/span[text()='Next: Location']"), "Next button");
	}

	public WbElement getChoiceItem(String text) {
		return new WbElement(By.xpath("//span[@class='ui-select-choices-row-inner']/div[text()='" + text + "']"),
				"Choice: " + text);

	}

	public WbElement getWarningIconOf(String id) {
		String xpath = "//div/input[@id='" + id + "']/../i[text()='warning']";
		return new WbElement(By.xpath(xpath), "Warning icon of: " + id);
	}

	public WbElement getWarningMessageOf(String id) {
		String xpath = "//div/input[@id='" + id + "']/../span";
		return new WbElement(By.xpath(xpath), "Warning message of: " + id);
	}

}
