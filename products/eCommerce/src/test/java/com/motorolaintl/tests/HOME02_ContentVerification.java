package com.motorolaintl.tests;

import org.testng.annotations.Test;
import com.motorolaintl.pages.PageHome;

public class HOME02_ContentVerification extends TestBaseMotoIntl {

	PageHome pageHome = new PageHome();

	@Test()
	public void contentVerificationTest() throws Exception {
		pageHome.verifyPageBasicContent();
		pageHome.verifyListProduct();
		pageHome.verifyPriceProduct();
		pageHome.clickProductAndVerify();
	}
}
