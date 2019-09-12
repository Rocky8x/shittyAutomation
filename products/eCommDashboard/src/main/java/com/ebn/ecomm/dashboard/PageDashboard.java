package com.ebn.ecomm.dashboard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cinatic.TimeHelper;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

import ch.qos.logback.core.joran.action.Action;
import io.appium.java_client.functions.ExpectedCondition;

/**
 * @author cobblestone
 *
 */
/**
 * @author cobblestone
 *
 */

@SuppressWarnings(value = "unused") public class PageDashboard {

	public static String	URL		= "https://datastudio.google.com/u/1/reporting/1oLtrDHwX06GSHn-ZlnCqC_tudGtGt94m/page/OlOa";
	public static String	uname	= "thach.nguyen@ebuynow.com";
	public static String	passwd	= "blu3R0ck";

	public PageDashboard() {

		openPage();
		login();
	}

	private WbElement getKodakBigLogo() {

		String xpath = "(//image-component/img)[1]";
		return new WbElement(By.xpath(xpath), "Kodak big logo with no action");
	}

	private WbElement getProgressBar() {

		String xpath = "//div[@class='mat-progress-bar-secondary mat-progress-bar-fill mat-progress-bar-element']";
		return new WbElement(By.xpath(xpath), "Progress bar");
	}

	private WbElement getEmailInput() {

		String xpath = "//input[@type='email']";
		return new WbElement(By.xpath(xpath), "Email input");
	}

	private WbElement getNextButton() {

		String xpath = "//span[@class='RveJvd snByac']";
		return new WbElement(By.xpath(xpath), "Next button");
	}

	private WbElement getPasswdInput() {

		String xpath = "//input[@type='password']";
		return new WbElement(By.xpath(xpath), "Password input");
	}

	private WbElement getButtonDatePicker(String picker) {

		String xpath = "//div[contains(@class,'date-table')]/div[contains(text(),'" + picker
				+ "')]/..//button[contains(@id,'datepicker')]";
		return new WbElement(By.xpath(xpath), picker + " date picker");
	}

	private WbElement getButtonDateSelect(String picker, String date) {

		String xpath = "//div[contains(@class,'date-table')]/div[contains(text(),'" + picker
				+ "')]/..//button/span[text()='" + date + "']";
		return new WbElement(By.xpath(xpath), picker + " Date Button: " + date);
	}

	private WbElement getMenuFilterCurrency() {

		String xpath = "//main-section[contains(text(),'Currency')]";
		return new WbElement(By.xpath(xpath), "Currency filter menu");
	}

	private WbElement getMenuFilterDate() {

		String xpath = "(//main-section)[1]";
		return new WbElement(By.xpath(xpath), "Date filter menu");
	}

	private WbElement getMenuFilterChanel() {

		String xpath = "//main-section[contains(text(),'channel_name')]";
		return new WbElement(By.xpath(xpath), "Chanel name filter menu");
	}

	private WbElement getSearchBoxFilter() {

		String xpath = "//input[@type='search']";
		return new WbElement(By.xpath(xpath), "Chanel searchbox");
	}

	private WbElement getMenuFilterProduct() {

		String xpath = "//main-section[contains(text(),'Product')]";
		return new WbElement(By.xpath(xpath), "Product filter menu");
	}

	private WbElement getCheckBoxCurrency(String currency) {

		String xpath = "//span[text()='" + currency + "']/../span[@class='check-icon']";
		return new WbElement(By.xpath(xpath), "Currency checkbox: " + currency);
	}

	private WbElement getTextboxCurrency(String currency) {

		String xpath = "//div[@class='md-virtual-repeat-offsetter']//span[text()='" + currency
				+ "']/..";
		return new WbElement(By.xpath(xpath), "Currency name: " + currency);
	}

	private WbElement getSimpleSelectorDate() {

		String xpath = "//md-select-value";
		return new WbElement(By.xpath(xpath), "Date simple selector");
	}

	private WbElement getSimpleDate(String simpleDay) {

		String xpath = "//md-option/div[contains(text(),'" + simpleDay + "')]";
		return new WbElement(By.xpath(xpath), "Select date: " + simpleDay);
	}

	private WbElement getFilterOnlyButton(String name) {

		String xpath = "//div[@aria-label='" + name + "']/../../span";
		return new WbElement(By.xpath(xpath), "Sale chanel Only button: " + name);
	}

	private WbElement getFilterCheckboxItem(String name) {

		String xpath = "//div[@aria-label='" + name + "']/../div[@class='md-label']/../..";
		return new WbElement(By.xpath(xpath), "Sale chanel checkbox: " + name);
	}

	private WbElement getButtonApply() {

		String xpath = "//button[contains(text(),'APPLY')]";
		return new WbElement(By.xpath(xpath), "Apply filter button");
	}

	private WbElement getCheckBoxSelectAll() {

		String xpath = "(//div[@class='md-icon'])[1]";
		return new WbElement(By.xpath(xpath), "Select all checkbox");
	}

	private WbElement getCheckboxCurrency(String currency) {

		String xpath = "//span[@title='" + currency + "']/../span[@class='check-icon']";
		return new WbElement(By.xpath(xpath), "Currency: " + currency);
	}

	private void enableCheckboxSelectAll() {

		WbElement e = getCheckBoxSelectAll();
		if (!e.isSelected()) {
			Actions a = new Actions(WbDriverManager.getDriver());
			a.moveToElement(e.getElement()).click().build().perform();
		}
	}

	private void selectFilterCurrency(String currency) {

		getMenuFilterCurrency().click();
		String currStat = getTextboxCurrency(currency).getAttribute("class");
		if (!currStat.contains("selected")) { getCheckBoxCurrency(currency).click(); }
	}

	private void setFilterDateYesterday() {

		getMenuFilterDate().click();
		getSimpleSelectorDate().click();
		getSimpleDate("Yesterday").click();
	}

	private void openPage() {

		WbDriverManager.newBrowser("chrome");
		WbDriverManager.getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WbDriverManager.getDriver().manage().window().maximize();
		WbDriverManager.navigateToUrl(URL);
	}

	private void login() {

		getEmailInput().sendKeys(uname);
		getNextButton().click();
		waitFor(1500);
		getPasswdInput().sendKeys(passwd);
		getNextButton().click();
		waitLoading();
	}

	public void quit() {

		WbDriverManager.closeBrowser();
	}

	private void waitFor(int time) {

		try {
			Thread.sleep(time);
		} catch (Exception e) {}
	}

	private void waitLoading() {

		while (getProgressBar().isDisplayed()) { waitFor(3000); }
	}

	/**
	 * @param picker "Start" or "End"
	 * @param date   must be in "YYYY-MM-DD" format
	 */
	private void pickDate(String picker, String date) {

		// click twice to select year
		getButtonDatePicker(picker).click();
		getButtonDatePicker(picker).click();

		int	year	= Integer.parseInt(date.split("-")[0]);
		int	month	= Integer.parseInt(date.split("-")[1]);
		int	day		= Integer.parseInt(date.split("-")[2]);

		LocalDate			d	= LocalDate.of(year, month, day);
		DateTimeFormatter	f	= DateTimeFormatter.ofPattern("yyyy-L-dd");
		d.format(f);

		// select year
		getButtonDateSelect(picker, d.getYear() + "").click();;

		// select month
		getButtonDateSelect(picker, StringUtils.capitalize(d.getMonth().name().toLowerCase()))
				.click();

		// select day
		getButtonDateSelect(picker, d.getDayOfMonth() + "").click();
	}

	public void filterDate(String start, String end) {

		getMenuFilterDate().click();
		pickDate("Start", start);
		pickDate("End", end);
		getButtonApply().click();

		Actions a = new Actions(WbDriverManager.getDriver());
		a.moveByOffset(100, 100).click().build().perform();
		waitLoading();
	}

	public void filterChanelOnly(String chanel) {

		getMenuFilterChanel().click();
		getSearchBoxFilter().sendKeys(chanel);
		waitFor(1000);
		getFilterCheckboxItem(chanel).hoverOnElement();
		getFilterOnlyButton(chanel).click();
		clickAway();
		waitLoading();
	}

	public void filterProductOnly(String product) {

		getMenuFilterProduct().click();
		getSearchBoxFilter().sendKeys(product);
		waitFor(1000);
		getFilterCheckboxItem(product).hoverOnElement();
		getFilterOnlyButton(product).click();
		clickAway();
		waitLoading();
	}

	public void clearFilter() {

		getMenuFilterChanel().hoverOnElement().click();
		TimeHelper.sleep(500);
		enableCheckboxSelectAll();
		clickAway();
		waitLoading();

		getMenuFilterProduct().hoverOnElement().click();
		TimeHelper.sleep(500);
		enableCheckboxSelectAll();
		clickAway();
		waitLoading();
	}

	public void filterCurrency(String currency) {

		getMenuFilterCurrency().click();
		getCheckBoxCurrency(currency).click();
	}

	private void clickAway() {

		getKodakBigLogo().hoverOnElement().click();
	}
	
	/**
	 * @param tbName : must be 'Country' | 'Channel' | 'Model' only
	 */
	public TableSummary getSumaryTable(String tableName) {

		return new TableSummary(tableName);
	}
	
	/**
	 * Get Dashboard table
	 */
	public TableDashboard getDashboardTable() {

		return new TableDashboard();
	}
}
