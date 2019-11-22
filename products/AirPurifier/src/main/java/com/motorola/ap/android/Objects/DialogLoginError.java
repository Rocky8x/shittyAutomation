package com.motorola.ap.android.Objects;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

public class DialogLoginError extends PageBase {

	public String msgNotVerifiedAccountError = "*Please check your spam/bulk/clutter"
			+ " folder if you haven't received your activation email within 10 minutes.\n"
			+ " Please check your email to complete the verification process.\n"
			+ " Alternatively, would you like to resend the verification email?";

	public String msgIncorrectUserPasswdError = "You entered an incorrect username or password."
			+ " Check your sign in details and try again."
			+ " Take note your account will be locked after next 4 failed attempt(s)."
			+ " Then you have to wait for 30 minutes to try again.";

	public String msgDontHaveAccountWithUs = "Looks like you don't have an account with us,"
			+ " do you want to register a new account now?";

	public By	titleLoginError		=  By.id("text_dialog_title");
	public By	contentLoginError	=  By.id("text_dialog_msg");

	public boolean verifyLoginError(String expected) {

		assertEquals(waitForAppear(titleLoginError,15).getText(), "Login Error");
		assertEquals(find(contentLoginError).getText(), expected);
		return true;
	}

	public boolean verifyLoginNotActiveAccount() {

		verifyLoginError(msgNotVerifiedAccountError);
		find(buttonCancel).click();
		return true;
	}

	public boolean verifyLoginIncorrectUserPasswordError() {

		verifyLoginError(msgIncorrectUserPasswdError);
		find(buttonOk).click();
		return true;
	}

	public DialogLoginError verifyDialogDontHaveAccountWithUs() {

		verifyLoginError(msgDontHaveAccountWithUs);
		return this;
	}

	public PageIntro closeDialog() {

		find(buttonCancel).click();
		return new PageIntro();
	}

	public PageSignUp goToPageSignUp() {

		find(buttonOk).click();
		return new PageSignUp();
	}
}
