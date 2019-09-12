package com.kodaksmarthome.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.TimeHelper;
import com.cinatic.log.Log;
import com.ebn.automation.core.WbDriverManager;
import com.kodaksmarthome.pages.GroupHeader;
import com.kodaksmarthome.pages.PageBase;
import com.kodaksmarthome.pages.PageHome;

public class CommonNavigationTests extends TestBaseKodak {

	Class<?>		pageClass;
	Object			pageObj;
	final String	testData	= "pageListUsOnly";

	Object[] pagesToTest = new Object[] {
			"com.kodaksmarthome.pages.PageHome",
			"com.kodaksmarthome.pages.PageCategoryBabyMonitors",
			"com.kodaksmarthome.pages.PageCategorySecurityCameras",
			"com.kodaksmarthome.pages.PageLogin",
			"com.kodaksmarthome.pages.PageForgotPassword",
			"com.kodaksmarthome.pages.PageSignUp",
			"com.kodaksmarthome.pages.PagePrivacyPolicy",
			"com.kodaksmarthome.pages.PageCart"
	};

	@DataProvider(name = "pageListUsOnly")
	public Object[][] pageListUsOnly() {

		PageBase	basePage	= new PageBase();
		Object[][]	data		= new Object[pagesToTest.length][2];

		int i = 0;
		for (Object page : pagesToTest) {
			data[i][0]	= "USA";
			data[i][1]	= page;
			i++;
		}

		return data;
	}

	@DataProvider(name = "pageListAllCountry")
	public Object[][] pageListAllCountry() {

		PageBase	basePage	= new PageBase();
		Object[][]	data		= new Object[pagesToTest.length
				* basePage.pathPerLang.keySet().size()][2];

		int i = 0;
		for (String country : basePage.pathPerLang.keySet()) {
			for (Object page : pagesToTest) {
				data[i][0]	= country;
				data[i][1]	= page;
				i++;
			}
		}
		return data;
	}

	@BeforeMethod
	public void prepare(Object[] testArgs) throws Exception {

		String		country			= (String) testArgs[0];
		String		className		= (String) testArgs[1];
		PageHome	homepage		= new PageHome();

		Log.info("Navigation test: " + testArgs.toString());
		pageClass = Class.forName(className);

		if (!country.equals("USA")) {
			homepage.header.getFlagStoreIcon().click();
			TimeHelper.sleep(500);
			homepage.getCountryByName(country).click();
		}

		pageObj = pageClass.newInstance();
		if (country.equals("Australia") || country.equals("Russia")) {
			GroupHeader h = new GroupHeader();
			h.noCart = true;
			pageClass.getField("header").set(pageObj, h);
		}

		pageClass.getField("baseURL").set(pageObj, WbDriverManager.getCurrentUrl());
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickMainLogo(String country, String page) throws Exception {

		pageClass.getMethod("clickMainLogoAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickBabyMonitorLink(String country, String page) throws Exception {

		pageClass.getMethod("clickBabyMonitorLinkAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickSecurityCameraLink(String country, String page) throws Exception {

		pageClass.getMethod("clickSecurityCameraLinkAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickSupportLink(String country, String page) throws Exception {

		pageClass.getMethod("clickSupportLinkAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickCloudPortalLink(String country, String page) throws Exception {

		pageClass.getMethod("clickCloudPortalLinkAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickCartIcon(String country, String page) throws Exception {

		pageClass.getMethod("clickCartIconAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickAccountIcon(String country, String page) throws Exception {

		pageClass.getMethod("clickAccountIconAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickAboutUsLink(String country, String page) throws Exception {

		pageClass.getMethod("clickAboutUsLinkAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickSupportLinkInFooter(String country, String page) throws Exception {

		pageClass.getMethod("clickSupportLinkInFooter").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}

	@Test(dataProvider = testData)
	public void clickRegisterProductLink(String country, String page) throws Exception {

		pageClass.getMethod("clickRegisterProductLinkAndVerify").invoke(pageObj);
		pageClass.getMethod("openPage").invoke(pageObj);
	}
}
