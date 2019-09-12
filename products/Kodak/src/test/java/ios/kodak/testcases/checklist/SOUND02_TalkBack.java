package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class SOUND02_TalkBack extends TestBaseIOS{
	
	private Device device;
	private Terminal terminal;

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		terminal = new Terminal(c_comport);
	}

	@Test(priority = 16, description = "Verify that user can talkback successfully")
	public void talkBack() {
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to stream view
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.clickMoreMenuIcon();
		
		// Enable talkback and verify 
		terminal.clearTeratermLog();
		PageStreamView.enableTalkBack(true);	
		String camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 1"), true);
		Assert.assertTrue(PageStreamView.verifyTalkbackEnable(true), "Talk back should enable");
		
		// Disable talkback and verify
		terminal.clearTeratermLog();
		PageStreamView.enableTalkBack(false);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 0"), true);
		Assert.assertTrue(PageStreamView.verifyTalkbackEnable(false), "Talk back should disable");
	}
	
	@AfterMethod
	public void cleanup() throws SerialPortException{
		terminal.closePort();
	}
}
