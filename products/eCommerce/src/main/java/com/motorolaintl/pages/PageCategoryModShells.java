package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryModShells extends PageBase {
	public PageCategoryModShells() {
		PATH = "mods/shells/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("Moto Style Shell + Wireless Charging", "Moto Style Shell");
		return product;
	}

}
