package com.motorolaintl.pages;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.cinatic.log.Log;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageProductDetail extends PageBase {

	public PageProductDetail() {
		PATH = "";
	}

	public String getTitleProductName() {
		String xpath = "//div[@class='product-name']/span";
		WbElement e = new WbElement(By.xpath(xpath), "Title Product");
		return e.getText();
	}

	public WbElement getDescriptionProduct() {
		String xpath = "//div[@class='product-shop']/div[@class='short-description']";
		return new WbElement(By.xpath(xpath),"Description Product");
	}

	public WbElement getImageProductCurrentShow() {
		String xpath = "//div[@class='product-image-gallery slick-initialized slick-slider']//div[@class='img slick-slide slick-current slick-active']//img";
		return new WbElement(By.xpath(xpath),"Image Product Current Show");
	}

	public WbElement getImagePreviousArrow() {
		String xpath = "//button[@class='slick-prev slick-arrow']";
		return new WbElement(By.xpath(xpath),"Previous Arrow");
	}

	public WbElement getImageNextArrow() {
		String xpath = "//button[@class='slick-next slick-arrow']";
		return new WbElement(By.xpath(xpath),"Next Arrow");
	}

	public List<WbElement> getImageProductList() {
		String xpath = "//div[@class='product-image-gallery product-thumbs slick-initialized slick-slider']//div[@class='bg-img']";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public WbElement getAddToCartBtn() {
		String xpath = "//button[@class='button btn-cart']";
		return new WbElement(By.xpath(xpath),"Add To Cart button");
	}
	
	public WbElement getMinusQuantityBtn() {
		String xpath = "//span[@class='qty-btn qty-minus']";
		return new WbElement(By.xpath(xpath),"Minus Quantity Btn");
	}
	
	public WbElement getPlusQuantityBtn() {
		String xpath = "//span[@class='qty-btn qty-plus']";
		return new WbElement(By.xpath(xpath),"Plus Quantity Btn");
	}
	
	public WbElement getQuantityTbx() {
		String xpath = "//input[@class='input-text qty']";
		return new WbElement(By.xpath(xpath),"Quantity Textbox");
	}

	public WbElement getOutOfStockStatus() {
		String xpath = "//div[@class='product-shop']//span[text()='Out of stock']";
		return new WbElement(By.xpath(xpath),"Out Of Stock Status");
	}

	public WbElement getVerizonHiddenBtn() {
		String xpath = "//div[@class='product-shop']//a[@class='top-Verizon-affiliate']";
		return new WbElement(By.xpath(xpath),"Verizon Hidden Button");
	}
	
	public WbElement getErrorMessageTxt() {
		String xpath = "//li[@class='error-msg']/ul/li";
		return new WbElement(By.xpath(xpath),"Error Message");
	}

	public List<String> getSrcImageProductList() {
		List<String> srcImageList = new ArrayList<>();
		for (WbElement elementImage : getImageProductList()) {
			String style = elementImage.getAttribute("style");
			String[] split = style.split("\\\"");
			srcImageList.add(split[1]);
		}
		return srcImageList;
	}

	public void chageImageProductAndVerify() {
		Log.info("Change image product and Verify");
		// Change by click arrow button and verify
		List<String> srcImageList = getSrcImageProductList();
		Log.info(srcImageList.toString());
		for (int i = 0; i < srcImageList.size(); i++) {
			// click next arrow
			getImageNextArrow().click();
			WbDriverManager.waitForPageLoad();
			if (i == (srcImageList.size() - 1)) {
				assertEquals(getImageProductCurrentShow().getAttribute("src"), srcImageList.get(0));
			} else {
				assertEquals(getImageProductCurrentShow().getAttribute("src"), srcImageList.get(i + 1));

			}
		}

		for (int i = 0; i < srcImageList.size(); i++) {
			// click previous arrow
			getImagePreviousArrow().click();
			WbDriverManager.waitForPageLoad();
			assertEquals(getImageProductCurrentShow().getAttribute("src"),
					srcImageList.get(srcImageList.size() - 1 - i));
		}

		// Change by click image below and verify
		for (int i = 0; i < srcImageList.size(); i++) {
			// click next arrow
			getImageProductList().get(i).click();
			WbDriverManager.waitForPageLoad();
			assertEquals(getImageProductCurrentShow().getAttribute("src"), srcImageList.get(i));
		}
	}

	public void addProductToCart() {
		getAddToCartBtn().click();
//		TimeHelper.sleep(3000);
		WbDriverManager.waitForPageLoad();
	}
	/**
	 * 
	 * @param time : the time to click
	 */
	public void clickMinusQuantityBtn(int time) {
		for(int i = 0; i < time; i++) {
			getMinusQuantityBtn().click();
		}
	}
	
	/**
	 * 
	 * @param time : the time to click
	 */
	public void clickPlusQuantityBtn(int time) {
		for(int i = 0 ; i < time; i++) {
			getPlusQuantityBtn().click();
		}
	}
	
	public void inputQuantity(String number) {
	
		getQuantityTbx().clear();
		getQuantityTbx().sendKeys(number);
	}
	
	public String getErrorMessage() {
		return getErrorMessageTxt().getText();
	}
	
	public void clickOutOfStockLabelAndVerify() {
		String urlBefore = WbDriverManager.getCurrentUrl();
		getOutOfStockStatus().click();
		WbDriverManager.waitForPageLoad();
		String urlAfter = WbDriverManager.getCurrentUrl();
		assertEquals(urlAfter, urlBefore);
	}
	
	public void addProductToCartAndVerify() {
		
		addProductToCart();
		PageCart pageCart = new PageCart();
		pageCart.getShoppingCartTitle().isDisplayed();
		pageCart.verifyUrl();
	}

}
