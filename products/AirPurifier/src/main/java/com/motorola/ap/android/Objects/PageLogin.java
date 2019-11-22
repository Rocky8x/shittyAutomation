package com.motorola.ap.android.Objects;

import org.openqa.selenium.By;

import com.motorola.ap.android.AppSetting;

public class PageLogin extends PageBase {

	public By	buttonBack			= By.id("imageview_login_back");
	public By	textviewUsername	= By.id("username_login");
	public By	buttonClearUsername	= By.id("img_username_cross");
	public By	textviewPassword	= By.id("password_login");
	public By	buttonShowPasswd	= By.id("ic_show_password");
	public By	buttonLogin			= By.id("button_login");
	public By	linkForgotPasswd	= By.id("forgot_pass_login");
	public By	labelAppVer			= By.id("text_app_version");

	public PageDashboard doLogin(String user, String passwd) {

		find(textviewUsername).sendKeys(user);
		find(textviewPassword).sendKeys(passwd);
		find(buttonLogin).click();
		AppSetting.currentLoggedUser = user;
		return new PageDashboard();
	}

	@Override
	public PageIntro goBack() {

		waitForAppear(buttonBack, 5).click();
		return new PageIntro();
	}

	public boolean isDisplay() {

		return find(textviewUsername) != null && find(textviewPassword) != null;
	}

	public String getPasswordText() {

		return find(textviewPassword).getText().trim();
	}

}
