package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryAccessoriesChargers extends PageBase {

	public PageCategoryAccessoriesChargers() {
		PATH = "accessories/chargers/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("TurboPower™ 15 Universal USB-C Wall Charger for Moto Z & Z2",
				"Motorola USB Rapid Charger with Micro-USB Data Cable",
				"Motorola TurboPower™ 15+ Wall Charger with USB-C Data Cable");
		return product;
	}
}
