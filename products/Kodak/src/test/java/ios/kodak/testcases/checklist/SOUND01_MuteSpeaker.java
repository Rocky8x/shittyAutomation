package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class SOUND01_MuteSpeaker extends TestBaseIOS{

	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 25, description = "Mute speaker on app")
	public void muteSpeakerOnApp() {
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to stream view
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.clickMoreMenuIcon();
		
		// Disable speaker and verify
		PageStreamView.enableSpeaker(false);
		Assert.assertTrue(PageStreamView.verifySpeakerOn(false), "Speaker should disable.");
		
		// Enable speaker and verify
		PageStreamView.enableSpeaker(true);
		Assert.assertTrue(PageStreamView.verifySpeakerOn(true), "Speaker should enable.");
		
		
	}
}
