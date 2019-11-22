package motorola.tests.android;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.motorola.ap.android.Objects.PageDashboard;
import com.motorola.ap.android.Objects.PageIntro;
import com.motorola.ap.android.Objects.PageLogin;

import motorola.tests.TestBaseAndroid;
import net.restmail.EbnRestMailHelper;

public class SIGNUP01_EnforceEmailVerification extends TestBaseAndroid {

	String			firstname;
	String			lastname;
	String			username;
	String			email;
	String			passwd;
	PageIntro		pageIntro;
	PageLogin		pageLogin;
	PageDashboard	pageDashboard;

	@Test
	public void forceEmailVerification() {

		firstname		= "qa";
		lastname		= StringHelper.randomString("", 6);
		username		= firstname + "_" + lastname;
		email			= username + "@restmail.net";
		passwd			= StringHelper.randomStringMustHaveNumber("Aa", 6);
		pageLogin		= new PageLogin();
		pageDashboard	= new PageDashboard();
		pageIntro		= new PageIntro();
		
		if (pageDashboard.isDisplay()) { pageDashboard.goToHomeMenu().doSignOut().goBack(); }
		pageIntro = pageIntro.goToPageSignUp().doSignUp(username, firstname, lastname, email,
				passwd);

		pageIntro.goToPageLogin().doLogin(username, passwd);
		pageLogin.loginErroDialog().verifyLoginNotActiveAccount();

		EbnRestMailHelper restMailHelper = new EbnRestMailHelper(email);
		restMailHelper.confirmSignupEmail();

		pageDashboard = pageLogin.doLogin(username, passwd);
		assertTrue(pageDashboard.isDisplay());
	}
}
