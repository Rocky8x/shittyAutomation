package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageHome extends PageBase {

	public PageHome() {
		PATH = "";
	}

	// Product List USA
	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("Moto Folio", "Moto Style Shell", "JBL SoundBoost 2",
				"JBL SoundBoost Speaker", "Moto Gamepad", "Moto Smart Speaker with Amazon Alexa",
				"Moto Insta Share Projector", "Hasselblad True Zoom", "Moto 360 Camera", "Polaroid Insta-Share Printer",
				"Moto Style Shell + Wireless Charging", "Moto Power Pack", "Moto TurboPower™ Pack", "Mophie Juice Pack",
				"Polaroid Premium 2x3” ZINK Paper", "Motorola USB Rapid Charger with Micro-USB Data Cable",
				"Motorola TurboPower™ 15+ Wall Charger with USB-C Data Cable",
				"TurboPower™ 15 Universal USB-C Wall Charger for Moto Z & Z2");
		return product;
	}

}
