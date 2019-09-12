package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGallery;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class MEDIA06_DownloadMotionClipToGallery extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 43, description = "Download motion clip from stream view")
	public void downloadMotionClipFromStreamView() {
		
		// Sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Delete all video in gallery page if any
		PageDashboard.gotoGalleryPage();
		PageGallery.deleteAllVideo();
		Assert.assertTrue(PageGallery.verifyNumberCameraInGallery(0));
		
		// Go to stream page
		PageDashboard.gotoHomePage();
		PageDashboard.selectDeviceToView(device.getName());
		
		boolean rs = PageStreamView.getMotionVideo(PageStreamView.CLOUD);
		if(!rs) {
			Assert.fail("There is no motion clip.");
		}
		
		// Play motion clip
		PageStreamView.playCloudMotionClip();
		
		// Download video
		PageStreamView.clickDownloadButton();
		
		// Go to Gallery page and check
		PageBase.clickBackButton();
		TimeHelper.sleep(5000);
		PageBase.clickBackButton();
		PageDashboard.gotoGalleryPage();
		Assert.assertTrue(PageGallery.verifyNumberCameraInGallery(1));
	}
	
	@Test(priority = 44, description = "Download motion clip from timeline")
	public void downloadMotionClipFromTimeline() {
		
		// Sign in
		PageGetStart.checkAndSignin(c_username, c_password);
				
		// Delete all video in gallery page if any
		PageDashboard.gotoGalleryPage();
		PageGallery.deleteAllVideo();
		Assert.assertTrue(PageGallery.verifyNumberCameraInGallery(0));
		
		PageDashboard.gotoTimelinePage();
		
		boolean rs = PageStreamView.getMotionVideo(PageStreamView.CLOUD);
		if(!rs) {
			Assert.fail("There is no motion clip.");
		}
		
		// Play motion clip
		PageStreamView.playCloudMotionClip();
		
		// Download video
		PageStreamView.clickDownloadButton();
		
		// Go to Gallery page and check
		PageBase.clickBackButton();
		TimeHelper.sleep(5000);
		PageDashboard.gotoGalleryPage();
		Assert.assertTrue(PageGallery.verifyNumberCameraInGallery(1));
	}
}
