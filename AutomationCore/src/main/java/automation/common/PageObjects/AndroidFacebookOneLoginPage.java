package automation.common.PageObjects;

import org.openqa.selenium.By;

import rocky.automation.POM.PageObject;

@SuppressWarnings("unused")
public class AndroidFacebookOneLoginPage extends PageObject {

	private By	usernameBox		= By
			.xpath("//android.widget.EditText[@resource-id='m_login_email']");
	private By	passwordBox		= By
			.xpath("//android.widget.EditText[@resource-id='m_login_password']");
	private By	theOnlyButton	= By.xpath("//android.widget.Button");
	private By	continueButton	= By.xpath("//android.widget.Button[0]");
	private By	cancelButton	= By.xpath("//android.widget.Button[1]");

	public void doLogin(String username, String password) {

		waitForAppear(usernameBox, 10).sendKeys(username);
		find(passwordBox).sendKeys(password);
		waitClickable(theOnlyButton).click();
		try { // some time it appears twice
			waitClickable(theOnlyButton).click();
			waitClickable(continueButton).click();
		} catch (Exception e) {}
	}
}
