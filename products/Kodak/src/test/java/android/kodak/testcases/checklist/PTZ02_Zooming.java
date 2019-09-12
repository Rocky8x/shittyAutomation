package android.kodak.testcases.checklist;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class PTZ02_Zooming extends TestBaseAndroid {

	ApiHelper api;
	Device device;

	@BeforeClass
	public void precondition() throws Exception {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 25, description = "Zoomming with zoom button in mini menu of streaming screen")
	public void zoomTest() {
		PageDashboard.selectDeviceToView(device.getName());

		PageStreamView.getMenuImage().click();
		PageStreamView.swipeUpMiniMenu();

		// Click Zoom+ four time then the Zoom- will appear
		for (int i = 0; i < 4; i++) {
			PageStreamView.getZoomInButton().click();
			TimeHelper.sleep(500);
		}
		PageStreamView.getZoomOutButton().click();
		PageStreamView.getZoomInButton().getWebElement();
	}

}
