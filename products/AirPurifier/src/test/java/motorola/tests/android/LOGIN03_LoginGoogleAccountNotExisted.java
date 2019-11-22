package motorola.tests.android;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorola.ap.android.Objects.DialogLoginError;
import com.motorola.ap.android.Objects.PageBase;
import com.motorola.ap.android.Objects.PageIntro;
import com.motorola.ap.android.Objects.PageSignUp;

import automation.common.PageObjects.AndroidGoogleOneLoginPage;
import motorola.tests.TestBaseAndroid;

public class LOGIN03_LoginGoogleAccountNotExisted extends TestBaseAndroid {

	String	gmailAccount	= "autoqatest01@gmail.com";
	String	gmailPassword	= "Cinatic123";

	@Test
	public void LoginGoogleAccountNotExisted() {

		PageIntro					pageIntro		= new PageBase().gotoIntroPage();
		AndroidGoogleOneLoginPage	pageGoogleLogin	= pageIntro.goToGoogleLoginPage();
		pageGoogleLogin.doLogin(gmailAccount, gmailPassword);

		DialogLoginError dialogLoginError = pageIntro.loginErroDialog();
		dialogLoginError.verifyDialogDontHaveAccountWithUs();
		PageSignUp pageSignUp = dialogLoginError.goToPageSignUp();
		assertTrue(pageSignUp.getEmailText().equals(gmailAccount));
		pageSignUp.goBack();
	}

}
