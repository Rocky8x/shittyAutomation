package com.motorolachargers.tests;

import org.testng.annotations.Test;

import com.motorolachargers.pages.PageRecoverPassword;

public class FORGOTPWD01_NavigationTest extends TestBaseChargers{
	
	PageRecoverPassword pageRecoverPassword = new PageRecoverPassword();

	@Test()
	public void navigationTest() throws Exception {
		pageRecoverPassword.openPage();
		pageRecoverPassword.clickLoginButtonAndVerify();
	}

}
