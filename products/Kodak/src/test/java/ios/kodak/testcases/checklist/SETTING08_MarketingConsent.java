package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.driver.DriverManager;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageProfiles;
import mobile.kodak.base.TestBaseIOS;

public class SETTING08_MarketingConsent extends TestBaseIOS{
	
	@Test(priority = 41, description = "Verify user can turn on/off marketing consent")
	public void verityUserCanOnOffMarketingConsent() {
		// Login app
		PageGetStart.checkAndSignin(c_username, c_password);
		// Disable marketing consent
		PageDashboard.gotoUserProfilePage();
		PageProfiles.disableMarketingConsent();
		// Kill app and open again
		DriverManager.getDriver().closeApp();
		DriverManager.createWebDriver(driverSetting);
		// Verify marketing consent switch status
		PageDashboard.gotoUserProfilePage();
		Assert.assertFalse(PageProfiles.verifyMarketingConsentEnable(), "Marketing consent switch should disable.");
		// Enable marketing consent and verify
		PageProfiles.enableMarketingConsent();
		Assert.assertTrue(PageProfiles.verifyMarketingConsentEnable(), "Marketing consent switch should enable.");
	}
}
