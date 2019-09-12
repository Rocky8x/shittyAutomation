package com.motorolachargers.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;

import com.cinatic.TimeHelper;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;
import com.ebn.base.PageBaseCommon;
import com.motorolachargers.pages.GroupHeader;
import com.motorolachargers.pages.PageHome;
import com.motorolachargers.pages.PageSupport;

import com.motorolachargers.pages.GroupFooter;

public class PageBase extends PageBaseCommon {

	GroupHeader header;
	GroupFooter footer;

	public final String urlMotorolaHomePage = "https://www.motorola.com/us/home";
	public final String urlMotorolaFacebookHomePage = "https://www.facebook.com/motorolahomeglobal/";
	public final String urlMotorolaTwitterHomePage = "https://twitter.com/motorolahome";
	public final String urlMotorolaInstagramHomePage = "https://www.instagram.com/motorola_home/";

	public final List<String> country = Arrays.asList("United States", "United Kingdom", "India", "Canada - English",
			"Canada - French", "Europe - English", "Germany", "France", "Spain", "Italy", "Mexico", "Brazil");
//	public final List<String> linkMainPage = Arrays.asList("https://motorolachargers.com/",
//			"https://uk.motorolachargers.com/", "https://in.motorolachargers.com/", "https://ca.motorolachargers.com/",
//			"https://fr-ca.motorolachargers.com/", "https://eu.motorolachargers.com/",
//			"https://de.motorolachargers.com/", "https://fr.motorolachargers.com/", "https://es.motorolachargers.com/",
//			"https://it.motorolachargers.com/", "https://mx.motorolachargers.com/",
//			"https://www.motorola.com.br/acessorios/carregador");

	public PageBase() {
		baseURL = "https://motorolachargers.com/";
		header = new GroupHeader();
		footer = new GroupFooter();

	}

	public WbElement getFlagCountry(String name) {
		String xpath = "//div[@class='modal-dialog modal-lg']//span[@class='countryName' and text()='" + name + "']";
		return new WbElement(By.xpath(xpath), name);
	}

	public WbElement getHomeBanner() {
		String xpath = "//div[contains(@style,'home-banner.jpg')]";
		return new WbElement(By.xpath(xpath), "Home Banner");
	}
//
//	public void backToHomePage() {
//		WebDriverManager.navigateToUrl(baseURL);
//	}
	
	public List<WbElement> getFlagCountryList() {
		String xpath = "//div[@id='region-options']/a[@class='otherCountry']";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public void setBaseURL(String url) {
		baseURL = url;
	}

	public void verifyHeaderFooter() {
		Log.info("Verify header content");
		assertTrue(header.getHomePageMotorolaTxt().isDisplayed());
//		assertTrue(header.getLoginBtn().isDisplayed());
//		assertTrue(header.getCartBtn().isDisplayed());
		assertTrue(header.getHomePageLogo().isDisplayed());
		assertTrue(header.getMenuProductTxt().isDisplayed());
		assertTrue(header.getMenuSupportTxt().isDisplayed());
		assertTrue(header.getChangeCountryIcon().isDisplayed());

		Log.info("Verify footer content");
		assertTrue(footer.getChangeCountry().isDisplayed());
		assertTrue(footer.getFacebookIcon().isDisplayed());
		assertTrue(footer.getInstagramIcon().isDisplayed());
		assertTrue(footer.getTwitterIcon().isDisplayed());
		assertTrue(footer.getDeleveryPolicyTxt().isDisplayed());
		assertTrue(footer.getHelpCenterTxt().isDisplayed());
		assertTrue(footer.getContactUsTxt().isDisplayed());
		assertTrue(footer.getContactUsTxt().isDisplayed());
		assertTrue(footer.getTermofServiceTxt().isDisplayed());
		assertTrue(footer.getReturnPolicyTxt().isDisplayed());
		assertTrue(footer.getAboutUsTxt().isDisplayed());
	}

	public void verifyHeader() {
		Log.info("Verify header content");
		assertTrue(header.getHomePageMotorolaTxt().isDisplayed());
		assertTrue(header.getLoginBtn().isDisplayed());
		assertTrue(header.getCartBtn().isDisplayed());
		assertTrue(header.getHomePageLogo().isDisplayed());
		assertTrue(header.getMenuProductTxt().isDisplayed());
		assertTrue(header.getMenuSupportTxt().isDisplayed());
		assertTrue(header.getChangeCountryIcon().isDisplayed());

	}

	public void verifyFooter() {
		Log.info("Verify footer content");
		assertTrue(footer.getChangeCountry().isDisplayed());
		assertTrue(footer.getFacebookIcon().isDisplayed());
		assertTrue(footer.getInstagramIcon().isDisplayed());
		assertTrue(footer.getTwitterIcon().isDisplayed());
		assertTrue(footer.getDeleveryPolicyTxt().isDisplayed());
		assertTrue(footer.getHelpCenterTxt().isDisplayed());
		assertTrue(footer.getContactUsTxt().isDisplayed());
		assertTrue(footer.getContactUsTxt().isDisplayed());
		assertTrue(footer.getTermofServiceTxt().isDisplayed());
		assertTrue(footer.getReturnPolicyTxt().isDisplayed());
		assertTrue(footer.getAboutUsTxt().isDisplayed());

	}

	public void clickMainLogoAndVerify() {
		header.getHomePageLogo().click();
		PageHome pageHome = new PageHome(this);
		pageHome.verifyPageBasicContent();
		pageHome.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void clickHeaderMotorolaHomePageTxtAndVerify() {
		header.getHomePageMotorolaTxt().click();
		WbDriverManager.switchToNewWindow();
		assertEquals(WbDriverManager.getCurrentUrl(), urlMotorolaHomePage);
		WbDriverManager.closeCurrentTab();
	}

	public void clickLoginButtonAndVerify() {
		header.getLoginBtn().click();
		PageLogin pageLogin = new PageLogin();
		pageLogin.verifyPageBasicContent();
		pageLogin.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void clickCartButtonAndVerify() {
		header.getCartBtn().click();
		PageCart pageCart = new PageCart(this);
		WbDriverManager.waitForPageLoad();
		pageCart.verifyPageBasicContent();
		pageCart.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyPageBasicContent() {
		verifyHeaderFooter();
	}

	public void clickMenuProductTxtAndVerify() {
		header.getMenuProductTxt().click();
		WbDriverManager.waitForPageLoad();
		PageProduct pageProduct = new PageProduct(this);
		pageProduct.verifyPageBasicContent();
		pageProduct.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void clickMenuSupportTxtAndVerify() {
		header.getMenuSupportTxt().click();
		WbDriverManager.waitForPageLoad();
		PageSupport pageSupport = new PageSupport(this);
		pageSupport.verifyPageBasicContent();
		pageSupport.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void clickLookingForHelpTxtAndVerify() {
		header.getLookingForHelpTxt().click();
		WbDriverManager.waitForPageLoad();
		PageSupport pageSupport = new PageSupport(this);
		pageSupport.verifyPageBasicContent();
		pageSupport.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyDeliveryPolicyLink() {
		Log.info("Go to Delivery Policy page from footer and verify");
		footer.getDeleveryPolicyTxt().clickByJavaScripts();
		WbDriverManager.waitForPageLoad();
		PageDeliveryPolicy pageDeliveryPolicy = new PageDeliveryPolicy();
		pageDeliveryPolicy.verifyPageBasicContent();
		pageDeliveryPolicy.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyHelpCenterLink() {
		Log.info("Go to Help Center page from footer and verify");
		footer.getHelpCenterTxt().clickByJavaScripts();
		WbDriverManager.waitForPageLoad();
		PageSupport pageSupport = new PageSupport(this);
		pageSupport.verifyPageBasicContent();
		pageSupport.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyContactUsLink() {
		Log.info("Go to Contact Us page from footer and verify");
		footer.getContactUsTxt().clickByJavaScripts();
		WbDriverManager.waitForPageLoad();
		PageContactUs pageContactUs = new PageContactUs();
		pageContactUs.verifyPageBasicContent();
		pageContactUs.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyPrivacyPolicyLink() {
		Log.info("Go to Privacy Policy page from footer and verify");
		footer.getDeleveryPolicyTxt().clickByJavaScripts();
		WbDriverManager.waitForPageLoad();
		PageDeliveryPolicy pagePrivacyPolicy = new PageDeliveryPolicy();
		pagePrivacyPolicy.verifyPageBasicContent();
		pagePrivacyPolicy.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyTermsOfServiceLink() {
		Log.info("Go to Terms Of Service page from footer and verify");
		footer.getTermofServiceTxt().clickByJavaScripts();
		WbDriverManager.waitForPageLoad();
		PageTermsOfService pageTermsOfService = new PageTermsOfService();
		pageTermsOfService.verifyPageBasicContent();
		pageTermsOfService.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyReturnPolicyLink() {
		Log.info("Go to Return Policy page from footer and verify");
		footer.getReturnPolicyTxt().clickByJavaScripts();
		WbDriverManager.waitForPageLoad();
		PageReturnPolicy pageReturnPolicy = new PageReturnPolicy();
		pageReturnPolicy.verifyPageBasicContent();
		pageReturnPolicy.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyAboutUsLink() {
		Log.info("Go to About Us page from footer and verify");
		footer.getAboutUsTxt().clickByJavaScripts();
		WbDriverManager.waitForPageLoad();
		PageAboutUs pageAboutUs = new PageAboutUs();
		pageAboutUs.verifyPageBasicContent();
		pageAboutUs.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void clickFacebookIconAndVerify() {
		footer.getFacebookIcon().click();
		WbDriverManager.switchToNewWindow();
		assertEquals(WbDriverManager.getCurrentUrl(), urlMotorolaFacebookHomePage);
		WbDriverManager.closeCurrentTab();
	}

	public void clickInstagramIconAndVerify() {
		footer.getInstagramIcon().click();
		WbDriverManager.switchToNewWindow();
		WbDriverManager.waitForPageLoad();
		assertEquals(WbDriverManager.getCurrentUrl(), urlMotorolaInstagramHomePage);
		WbDriverManager.closeCurrentTab();
	}

	public void clickTwitterIconAndVerify() {
		footer.getTwitterIcon().click();
		WbDriverManager.switchToNewWindow();
		WbDriverManager.waitForPageLoad();
		assertEquals(WbDriverManager.getCurrentUrl(), urlMotorolaTwitterHomePage);
		WbDriverManager.closeCurrentTab();
	}

	public void verifyFacebookLink() {
		Log.info("Verify Facebook link");
		String link = footer.getFacebookIcon().getAttribute("href");
		assertEquals(link, urlMotorolaFacebookHomePage);
	}

	public void verifyTwitterLink() {
		Log.info("Verify Twitter link");
		String link = footer.getTwitterIcon().getAttribute("href");
		assertEquals(link, urlMotorolaTwitterHomePage);
	}

	public void verifyInstagramLink() {
		Log.info("Verify Instagram link");
		String link = footer.getInstagramIcon().getAttribute("href");
		assertEquals(link, urlMotorolaInstagramHomePage);
	}

	public void verifySocialMediaLink() {
		verifyFacebookLink();
		verifyTwitterLink();
		verifyInstagramLink();
//		clickFacebookIconAndVerify();
//		clickInstagramIconAndVerify();
//		clickTwitterIconAndVerify();
	}

	public String changeCountryHeaderIcon(String country) {
		header.getChangeCountryIcon().click();
		getFlagCountry(country).clickByJavaScripts();
		return WbDriverManager.getCurrentUrl();
	}

	public String changeCountryFooterIcon(String country) {
		footer.getChangeCountryIcon().click();
		getFlagCountry(country).click();
		return WbDriverManager.getCurrentUrl();
	}
	
	public void changeCountryAndVerify() {
		Log.info("Click all product and verify");
		int lenght = getFlagCountryList().size();
		for (int i = 1; i < lenght; i++) {
			header.getChangeCountryIcon().click();
			// get href product
			WbDriverManager.waitForPageLoad();
			WbElement element = getFlagCountryList().get(i);
			String href = element.getAttribute("href");
			element.clickByJavaScripts();
			TimeHelper.sleep(1000);
			WbDriverManager.waitForPageLoad();
			String currentUrl = WbDriverManager.getCurrentUrl();
			// verify url
			if(href.equals("https://br.motorolachargers.com/")) {
				href = "https://www.motorola.com.br/acessorios/carregador";
			}
			assertEquals(currentUrl, href);
			WbDriverManager.backPreviousPage();
			WbDriverManager.waitForPageLoad();
		}
	}

}