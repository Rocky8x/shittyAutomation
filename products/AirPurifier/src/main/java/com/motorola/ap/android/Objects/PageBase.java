package com.motorola.ap.android.Objects;

import org.openqa.selenium.By;

import com.cinatic.log.Log;
import com.motorola.ap.android.AppSetting;

import rocky.automation.POM.PageObject;

public class PageBase extends PageObject {

	protected By	buttonOk			= By.id("btn_confirm");
	protected By	buttonCancel		= By.id("btn_cancel");
	protected By	buttonCommonBack	= By.id("img_back"); 

	public PageBase goBack() {

		Log.info("Clicking Back button");
		find(buttonCommonBack).click();
		return this;
	}

	public PageDashboard checkAndLogin(String username, String passwd) {

		if (AppSetting.currentLoggedUser.equals(username)) { return new PageDashboard(); }

		PageDashboard pageDashboard = new PageDashboard();
		if (pageDashboard.isDisplay()) {
			Log.info("App is in Dashboard page");
			String userInDashboard = pageDashboard.getLoggedInUser();
			if (userInDashboard.equals(username)) {
				AppSetting.currentLoggedUser = username;
				return pageDashboard;
			}
			pageDashboard.goToHomeMenu().doSignOut();
		}
		PageLogin pageLogin = new PageLogin();

		if (pageLogin.isDisplay()) {
			Log.info("App is in Login page");
			pageLogin.doLogin(username, passwd);
			return pageDashboard;
		}

		PageIntro pageIntro = new PageIntro();
		pageIntro.goToPageLogin().doLogin(username, passwd);

		return pageDashboard;
	}

	public DialogLoginError loginErroDialog() {

		return new DialogLoginError();
	}

	public PageIntro gotoIntroPage() {

		PageDashboard pageDashboard = new PageDashboard();
		if (pageDashboard.isDisplay()) { return pageDashboard.goToHomeMenu().doSignOut().goBack(); }
		PageLogin pageLogin = new PageLogin();
		if (pageLogin.isDisplay()) { return pageLogin.goBack(); }
		return new PageIntro();
	}
}
