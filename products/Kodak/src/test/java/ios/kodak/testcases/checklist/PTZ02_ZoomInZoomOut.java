package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class PTZ02_ZoomInZoomOut extends TestBaseIOS{

	ApiHelper api;
	Device device;
	PageStreamView cameraStreamView;
	PageDashboard pageDashboard;

	@BeforeClass
	public void precondition() throws Exception {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 15, description = "Zoomming with zoom button in mini menu of streaming screen")
	public void zoomTest() {
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.selectDeviceToView(device.getName());

		// Swipe to zoom icon
		PageStreamView.clickMoreMenuIcon();
		PageStreamView.swipeUpMiniMenu();
		
		// Zoom in
		for(int i = 0; i < 4; i++) {
			PageStreamView.clickZoomIn();
		}
		// Verify zoom in
		Assert.assertNotEquals(PageStreamView.getZoomOutIcon().getWebElement(), null);
		
		// Zoom out and verify
		PageStreamView.clickZoomOut();
		Assert.assertNotEquals(PageStreamView.getZoomInIcon().getWebElement(), null);
		
	}
}
