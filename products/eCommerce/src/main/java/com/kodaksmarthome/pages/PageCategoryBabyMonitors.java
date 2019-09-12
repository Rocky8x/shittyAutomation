package com.kodaksmarthome.pages;

import java.util.HashMap;

import com.ebn.automation.core.WbElement;

public class PageCategoryBabyMonitors extends PageCategoryBase {

	public PageCategoryBabyMonitors() {

		PATH = "baby-monitors/";
	}

	public PageCategoryBabyMonitors(PageBase P) {

		this.PATH		= "baby-monitors/";
		this.baseURL	= P.baseURL;
		this.header		= P.header;
		this.footer		= P.footer;
	}

	public PageCategoryBabyMonitors(String baseUrl, String path) {

		this.baseURL	= baseUrl;
		this.PATH		= path;
	}

	/*
	 * ELEMENTS
	 */

	@Override
	public HashMap<String, WbElement> getDefinedProducts() {

		HashMap<String, WbElement> products = new HashMap<String, WbElement>();
		products.put("KODAK CHERISH C120", getProductByName("C120"));
		products.put("KODAK CHERISH C125", getProductByName("C125"));
		products.put("KODAK CHERISH C220", getProductByName("C220"));
		products.put("KODAK CHERISH C225", getProductByName("C225"));
		products.put("KODAK CHERISH C520", getProductByName("C520"));
		products.put("KODAK CHERISH C525", getProductByName("C525"));
		products.put("KODAK CHERISH C525IR", getProductByName("C525IR"));
		return products;
	}

	/*
	 * ACTIONS
	 */

}
