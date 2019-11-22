package motorola.tests.android;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorola.ap.android.Objects.PageBase;
import com.motorola.ap.android.Objects.PageDashboard;
import com.motorola.ap.android.Objects.PageIntro;

import automation.common.PageObjects.AndroidFacebookOneLoginPage;
import motorola.tests.TestBaseAndroid;

public class LOGIN02_LoginFbAccountExisted extends TestBaseAndroid{
	
	@Test
	public void LoginFbAccountExisted() {

		String	facebookAccount		= "qa4290@gmail.net";
		String	facebookPassword	= "123456aA";

		PageIntro					pageIntro		= new PageBase().gotoIntroPage();
		AndroidFacebookOneLoginPage	pageFbLogin		= pageIntro.goToFacebookLoginPage();
		pageFbLogin.doLogin(facebookAccount, facebookPassword);
		assertTrue(new PageDashboard().isDisplay());
	}
}
