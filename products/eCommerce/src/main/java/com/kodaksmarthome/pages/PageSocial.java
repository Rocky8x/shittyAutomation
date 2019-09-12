package com.kodaksmarthome.pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageSocial extends PageBase{
	public final String KODAK_TITLE = "KODAK Smart Home";
	
	/*
	 * ELEMENTS
	 */
	
	public WbElement getKodakFacebookChanel() {
		String chanel = "//a[@href='https://www.facebook.com/KODAKSmartHome/']";
		return new WbElement(By.xpath(chanel), "Facebook Chanel");
	}
	
	public WbElement getKodakInstagramChanel() {
		String chanel = "//a[contains(@href, 'kodak-giveaway')]/ancestor::div/h1";
		return new WbElement(By.xpath(chanel), "Instagram Chanel");
	}
	
	public WbElement getKodakTwitterChanel() {
		String chanel = "//h1[@class='ProfileHeaderCard-name']/a";
		return new WbElement(By.xpath(chanel), "Twitter Chanel");
	}
	
	public WbElement getKodakYoutubeChanel() {
		String chanel = "//span[@id='channel-title']";
		return new WbElement(By.xpath(chanel), "Youtube Chanel");
	}
	
	
	public void verifyFacebookKodakSmartHomeDisplay(String socialPage) {
		String currentHandle = WbDriverManager.getDriver().getWindowHandle();
		switch (socialPage) {
		case "Facebook":
			footer.getFacebookIcon().hoverOnElement();
			break;

		default:
			break;
		}
		clickFacebookIcon();
		for (String handle : WbDriverManager.getDriver().getWindowHandles()) {
			if (handle != currentHandle) {
				WbDriverManager.getDriver().switchTo().window(handle);
			}
		}
		
		assertEquals(getKodakFacebookChanel().getText(), KODAK_TITLE);
		WbDriverManager.getDriver().close();
		WbDriverManager.getDriver().switchTo().window(currentHandle);
	}
}
