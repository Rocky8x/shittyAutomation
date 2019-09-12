package com.motorolachargers.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorolachargers.pages.PageLogin;

public class SIGNIN02_ContentVerifyTest extends TestBaseChargers{

	PageLogin pageLogin = new PageLogin();
	
	@Test()
	public void contentVerifyTest() throws Exception {
		pageLogin.openPage();
		assertTrue(pageLogin.getEmailTxb().isDisplayed());
		assertTrue(pageLogin.getPasswordTxb().isDisplayed());
		assertTrue(pageLogin.getLoginBtn().isDisplayed());
		assertTrue(pageLogin.getForgotPassTxt().isDisplayed());
		assertTrue(pageLogin.getCreateAccountBtn().isDisplayed());
	}
}
