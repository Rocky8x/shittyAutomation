package com.kodaksmarthome.pages;

public class PageTermService extends PageBase {

	public PageTermService() {

		PATH = "tos/";
	}

	public PageTermService(PageBase P) {

		PATH			= "tos/";
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
