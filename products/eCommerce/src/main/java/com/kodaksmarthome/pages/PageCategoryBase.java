package com.kodaksmarthome.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageCategoryBase extends PageBase {

	public PageCategoryBase() {}

	public PageCategoryBase(PageBase P) {

		this.baseURL	= P.baseURL;
		this.header		= P.header;
		this.footer		= P.footer;
	}

	public PageCategoryBase(String baseUrl, String path) {

		this.baseURL	= baseUrl;
		this.PATH		= path;
	}

	/*
	 * ELEMENTS
	 */

	public WbElement getPageTitle() {

		String title = "//span[@class='ksm kred']";
		return new WbElement(By.xpath(title), "Page title");
	}

	public WbElement getProductByName(String name) {

		String xpath = String.format(
				"//ol[contains(@class,'products-grid')]/li//h3[contains(text(),'%s') and not(substring-after(normalize-space(),'%s'))]",
				name, name);
		return new WbElement(By.xpath(xpath), "Product: " + name);
	}

	/**
	 * Only for PageCategory...
	 * 
	 * @return
	 */
	public List<WbElement> getProductList() {

		String xpath = "//ol[contains(@class,'products-grid')]/li//h3";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	/*
	 * ACTIONS
	 */

	public void verifyPageContent() {

		verifyUrl();
		verifyPageHeader();
		verifyPageTitleDisplay();
		verifyPageFooter();
		verifyAllProductsInfo();
	}

	public void verifyPageTitleDisplay() {

		assertTrue(getPageTitle().isDisplayed());
	}

	public void verifyProductLinks() {

		HashMap<String, WbElement> products = getDefinedProducts();

		for (String productName : products.keySet()) {
			getProductByName(productName).click();
			PageProduct productPage = new PageProduct();
			assertTrue(productPage.getProductPrice().isDisplayed());
			assertTrue(!productPage.getProductPrice().getText().equals(""));
			assertTrue(productPage.getProductName().getText().contains(productName));
			productPage.verifyPageHeader();
			productPage.verifyPageFooter();
			WbDriverManager.getDriver().navigate().back();
		}
	}

	public void verifyAllProductsInfo() {

		HashMap<String, WbElement> products = getDefinedProducts();

		for (String productName : products.keySet()) {
			WbElement	productEle	= getProductByName(productName);
			String		name		= productEle.getText();
			assertEquals(name, productName);
			assertTrue(getPriceOfProducElement(productEle).isDisplayed());
			assertTrue(getShortDescriptionOfProductElement(productEle).isDisplayed());
			assertTrue(getButtonOfProductElement(productEle).isDisplayed());
		}
	}

	public HashMap<String, WbElement> getDefinedProducts() {

		// leave empty on purpose
		return null;
	}

	public WebElement getPriceOfProducElement(WbElement e) {

		String relatedXpath = "./..//span[@class='price']";
		return e.findSubElement(By.xpath(relatedXpath), "Product price");
	}

	public WbElement getShortDescriptionOfProductElement(WbElement e) {

		String relatedXpath = "./../div[@class='product-short-desc']";
		return e.findSubElement(By.xpath(relatedXpath), "Product description");
	}

	public WbElement getButtonOfProductElement(WbElement e) {

		String relatedXpath = "./../../../div[@class='btn-area']/a";
		return e.findSubElement(By.xpath(relatedXpath), "Product button at the end");
	}

	/**
	 * @return first product that available for purchase
	 */
	public WbElement getFirstAvailProduct() {

		List<WbElement> products = getProductList();
		for (WbElement item : products) {
			if (getButtonOfProductElement(item).getText() == "Buy Now") { return item; }
		}
		return null;
	}
}
