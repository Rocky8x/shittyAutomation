package com.motorolaintl.tests;

import org.testng.annotations.Test;
import com.motorolaintl.pages.PageHome;

public class HOME01_NavigationTest extends TestBaseMotoIntl {

	PageHome pageHome = new PageHome();

	@Test()
	public void navigationTest() throws Exception {
		pageHome.clickProductAndVerify();
	}
}
