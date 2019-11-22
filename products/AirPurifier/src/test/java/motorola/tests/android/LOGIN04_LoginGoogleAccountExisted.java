package motorola.tests.android;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorola.ap.android.Objects.PageBase;
import com.motorola.ap.android.Objects.PageDashboard;
import com.motorola.ap.android.Objects.PageIntro;

import automation.common.PageObjects.AndroidGoogleOneLoginPage;
import motorola.tests.TestBaseAndroid;

public class LOGIN04_LoginGoogleAccountExisted extends TestBaseAndroid {

	String	gmailAccount	= "qa0855@gmail.com";
	String	gmailPassword	= "Cinatic123";

	@Test
	public void LoginGoogleAccountExisted() {

		PageIntro					pageIntro		= new PageBase().gotoIntroPage();
		AndroidGoogleOneLoginPage	pageGoogleLogin	= pageIntro.goToGoogleLoginPage();
		pageGoogleLogin.doLogin(gmailAccount, gmailPassword);

		assertTrue(new PageDashboard().isDisplay());
	}
}
