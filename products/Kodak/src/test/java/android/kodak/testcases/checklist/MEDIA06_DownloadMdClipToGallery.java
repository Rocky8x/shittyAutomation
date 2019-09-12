package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageGallery;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseAndroid;

public class MEDIA06_DownloadMdClipToGallery extends TestBaseAndroid {
	
	@Test(priority = 11, description = "Download motion recording clip to gallery")
	public void verifyDownloadMotionClipToGallery() {
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageBase.navigateToGalleryPage();
		// Delete all existed video in gallery page
		if (!PageGallery.verifyEmptyMotionVideo()) {
			PageGallery.deleteAllMotionVideo();
			Assert.assertTrue(PageGallery.verifyEmptyMotionVideo(), "All video should removed.");
		}
		// Navigate to timeline page and download motion video
		PageBase.gotoToTimelinePage();
		boolean rs = PageTimeline.donwloadMotionVideo(); 
		if (!rs) {
			Assert.fail("There are no motion video in timeline page");
		}
		PageBase.navigateToGalleryPage();
		Assert.assertFalse(PageGallery.verifyEmptyMotionVideo(), "Gallery page should contains motion video.");
	}
}
