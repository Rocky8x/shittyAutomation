package com.kodaksmarthome.pages;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class GroupHeader{
	
	public boolean noCart = false;
	
	public WbElement getMainLogo() {
		String logo = "//*[@class='logo']";
		return new WbElement(By.xpath(logo), "Home Logo");
	}
	
	public WbElement getBabyMonitorLink() {
		String link = "//div[@id='header-nav']//li[contains(@class,'level0 nav-1 first')]";
		return new WbElement(By.xpath(link), "Baby Monitor link");
	}
	
	public WbElement getSecurityCameraLink() {
		String link = "//div[@id='header-nav']//li[contains(@class,'level0 nav-2')]";
		return new WbElement(By.xpath(link), "Security Camera");
	}
	
	public WbElement getSupportLink() {
		String link = "//div[@id='header-nav']//li[@class='level0 nav-3']";
		return new WbElement(By.xpath(link), "Support link");
	}
	
	public WbElement getCloudPortalLink() {
		String link = "//div[@id='header-nav']//li[@class='level0 nav-4 last']";
		return new WbElement(By.xpath(link), "Cloud Portal");
	}

	public WbElement getCartIcon() {
		String icon = "//header[@id='header']//div[@class='cart-link']/a";
		return new WbElement(By.xpath(icon), "Cart icon");
	}
	
	public WbElement getAccountIcon() {
		String icon = "//div[contains(@class,'header-right')]//a[contains(@href, 'login')]";
		return new WbElement(By.xpath(icon), "Account icon");
	}
	
	public WbElement getFlagStoreIcon() {
		String icon = "//div[contains(@class,'header-right')]//button";
		return new WbElement(By.xpath(icon), "Switching store icon");
	}
}
