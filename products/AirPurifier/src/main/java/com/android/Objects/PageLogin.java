package com.android.Objects;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageLogin extends PageBase {

	public Element getBackBtn() {

		return new Element(By.id("imageview_login_back"), "Back button");
	}

	public Element getUsernameTextInput() {

		return new Element(By.id("username_login"), "Username input");
	}

	public Element getUsernameClearBtn() {

		return new Element(By.id("img_username_cross"), "Clear username button");
	}

	public Element getPasswordTextInput() {

		return new Element(By.id("password_login"), "Password input");
	}

	public Element getShowPasswdBtn() {

		return new Element(By.id("ic_show_password"), "Show password button");
	}

	public Element getLoginButton() {

		return new Element(By.id("button_login"), "Login button");
	}

	public Element getForgotPasswdLink() {

		return new Element(By.id("forgot_pass_login"), "Forgot passwd link");
	}

	public Element getAppVerText() {

		return new Element(By.id("text_app_version"), "App version text");
	}

	public Element getLoginErrorTitle() {

		return new Element(By.id("text_dialog_title"));
	}

	public Element getLoginErrorContent() {

		return new Element(By.id("text_dialog_msg"));
	}

	public PageBase doLogin(String user, String passwd) {

		getUsernameTextInput().sendKeys(user);
		getPasswordTextInput().sendKeys(passwd);
		getLoginButton().click();
		return new PageBase();
	}

	public boolean isDisplay() {

		return getBackBtn().isDisplayed() && getUsernameTextInput().isDisplayed()
				&& getPasswordTextInput().isDisplayed();
	}

}
