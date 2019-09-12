package com.kodaksmarthome.pages;

public class PagePrivacyPolicy extends PageBase {

	public PagePrivacyPolicy() {

		PATH = "privacypolicy/";
	}

	public PagePrivacyPolicy(PageBase P) {

		PATH			= "privacypolicy/";
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
