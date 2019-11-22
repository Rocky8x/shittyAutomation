package com.motorola.ap.android.Objects;

import org.openqa.selenium.By;

public class PageVerifySignUp extends PageBase {

	public By	buttonCheckEmailNow		= By.id("btn_change_email");
	public By	textEmailSent			= By.id("text_verify_acc_message");
	public By	textInstruction			= By.id("text_verify_acc_instruction");
	public By	textVerifyEmailEnforce	= By.id("text_verify_acc_instruction_extra");
	public By	textCheckSpam			= By.id("text_check_spam_instruction");

	public PageIntro doExit() {

		find(buttonCommonBack).click();
		return new PageIntro();
	}
}
