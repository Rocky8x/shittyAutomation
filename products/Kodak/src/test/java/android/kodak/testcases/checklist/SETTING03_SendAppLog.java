package android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.log.Log;
import com.cinatic.object.Device;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageFeedback;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageGmail;
import android.kodak.object.PageHomeMenu;
import mobile.kodak.base.TestBaseAndroid;
import net.restmail.KodakRestMailHelper;

public class SETTING03_SendAppLog extends TestBaseAndroid {
	private Device device;
	
	@BeforeMethod
	public void Precondition() {
		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 11, description = "Send app log (camera log & app log)")
	public void verifySendAppLog() {

		String user = StringHelper.randomString("qaauto", 5).toLowerCase();
		String email = user + "@restmail.net";
		String subJ = StringHelper.randomNumber("Feedback from: ", 10);
		String description = StringHelper.randomNumber("Descriptions ", 10);
		
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoFeedbackPage();
		// switch all option in What is the issue
		List<String> rs = PageFeedback.switchAllWhatIssueOption();
		if(rs.size() > 0) {
			Assert.fail("Cannot select options: " + rs.toString());
		}
		// switch all option in When happened
		rs = PageFeedback.switchAllWhenIssueHappened();
		if(rs.size() > 0) {
			Assert.fail("Cannot select options: " + rs.toString());
		}
		// Select device
		PageFeedback.selectDeviceByName(device.getName());
		PageBase.exitPage();
		// Input description
		PageFeedback.inputShortDescription(description);
		// Collect data to compare with email received
		rs = PageFeedback.collectReportData();
		rs.add(c_username);
		rs.add(device.getName());
		// Send email report
		PageFeedback.clickSendReport();
		PageGmail.sendEmail(email, subJ);
		// Open email and verify
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(user);
		restMailHelper.deleteAllRestMail();
		String contentEmail = restMailHelper.getRestMailDriver().getTextEmail();
		Log.info("Content email: " + contentEmail);
		for(String data : rs) {
			if(!contentEmail.contains(data)) {
				Assert.fail(String.format("Option: %s when send log not contain in email.", data));
			}
		}
	}
}
