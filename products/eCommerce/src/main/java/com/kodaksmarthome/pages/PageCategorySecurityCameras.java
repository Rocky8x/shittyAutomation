package com.kodaksmarthome.pages;

import java.util.HashMap;

import com.ebn.automation.core.WbElement;

public class PageCategorySecurityCameras extends PageCategoryBase {

	public PageCategorySecurityCameras() {

		PATH = "security-cameras/";
	}

	/*
	 * ELEMENTS
	 */

	@Override
	public HashMap<String, WbElement> getDefinedProducts() {

		HashMap<String, WbElement> products = new HashMap<String, WbElement>();
		products.put("KODAK CHERISH F680", getProductByName("F680"));
		products.put("KODAK CHERISH F670", getProductByName("F670"));
		products.put("KODAK CHERISH F685", getProductByName("F685"));

		return products;
	}
	/*
	 * ACTIONS
	 */

}
