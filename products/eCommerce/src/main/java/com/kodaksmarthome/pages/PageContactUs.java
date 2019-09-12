package com.kodaksmarthome.pages;

public class PageContactUs extends PageBase {

	public PageContactUs() {

		PATH = "contact-us/";
	}

	public PageContactUs(PageBase P) {

		PATH			= "contact-us/";
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
