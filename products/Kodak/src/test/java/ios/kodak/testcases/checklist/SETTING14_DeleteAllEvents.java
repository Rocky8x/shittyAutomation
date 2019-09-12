package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import ios.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseIOS;

public class SETTING14_DeleteAllEvents extends TestBaseIOS{

	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}
	
	@Test(priority = 49, description = "Delete all event")
	public void verifyUserCanDeleteAllEvent() {
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();
		PageDeviceSettings.deleteAllEvent();
		
		// Go to stream view
		PageBase.clickBackButton();
		PageDashboard.selectDeviceToView(device.getName());
		
		// Verify there are no event in stream view page
		Assert.assertTrue(PageStreamView.verifyNoEventAvailable(), "All event should deleted.");
		
		// Go to timeline page
		PageBase.clickBackButton();
		PageDashboard.gotoTimelinePage();
		
		// Verify all event in timeline was deteled
		PageTimeline.filterEventByDevice(device.getName());
		Assert.assertTrue(PageTimeline.vefifyNoEventInTimelinePage(), "All event should deleted.");
	}
}
