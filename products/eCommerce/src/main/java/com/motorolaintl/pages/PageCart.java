package com.motorolaintl.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageCart extends PageBase {

	public final String urlPaypal = "https://www.paypal.com/";

	public PageCart() {
		PATH = "checkout/cart/";
	}

	public WbElement getProceedToCheckoutBtn() {
		String xpath = "//button[@class='button btn-proceed-checkout btn-checkout']/span/span";
		return new WbElement(By.xpath(xpath),"Proceed To Check Out Button");
	}

	public WbElement getCheckoutPaypalBtn() {
		String xpath = "//a[@class='btn btn-paypal-checkout']";
		return new WbElement(By.xpath(xpath),"Check out PayPal Button");
	}

	public List<WbElement> getProductList() {
		String xpath = "//table[@id='shopping-cart-table']/tbody/tr";
		return WbDriverManager.findElements(By.xpath(xpath));
	}
	
	public List<WbElement> getElementProductList() {
		List<WbElement> productList = new ArrayList<>();
		String xpath = "./td[@class='product-cart-info']/h2[@class='product-name']";
		for (WbElement element : getProductList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"Name Product");
			productList.add(temp);
		}
		return  productList;
	}

	public List<String> getNameProductList() {
		List<String> name = new ArrayList<>();
		for (WbElement element : getElementProductList()) {
			name.add(element.getText());
		}
		return name;
	}

	public List<String> getPriceProductList() {
		List<String> price = new ArrayList<>();
		String xpath = "./td[@class='product-cart-price']/span[@class='cart-price']/span";
		for (WbElement element : getProductList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"Price Product");
			price.add(temp.getText());
		}
		return price;
	}
	
	public List<WbElement> getQuantityProductBoxList() {
		List<WbElement> quantity = new ArrayList<>();
		String xpath = "./td[@class='product-cart-actions']/input";
		for (WbElement element : getProductList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"Quantity Product");
			quantity.add(temp);
		}
		return quantity;
	}

	public List<String> getValueQuantityProductBoxList() {
		List<String> value = new ArrayList<String>();
		for (WbElement element : getQuantityProductBoxList()) {
			value.add(element.getAttribute("value"));
		}
		return value;
	}

	public List<String> getSubTotalPriceList() {
		List<String> price = new ArrayList<>();
		String xpath = "./td[@class='product-cart-total']/span[@class='cart-price']/span";
		for (WbElement element : getProductList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath), "SubTotal Price");
			price.add(temp.getText());
		}
		return price;
	}

	public List<WbElement> getTrashProductBtnList() {
		List<WbElement> trashBtn = new ArrayList<>();
		String xpath = "./td[@class='a-center product-cart-remove']/a[@class='btn-remove btn-remove2']";
		for (WbElement element : getProductList()) {
			WbElement temp = element.findSubElement(By.xpath(xpath),"Trash Product Btn");
			trashBtn.add(temp);
		}
		return trashBtn;
	}

	public WbElement getShippingSelection() {
		String xpath = "//div[@class='shipping interactive']";
		return new WbElement(By.xpath(xpath),"Shipping Selection");
	}

	public WbElement getSetShippingBtn() {
		String xpath = "//span[text()='Set Shipping']";
		return new WbElement(By.xpath(xpath),"Set Shipping Button");
	}

	public WbElement getCouponTbx() {
		String id = "coupon_code";
		return new WbElement(By.id(id),"Promo code Text box");
	}

	public WbElement getPromoCodeToggle() {
		String xpath = "//a[@class='toggle' and contains(text(),'I have a promo code')]";
		return new WbElement(By.xpath(xpath),"Link Promo Code Txt");
	}

	public WbElement getPriceSubTotal() {
		String xpath = "//td[contains(text(),'Subtotal')]/..//span[@class='price']";
		return new WbElement(By.xpath(xpath),"Sub Total price");
	}

	public WbElement getPriceShipping() {
		String xpath = "//td[contains(text(),'Shipping')]/..//span[@class='price']";
		return new WbElement(By.xpath(xpath),"Shipping price");
	}

	public WbElement getPriceAmountPayable() {
		String xpath = "//*[.='Amount Payable']/../..//span[@class='price']";
		return new WbElement(By.xpath(xpath));
	}
	
	public WbElement getAddToCartProductSuggestBtn() {
		String xpath = "//div[@class='cart-footer']//button[@class='button btn-cart']";
		return new WbElement(By.xpath(xpath));
	}

	public void verifyTrashProductIsDisplay() {
		Log.info("Verify trash product icon is display");
		for (WebElement element : getTrashProductBtnList()) {
			element.isDisplayed();
		}
	}

	public void clickProceedCheckoutBtn() {
		getProceedToCheckoutBtn().click();
	}
	

	public void verifyPromoCodeIsDisplay() {
		Log.info("Verify promotion code is display");
		getPromoCodeToggle().click();
		WbDriverManager.waitForPageLoad();
		getCouponTbx().isDisplayed();
	}

	public void clickProceedToCheckoutBtnAndVerify() {
		Log.info("Click Proceed To Check out And Verify");
		getProceedToCheckoutBtn().click();
		PageShippingInformation pageShippingInformation = new PageShippingInformation();
		WbDriverManager.waitForPageLoad();
		pageShippingInformation.verifyUrl();
		WbDriverManager.backPreviousPage();
	}

	public void clickCheckoutPaypalBtnAndVerify() {
		Log.info("Click Proceed To Check out And Verify");
		getCheckoutPaypalBtn().click();
		WbDriverManager.waitForPageLoad();
		assertTrue(WbDriverManager.getCurrentUrl().contains(urlPaypal));
		WbDriverManager.backPreviousPage();
	}

	public void clickShowShippingSelectionAndVerfiy() {
		Log.info("Click Proceed To Check out And Verify");
		getShippingSelection().click();
		WbDriverManager.waitForPageLoad();
		assertTrue(getSetShippingBtn().isDisplayed());
	}
	
	public String getPriceSubTotalString() {
		WbElement ele = getPriceSubTotal();
		return ele.getText();
	}

	public String getPriceShippingString() {
		WbElement ele = getPriceShipping();
		return ele.getText();
	}

	public String getPriceAmountPayableString() {
		WbElement ele = getPriceAmountPayable();
		return ele.getText();
	}
	
	// Input value to quantity product box
	/**
	 * @param value : value to input Quantity product box
	 * @param position : 0,1,2,.... position product
	 */
	public void inputQuantity(int value, int position) {
		
		Log.info("input value " +value+ " into product "+position );
		WbElement ele = getQuantityProductBoxList().get(position);
		ele.clear();
		ele.sendKeys(String.valueOf(value));
		WbDriverManager.waitForPageLoad();
		TimeHelper.sleep(15000);
	}
	
	// Trash product out of cart
	/**
	 * @param position : 0,1,2,.... position product
	 */
	public void trashProductOutOfCart(int position) {
		
		Log.info("Trash product "+ position+" out of cart");
		WbElement ele = getTrashProductBtnList().get(position);
		ele.click();
		WbDriverManager.acceptAlert();
	}
	
	// Trash product out of cart
	/**
	 * @return FLoat price 
	 * @param stringPrice : 0,1,2,.... position product
	 */
	public Double convertPriceStringToDouble(String stringPrice) {
		DecimalFormat df = new DecimalFormat("#.00");
		String temp = stringPrice.replace("$", "");
		Double price = Double.parseDouble(temp);
		df.format(price);
		return price;
	}	
	
	public String getErrorMessage() {
		String xpath = "//li[@class='error-msg']/ul/li/span";
		WbElement ele = new WbElement(By.xpath(xpath));
		return ele.getText();
	}
	
	public void inputPromoteCode(String promoteCode) {
		
		getPromoCodeToggle().click();
		WbDriverManager.waitForPageLoad();
		getCouponTbx().sendKeys(promoteCode);
	}
	
	public void addProductSuggestToCart() {
		getAddToCartProductSuggestBtn().click();
	}
	
	public void addProductSuggestAndVerify() {
		
		String xpath = "./../..//a";
		WbElement ele = getAddToCartProductSuggestBtn().findSubElement(By.xpath(xpath), "link Suggest Product");
		String href = ele.getAttribute("href");
		addProductSuggestToCart();
		WbDriverManager.waitForPageLoad();
		String currentURL = WbDriverManager.getCurrentUrl();
		assertEquals(currentURL, href);
		
	}


}
