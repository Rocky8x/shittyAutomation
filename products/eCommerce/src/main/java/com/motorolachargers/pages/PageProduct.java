package com.motorolachargers.pages;

public class PageProduct extends PageBase {

	public PageProduct() {
		PATH = "phone-chargers/";
	}

	public PageProduct(PageBase p) {
		PATH = "phone-chargers/";
		this.baseURL = p.baseURL;
	}
}
