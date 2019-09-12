package ios.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceOrder;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseIOS;

public class SETTING09_DeviceOrder extends TestBaseIOS{

	@Test(priority = 42, description = "Verify device order")
	public void verifyDeviceOrder() {
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Get list device in dashboard and verify
		List<String> listCameBefore = PageDashboard.getListDeviceNameInDashboard();
		Assert.assertTrue(listCameBefore.size() > 1, "Not enought camera for this test case. Expected > 1, but found: " + listCameBefore.size());
		
		// Go to Device Order page
		PageDashboard.gotoDeviceOrderPage();
		Assert.assertEquals(PageDeviceOrder.getListDeviceOrder(), listCameBefore, "The order of device not match.");
	
		// Change device order
		PageDeviceOrder.changeDeviceOrder();
		
		// Get list camera after change
		List<String> listCameraAfter = PageDeviceOrder.getListDeviceOrder();
		Assert.assertNotEquals(listCameBefore, listCameraAfter, "The order of device should change.");
		
		// Back to dashboard page
		PageBase.clickBackButton();
		PageDashboard.clickHomeButton();
		
		// Verify the order of device in dashboard after change
		Assert.assertEquals(PageDashboard.getListDeviceNameInDashboard(), listCameraAfter, "The order of device in dashboard should match with order in device order page.");
		
		// Log out - login again and verify
		PageGetStart.gotoSigninPage();
		PageLogin.loginApp(c_username, c_password);
		PageDashboard.isDisplayed();
		Assert.assertEquals(PageDashboard.getListDeviceNameInDashboard(), listCameraAfter, "The order of device in dashboard should match with order in device order page.");
	}
}
