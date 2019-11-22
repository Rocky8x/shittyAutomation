package motorola.tests.android;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.motorola.ap.android.Objects.PageDashboard;
import com.motorola.ap.android.Objects.PageHomeMenu;

import motorola.tests.TestBaseAndroid;
import rocky.automation.POM.DriverManager;

public class LOGIN07_RememberMe extends TestBaseAndroid {

	@Test
	public void RememberMe() {

		PageDashboard	pageDashboard	= new PageDashboard();
		String			currentUser		= pageDashboard.goToHomeMenu().getCurrentUsername();
		DriverManager.getDriver().quit();
		DriverManager.createMobileDriver(driverSetting);
		pageDashboard.setDriver(DriverManager.getDriver());
		PageHomeMenu pageHomeMenu = pageDashboard.goToHomeMenu();
		assertEquals(pageHomeMenu.getCurrentUsername(), currentUser);
	}
}
