package motorola.tests.android;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.motorola.ap.android.Objects.PageDashboard;
import com.motorola.ap.android.Objects.PageLogin;

import motorola.tests.TestBaseAndroid;

public class LOGIN06_ClearPasswdWhenLogout extends TestBaseAndroid {

	@Test
	public void ClearPasswdWhenLogout() {

		PageDashboard	pageDashboard	= new PageDashboard();
		PageLogin		pageLogin		= pageDashboard.goToHomeMenu().doSignOut();

		assertTrue(pageLogin.getPasswordText().equals("Password"));
	}
}
