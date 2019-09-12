package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategorySpeakers extends PageBase {

	public PageCategorySpeakers() {
		PATH = "mods/speakers/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("JBL SoundBoost Speaker", "JBL SoundBoost 2",
				"Moto Smart Speaker with Amazon Alexa");
		return product;
	}
}
