package com.motorolachargers.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorolachargers.pages.PageRecoverPassword;

public class FORGOTPWD02_ContentVerifyTest extends TestBaseChargers{

	PageRecoverPassword pageRecoverPassword = new PageRecoverPassword();

	@Test()
	public void contentVerifyTest() throws Exception {
		pageRecoverPassword.openPage();
		assertTrue(pageRecoverPassword.getEmailRecoveryTxb().isDisplayed());
		assertTrue(pageRecoverPassword.getSubmitBtn().isDisplayed());
		assertTrue(pageRecoverPassword.getNavigateLoginPage().isDisplayed());
	}
	
}
