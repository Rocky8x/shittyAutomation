package com.motorolachargers.pages;

public class PageCheckOut extends PageBase {

	public PageCheckOut() {
		PATH = "checkout/onepage/#";
	}

	public PageCheckOut(PageBase p) {
		PATH = "checkout/onepage/#";
		this.baseURL = p.baseURL;
	}

}
