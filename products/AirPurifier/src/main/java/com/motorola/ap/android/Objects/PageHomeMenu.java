package com.motorola.ap.android.Objects;

import org.openqa.selenium.By;

import com.motorola.ap.android.AppSetting;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class PageHomeMenu extends PageBase {

	public By	buttonSignOut		= By.xpath("//android.widget.TextView[@text='Sign Out']");
	public By	textviewCurrentUser	= By.id("tv_account_name");

	public PageLogin doSignOut() {

		find(buttonSignOut).click();
		AppSetting.currentLoggedUser = "";
		return new PageLogin();
	}

	@Override
	public PageBase goBack() {

		((AndroidDriver<?>) getDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
		return (PageBase) this;
	}

	public String getCurrentUsername() {

		return waitForAppear(textviewCurrentUser, 10).getText().trim();
	}
}
