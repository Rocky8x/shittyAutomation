package com.automation.tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.WbDriverManager;
import com.automation.object.PagePersonal;

public class PagePersonalTest {

	@DataProvider
	public Object[][] testData() {

		return new Object[][] { { "Not", "Ninety Minute", "not90@restmail.net", "30", "January", "1987", "Male" } };
	}

	PagePersonal pagePersonal;

	@BeforeSuite
	public void beforeSuite() {

		pagePersonal = new PagePersonal();
		WbDriverManager.newBrowser("Chrome");

	}

	@BeforeMethod
	public void beforeMethod() throws Exception {
		WbDriverManager.navigateToUrl(PagePersonal.URL);
		Thread.sleep(3000);
	}

	@Test(dataProvider = "testData")
	public void inputValidData(String firstName, String lastName, String email, String day, String month, String year,
			String gender) {
		pagePersonal.getFirstNameTxt().sendKeys(firstName);
		pagePersonal.getLastNameTxt().sendKeys(lastName);
		pagePersonal.getEmailAddrTxt().sendKeys(email);

		pagePersonal.getMonthBox().click();
		pagePersonal.getChoiceItem(month).click();

		pagePersonal.getDayBox().click();
		pagePersonal.getChoiceItem(day).click();

		pagePersonal.getYearBox().click();
		pagePersonal.getChoiceItem(year).click();

		pagePersonal.getGenderBox().click();
		pagePersonal.getChoiceItem(gender).click();

		pagePersonal.getNextBtn().click();

		String url = WbDriverManager.getCurrentUrl();
		assertNotEquals(PagePersonal.URL, url);
	}

	@Test
	public void inputInvalidEmailFormat() throws Exception {
		String invalidEmailFormat = "ABC@com";
		assertFalse(pagePersonal.getWarningIconOf("email").isDisplayed());
		pagePersonal.getEmailAddrTxt().sendKeys(invalidEmailFormat);
		Thread.sleep(1000); // UI attribute change needs time to take affect
		assertTrue(pagePersonal.getWarningIconOf("email").isDisplayed());
		assertTrue(pagePersonal.getWarningMessageOf("email").getText().trim().equals("Enter valid email"));

	}

	@AfterSuite
	public void cleanup() {
		WbDriverManager.closeBrowser();
	}
}
