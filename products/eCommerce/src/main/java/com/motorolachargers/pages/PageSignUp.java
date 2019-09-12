package com.motorolachargers.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageSignUp extends PageBase {

	public PageSignUp() {
		PATH = "customer/account/create/";
	}

	public WbElement getFirstNameTxb() {
		String id = "firstname";
		return new WbElement(By.id(id), "First Name TextBox");
	}

	public WbElement getLastNameTxb() {
		String id = "lastname";
		return new WbElement(By.id(id), "Last Name TextBox");
	}

	public WbElement getEmailTxb() {
		String id = "email_address";
		return new WbElement(By.id(id), "Last Name TextBox");
	}

	public WbElement getIsSubcribedCkb() {
		String id = "is_subscribed";
		return new WbElement(By.id(id), "Is Subscribed Check Box");
	}

	public WbElement getPasswordTxb() {
		String id = "password";
		return new WbElement(By.id(id), "Password TextBox");
	}

	public WbElement getComfirmPasswordTxb() {
		String id = "confirmation";
		return new WbElement(By.id(id), "Comfirm Password TextBox");
	}

	public WbElement getCreateAccountBtn() {
		String xpath = "//button[@class='button btn btn-default btn-fluid']";
		return new WbElement(By.xpath(xpath), "Submit Create Account Button");
	}

	public WbElement getLoginBtn() {
		String xpath = "//a[@class='btn btn-alt btn-sm btn-fluid']";
		return new WbElement(By.xpath(xpath), "Navigate To Login Page Button");
	}

	public void clickLoginButtonAndVerify() {
		getLoginBtn().click();
		PageLogin pageLogin = new PageLogin();
		pageLogin.verifyPageBasicContent();
		pageLogin.verifyUrl();
	}

}
