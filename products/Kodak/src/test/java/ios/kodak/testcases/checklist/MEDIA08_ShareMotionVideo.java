package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageEmail;
import ios.kodak.object.PageGallery;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import ios.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class MEDIA08_ShareMotionVideo extends TestBaseIOS{
	ApiHelper api;
	Device device;
	String user, email, subj;
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	/*
	 * Share video in galley was covered on Gallery 
	 */
	
	@BeforeMethod
	public void prepareData() {
		user = StringHelper.randomString("qauto_", 7).toLowerCase();
		email = user + "@restmail.net";
		subj = "Video share from: " + email;
	}
	
	@Test(priority = 47, description = "Share motion clip in stream page")
	public void shareMotionClipFromStreamView() {
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.selectDeviceToView(device.getName());
		
		// Verify motion clip in stream view
		boolean rs = PageStreamView.getMotionVideo(PageStreamView.CLOUD);
		if(!rs) {
			Assert.fail("There is no motion clip in stream view.");
		}
		
		// Click share icon in first clip
		PageStreamView.clickShareFirstMotionClip();
		
		// Send email
		PageGallery.clickOnEmailIcon();
		PageEmail.sendEmail(email, subj);
		
		// Go to email and verify
		KodakRestMailHelper helper = new KodakRestMailHelper(user);
		helper.deleteAllRestMail();
		Assert.assertTrue(helper.getEmailSubject().contains(subj));
		
	}
	
	@Test(priority = 48, description = "Share motion clip in timeline page")
	public void shareMotionClipFromTimelinePage() {
		
		// Sign and go to timeline page
		PageGetStart.checkAndSignin(c_username, c_password);	
		PageDashboard.gotoTimelinePage();
		
		// Verify motion clip in timeline page
		boolean rs = PageStreamView.getMotionVideo(PageStreamView.CLOUD);
		if(!rs) {
			Assert.fail("There is no motion clip in stream view.");
		}
		
		// Click share icon in first clip
		PageTimeline.clickShareFirstMotionClip();
		
		// Send email
		PageGallery.clickOnEmailIcon();
		PageEmail.sendEmail(email, subj);
		
		// Go to email and verify
		KodakRestMailHelper helper = new KodakRestMailHelper(user);
		helper.deleteAllRestMail();
		Assert.assertTrue(helper.getEmailSubject().contains(subj));
		
	}
}
