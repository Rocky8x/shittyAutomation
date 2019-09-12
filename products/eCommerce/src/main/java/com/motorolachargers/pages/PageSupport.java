package com.motorolachargers.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageSupport extends PageBase {

	public PageSupport() {
		PATH = "support/";
	}

	public final String[] categories = { "USB-C", "Micro-USB" };

	public PageSupport(PageBase p) {
		PATH = "support/";
		this.baseURL = p.baseURL;
	}

	public WbElement getAricleSupportsTxb() {
		String id = "articles_search_input";
		return new WbElement(By.id(id), "Search Article Support TextBox");
	}

	public WbElement getSupportCategoriesItem(String name) {
		String xpath = "//li[contains(@class,'cat-power-charging')]//*[contains(text(),'" + name + "')]";
		return new WbElement(By.xpath(xpath), "Supoprt Categorie Menu");
	}

	public WbElement getSupportCategoriesMenu() {
		String xpath = "//*[contains(@class,\"category-list\")]//i[@class='fa fa-angle-down']";
		return new WbElement(By.xpath(xpath), "Supoprt Categorie Menu");
	}

	public WbElement getViewAllBtn() {
		String xpath = "//a[@class='showAll']";
		return new WbElement(By.xpath(xpath), "Show All Button");
	}

	public WbElement getSupportProductItem(String name) {
		String xpath = "//a[@class='item-area' and @data-name = '" + name + "']";
		return new WbElement(By.xpath(xpath), "Product:" + name);
	}

//	public List<WebElement> getSupportProductList() {
//		String xpath = "//a[@class='item-area']";
//		WbElement ele = new WbElement(By.xpath(xpath),"Get Product Item");
//		List<WebElement> ele1 = ele.findElements(By.xpath(xpath));
//		return ele1;
//	}

	public List<WbElement> getSupportProductList() {
		String xpath = "//a[@class='item-area']";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public List<String> getLinkProduct() {
		List<WbElement> ele = getSupportProductList();
		List<String> link = new ArrayList<>();
		for (WebElement element : ele) {
			link.add(element.getAttribute("href"));
		}
		return link;
	}

	public List<String> getNameProduct() {
		List<WbElement> ele = getSupportProductList();
		List<String> nameProduct = new ArrayList<>();
		for (WbElement element : ele) {
			nameProduct.add(element.getAttribute("data-name"));
		}
		return nameProduct;
	}

	public void clickSupportCategoriesMenu() {
		getSupportCategoriesMenu().clickByJavaScripts();
	}

	public void clickCategoriesItem(String name) {
		getSupportCategoriesItem(name).clickByJavaScripts();
	}

	public void navigateCategoriesItemAndVerify() {
		clickSupportCategoriesMenu();
		clickCategoriesItem(categories[0]);
		TimeHelper.sleep(2000);
		PageSupportUSBCPlug pageSupportUSBCPlug = new PageSupportUSBCPlug(this);
		pageSupportUSBCPlug.verifyUrl();
		WbDriverManager.backPreviousPage();
		TimeHelper.sleep(2000);
		clickSupportCategoriesMenu();
		clickCategoriesItem(categories[1]);
		TimeHelper.sleep(2000);
		PageSupportMicroUSBPlug pageSupportMicroUSBPlug = new PageSupportMicroUSBPlug(this);
		pageSupportMicroUSBPlug.verifyUrl();
	}

	public void clickViewAllBtn() {
		getViewAllBtn().clickByJavaScripts();
	}

	public void navigateSupportProductDetail(String name) {
		getSupportProductItem(name).click();
	}

}
