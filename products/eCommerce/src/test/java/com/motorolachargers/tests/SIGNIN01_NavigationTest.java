package com.motorolachargers.tests;

import org.testng.annotations.Test;

import com.motorolachargers.pages.PageCustomerAccount;
import com.motorolachargers.pages.PageLogin;

public class SIGNIN01_NavigationTest extends TestBaseChargers {

	PageLogin pageLogin = new PageLogin();
	PageCustomerAccount pageCustomerAccount = new PageCustomerAccount();

	@Test()
	public void navigationTest() throws Exception {
		pageLogin.openPage();
		pageLogin.clickCreateAccountButtonAndVerify();
		pageLogin.clickForgotPasswordTxtAndVerify();
	}

}
