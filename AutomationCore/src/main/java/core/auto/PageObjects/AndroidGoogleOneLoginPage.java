package core.auto.PageObjects;

import java.util.List;

import org.openqa.selenium.By;

import core.auto.pom.PageObject;
import io.appium.java_client.MobileElement;

public class AndroidGoogleOneLoginPage extends PageObject {

	private By	existingAccounts		= By.xpath(
			"//android.widget.TextView[@resource-id='com.google.android.gms:id/account_name']");
	private By	addAnotherAccountBtn	= By.xpath(
			"//android.widget.TextView[@resource-id='com.google.android.gms:id/add_account_chip_title']/..");
	private By	emailInputBox			= By
			.xpath("//android.widget.EditText[@resource-id='identifierId']");
	private By	passwdInputBox			= By
			.xpath("//android.view.View[@resource-id='password']//android.widget.EditText");
	private By	emailNextButton			= By
			.xpath("//android.widget.Button[@resource-id='identifierNext']");
	private By	passwdNextButton		= By
			.xpath("//android.widget.Button[@resource-id='passwordNext']");
	private By	agreeButton				= By
			.xpath("//android.widget.Button[@resource-id='signinconsentNext']");

	private MobileElement isLoggedIn(String account) {

		waitForAppear(existingAccounts, 30);
		List<MobileElement> listElements = ((MobileElement) getDriver()).findElements(existingAccounts);
		for (MobileElement element : listElements) {
			if (element.getText().equals(account)) { return element; }
		}
		return null;
	}

	public void doLogin(String username, String password) {

		MobileElement ifLoggedIn = isLoggedIn(username);
		if (ifLoggedIn != null) {
			ifLoggedIn.click();
			return;
		}

		find(addAnotherAccountBtn).click();
		waitForAppear(emailInputBox, 10).sendKeys(username);
		find(emailNextButton).click();
		waitForAppear(passwdInputBox, 5).sendKeys(password);
		find(passwdNextButton).click();
		waitForAppear(agreeButton, 5).click();
	}
}
