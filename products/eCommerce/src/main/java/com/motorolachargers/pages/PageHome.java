package com.motorolachargers.pages;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.TimeHelper;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbElement;
import com.ebn.automation.core.WbDriverManager;

public class PageHome extends PageBase {

	public PageHome() {
		PATH = "";
	}

	public PageHome(PageBase p) {
		this.baseURL = p.baseURL;
		PATH = "";
	}

	// Product list on page United States
	public final List<String> product = Arrays.asList("Motorola TurboPower 15 USB-C Wall Charger",
			"Motorola TurboPower 15+ Wall Charger + USB-C Data Cable",
			"Motorola TurboPower 15+ Wall Charger + Micro-USB Data Cable",
			"Motorola TurboPower 15 Wall Charger + Micro-USB Data Cable",
			"Motorola USB Rapid Charger + Micro-USB Data Cable", "Motorola USB Wall Charger + Micro-USB Data Cable",
			"Motorola TurboPower 15 USB-C Car Charger", "Motorola TurboPower 25 Micro-USB Car Charger",
			"Motorola TurboPower 15 Car Charger + Micro-USB Data Cable",
			"Motorola Data/Charging Cable USB-A to USB-C — Black",
			"Motorola Data/Charging Cable USB-A to Micro-USB — Black",
			"Motorola Data/Charging Cable USB-A to Micro-USB — White");

	public List<WbElement> getProductList() {
		String xpath = "//h3[@class='product-name']";
		return WbDriverManager.findElements(By.xpath(xpath));
	}

	public List<String> getLinkProduct() {
		List<String> link = new ArrayList<>();
		String xpath = "./../..";
		for (WbElement element : getProductList()) {
			WebElement temp = element.findSubElement(By.xpath(xpath),"product link");
			link.add(temp.getAttribute("href"));
		}
		return link;
	}

	public List<String> getNameProductList() {
		List<String> name = new ArrayList<>();
		for (WebElement element : getProductList()) {
			name.add(element.getText());
		}
		return name;
	}
	
	public void clickProductAndVerify() {
		Log.info("Click all product and verify");
		for (int i = 0; i < getProductList().size(); i++) {
			// get href product
			WbElement element = getProductList().get(i);
			String href = getLinkProduct().get(i);
			element.clickByJavaScripts();
			TimeHelper.sleep(1000);
			WbDriverManager.waitForPageLoad();
			String currentUrl = WbDriverManager.getCurrentUrl();
			// verify url
			assertEquals(currentUrl, href);
			WbDriverManager.backPreviousPage();
		}
	}
}
