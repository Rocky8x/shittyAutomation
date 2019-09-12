package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageLogin;
import android.kodak.object.PageProfile;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING08_MarketingConsent extends TestBaseAndroid {
	ApiHelper api;
		
	@Test(priority = 12, description = "Turn of Marketing Consent in Profile page")
	public void verifyUserCanTurnOnMarketingConsent() {
		try {
			PageGetStart.checkAndSignin(c_username, c_password);	
			Assert.assertEquals(PageDashboard.getAddDeviceBigBtn().isEnabled(), true);
			
			PageDashboard.gotoHomeMenuPage();
			PageHomeMenu.gotoHomeProfilePage();
			PageProfile.enableMarketingConsent();
			PageBase.exitPage();
			PageGetStart.goToSigninPage();
			PageLogin.loginApp(c_username, c_password);
			PageDashboard.gotoHomeMenuPage();
			PageHomeMenu.gotoHomeProfilePage();
			Assert.assertTrue(PageProfile.verifyMarketingConsentOn(), "Marketing Consent should be turn on");
		}catch (Exception e) {
			PageProfile.disableMarketingConsent();
			throw e;
		}	
		PageProfile.disableMarketingConsent();		
	}
}
