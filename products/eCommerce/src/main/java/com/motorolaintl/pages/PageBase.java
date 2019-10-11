package com.motorolaintl.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.cinatic.TimeHelper;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;
import com.ebn.base.PageBaseCommon;

public class PageBase extends PageBaseCommon {

	GroupHeader header;
	GroupFooter footer;

	public final String urlMotorolaHomePage = "https://www.motorola.com/mea/home";
	public final String urlMotorolaPhoneHomePage = "https://www.motorola.com/us/products/moto-z-family";
	public final String urlFacebookHomePage = "https://www.facebook.com/MotorolaUS/";
	public final String urlInstagramHomePage = "https://www.instagram.com/motorolaus/";
	public final String urlTwitterHomePage = "https://twitter.com/MotorolaUS";
	public final String urlPayPal = "https://www.paypal.com/";

	public final String urlEbuyNowHomePage = "https://ebuynow.com/";
	public final String urlMotoralHomePage = "https://www.motorola.com/us/products/moto-mods";
	public final String urlMotoralPrivacyPolicyPage = "http://www.motorola.com/us/consumers/about-motorola-us/About_Motorola-Legal-Privacy_Policy/About_Motorola-Legal-Privacy_Policy.html";
	
	public PageBase() {
		header = new GroupHeader();
		footer = new GroupFooter();
		baseURL = "https://intl.motorola.com/";

	}

	public void backToHomePage() {
		WbDriverManager.navigateToUrl(baseURL);
	}

	public List<WbElement> getCountryFlagList() {
		String xpath = "//a[@class='store']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public List<WbElement> getParentPageSubMenuList() {
		String xpath = "//ul[@class='list-unstyled list-inline']/li";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public void verifyHeaderFooter() {
		Log.info("Verify header content");
		assertTrue(header.getAccessoriesMenu().isDisplayed());
		assertTrue(header.getBackToMotorolaTopLeftLink().isDisplayed());
		assertTrue(header.getCartMenu().isDisplayed());
		assertTrue(header.getCountrySwitchMenu().isDisplayed());
		assertTrue(header.getMainLogo().isDisplayed());
		assertTrue(header.getMotoModMenu().isDisplayed());
		assertTrue(header.getSupportMenu().isDisplayed());
		assertTrue(header.getCartIcon().isDisplayed());

		Log.info("Verify footer content");
		assertTrue(footer.getCopyrightLabel().hoverOnElement().isDisplayed());
//		TimeHelper.sleep(1000);
		assertTrue(footer.getCountrySwitch().isDisplayed());
		assertTrue(footer.getCountrySwitchLabel().isDisplayed());
		assertTrue(footer.getInfomationLabel().isDisplayed());
		assertTrue(footer.getMotorolaLogo().isDisplayed());
		assertTrue(footer.getPoweredByLabel().isDisplayed());
		assertTrue(footer.getPrivacyPolicyLink().isDisplayed());
		assertTrue(footer.getSocialLinkFacebook().isDisplayed());
		assertTrue(footer.getSocialLinkInstagram().isDisplayed());
		assertTrue(footer.getSocialLinkTwitter().isDisplayed());
		assertTrue(footer.getSupportAndContactLink().isDisplayed());

		header.getAccessoriesMenu().scrollToElement();
	}

	public void clickMainLogoAndVerify() {
		header.getMainLogo().click();
		PageBase pagebase = new PageBase();
		verifyPageBasicContent();
		pagebase.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyGotoMotorolaTopLeftLink() {
		header.getBackToMotorolaTopLeftLink().click();
		WbDriverManager.waitForPageLoad();
		String currentUrl = WbDriverManager.getCurrentUrl();
		assertEquals(currentUrl, urlMotorolaHomePage);
		WbDriverManager.backPreviousPage();
	}

	public void verifyGotoMotorolaPhoneTopRightLink() {
		header.getBackToMotorolaPhoneTopRightLink().click();
		WbDriverManager.waitForPageLoad();
		String currentUrl = WbDriverManager.getCurrentUrl();
		assertEquals(currentUrl, urlMotorolaPhoneHomePage);
		WbDriverManager.backPreviousPage();
	}

	public void hoverMotoModMenuAndVerify() {
		Log.info("Verify Mod menu content");
		for (int i = 0; i < header.getModMenuSubItems().size(); i++) {
			header.getMotoModMenu().hoverOnElement();
			// get href sub mod menu
			WbElement element = header.getModMenuSubItems().get(i);
			String href = element.getAttribute("href");
			element.click();
			String currentUrl = WbDriverManager.getCurrentUrl();
			verifyPageBasicContent();
			assertEquals(currentUrl, href);
			WbDriverManager.backPreviousPage();
		}
	}

	public void verifyMinicart() {
		header.getCartMenu().click();
		assertTrue(header.getCartTitle().isDisplayed());
		assertTrue(header.getEmptyCartLabel().isDisplayed());
		header.getCloseMinicartButton().click();
	}

	public void hoverAccessoriesMenuAndVerify() {
		Log.info("Verify Accessories menu content");
		for (int i = 0; i < header.getModMenuSubItems().size(); i++) {
			header.getMotoModMenu().hoverOnElement();
			// get href sub accessories menu
			WbElement element = header.getModMenuSubItems().get(i);
			String href = element.getAttribute("href");
			element.click();
			String currentUrl = WbDriverManager.getCurrentUrl();
			verifyPageBasicContent();
			assertEquals(currentUrl, href);
			WbDriverManager.backPreviousPage();
		}
	}

	public void verifyFooterSupportContactLink() {
		Log.info("Go to Support page from footer and verify");
		footer.getSupportAndContactLink().click();
		verifyPageBasicContent();
		PageSupport pageSupport = new PageSupport();
		pageSupport.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyHeaderSupportLink() {
		Log.info("Go to Support page from header and verify");
		header.getSupportMenu().click();
		verifyPageBasicContent();
		PageSupport pageSupport = new PageSupport();
		pageSupport.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyPrivacyPolicyLink() {
		Log.info("Goto Privacy Policy page and verify");
		footer.getPrivacyPolicyLink().click();
		verifyPageBasicContent();
		PagePrivacyPolicy pagePrivacyPolicy = new PagePrivacyPolicy();
		pagePrivacyPolicy.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void verifyFacebookLink() {
		Log.info("Verify Facebook link");
		String link = footer.getSocialLinkFacebook().getAttribute("href");
		assertEquals(link, urlFacebookHomePage);
	}

	public void verifyTwitterLink() {
		Log.info("Verify Twitter link");
		String link = footer.getSocialLinkTwitter().getAttribute("href");
		assertEquals(link, urlTwitterHomePage);
	}

	public void verifyInstagramLink() {
		Log.info("Verify Instagram link");
		String link = footer.getSocialLinkInstagram().getAttribute("href");
		assertEquals(link, urlInstagramHomePage);
	}

	public void verifyPageBasicContent() {
		WbDriverManager.waitForPageLoad();
		verifyHeaderFooter();
	}

	public List<WbElement> getProductList() {
		String xpath = "//li[@class='item']/article/a[@href]";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public List<WbElement> getProductDisplayPriceList() {
		String xpath = "//article[@class='item-wrap  on-sale buyable' or @class ='item-wrap  buyable']";
		return WbDriverManager.findElements(By.xpath(xpath));
	}
	
	public List<WbElement> getImageProductDisplayPriceList() {
		String xpath = "//article[@class='item-wrap  on-sale buyable' or @class ='item-wrap  buyable']/a[@class='product-image']/span";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public List<WbElement> getProductNotDisplayPriceList() {
		String xpath = "//article[@class='item-wrap  not-buyable']";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public List<String> getNameProductList() {
		List<String> name = new ArrayList<>();
		String xpath = "./../div[@class='product-info']//a";
		for (WbElement element : getProductList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"Name Product");
			name.add(temp.getText());
		}
		return name;
	}

	public List<String> getNameProductBuyableList() {
		List<String> name = new ArrayList<>();
		String xpath = ".//div[@class='product-info']//a";
		for (WbElement element : getProductDisplayPriceList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"Name Product BuyAble");
			name.add(temp.getText());
		}
		return name;
	}

	public List<String> getPriceProductBuyableList() {
		List<String> price = new ArrayList<>();
		String xpath = ".//span[@class='regular-price' or contains(@id,'product-price')]";
		for (WbElement element : getProductDisplayPriceList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"Price Product BuyAble");
			price.add(temp.getText());
		}
		return price;
	}

	public List<String> getLinkProduct() {
		List<String> link = new ArrayList<>();
		String xpath = "./../div[@class='product-info']//a";
		for (WbElement element : getProductList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"link product");
			link.add(temp.getAttribute("href"));
		}
		return link;
	}

	public void clickProductAndVerify() {
		Log.info("Click all product and verify");
		for (int i = 0; i < getProductList().size(); i++) {
			// get href product
			WbElement element = getProductList().get(i);
			String href = element.getAttribute("href");
			element.scrollToElement();
			element.click();
			WbDriverManager.waitForPageLoad();
			String currentUrl = WbDriverManager.getCurrentUrl();
			// verify url
			assertEquals(currentUrl, href);
			// verify basic content
			verifyPageBasicContent();
			WbDriverManager.backPreviousPage();
		}
	}

	public void changeCountryAndVerify() {
		Log.info("Click flag country and verify");
		header.getCountrySwitchMenu().scrollToElement();
		header.getCountrySwitchMenu().click();
		WbDriverManager.waitForPageLoad();
		for (int i = 0; i < getCountryFlagList().size(); i++) {
			//get href
			TimeHelper.sleep(3000);
			WbElement element = getCountryFlagList().get(i);
			String href = element.getAttribute("href");
			element.click();
			WbDriverManager.waitForPageLoad();
			String currentUrl = WbDriverManager.getCurrentUrl();
			// verify url
			assertEquals(currentUrl, href);
			// verify basic content
			WbDriverManager.backPreviousPage();
			WbDriverManager.waitForPageLoad();
			header.getCountrySwitchMenu().click();
		}
	}

	public void verifyPriceProduct() {
		Log.info("Check price all product");

		// Check price is displayed on buyable product
		String xpath = "./div[@class='product-info']//div[@class='price-box']";
		for (WbElement element : getProductDisplayPriceList()) {
			assertTrue(element.findSubElement(By.xpath(xpath),"Price Product BuyAble").isDisplayed());
		}

		// Check price is not displayed on not-buyable product
		for (WbElement element : getProductNotDisplayPriceList()) {
			assertFalse(element.findSubElement(By.xpath(xpath),"Price Product Not-BuyAble").isDisplayed());
		}
	}

	public List<String> productList() {

		return null;
	}

	public void verifyListProduct() {
		Log.info("Check list product");
		Log.info(getNameProductList().toString());
		assertEquals(getNameProductList(), productList());
	}

	// Check product is buyable
	/**
	 * @return boolean true/false(true: buyable, false : not buyable)
	 * @param nameProduct : name of product
	 */
	public boolean isProductBuyAble(String nameProduct) {
		String temp = nameProduct + " ";
		String xpath = "//a[text()='" + nameProduct + "' or text()='" + temp + "']/../../..";
		WbElement element = new WbElement(By.xpath(xpath));
		String status = element.getAttribute("class");
		Log.info(status);
		if (status.contains("not-buyable")) {
			return false;
		} else
			return true;
	}


	public void addProductToCart() {
		Log.info("Add some product to Cart");
		for (int i = 0; i < getProductDisplayPriceList().size(); i++) {
			WbElement element = getImageProductDisplayPriceList().get(i);
			element.scrollToElement();
			element.click();
			WbDriverManager.waitForPageLoad();
			PageProductDetail pageProductDetail = new PageProductDetail();
			pageProductDetail.addProductToCart();
			WbDriverManager.backPreviousPage();
			WbDriverManager.backPreviousPage();
			WbDriverManager.waitForPageLoad();
		}
	}

	public void navigateCartPage() {
		WbDriverManager.waitForPageLoad();
		header.getCartIcon().click();
		WbDriverManager.waitForPageLoad();
		header.getViewShoppingCartLink().click();
	}

	public void navigateToParentSubCategoryMenuAndVeridy(String path) {
		Log.info("Navigate to parent page by clicking sub category menu");
		Log.info(path);
		String[] temp = path.split("/");
		List<String> subPath = new ArrayList<>();
		String url = baseURL;
		subPath.add(url);

		for (int i = 0; i < temp.length; i++) {
			url = url + temp[i] + "/";
			subPath.add(url);
		}
		for (int i = 0; i < (getParentPageSubMenuList().size() - 1); i++) {
			WbElement ele = getParentPageSubMenuList().get(i);
			ele.click();
//			TimeHelper.sleep(3000);
			WbDriverManager.waitForPageLoad();
			assertEquals(WbDriverManager.getCurrentUrl(), subPath.get(i));
			WbDriverManager.backPreviousPage();
		}
	}
	
	public int getCountProductCartPopUp() {
		String count =  header.getCartCount().getText();
		return Integer.parseInt(count);
	}
	
	public List<String> getListProductNameCartPopUp() {
		List<String> productName = new ArrayList<String>();
		header.getCartMenu().click();
		List<WbElement> productCartPopUp = header.getListProductCart();
		for(WbElement ele : productCartPopUp) {
			productName.add(ele.getText());
		}
		return productName;
	}
	
	public void navigateCheckoutPage() {
		header.getCartMenu().click();
		header.getCheckOutBtnCartPopUp().click();
		WbDriverManager.waitForPageLoad();
	}
	
	public void navigateCheckoutPageAndVerify() {
		navigateCheckoutPage();
		PageShippingInformation pageShippingInformation = new PageShippingInformation();
		pageShippingInformation.verifyUrl();
		WbDriverManager.backPreviousPage();
		
		//handle running on Firefox
		if(WbDriverManager.getCurrentUrl().contains("#shipping")) {
			WbDriverManager.backPreviousPage();
		}
	}
	
	public void navigateCheckouPaypalPageAndVerify() {
		header.getCartMenu().click();
		header.getCheckOutPaypalBtnCartPopUp().click();
		WbDriverManager.waitForPageLoad();
		String currentUrl = WbDriverManager.getCurrentUrl();
		assertTrue(currentUrl.contains(urlPayPal));
		WbDriverManager.backPreviousPage();
	}
	
	public void navigateCartAndVerify() {
		navigateCartPage();
		PageCart pageCart = new PageCart();
		//Check title and url 
		pageCart.getShoppingCartTitle().isDisplayed();
		pageCart.verifyUrl();
		WbDriverManager.backPreviousPage();
	}
	
	public void verifyLinkOnPoweredPopUp() {
		footer.getPoweredByLabel().click();
		WbDriverManager.waitForPageLoad();
		//Check link visit Ebuynow
		String hrefVisitEbuyNow = footer.getVisitEbuyNowOnPoweredByPopUp().getAttribute("href");
		assertEquals(hrefVisitEbuyNow, urlEbuyNowHomePage);
		
		//Check link goto Motorola
		String hrefMotorolaPage = footer.getGotoMotorolaOnPoweredByPopUp().getAttribute("href");
		assertEquals(hrefMotorolaPage, urlMotoralHomePage);
		
		//Check link Motorola Privacy Policy
		String hrefMotoralPrivacyPolicyPage = footer.getMotorolaPrivacyPolicyOnPoweredByPopUp().getAttribute("href");
		assertEquals(hrefMotoralPrivacyPolicyPage, urlMotoralPrivacyPolicyPage);
		
		//verify link Privacy Policy
		footer.getPrivacyPolicyOnPoweredByPopUp().click();
		PagePrivacyPolicy pagePrivacyPolicy = new PagePrivacyPolicy();
		WbDriverManager.waitForPageLoad();
		pagePrivacyPolicy.verifyUrl();
		WbDriverManager.backPreviousPage();
		
	}
	
	public void changeCountryFooterAndVerify() {
		Log.info("Click flag country at footer and verify");
		footer.getCountrySwitch().scrollToElement();
		footer.getCountrySwitch().click();
		WbDriverManager.waitForPageLoad();
		for (int i = 0; i < getCountryFlagList().size(); i++) {
			//get href
			WbElement element = getCountryFlagList().get(i);
			String href = element.getAttribute("href");
			element.click();
			WbDriverManager.waitForPageLoad();
			String currentUrl = WbDriverManager.getCurrentUrl();
			// verify url
			assertEquals(currentUrl, href);
			// verify basic content
			WbDriverManager.backPreviousPage();
			WbDriverManager.waitForPageLoad();
			footer.getCountrySwitch().click();
		}
	}

}
