package com.motorolaintl.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.cinatic.TimeHelper;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageOrderConfirmation extends PageBase{
	public PageOrderConfirmation() {
		PATH = "checkout/onepage/#review";
	}
	
	public WbElement getLogoIcon() {
		String xpath = "//a[@class='logo']";
		return new WbElement(By.xpath(xpath),"Logo Icon");
	}
	
	public List<WbElement> getProducList() {
		String xpath = "//table[@id='checkout-review-table']/tbody/tr";
		return WbDriverManager.findElements(By.xpath(xpath));
	}
	
	public List<String> getNameProduct(){
		Log.info("Get name product");
		List <String> name = new ArrayList<String>();
		String xpath = ".//div[@class='product-name']/h3";
		for(WbElement ele : getProducList()) {
			WbElement temp = ele.findSubElement(By.xpath(xpath),"Name Product");
			name.add(temp.getText());
		}
		return name;
	}
	
	public List<String> getPriceProduct(){
		Log.info("Get price product");
		List <String> price = new ArrayList<String>();
		String xpath = ".//td[@class='prod-price']/span[@class='cart-price']/span";
		for(WbElement ele : getProducList()) {
			WbElement temp = ele.findSubElement(By.xpath(xpath),"Price Product");
			price.add(temp.getText());
		}
		return price;
	}
	
	public List<String> getQuantityProduct(){
		Log.info("Get Quantiy product");
		List<String> quantity = new ArrayList<String>();
		String xpath = ".//td[@class='prod-qty a-center']";
		for(WbElement ele : getProducList()) {
			WbElement temp = ele.findSubElement(By.xpath(xpath),"Quantity Product");
			quantity.add(temp.getText());
		}
		return quantity;
	}
	
	public List<String> getPriceSubTotalProduct(){
		Log.info("Get price sub total");
		List<String> priceSubTotal = new ArrayList<String>();
		String xpath = ".//td[contains(@class,'prod-subtotal')]//span[@class='price']";
		for(WbElement ele : getProducList()) {
			WbElement temp = ele.findSubElement(By.xpath(xpath),"Sub Total Price");
			priceSubTotal.add(temp.getText());
		}
		return priceSubTotal;
	}
	
	public String getSubTotalPriceTotalTable() {
		Log.info("Get SubTotal Price Element on Total Table");
		String xpath = "(//td[@class='a-right'])[2]/span";
		WbElement ele = new WbElement(By.xpath(xpath));
		return ele.getText();
	}
	
	public String getShippingPriceTotalTable() {
		Log.info("Get Shipping Price Element on Total Table");
		String xpath = "//td[contains(text(),'Shipping')]/../td[@class='a-right']/span[@class='price']";
		WbElement ele = new WbElement(By.xpath(xpath));
		return ele.getText();
	}
	
	public String getTotalPrice() {
		Log.info("Get Total Price");
		String xpath = "//td[contains(.,'Grand Total')]/..//td[@class='a-right']//span[@class='price']";
		WbElement ele = new WbElement(By.xpath(xpath));
		return ele.getText();
	}
	
	public WbElement getPlaceOrderBtn() {
		String xpath = "//div[@id='review-buttons-container']/button[@class='button btn-checkout']";
		return new WbElement(By.xpath(xpath));
	}
	
	public void clickLogoIcon() {
		getLogoIcon().click();
	}
	
	public void clickLogoIconAndVerify() {
		clickLogoIcon();
		WbDriverManager.waitForPageLoad();
		TimeHelper.sleep(5000);
		PageCart pageCart = new PageCart();
		pageCart.verifyUrl();
		WbDriverManager.backPreviousPage();
	}
	
}
