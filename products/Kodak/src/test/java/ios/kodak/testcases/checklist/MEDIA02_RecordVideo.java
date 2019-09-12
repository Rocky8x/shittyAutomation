package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGallery;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class MEDIA02_RecordVideo extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 23, description = "Verify that user can recording video")
	public void recordVideo() {
		
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
//		 Delete all video in gallery page if any
		PageDashboard.gotoGalleryPage();
		PageGallery.deleteAllVideo();
		Assert.assertTrue(PageGallery.verifyNumberCameraInGallery(0));
		
		// Go to stream page
		PageDashboard.gotoHomePage();
		PageDashboard.selectDeviceToView(device.getName());
		
		// Record video
		PageStreamView.clickMoreMenuIcon();
		PageStreamView.clickStartRecording(30);
		PageStreamView.clickStopRecording();
		
		// Go to gallery page and verify
		PageBase.clickBackButton();
		PageDashboard.gotoGalleryPage();
		Assert.assertTrue(PageGallery.verifyNumberCameraInGallery(1));
	}
}
