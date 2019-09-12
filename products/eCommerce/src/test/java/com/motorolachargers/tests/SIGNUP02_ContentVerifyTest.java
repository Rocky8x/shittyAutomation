package com.motorolachargers.tests;



import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorolachargers.pages.PageSignUp;

public class SIGNUP02_ContentVerifyTest extends TestBaseChargers{
	PageSignUp pageSignUp = new PageSignUp();

	@Test()
	public void contentVerifyTest() throws Exception {
		pageSignUp.openPage();
		assertTrue(pageSignUp.getEmailTxb().isDisplayed());
		assertTrue(pageSignUp.getFirstNameTxb().isDisplayed());
		assertTrue(pageSignUp.getLastNameTxb().isDisplayed());
		assertTrue(pageSignUp.getIsSubcribedCkb().isDisplayed());
		assertTrue(pageSignUp.getPasswordTxb().isDisplayed());
		assertTrue(pageSignUp.getComfirmPasswordTxb().isDisplayed());
		assertTrue(pageSignUp.getCreateAccountBtn().isDisplayed());
		assertTrue(pageSignUp.getLoginBtn().isDisplayed());
	}

}
