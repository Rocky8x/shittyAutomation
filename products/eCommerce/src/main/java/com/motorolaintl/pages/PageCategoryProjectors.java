package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryProjectors extends PageBase {

	public PageCategoryProjectors() {
		PATH = "mods/projectors/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("Moto Insta Share Projector");
		return product;
	}
}
