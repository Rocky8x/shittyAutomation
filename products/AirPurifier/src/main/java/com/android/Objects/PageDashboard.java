package com.android.Objects;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageDashboard extends PageBase {

	public Element getPageTitleTxtbox() {

		return new Element(By.id("textview_title_main"));
	}

	public Element getMenuButton() {

		return new Element(By.id("imgMain"));
	}

	public Element getHelpButton() {

		return new Element(By.id("menu_help"));
	}

	public Element getAddDeviceButton() {

		return new Element(By.id("menu_add_device"));
	}
}
