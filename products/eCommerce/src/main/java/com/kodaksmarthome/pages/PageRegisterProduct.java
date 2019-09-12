package com.kodaksmarthome.pages;

public class PageRegisterProduct extends PageBase {

	public PageRegisterProduct() {

		PATH = "support/deviceregistration/";
	}

	public PageRegisterProduct(PageBase P) {

		PATH			= "support/deviceregistration/";
		this.baseURL	= P.baseURL;
		this.footer		= P.footer;
		this.header		= P.header;
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
