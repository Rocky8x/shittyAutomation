package com.kodaksmarthome.pages;

public class PageAccount extends PageBase{
	public PageAccount() {
		PATH = "customer/account/login/";
	}
	
	/*
	 * ACTIONS
	 */
	
	public void verifyPageContent() {
		verifyUrl();
		verifyPageHeader();
		verifyPageFooter();
	}
}
