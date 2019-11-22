package com.motorola.ap.android.Objects;

import org.openqa.selenium.By;

import automation.common.PageObjects.AndroidFacebookOneLoginPage;
import automation.common.PageObjects.AndroidGoogleOneLoginPage;

public class PageIntro extends PageBase {

	public By	buttonFbLogin		= By.id("layout_button_facebook");
	public By	buttonGoogleLogin	= By.id("layout_button_google");
	public By	buttonSignUp		= By.id("textview_introduction_register");
	public By	buttonLogin			= By.id("textview_introduction_login");
	public By	imageMainLogo		= By.id("img_app_logo");

	public PageSignUp goToPageSignUp() {

		find(buttonSignUp).click();
		return new PageSignUp();
	}

	public PageLogin goToPageLogin() {

		find(buttonLogin).click();
		return new PageLogin();
	}

	public AndroidFacebookOneLoginPage goToFacebookLoginPage() {

		find(buttonFbLogin).click();
		return new AndroidFacebookOneLoginPage();
	}

	public AndroidGoogleOneLoginPage goToGoogleLoginPage() {

		find(buttonGoogleLogin).click();
		return new AndroidGoogleOneLoginPage();
	}

	public boolean isDisplay() {

		if (find(buttonSignUp) != null) { return true; }
		return false;
	}
}
