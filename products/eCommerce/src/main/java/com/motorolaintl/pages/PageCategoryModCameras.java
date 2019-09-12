package com.motorolaintl.pages;

import java.util.Arrays;
import java.util.List;

public class PageCategoryModCameras extends PageBase {
	public PageCategoryModCameras() {
		PATH = "mods/cameras/";
	}

	@Override
	public List<String> productList() {
		List<String> product = Arrays.asList("Hasselblad True Zoom", "Moto 360 Camera");
		return product;
	}
}
