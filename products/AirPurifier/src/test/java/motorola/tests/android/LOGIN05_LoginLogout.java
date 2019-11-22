package motorola.tests.android;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorola.ap.android.Objects.PageDashboard;
import com.motorola.ap.android.Objects.PageLogin;

import motorola.tests.TestBaseAndroid;

public class LOGIN05_LoginLogout extends TestBaseAndroid {

	@Test
	public void loginLogout() {

		PageDashboard	pageDashboard	= new PageDashboard();
		PageLogin		pageLogin		= pageDashboard.goToHomeMenu().doSignOut();
		pageLogin.doLogin(testParams.get("username"), testParams.get("password"));

		assertTrue(pageDashboard.isDisplay());
	}
}
