package ios.kodak.object;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;

/**
 * 
 * @author Duong Nguyen
 * 
 * PageEmail is a third party to support send email when user share event, send log...
 *
 */

public class PageEmail extends PageBase{

	/*
	 * ELEMENTS
	 */
	
	public static Element getMailTo() {
		return new Element(By.name("toField"), 180).setDescription("Mail to");
	}
	
	public static Element getMailTitle() {
		return new Element(By.name("subjectField")).setDescription("Subject");
	}
	
	public static Element getSendBtn() {
		return new Element(By.name("Send")).setDescription("Send button");
	}
	
	public static List<WebElement> getAttachmentsInEmail() {
		String content = "//XCUIElementTypeTextView[@name='Message body']/XCUIElementTypeImage";
		return DriverManager.getDriver().findElements(By.xpath(content));
	}
	/*
	 * ACTIONS
	 */
	
	public static void inputMailTo(String email) {
		getMailTo().sendKeys(email);
	}
	
	public static void inputSubject(String subject) {
		getMailTitle().sendKeys(subject);
	}
	
	public static void clickSendButton() {
		getSendBtn().click();
	}
	
	public static List<String> getListAttachmentsInEmail(){
		List<String> lstAttch = new ArrayList<String>();
		for (WebElement ele : getAttachmentsInEmail()) {
			lstAttch.add(ele.getText());
		}
		return lstAttch;
	}
	
	public static void sendEmail(String mailTo, String subject) {
		inputMailTo(mailTo);
		inputSubject(subject);
		clickSendButton();
	}
}
