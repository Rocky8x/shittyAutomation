package com.motorolachargers.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.cinatic.TimeHelper;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageProductDetail extends PageBase {

	String urlAmazon = "https://www.amazon.";

	public PageProductDetail() {
		PATH = "";
	}

	public PageProductDetail(PageBase p) {
		PATH = "";
		this.baseURL = p.baseURL;
	}

	public WbElement getAmazonCheckoutBtn() {
		String xpath = "//a[@class='top-amazon-affiliate']";
		return new WbElement(By.xpath(xpath), "Amazon icon button");
	}

	public WbElement getAddToCartBtn() {
		String xpath = "//button[contains(@class,'button btn-cart btn-default full-btn')]";
		return new WbElement(By.xpath(xpath), "Add To Cart Button");
	}

	public WbElement getContinueToCheckOutPopUp() {
		String xpath = "//a[@class='button btn-checkout']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Continue To Check Out PopUp txt");
	}

	public WbElement getKeepShoppingPopUp() {
		String xpath = "//a[@class='keep-shopping']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Keep Shopping PopUp txt");
	}

	public WbElement getSeeCartPopUp() {
		String xpath = "//a[@class='see-cart']";
		WbDriverManager.waitElement(By.xpath(xpath));
		return new WbElement(By.xpath(xpath), "Keep See Cart PopUp txt");
	}

	public String getProductName() {
		String xpath = "//div[@class='product-name hidden-sm hidden-xs']/h1";
		WbDriverManager.waitElement(By.xpath(xpath));
		WbElement e = new WbElement(By.xpath(xpath));
		return new String(e.getText());
	}

	public WbElement getProductPrice() {
		String xpath = "//span[@class='price']";
		return new WbElement(By.xpath(xpath));
	}

	public WbElement getProductStatus() {
		String xpath = "//div[@class='marketplaces-buttons no-prices']/h4";
		return new WbElement(By.xpath(xpath));
	}

	public WbElement getOutofStockProductStatus() {
		String xpath = "//div[@class='pdp-outofstock']/span";
		return new WbElement(By.xpath(xpath));
	}

	public WbElement getImageProductCurrentShow() {
		TimeHelper.sleep(2000);
		String xpath = "//div[@class='slick-track' ]//div[contains(@class,'img slick-slide ') and @tabindex='0']//div[@class='img-wrap']/img";
		return new WbElement(By.xpath(xpath));
	}

	public List<WbElement> getImageProductList() {
		String xpath = "//div[@class='slick-track' ]//div[contains(@class,'img slick-slide ') and @tabindex='0']//div[@class='bg-img']";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public void verifyBasicContent() {
		TimeHelper.sleep(2000);
		verifyHeader();
		verifyFooter();
	}

	public void clickToAmazonPage() {
		getAmazonCheckoutBtn().click();

	}

	public void addToCartAndCheckOut() {
		getAddToCartBtn().click();
		getContinueToCheckOutPopUp().click();
	}

	public void addToCartAndKeepShopping() {
		getAddToCartBtn().click();
		getKeepShoppingPopUp().click();

	}

	public void addToCartAndGotoCart() {
		getAddToCartBtn().click();
		getSeeCartPopUp().click();
	}

	public void navigateTest() {
		try {
			addToCartAndKeepShopping();
			WbDriverManager.waitForPageLoad();
			verifyUrl();
			addToCartAndGotoCart();
			WbDriverManager.waitForPageLoad();
			PageCart pageCart = new PageCart(this);
			pageCart.verifyUrl();
			WbDriverManager.backPreviousPage();
			addToCartAndCheckOut();
			WbDriverManager.waitForPageLoad();
			PageCheckOut pageCheckOut = new PageCheckOut(this);
			pageCheckOut.verifyUrl();
			WbDriverManager.backPreviousPage();
		} catch (Exception e1) {
			// Do nothing
		}
		try {
			clickToAmazonPage();
			WbDriverManager.switchToNewWindow();
			Assert.assertTrue(WbDriverManager.getCurrentUrl().contains(urlAmazon));
			WbDriverManager.closeCurrentTab();
		} catch (Exception e) {
			// Do nothing
		}

	}

	public void swipeAllProductImageAndVerify() {
		for (WbElement e : getImageProductList()) {
			e.click();
			String style = e.getAttribute("style");
			String src = getImageProductCurrentShow().getAttribute("src");
			String[] split = style.split("\\\"");
			assertEquals(split[1], src);
		}
	}

	public void verifyPriceAndStatus() {
		try {
			getProductPrice().isDisplayed();
			String status = getOutofStockProductStatus().getText();
			assertEquals(status, "Unavailable");
		} catch (Exception e) {
			try {
				getProductStatus().isDisplayed();
				String status = getProductStatus().getText();
				assertEquals(status, "In Stock");
				assertTrue(getAmazonCheckoutBtn().isDisplayed());
			} catch (Exception e1) {
				Assert.fail("Page is not display price and status product");
			}
		}
	}
}
	
	

