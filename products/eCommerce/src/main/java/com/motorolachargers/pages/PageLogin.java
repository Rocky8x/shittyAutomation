package com.motorolachargers.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;;

public class PageLogin extends PageBase {

	public final String MSG_LOGIN_ERROR = "Invalid login or password.";

	public PageLogin() {
		PATH = "customer/account/login/";
	}

	public WbElement getEmailTxb() {
		String id = "email";
		WbDriverManager.waitElement(By.id(id));
		return new WbElement(By.id(id), "Email TextBox");
	}

	public WbElement getPasswordTxb() {
		String id = "pass";
		return new WbElement(By.id(id), "Password TextBox");
	}

	public WbElement getLoginBtn() {
		String id = "send2";
		return new WbElement(By.id(id), "Login Button");
	}

	public WbElement getCreateAccountBtn() {
		String xpath = "//a[@class=\"btn btn-alt btn-sm btn-fluid\"]";
		return new WbElement(By.xpath(xpath), "Create Account Button");
	}

	public WbElement getForgotPassTxt() {
		String xpath = "//div[@class='forgot-pass']";
		return new WbElement(By.xpath(xpath), "Recovery Password Text");
	}

	public WbElement getErrorMessageTxt() {
		String xpath = "//li[@class='error-msg']//span";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Error Message Txt");
	}

	// action
	
	public void clickCreateAccount() {
		getCreateAccountBtn().click();
	}
	
	public void clickForgetPassword() {
		getForgotPassTxt().click();
	}

	public void login(String email, String pwd) {
		getEmailTxb().sendKeys(email);
		getPasswordTxb().sendKeys(pwd);
		getLoginBtn().click();
	}

	public void navigateSignUpPage() {
		getCreateAccountBtn().click();
	}

	public void clickCreateAccountButtonAndVerify() {
		clickCreateAccount();
		PageSignUp pageSignUp = new PageSignUp();
		pageSignUp.verifyPageBasicContent();
		pageSignUp.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void clickForgotPasswordTxtAndVerify() {
		clickForgetPassword();
		PageRecoverPassword pageRecoverPassword = new PageRecoverPassword();
		pageRecoverPassword.verifyPageBasicContent();
		pageRecoverPassword.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public String errorMessage() {
		String error = getErrorMessageTxt().getText();
		return error;
	}

}
