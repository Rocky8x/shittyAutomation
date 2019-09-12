package com.motorolaintl.pages;

import java.util.List;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class GroupHeader {
	public WbElement getMainLogo() {
		String xpath = "//span[@class='logo-img']";
		return new WbElement(By.xpath(xpath), "Main motorola logo");
	}

	public WbElement getMotoModMenu() {
		String xpath = "//a[contains(@class,'categ-338 level0 has-children')]";
		return new WbElement(By.xpath(xpath), "Moto mod menu");
	}

	public List<WbElement> getModMenuSubItems() {
		String xpath = "//a[contains(@class,'categ-338 level0 has-children')]/../ul/li/a";
		return WbDriverManager.findElements(By.xpath(xpath));
	}
	
	public WbElement getAccessoriesMenu() {
		String xpath = "//a[contains(@class,'categ-340 level0 has-children')]";
		return new WbElement(By.xpath(xpath), "Moto mod menu");
	}
	
	public List<WbElement> getAccessoriesMenuSubItems() {
		String xpath = "//a[contains(@class,'categ-340 level0 has-children')]/../ul/li/a";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public WbElement getSupportMenu() {
		String xpath = "//a[contains(@class,'categ-323 level0')]";
		return new WbElement(By.xpath(xpath), "Moto mod menu");
	}

	public WbElement getCartMenu() {
		String xpath = "//div[@class='header-minicart']/a/span[@class='icon']";
		return new WbElement(By.xpath(xpath), "Moto mod menu");
	}

	public WbElement getCountrySwitchMenu() {
		String xpath = "//header[@id='header']//div[@class='storeSwitcher toggle']/button/i";
		return new WbElement(By.xpath(xpath), "Country Switch menu");
	}

	public WbElement getBackToMotorolaTopLeftLink() {
		String xpath = "//div[@class='topbar-left']/div[@class='backToMotorola']/a";
		return new WbElement(By.xpath(xpath), "Motorala Top Left Link");
	}
	
	public WbElement getBackToMotorolaPhoneTopRightLink() {
		String xpath = "//div[@class='topbar-right']/div[@class='topBarRightInfo']/a";
		return new WbElement(By.xpath(xpath), "Motorala Phone Top Right Link");
	}

	public WbElement getCartIcon() {
		String xpath = "//a[contains(@class,'skip-cart')]/span[@class='icon']";
		return new WbElement(By.xpath(xpath), "Cart icon");
	}

	public WbElement getCartCount() {
		String xpath = "//a[contains(@class,'skip-cart')]/span[@class='count']";
		return new WbElement(By.xpath(xpath), "Cart count");
	}

	public WbElement getEmptyCartLabel() {
		String xpath = "//div[@class='minicart-wrapper']/p[@class='block-subtitle']";
		return new WbElement(By.xpath(xpath), "Empty cart label");
	}

	public WbElement getCartTitle() {
		String xpath = "//div[@class='minicart-wrapper']/p[@class='block-subtitle']";
		return new WbElement(By.xpath(xpath), "Cart title");
	}
	
	public List<WbElement> getListProductCart() {
		String xpath = "//ul[@id='cart-sidebar']//p[@class='product-name']/a";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public WbElement getCloseMinicartButton() {
		String xpath = "//div[@class='minicart-wrapper']/p[@class='block-subtitle']/a[contains(@class,'close')]";
		return new WbElement(By.xpath(xpath), "Close minicart button");
	}
	
	public WbElement getViewShoppingCartLink() {
		String xpath = "//div[@id='header-cart']//a[@class='cart-link']";
		return new WbElement(By.xpath(xpath),"Cart Link on Cart PopUp");
	}
	
	public WbElement getCheckOutBtnCartPopUp() {
		String xpath = "//div[@id='header-cart']//a[@class='button checkout-button']";
		return new WbElement(By.xpath(xpath),"Check out Btn on Cart PopUp");
	}
	
	public WbElement getCheckOutPaypalBtnCartPopUp() {
		String xpath = "//div[@id='header-cart']//img[@title='Checkout with PayPal']";
		return new WbElement(By.xpath(xpath),"Check out Btn on Cart PopUp");
	}
}
