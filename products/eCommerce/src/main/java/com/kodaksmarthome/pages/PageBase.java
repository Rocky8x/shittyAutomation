package com.kodaksmarthome.pages;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;
import com.ebn.base.PageBaseCommon;

public class PageBase extends PageBaseCommon {

	public GroupHeader				header;
	public GroupFooter				footer;
	public String					currentLang;
	public HashMap<String, String>	pathPerLang;

	public PageBase() {

		baseURL		= "https://kodaksmarthome.com/";
		header		= new GroupHeader();
		footer		= new GroupFooter();
		currentLang	= "USA";

		pathPerLang = new HashMap<>();
		pathPerLang.put("USA", "");
		pathPerLang.put("Australia", "");
		pathPerLang.put("Canada (En", "");
		pathPerLang.put("Canada (Fr", "");
		pathPerLang.put("Europe", "");
		pathPerLang.put("France", "");
		pathPerLang.put("Germany", "");
		pathPerLang.put("India", "");
		pathPerLang.put("Italy", "");
		pathPerLang.put("Mexico", "");
		pathPerLang.put("Russia", "");
		pathPerLang.put("Spain", "");
		pathPerLang.put("United Kingdom", "");

		PATH = pathPerLang.get(currentLang);
	}

/*
 * PageBase(PageBase P) {
 * PATH = "";
 * this.baseURL = P.baseURL;
 * header = P.header;
 * footer = P.footer;
 * }
 */

	public void verifyProductLinks() {

		// left empty on purpose
	}

	public void verifyPageHeader() {

		assertTrue(header.getMainLogo().isDisplayed(), "Home logo should display.");
		assertTrue(header.getBabyMonitorLink().isDisplayed(), "Baby Monitor link should display.");
		assertTrue(header.getSecurityCameraLink().isDisplayed(),
				"Security Camera link should display.");
		assertTrue(header.getSupportLink().isDisplayed(), "Support link should display.");
		assertTrue(header.getCloudPortalLink().isDisplayed(), "Cloud Portal link should display.");
		assertTrue(header.getAccountIcon().isDisplayed(), "Account icon should display.");
		if (!header.noCart) {
			assertTrue(header.getCartIcon().isDisplayed(), "Cart icon should display.");
		}
		assertTrue(header.getFlagStoreIcon().isDisplayed(), "Country Switch menu should display.");
	}

	public void verifyPageFooter() {

		footer.getCopyRightEle().hoverOnElement();
		assertTrue(footer.getAboutUsLink().isDisplayed(), "About Us link should display.");
		assertTrue(footer.getContactUsLink().isDisplayed(), "Contact Us link should display.");
		assertTrue(footer.getContrySwitchMenu().isDisplayed(),
				"Country Switch menu should display.");
		assertTrue(footer.getPrivacyPolicyLink().isDisplayed(),
				"Privacy Policy link should display.");
		assertTrue(footer.getRegisterEmailEle().isDisplayed(),
				"Register email textbox should display.");
		assertTrue(footer.getRegisterProductLink().isDisplayed(),
				"Register Product link should display.");
		assertTrue(footer.getSupportLink().isDisplayed(), "Support link should display.");
		assertTrue(footer.getTermServiceLink().isDisplayed(),
				"Term of Service link should display.");

		// social link verification
		assertTrue(footer.getInstagramIcon().isDisplayed(), "Instagram icon should display.");
		assertTrue(footer.getFacebookIcon().isDisplayed(), "Facebook icon should display.");
		assertTrue(footer.getTwitterIcon().isDisplayed(), "Twitter icon should display.");
		assertTrue(footer.getYoutubeIcon().isDisplayed(), "Youtube icon should display.");
	}

	public void clickMainLogoAndVerify() {

		header.getMainLogo().click();
		new PageHome(this).verifyPageContent();
	}

	public void clickBabyMonitorLinkAndVerify() {

		header.getBabyMonitorLink().click();
		new PageCategoryBabyMonitors(this).verifyPageContent();
	}

	public void clickSecurityCameraLinkAndVerify() {

		header.getSecurityCameraLink().click();
		new PageSecurityCamera(this).verifyPageContent();
	}

	public void clickSupportLinkAndVerify() {

		header.getSupportLink().click();
// new PageSupport(this).verifyPageContent();
	}

	public void clickCloudPortalLinkAndVerify() {

		header.getCloudPortalLink().click();
		new PageCloudPortal(this).verifyPageContent();
	}

	public void clickAccountIconAndVerify() {

		header.getAccountIcon().click();
	}

	public void clickCartIconAndVerify() {

		if (header.noCart)
			return;
		header.getCartIcon().click();
		new PageCart(baseURL).verifyPageContent();
	}

	public void changeLanguage() {

	}

	public void clickAboutUsLinkAndVerify() {

		footer.getAboutUsLink().hoverOnElement();
		footer.getAboutUsLink().click();
		new PageAboutUs(this).verifyPageContent();
	}

	public void clickContactUsLinkAndVerify() {

		footer.getContactUsLink().hoverOnElement();
		footer.getContactUsLink().click();
		new PageContactUs(this).verifyPageContent();
	}

	public void clickFacebookIcon() {

		footer.getFacebookIcon().click();
	}

	public void clickInstagramIcon() {

		footer.getInstagramIcon().click();
	}

	public void clickPrivacyPolicyLinkAndVerify() {

		footer.getPrivacyPolicyLink().hoverOnElement();
		footer.getPrivacyPolicyLink().click();
		new PagePrivacyPolicy(this).verifyPageContent();
	}

	public void clickRegisterProductLinkAndVerify() {

		footer.getRegisterProductLink().hoverOnElement();
		footer.getRegisterProductLink().click();
		new PageRegisterProduct(this).verifyPageContent();
	}

	public void clickSupportLinkInFooter() {

		footer.getSupportLink().hoverOnElement();
		footer.getSupportLink().click();
// new PageSupport(this).verifyPageContent();
	}

	public void clickTermServiceLinkAndVerify() {

		footer.getTermServiceLink().hoverOnElement();
		footer.getTermServiceLink().click();
		new PageTermService(this).verifyPageContent();
	}

	public void clickTwiterIcon() {

		footer.getTwitterIcon().click();
	}

	public void clickYoutubeIcon() {

		footer.getYoutubeIcon().click();
	}

	public List<WbElement> getCountryList() {

		String xpath = "//div[@class='modal-body']/a/i";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public List<String> getCountryNameList() {

		List<String>	countryName		= new ArrayList<String>();
		List<WbElement>	countryElement	= getCountryList();
		for (WbElement wbElement : countryElement) {
			String name = wbElement.findElement(By.xpath("./../span")).getText();
			countryName.add(name);
		}
		return countryName;
	}

	public WbElement getCountryFlagAustralia() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'au.svg')]";
		return new WbElement(By.xpath(xpath), "Australia store");
	}

	public WbElement getCountryFlagCananadaEn() {

		String xpath = "//div[@class='modal-body']/a[not(contains(@href,'fr.'))]/i[contains(@data-flag,'ca.svg')]";
		return new WbElement(By.xpath(xpath), "Canada (En) store");
	}

	public WbElement getCountryFlagCananadaFr() {

		String xpath = "//div[@class='modal-body']/a[contains(@href,'fr.')]/i[contains(@data-flag,'ca.svg')]";
		return new WbElement(By.xpath(xpath), "Canada (Fr) store");
	}

	public WbElement getCountryFlagEu() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'eu.svg')]";
		return new WbElement(By.xpath(xpath), "Eu store");
	}

	public WbElement getCountryFlagFrance() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'fr.svg')]";
		return new WbElement(By.xpath(xpath), "France store");
	}

	public WbElement getCountryFlagGermany() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'de.svg')]";
		return new WbElement(By.xpath(xpath), "Germany store");
	}

	public WbElement getCountryFlagIndia() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'in.svg')]";
		return new WbElement(By.xpath(xpath), "India store");
	}

	public WbElement getCountryFlagItaly() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'it.svg')]";
		return new WbElement(By.xpath(xpath), "Italy store");
	}

	public WbElement getCountryFlagMexico() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'mx.svg')]";
		return new WbElement(By.xpath(xpath), "Mexico store");
	}

	public WbElement getCountryFlagRussia() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'ru.svg')]";
		return new WbElement(By.xpath(xpath), "Russia store");
	}

	public WbElement getCountryFlagSpain() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'es.svg')]";
		return new WbElement(By.xpath(xpath), "Spain store");
	}

	public WbElement getCountryFlagUk() {

		String xpath = "//div[@class='modal-body']/a/i[contains(@data-flag,'gb.svg')]";
		return new WbElement(By.xpath(xpath), "United Kingdom store");
	}

	public WbElement getCountryByName(String name) {

		String xpath = String.format("//div[@class='modal-body']/a/span[contains(text(),'%s')]",
				name);
		return new WbElement(By.xpath(xpath));
	}
}
