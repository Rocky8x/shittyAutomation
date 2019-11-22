package com.motorola.ap.android.Objects;

import org.openqa.selenium.By;

public class PageSignUp extends PageBase {

	public PageSignUp() {

		buttonCommonBack = (By) (By.id("imageview_register_back"));
	}

	public By	textboxUserName				= By.id("username_register");
	public By	textboxFirstName			= By.id("first_name_register");
	public By	textboxLastName				= By.id("last_name_register");
	public By	textboxEmail				= By.id("email_register");
	public By	textboxPasswd				= By.id("password_register");
	public By	checkboxTos					= By.id("textview_register_agree");
	public By	checkboxMarketingConsent	= By.id("checkbox_marketing_consent_opt");
	public By	buttonSignUp				= By.id("textview_register_create");

	/**
	 * @param args must be in following order username, first name, last name,
	 *             email, password
	 * @return {@link PageIntro}
	 */
	public PageIntro doSignUp(String... args) {

		find(textboxUserName).sendKeys(args[0]);
		find(textboxFirstName).sendKeys(args[1]);
		find(textboxLastName).sendKeys(args[2]);
		find(textboxEmail).sendKeys(args[3]);
		find(textboxPasswd).sendKeys(args[4]);
		find(checkboxTos).click();
		find(buttonSignUp).click();
		return new PageVerifySignUp().doExit();
	}

	public String getEmailText() {

		return find(textboxEmail).getText().trim();
	}
}
