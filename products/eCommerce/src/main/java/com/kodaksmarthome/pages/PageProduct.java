package com.kodaksmarthome.pages;

import static org.testng.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class PageProduct extends PageBase {

	public WbElement getProductPrice() {

		String xpath = "//div[@class='product-shop']//span[contains(@id,'product-price')]";
		return new WbElement(By.xpath(xpath), "Product's price");
	}

	public WbElement getProductName() {

		String xpath = "//div[@class='product-shop']/div[@class='product-name']";
		return new WbElement(By.xpath(xpath), "Product's name");
	}

	public WbElement getProducDescription() {

		String xpath = "//div[@class='product-shop']/div[@class='short-description']/div";
		return new WbElement(By.xpath(xpath), "Product's description");
	}

	public WbElement getProductAvailability() {

		String xpath = "//div[contains(@class,'availability')]";
		return new WbElement(By.xpath(xpath), "Product availability status");
	}

	public WbElement getAddToBabyListBtn() {

		String xpath = "//div[@class='add-to-babylist']/a";
		return new WbElement(By.xpath(xpath), "Add to baby list button");
	}

	public WbElement getSlideImage() {

		String xpath = "//div[contains(@class, 'img slick-slide')]";
		return new WbElement(By.xpath(xpath), "Product slide image");
	}

	public WbElement getMinusButton() {

		String xpath = "//span[@class='qty-btn qty-minus']";
		return new WbElement(By.xpath(xpath), "- button");
	}

	public WbElement getPlusButton() {

		String xpath = "//span[@class='qty-btn qty-plus']";
		return new WbElement(By.xpath(xpath), "+ buton");
	}

	public WbElement getAddToCartButton() {

		String xpath = "//div[@class='add-to-cart-buttons']/button";
		return new WbElement(By.xpath(xpath), " Add to cart button");
	}

	public WbElement getAddQuantityTextbox() {

		String xpath = "//div[@class='add-to-cart-wrapper']//input";
		return new WbElement(By.xpath(xpath), "Quantity input");
	}

	public void verifyPageContent() throws Exception {

		verifyPageHeader();
		verifyPageFooter();
		assertTrue(getProductName().isDisplayed());
		assertTrue(getProducDescription().isDisplayed());
		assertTrue(getProductPrice().isDisplayed());
		try {
			assertTrue(getProductAvailability().isDisplayed());
		} catch (Exception e1) {
			try {
				assertTrue(getMinusButton().isDisplayed());
				assertTrue(getPlusButton().isDisplayed());
				assertTrue(getAddToCartButton().isDisplayed());
				assertTrue(getAddQuantityTextbox().isDisplayed());
			} catch (Exception e2) {
				throw new Exception("There's no 'Add to cart' nor 'Out of stock' buton");
			}
		}

		// the "Add to baby list" is button only shown on C2xx or C5xx model
		Pattern	regex			= Pattern.compile("^.*C[25].*$", Pattern.DOTALL);
		Matcher	regexMatcher	= regex.matcher(getProductName().getText());
		if (regexMatcher.find()) {
			try {
				getAddToBabyListBtn().isDisplayed();
			} catch (Exception e) {

				NoSuchElementException ne = new NoSuchElementException(
						"Cannot find 'Add to babylist button' on " + getProductName().getText());
				
				ne.setStackTrace(e.getStackTrace());
				throw ne;
			}
		}

	}
}
