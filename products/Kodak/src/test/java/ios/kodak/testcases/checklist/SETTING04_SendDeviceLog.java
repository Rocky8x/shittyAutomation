package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageEmail;
import ios.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseIOS;
import net.restmail.KodakRestMailHelper;

public class SETTING04_SendDeviceLog extends TestBaseIOS{

	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}
	
	@Test(priority = 36, description = "Verify that user can send device log")
	public void verifySendDeviceLog() {
		String user = StringHelper.randomString("qaauto_", 7).toLowerCase();
		String email = user + "@restmail.net";
		String subj = StringHelper.randomString("Feedback from user: ", 10);
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to device setting and send camera log
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();
		PageDeviceSettings.clickSendCameraLog();
		
		// Send email
		PageEmail.sendEmail(email, subj);
		
		// Go to email and verify
		KodakRestMailHelper helper = new KodakRestMailHelper(user);
		helper.deleteAllRestMail();
		Assert.assertTrue(helper.getEmailSubject().contains(subj));
	}
}
