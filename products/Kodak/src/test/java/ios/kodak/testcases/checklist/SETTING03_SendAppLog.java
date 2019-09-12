package ios.kodak.testcases.checklist;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageEmail;
import ios.kodak.object.PageFeedback;
import ios.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class SETTING03_SendAppLog extends TestBaseIOS {
	ApiHelper api;
	Device device;

	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 35, description = "Verify that user can send app log")
	public void verifySendAppLog() {
		String user = StringHelper.randomString("qaauto_", 7).toLowerCase();
		String email = user + "@restmail.net";
		String subj = StringHelper.randomString("Feedback from: ", 10);
		String description = StringHelper.randomString("Description ", 10);
		List<String> data = new ArrayList<String>();
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);

		// Go to report issue page
		PageDashboard.gotoReportIssuePage();

		// Select random what is the issue
		data.add(PageFeedback.selectRandomWhatIsIssue());

		// Select random when issue happened
		data.add(PageFeedback.selectRandomWhenIssueHappened());

		// Select camera's issue
		PageFeedback.selectCameraIssue(device.getName());
		data.add(device.getName());

		// Input description for issue
		PageFeedback.inputDescription(description);
		data.add(description);
		
		// Send email
		PageFeedback.clickComposeEmail();
		PageEmail.sendEmail(email, subj);
		
		// Open email and verify
		KodakRestMailHelper emailHelper = new KodakRestMailHelper(user);
		emailHelper.deleteAllRestMail();
		
		// Verify receive emai
		Assert.assertTrue(emailHelper.getEmailSubject().contains(subj), "Cannot received emai");
		
		// Verify content email
		String contentEmail = emailHelper.getRestMailDriver().getTextEmail();
		for(String s : data) {
			if(!contentEmail.contains(s)) {
				Assert.fail("Email not contains: " + s);
			}
		}
	}

}
