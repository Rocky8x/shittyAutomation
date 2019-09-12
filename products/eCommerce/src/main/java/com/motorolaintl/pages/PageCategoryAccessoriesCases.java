package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryAccessoriesCases extends PageBase {

	public PageCategoryAccessoriesCases() {
		PATH = "accessories/cases/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("Two-Tone Bumper for Moto Z2 Play", "Carrying Case for Moto Mods",
				"Gadget Guard - Black Ice Glass Screen Protector for Motorola Moto Z3 Play - Clear",
				"Gadget Guard - Black Ice Plus Glass Screen Protector For Motorola Moto Z3 Play - Clear",
				"Otterbox - Commuter Case For Motorola Moto Z3 Play",
				"Speck - Presidio Grip Case For Motorola Moto Z3 Play",
				"Incipio - Dualpro Case For Motorola Moto Z3 Play");
		return product;
	}
}
