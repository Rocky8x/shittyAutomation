package motorola.tests.android;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorola.ap.android.Objects.DialogLoginError;
import com.motorola.ap.android.Objects.PageBase;
import com.motorola.ap.android.Objects.PageIntro;
import com.motorola.ap.android.Objects.PageSignUp;

import automation.common.PageObjects.AndroidFacebookOneLoginPage;
import motorola.tests.TestBaseAndroid;

public class LOGIN01_LoginFbAccountNotExisted extends TestBaseAndroid {

	@Test
	public void LoginFbAccountNotExisted() {

		String	facebookAccount		= "ebn_ben@restmail.net";
		String	facebookPassword	= "123123Aa";

		PageIntro					pageIntro		= new PageBase().gotoIntroPage();
		AndroidFacebookOneLoginPage	pageFbLogin		= pageIntro.goToFacebookLoginPage();
		pageFbLogin.doLogin(facebookAccount, facebookPassword);
		DialogLoginError dialogLoginError = pageIntro.loginErroDialog();
		dialogLoginError.verifyDialogDontHaveAccountWithUs();
		PageSignUp pageSignUp = dialogLoginError.goToPageSignUp();
		assertTrue(pageSignUp.getEmailText().equals(facebookAccount));
		pageSignUp.goBack();
	}
}
