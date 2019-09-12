package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.object.Device;
import com.cinatic.object.MqttObject;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageGmail;
import mobile.kodak.base.TestBaseAndroid;
import net.restmail.KodakRestMailHelper;

public class SETTING04_SendCameraLog extends TestBaseAndroid {
	Device device;
	
	@BeforeMethod
	public void Precondition() throws Exception{
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		MqttObject o = api.registerApp();
	}
	
	@Test(priority = 14, description = "Send camera log")
	public void verifySendingCameraLog() {

		String user = StringHelper.randomString("qaauto", 5).toLowerCase();
		String email = user + "@restmail.net";
		String subJ = StringHelper.randomString("Feedback from user", 10);
		PageGetStart.checkAndSignin(c_username, c_password);
		
//		pageDashboard = new PageDashboard();
		PageDashboard.openDeviceSetting(device.getName());
		PageCameraSetting.sendCameraLog();
		PageGmail.sendEmail(email, subJ);
		
		KodakRestMailHelper restMailHelper = new KodakRestMailHelper(user);
		Assert.assertTrue(restMailHelper.getEmailSubject().contains(subJ), "Subject of feedback email should contains string: " + subJ);
	}
	
}
