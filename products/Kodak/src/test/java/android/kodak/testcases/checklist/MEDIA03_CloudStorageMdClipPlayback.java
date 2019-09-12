package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseAndroid;

public class MEDIA03_CloudStorageMdClipPlayback extends TestBaseAndroid {

	@Test(groups = "mdclip", priority = 11, description = "CLOUD storage: MVR clip playback on apps")
	public void verifyCloudStorageClipPlayBack() {
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageBase.gotoToTimelinePage();
		// Verify motion clip storage in cloud exist
		boolean rs = PageTimeline.findVideoStorageInCloud();
		Assert.assertTrue(rs, "There are no motion video storage in cloud in timeline page.");
		// Playback motion clip and verify
		int playTimes = 3;
		int playFail = PageTimeline.verifyPlayBackVideoFunction("cloud", playTimes);
		if (playFail != 0) {
			Assert.fail(String.format("Playback video in %s times, but fail %s times.", playTimes, playFail));
		}
	}
	
}
