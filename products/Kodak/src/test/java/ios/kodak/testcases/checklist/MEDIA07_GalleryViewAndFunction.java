package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageEmail;
import ios.kodak.object.PageGallery;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class MEDIA07_GalleryViewAndFunction extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 50, description = "Verify that user can select video in gallery")
	public void selectFunction() {
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to gallery tab
		PageDashboard.gotoGalleryPage();
		int numberVideo = PageGallery.getNumberVideoInGallery();
		
		// If there is no video, back to stream view and record videos
		if (numberVideo < 2) {
			recordVideos(2);
		}
		numberVideo = PageGallery.getNumberVideoInGallery();
		// Verify user can select single video
		PageGallery.selectVideos(1);
		Assert.assertTrue(PageGallery.verifyNumberVideoSelected(1));
		
		// Verify user can deselect video
		PageGallery.deSelectVideos(1);
		Assert.assertTrue(PageGallery.verifyNumberVideoSelected(0));
		PageBase.clickCancelButton();
		
		// Verify user can select all video
		PageGallery.selectAllVideo();
		Assert.assertTrue(PageGallery.verifyNumberVideoSelected(numberVideo));
		
		// Verify user can deselect all video
		PageGallery.clickDeselectAllVideo();
		Assert.assertTrue(PageGallery.verifyNumberVideoSelected(0));
		
	}
	
	@Test(priority = 50, description = "Verify that user can share video in gallery page")
	public void shareVideos() {
		String user = StringHelper.randomString("qauto_", 7).toLowerCase();
		String email = user + "@restmail.net";
		String subj = "Video share from: " + email;
		
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to gallery tab
		PageDashboard.gotoGalleryPage();
		int numberVideo = PageGallery.getNumberVideoInGallery();
		
		// If there is no video, back to stream view and record videos
		if (numberVideo < 2) {
			recordVideos(2);
		}
		// Verify user can select single video
		PageGallery.selectVideos(1);
		Assert.assertTrue(PageGallery.verifyNumberVideoSelected(1));
		
		// Share video clip in gallery 
		PageGallery.clickShareButton();
		PageGallery.clickOnEmailIcon();
		PageEmail.sendEmail(email, subj);
		
		// Go to email and verify
		KodakRestMailHelper helper = new KodakRestMailHelper(user);
		helper.deleteAllRestMail();
		Assert.assertTrue(helper.getEmailSubject().contains(subj));
	}
	
	@Test(priority = 50, description = "Verify that user can delete video in gallery page")
	public void deleteVideos() {
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to gallery tab
		PageDashboard.gotoGalleryPage();
		int numberVideo = PageGallery.getNumberVideoInGallery();
		
		// If there is no video, back to stream view and record videos
		if (numberVideo < 2) {
			recordVideos(2);
			numberVideo = PageGallery.getNumberVideoInGallery();
		}
		// Verify user can select single video
		PageGallery.selectVideos(1);
		Assert.assertTrue(PageGallery.verifyNumberVideoSelected(1));
		
		// Delete a video
		PageGallery.deleteVideo();
		int numVideoRemain = PageGallery.getNumberVideoInGallery();
		Assert.assertEquals(numberVideo - numVideoRemain, 1);
		
		// Delete all video
		PageGallery.selectAllVideo();
		PageGallery.deleteVideo();
		Assert.assertEquals(0, PageGallery.getNumberVideoInGallery());
				
	}
	
	public void recordVideos(int numberVideo) {
		PageDashboard.gotoHomePage();
		PageDashboard.selectDeviceToView(device.getName());
		
		// Record video
		PageStreamView.clickMoreMenuIcon();
		for (int i = 0; i < numberVideo; i++) {
			PageStreamView.clickStartRecording(10);
			PageStreamView.clickStopRecording();
			TimeHelper.sleep(5000);
		}
		
		// Go to gallery page and verify
		PageBase.clickBackButton();
		PageDashboard.gotoGalleryPage();
	}
	
}
