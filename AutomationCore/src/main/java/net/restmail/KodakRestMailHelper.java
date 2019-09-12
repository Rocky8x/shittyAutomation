package net.restmail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cinatic.TimeHelper;
import com.ebn.automation.core.WbDriverManager;

public class KodakRestMailHelper {

	private RestMailDriver		restMailDriver;
	private static final String	msg_VERIFY_SUCCESS			= "Congratulation! Your account has been verified";
	private static final String	MSG_SHARE_DEVICE_SUCCESS	= "%s device is shared successfully";

	// Constructors
	public KodakRestMailHelper() {

		restMailDriver = new RestMailDriver();
	}

	/**
	 * @param userName
	 *                 acb@mail.com
	 *                 or just acb
	 *                 both are acceptable
	 */
	public KodakRestMailHelper(String userName) {

		userName		= userName.split("@")[0];
		restMailDriver	= new RestMailDriver(userName);
	}

	// Methods
	/**
	 * get url in sign up email to confirm new account
	 * then open the url to confirm
	 * 
	 * @return
	 */
	public String confirmSignupEmail() {

		// open 'Account confirm' mail
		String url = getNewAccountConfirmLink();
		WbDriverManager.newBrowser("chrome");
		WbDriverManager.getDriver().manage().window().maximize();
		WbDriverManager.navigateToUrl(url);
		try {
			// get message
			String message = getVerificationMessage().getText().trim();
			// Verify message
			Assert.assertEquals(message, msg_VERIFY_SUCCESS, "Error: message is " + message);
			WbDriverManager.closeBrowser();
			return message;
		} catch (Exception e) {
			WbDriverManager.closeBrowser();
			throw e;
		}
	}

	/**
	 * Get the link to confirm share camera from email,
	 * Open the link, and login to confirm
	 * 
	 * @param userName   of account which camera has been shared to
	 * @param password
	 * @param deviceName name of the shared camera
	 */
	public void confirmGrantAccesEmail(String userName, String password, String deviceName) {

		try {
			String url = getGrantAccessConfirmLink();
			WbDriverManager.newBrowser("chrome");
			WbDriverManager.getDriver().manage().window().maximize();
			WbDriverManager.navigateToUrl(url);
			verifyConfirmGrantAccessSuccess(userName, password, deviceName);
			WbDriverManager.closeBrowser();
		} catch (AWTException e) {
			WbDriverManager.closeBrowser();
			e.printStackTrace();
		}
	}

	/**
	 * delete all email from this mail box
	 */
	public KodakRestMailHelper deleteAllRestMail() {

		restMailDriver.deleteAllMail();
		return this;
	}

	/**
	 * Get email subject
	 * 
	 * @return
	 */
	public String getEmailSubject() {

		return restMailDriver.getEmailSubject();
	}

	/**
	 * Duong Nguyen
	 * get all attachment in email
	 * 
	 * @return list attachments
	 *         21-01-2019: Thach: change method to get attachment since we change
	 *         from Response to JSONArray.
	 */
	public List<String> getEmailAttachments() {

		List<String>	listAtt					= new ArrayList<>();
		JSONArray		attachmentsJSONObject	= restMailDriver.getAttachmentEmail();
		for (Object jO : attachmentsJSONObject) {
			String fileName = ((JSONObject) jO).getString("fileName");
			listAtt.add(fileName);
		}
		return listAtt;
	}

	// web element on kodak links
	private WebElement getUsernameTextbox() {

		String locatorUser = "//input[@id='login']";
		return WbDriverManager.getDriver().findElement(By.xpath(locatorUser));
	}

	private WebElement getPasswordTextbox() {

		String locatorPwd = "//input[@id='password']";
		return WbDriverManager.getDriver().findElement(By.xpath(locatorPwd));
	}

	private WebElement getSignInButton() {

		String locatorSignIn = "//input[@type='submit' and @value='Sign In']";
		return WbDriverManager.getDriver().findElement(By.xpath(locatorSignIn));
	}

	/**
	 * get the message after goto the sign up confirm link in email
	 * 
	 * @return
	 */
	public WebElement getVerificationMessage() {

		String		xpathLocator		= "//div[@class='alert alert-success ng-binding']";
		WebElement	verificationMessage	= null;
		try {
			verificationMessage = WbDriverManager.getDriver().findElement(By.xpath(xpathLocator));
		} catch (Exception e) {
			WbDriverManager.getDriver().manage().deleteAllCookies();
			WbDriverManager.getDriver().navigate().refresh();
			verificationMessage = WbDriverManager.getDriver().findElement(By.xpath(xpathLocator));
		}
		return verificationMessage;
	}

	/**
	 * @param newEmail
	 * @return
	 */
	public boolean confirmChangeEmail(KodakRestMailHelper newEmail) {

		try {

			String url = this.getChangeEmailConfirmOldEmailLink();
			WbDriverManager.newBrowser("chrome");
			WbDriverManager.getDriver().manage().window().maximize();
			WbDriverManager.navigateToUrl(url);
			WbDriverManager.closeBrowser();

			TimeHelper.sleep(5000);
			newEmail.getRestMailDriver().downloadEmail();
			String url2 = newEmail.getChangeEmailConfirmNewEmailLink();
			WbDriverManager.navigateToUrl(url2);
			WbDriverManager.closeBrowser();
			return false;
		} catch (Exception e) {
			WbDriverManager.closeBrowser();
			throw e;
		}
	}

	public RestMailDriver getRestMailDriver() {

		return restMailDriver;
	}

	// private utilities

	private String getNewAccountConfirmLink() {

		String emailContent = restMailDriver.getHtmlEmail();

		// Use jsoup to parse the email and find the link to activate account
		Document doc = Jsoup.parse(emailContent);
		// the link contain "user/verify" in it
		Element e = doc.select("a[href~=.*user/verify.*]").first();

		// get value of href attribute and return
		return e.attr("href");
	}

	/**
	 * Get confirm url sent to email when share camera
	 * 
	 * @return url
	 */
	private String getGrantAccessConfirmLink() {

		String emailContent = restMailDriver.getHtmlEmail();

		Document	doc	= Jsoup.parse(emailContent);
		Element		e	= doc.select("a[href~=.*user/login.*]").first();

		return e.attr("href");
	}

	private void verifyConfirmGrantAccessSuccess(String username, String password,
			String deviceName) throws AWTException {

		WebDriverWait wait = new WebDriverWait(WbDriverManager.getDriver(), 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='login']")))
				.click();
		try {
			WbDriverManager.getDriver().findElement(By.xpath("//a[@id='headerLogo']"));
		} catch (Exception e) {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_F5);
			r.keyRelease(KeyEvent.VK_F5);
		}
		getUsernameTextbox().sendKeys(username);
		getPasswordTextbox().sendKeys(password);
		getSignInButton().click();
		String msg = getGrantAccessSuccessMessage();
		Assert.assertEquals(msg, String.format(MSG_SHARE_DEVICE_SUCCESS, deviceName),
				"Error: message is " + msg);
	}

	private String getGrantAccessSuccessMessage() {

		String		locatorMsg	= "//div[@class='alert alert-success']/span[@class='ng-binding ng-scope']";
		WebElement	verificationMessage;
		try {
			verificationMessage = WbDriverManager.getDriver().findElement(By.xpath(locatorMsg));
		} catch (Exception e) {
			WbDriverManager.getDriver().manage().deleteAllCookies();
			WbDriverManager.getDriver().navigate().refresh();
			verificationMessage = WbDriverManager.getDriver().findElement(By.xpath(locatorMsg));
		}
		return verificationMessage.getText().trim();
	}

	/**
	 * Get confirm link which send to your NEW email to change your email to a new
	 * one
	 * 
	 * @return
	 */
	private String getChangeEmailConfirmNewEmailLink() {

		String emailContent = restMailDriver.getHtmlEmail();

		// Use jsoup to parse the email and find the link to activate account
		Document doc = Jsoup.parse(emailContent);
		// the link contain "user/verify" in it
		Element e = doc.select("a[href~=.*user/email/verify_new_change.*]").first();

		// get value of href attribute and return
		return e.attr("href");
	}

	/**
	 * Get confirm link which send to your NEW old to change your email to a new one
	 * 
	 * @return
	 */
	private String getChangeEmailConfirmOldEmailLink() {

		String emailContent = restMailDriver.getHtmlEmail();

		// Use jsoup to parse the email and find the link to activate account
		Document doc = Jsoup.parse(emailContent);
		// the link contain "user/verify" in it
		Element e = doc.select("a[href~=.*user/email/verify_old_change.*]").first();

		// get value of href attribute and return
		return e.attr("href");
	}
}
