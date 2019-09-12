package com.motorolaintl.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.cinatic.TimeHelper;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PagePayment extends PageBase{
	public PagePayment() {
		PATH = "checkout/onepage/#payment";
	}
	
	public WbElement getLogoIcon() {
		String xpath = "//a[@class='logo']";
		return new WbElement(By.xpath(xpath),"Logo Icon");
	}
	
	public WbElement getCreditCardMethodRadio() {
		String xpath = "//input[@title='Credit Card']/..";
		return new WbElement(By.xpath(xpath),"Credit Card Method Radio");
	}
	
	public WbElement getPayPalMethodRadio() {
		String xpath = "//input[@title = 'PayPal Express Checkout']/..";
		return new WbElement(By.xpath(xpath),"Paypal Checkout Method Radio");
	}
	
	public WbElement getCreditCardNumberTbx() {
		String id = "paypal_direct_cc_number";
		return new WbElement(By.id(id),"Credit Card Number Text box");
	}

	public WbElement getExpirationDate() {
		String id = "paypal_direct_expiration";
		return new WbElement(By.id(id),"Expiration Date Text Box");
	}
	
	public WbElement getExpirationYear() {
		String id = "paypal_direct_expiration_yr";
		return new WbElement(By.id(id),"Expiration Year Text Box");
	}
	
	public WbElement getSecurityCode() {
		String id = "paypal_direct_cc_cid";
		return new WbElement(By.id(id),"Security Code Text Box");
	}
	
	public WbElement getContinueBtn() {
		String xpath = "//*[@id='payment-buttons-container']/button";
		return new WbElement(By.xpath(xpath),"Continue Button");
	}
	
	public WbElement getSameShippingAddressCkb() {
		String xpath = "//input[@name='billing[same_as_shipping]']";
		return new WbElement(By.xpath(xpath),"Same Shipping Address Check box");
	}
	
	public WbElement getFullNameTbx() {
		String id = "billing:fullname";
		return new WbElement(By.id(id),"Full Name Text box");
	}
	
	public WbElement getPhoneNumberTbx() {
		String id = "billing:telephone";
		return new WbElement(By.id(id),"Phone Number Text box");
	}
	
	public WbElement getCompanyTbx() {
		String id = "billing:company";
		return new WbElement(By.id(id),"Company Text box");
	}
	
	public WbElement getAddres1Tbx() {
		String id = "billing:street1";
		return new WbElement(By.id(id),"Address Text box 1");
	}
	
	public WbElement getAddres2Tbx() {
		String id = "billing:street2";
		return new WbElement(By.id(id),"Address Text box 2");
	}
	
	public WbElement getZipCodeTbx() {
		String id = "billing:postcode";
		return new WbElement(By.id(id),"Zip Code Text Box");
	}
	
	public WbElement getCityTbx() {
		String id = "billing:city";
		return new WbElement(By.id(id),"City Textbox");
	}
	
	public WbElement getCountryTbx() {
		String id = "billing:country_label";
		return new WbElement(By.id(id),"Country Text box");
	}
	
	public WbElement getStateDropBox() {
		String id = "billing:region_id";
		return new WbElement(By.id(id),"State Drop Box");
	}
	
	public WbElement getCloseErrorBtn() {
		String xpath = "//div[@class='modal-ac fade in']//button";
		return new WbElement(By.xpath(xpath),"Close Button");
	}
	
	public WbElement getTitleErrorTxt() {
		String xpath = "//div[@class='modal-ac fade in']/div[@class='modal-ac-header']/h3";
		return new WbElement(By.xpath(xpath),"Title Error");
	}

	public void clickLogoIcon() {
		getLogoIcon().click();
	}
	
	public void clickContinueBtn() {
		getContinueBtn().click();
		WbDriverManager.waitForPageLoad();
	}
	
	public void clickCreditCardMethod() {
		getCreditCardMethodRadio().clickByJavaScripts();
	}
	
	public void inputCardNumber(String number) {
		getCreditCardNumberTbx().sendKeys(number);
	}
	
	public void selectDateExpiration(String date) {
		WebElement ele = getExpirationDate();
		Select select = new Select(ele);
		select.selectByVisibleText(date);
	}
	
	public void selectYearExpiration(String year) {
		WebElement ele = getExpirationYear();
		Select select = new Select(ele);
		select.selectByVisibleText(year);
	}
	
	public void inputSecurityCode(String code) {
		getSecurityCode().sendKeys(code);
	}
	
	public void clickLogoIconAndVerify() {
		clickLogoIcon();
		WbDriverManager.waitForPageLoad();
		TimeHelper.sleep(5000);
		PageCart pageCart = new PageCart();
		pageCart.verifyUrl();
		WbDriverManager.backPreviousPage();
	}
	
	public void clickContinueBtnAndVerify() {
		clickContinueBtn();
		WbDriverManager.waitForPageLoad();
		TimeHelper.sleep(5000);
		PageOrderConfirmation pageOrderConfirmation = new PageOrderConfirmation();
		pageOrderConfirmation.verifyUrl();
		WbDriverManager.backPreviousPage();
	}
	
	public void clickSameShippingAddressCkb() {
		getSameShippingAddressCkb().clickByJavaScripts();
	}
	
	public List<String> getListFieldErrorMessage() {
		List<String> listField = new ArrayList<String>();
		String xpath = "//div[@class='modal-ac fade in']//ul/li";
		List<WbElement> errorElement = WbDriverManager.findElements(By.xpath(xpath));
		for(WbElement ele : errorElement) {
			listField.add(ele.getText());
		}
		return listField;
	}
	
	public void clickCloseError() {
		getCloseErrorBtn().clickByJavaScripts();
	}
	
	public String getTitleErrorMessage() {
		return getTitleErrorTxt().getText();
	}
	
	public void inputFullNameTbx(String fullName) {
		getFullNameTbx().clear();
		getFullNameTbx().sendKeys(fullName);
	}
	
	public void inputPhoneNumberTbx(String phoneNumber) {
		getPhoneNumberTbx().clear();
		getPhoneNumberTbx().sendKeys(phoneNumber);
	}
	
	public void inputCompanyTbx(String companyName) {
		getCompanyTbx().clear();
		getCompanyTbx().sendKeys(companyName);
	}
	
	public void inputAddres1Tbx(String address1) {
		getAddres1Tbx().clear();
		getAddres1Tbx().sendKeys(address1);
		
	}
	
	public void inputAddres2Tbx(String address2) {
		getAddres2Tbx().clear();
		getAddres2Tbx().sendKeys(address2);
	}
	
	public void inputZipCodeTbx(String zipcode) {
		getZipCodeTbx().clear();
		getZipCodeTbx().sendKeys(zipcode);
	}
	
	public void inputCityTbx(String city) {
		getCityTbx().clear();
		getCityTbx().sendKeys(city);
	}
	
	public void inputCountryTbx(String country) {
		getCountryTbx().clear();
		getCountryTbx().sendKeys(country);
	}
	
	public void setStateDropBox(String state) {
		WbElement ele = getStateDropBox();
		ele.scrollToElement();
		ele.click();
		String xpath = "//select[@id='billing:region_id']//option[text()='"+state+"']";
		WbElement option = new WbElement(By.xpath(xpath));
		option.click();
	}
}
