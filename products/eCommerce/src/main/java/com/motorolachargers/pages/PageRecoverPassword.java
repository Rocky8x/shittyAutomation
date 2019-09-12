package com.motorolachargers.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageRecoverPassword extends PageBase {

	public PageRecoverPassword() {
		PATH = "customer/account/forgotpassword/";
	}

	public WbElement getEmailRecoveryTxb() {
		String id = "email_address";
		return new WbElement(By.id(id), "Email Recovery TextBox");
	}

	public WbElement getSubmitBtn() {
		String xpath = "//button[@class='button btn btn-default btn-fluid']";
		return new WbElement(By.xpath(xpath), "Submit Button");
	}

	public WbElement getNavigateLoginPage() {
		String xpath = "//a[contains(.,\"«\") ]";
		return new WbElement(By.xpath(xpath), "Navigate To Login Page Button");
	}
}
