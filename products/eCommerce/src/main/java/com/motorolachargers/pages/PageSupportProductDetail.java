package com.motorolachargers.pages;

public class PageSupportProductDetail extends PageBase {

	public PageSupportProductDetail(PageBase p) {
		PATH = "";
		this.baseURL = p.baseURL;
	}

	public void setPATH(String path) {
		PATH = path;
	}

}
