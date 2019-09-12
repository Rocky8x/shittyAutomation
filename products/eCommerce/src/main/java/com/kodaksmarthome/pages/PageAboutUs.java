package com.kodaksmarthome.pages;

public class PageAboutUs extends PageBase {

	public PageAboutUs() {

		PATH = "about/";
	}

	public PageAboutUs(PageBase P) {

		PATH			= "about/";
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
