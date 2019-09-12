package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseAndroid;

public class MEDIA04_SdcardMdClipPlayback extends TestBaseAndroid {

	@Test(groups = "mdclip", priority = 11, description = "SD Card storage: MVR clip playback on apps")
	public void verifySDCardStorageClipPlayBack() {
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageBase.gotoToTimelinePage();
		// Verify motion clip storage in cloud exist
		boolean rs = PageTimeline.findVideoStorageInSDCard();
		Assert.assertTrue(rs, "There are no motion video storage in sd-card in timeline page.");
		// Playback motion clip and verify
		int playTimes = 3;
		int playFail = PageTimeline.verifyPlayBackVideoFunction("sd-card", playTimes);
		if (playFail != 0) {
			Assert.fail(String.format("Playback video in %s times, but fail %s times.", playTimes, playFail));
		}
	}
}
