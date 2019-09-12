package android.kodak.testcases.checklist;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.object.Device;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGallery;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageGmail;
import android.kodak.object.PageStreamView;
import android.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseAndroid;
import net.restmail.KodakRestMailHelper;

public class MEDIA07_GalleryTabView extends TestBaseAndroid {

	private String randomEmailUser;
	private String randomEmailToShare;
	private String randomEmailSubj;
	
	@BeforeMethod
	public void prepare() {
		randomEmailUser = StringHelper.randomString("qaauto", 10).toLowerCase();
		randomEmailToShare = randomEmailUser + "@restmail.net";
		randomEmailSubj = StringHelper.randomString("Share gallery from: ", 10);
	}
	
	@Test(priority = 12, description = "Gallery tab view and function: Select, delete, share")
	public void verifyFunctionInGalleryTab() {

		PageGetStart.checkAndSignin(c_username, c_password);
		PageBase.navigateToGalleryPage();
		// If not video in gallery, navigate to time line tab and download motion clip
		if (PageGallery.verifyEmptyMotionVideo()) {
			PageBase.gotoToTimelinePage();
			boolean rs = PageTimeline.donwloadMotionVideo();
			if(!rs) {
				Assert.fail("There are no motion video in timeline page");
			}else {
				PageBase.navigateToGalleryPage();
			}
		}
		// verify select motion clip  
		int numberVideo = PageGallery.selectMotionClip();
		Assert.assertTrue(PageGallery.verifyNumberVideoSeleted(numberVideo), String.format("Number video selected expected: %s", numberVideo));
		// verify share function
		
		PageGallery.shareMotionVideo();
		PageGmail.sendEmail(randomEmailToShare, randomEmailSubj);	
		
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(randomEmailUser);
		Assert.assertTrue(restMailHelper.getEmailSubject().contains(randomEmailSubj), "Subject feedback email should contains string: " + randomEmailSubj);
		// verify delete all video
		PageGallery.clickCancelSelected();
		PageGallery.deleteAllMotionVideo();
		Assert.assertTrue(PageGallery.verifyEmptyMotionVideo(), "All video should removed.");
	}
	
	@Test(groups = "mdclip", priority = 11, description = "Share motion video & recorded video from: Stream view, Video Gallery, Events tab")
	public void verifyShareFunction() {
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageBase.navigateToGalleryPage();
		// If not video in gallery, navigate to time line tab and download motion clip
		if (PageGallery.verifyEmptyMotionVideo()) {
			PageBase.gotoToTimelinePage();
			boolean rs = PageTimeline.donwloadMotionVideo();
			if(!rs) {
				Assert.fail("There are no motion video in timeline page");
			}else {
				PageBase.navigateToGalleryPage();
			}
		}
		// Verify share motion clip in gallery tab
		PageGallery.selectMotionClip();
		PageGallery.shareMotionVideo();
		DriverManager.getDriver().hideKeyboard();
		// get attachment file in email before share
		List<String> attachments = PageGmail.getAttachmentsFile();
		
		// Send email
		PageGmail.sendEmail(randomEmailToShare, randomEmailSubj);
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(randomEmailUser);

//		RestMailHelper restMailHelper = new RestMailHelper(user);

		// Get attachment file in received
		List<String> attachments1 = restMailHelper.getEmailAttachments();
		Collections.sort(attachments);
		Collections.sort(attachments1);
		Assert.assertEquals(attachments, attachments1, String.format("Expected: %s, actual: %s", attachments, attachments1));
		
		// Verify share motion clip in event tab
		PageGallery.clickCancelSelected();
		PageBase.gotoToTimelinePage();
		PageTimeline.shareMotionVideo();
		// get attachment file in email before share
		attachments = PageGmail.getAttachmentsFile();
		// Send email
		PageGmail.sendEmail(randomEmailToShare, randomEmailSubj);
		
		restMailHelper.getRestMailDriver().downloadEmail();
		// Get attachment file in received
		attachments1 = restMailHelper.getEmailAttachments();
		restMailHelper.deleteAllRestMail();
		Collections.sort(attachments);
		Collections.sort(attachments1);
		Assert.assertEquals(attachments, attachments1, String.format("Expected: %s, actual: %s", attachments, attachments1));
		
		PageBase.navigateToDashBoardPage();
		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		Device device = api.getDeviceByDeviceId(c_deviceid);
		PageDashboard.selectDeviceToView(device.getName());
		// Verify share  motion in stream page
		Assert.assertFalse(PageStreamView.verifyEmptyEventInStreamPage(), "There are no event in stream view page.");
		PageTimeline.shareMotionVideo();
		// get attachment file in email before share
		attachments = PageGmail.getAttachmentsFile();
		// Send email
		PageGmail.sendEmail(randomEmailToShare, randomEmailSubj);
		
		restMailHelper.getRestMailDriver().downloadEmail();
		// Get attachment file in received
		attachments1 = restMailHelper.getEmailAttachments();
		restMailHelper.deleteAllRestMail();
		Collections.sort(attachments);
		Collections.sort(attachments1);
		Assert.assertEquals(attachments, attachments1, String.format("Expected: %s, actual: %s", attachments, attachments1));
	}
}
