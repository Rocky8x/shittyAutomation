package android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.log.Log;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageDevicesOrder;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING09_DevicesOrder extends TestBaseAndroid {
	
	@Test(priority = 45, description = "Verify devices order")
	public void verifyChangeDevicesOrder() {
//		PageLogin loginPage = page.goToSigninPage();
//		pageDashboard = loginPage.loginApp(c_username, c_password);
//		dashBoardPage = new PageDashboard();
		PageGetStart.checkAndSignin(c_username, c_password);
		List<String> lstCamInDashBoard = PageDashboard.getListCameraNameInDashBoard();
		Assert.assertTrue(lstCamInDashBoard.size() > 1, "Not enought camera for this test case. Expected > 1, actual: " + lstCamInDashBoard.size());
		// Go to Device order page
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.clickOnHomeDashboardTextView();
		PageHomeMenu.gotoDevicesOrderpage();
		
		List<String> lstDeviceBefore = PageDevicesOrder.getListDeviceName();
		Assert.assertEquals(lstCamInDashBoard, lstDeviceBefore, "The order of device not match.");
		//Change devices order
		PageDevicesOrder.changeDevicesOrder();
		List<String> lstDeviceAfter = PageDevicesOrder.getListDeviceName();
		Assert.assertNotEquals(lstDeviceBefore, lstDeviceAfter, "Devices order should change.");
		// Back to dash board page and verify
		PageBase.exitPage();
		lstCamInDashBoard = PageDashboard.getListCameraNameInDashBoard();
		Assert.assertEquals(lstCamInDashBoard, lstDeviceAfter, "The order of device in dashboard should match with order in device order page.");
		
		Log.info("Logout and login again to verify");
		PageGetStart.goToSigninPage();
		PageLogin.loginApp(c_username, c_password);
		lstCamInDashBoard = PageDashboard.getListCameraNameInDashBoard();
		Assert.assertEquals(lstCamInDashBoard, lstDeviceAfter, "The order of device in dashboard should match with order in device order page.");
	}
}
