package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import android.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING14_DeleteEvent extends TestBaseAndroid {
	Device device;
	
	@BeforeMethod
	public void Precondition() throws Exception{
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 40, description = "Delete all events")
	public void verifyDeleteAllEvents() {
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.openDeviceSetting(device.getName());
		//Delete all events
		PageCameraSetting.deleteAllEvents();
		// Verify empty event in page stream view
		PageBase.exitPage();
		PageDashboard.selectDeviceToView(device.getName());
		Assert.assertTrue(PageBase.verifyEmptyEvent(), "Empty event image should display.");
		// Verify empty event in time line page
		PageStreamView.exitPage();
		PageBase.gotoToTimelinePage();
		PageTimeline.filterByDeviceName(device.getName());
		Assert.assertTrue(PageBase.verifyEmptyEvent(), "Empty event image should display.");
	}	
}
