package com.motorola.ap.android.Objects;

import org.openqa.selenium.By;

public class PageDashboard extends PageBase {

	public By	textboxPageTitle	= By.id("textview_title_main");
	public By	buttonMenu			= By.id("imgMain");
	public By	buttonAddDevice		= By.id("menu_add_device");

	public boolean isDisplay() {

		if (waitForAppear(textboxPageTitle, 20) != null) { return true; }
		return false;
	}

	public PageHomeMenu goToHomeMenu() {

		waitForAppear(buttonMenu, 15).click();
		return new PageHomeMenu();
	}

	public String getLoggedInUser() {

		PageHomeMenu	pageHomeMenu	= goToHomeMenu();
		String			name			= pageHomeMenu.getCurrentUsername();
		pageHomeMenu.goBack();

		return name;
	}
}
