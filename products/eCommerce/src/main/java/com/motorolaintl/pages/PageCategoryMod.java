package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryMod extends PageBase {

	public PageCategoryMod() {
		PATH = "mods/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("Moto Smart Speaker with Amazon Alexa", "JBL SoundBoost 2",
				"JBL SoundBoost Speaker", "Moto 360 Camera", "Hasselblad True Zoom", "Moto Insta Share Projector",
				"Moto Gamepad", "Polaroid Insta-Share Printer", "Moto TurboPower™ Pack", "Mophie Juice Pack",
				"Moto Folio", "Moto Style Shell + Wireless Charging", "Moto Style Shell", "Moto Power Pack");
		return product;
	}
}
