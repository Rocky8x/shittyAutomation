package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryAcessories extends PageBase {

	public PageCategoryAcessories() {
		PATH = "accessories/";
	}
	
	@Override
	public List<String> productList(){
		 List<String> product = Arrays.asList(
				 "Polaroid Premium 2x3” ZINK Paper",
				 "Incipio - Dualpro Case For Motorola Moto Z3 Play",
				 "Speck - Presidio Grip Case For Motorola Moto Z3 Play",
				 "Otterbox - Commuter Case For Motorola Moto Z3 Play",
				 "Gadget Guard - Black Ice Plus Glass Screen Protector For Motorola Moto Z3 Play - Clear",
				 "Gadget Guard - Black Ice Glass Screen Protector for Motorola Moto Z3 Play - Clear",
				 "Carrying Case for Moto Mods",
				 "Motorola USB Rapid Charger with Micro-USB Data Cable",
				 "Motorola TurboPower™ 15+ Wall Charger with USB-C Data Cable",
				 "TurboPower™ 15 Universal USB-C Wall Charger for Moto Z & Z2"
				 );
		 return product;
	}
}
