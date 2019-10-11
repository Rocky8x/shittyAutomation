package com.android.Objects;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageIntro extends PageBase {

	public Element getFbLoginBtn() {

		return new Element(By.id("img_facebook_login"), "Facebook login button");
	}

	public Element getGoogleLoginBtn() {

		return new Element(By.id("img_google_login"), "Google login button");
	}

	public Element getSignUpBtn() {

		return new Element(By.id("textview_introduction_register"), "Sign Up button");
	}

	public Element getLoginBtn() {

		return new Element(By.id("textview_introduction_login"), "Login button");
	}

	public Element getMainLogoImg() {

		return new Element(By.id("img_app_logo"), " Main logo");
	}
}
