package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryPower extends PageBase {

	public PageCategoryPower() {
		PATH = "mods/batteries/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("Moto TurboPower™ Pack", "Moto Power Pack",
				"Moto Style Shell + Wireless Charging", "Mophie Juice Pack");
		return product;
	}
}
