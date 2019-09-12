package com.motorolachargers.tests;

import org.testng.annotations.Test;

import com.motorolachargers.pages.PageSignUp;

public class SIGNUP01_NavigationTest extends TestBaseChargers{
	
	PageSignUp pageSignUp = new PageSignUp();

	@Test()
	public void navigationTest() throws Exception {
		pageSignUp.openPage();
		pageSignUp.clickLoginButtonAndVerify();
	}

}
