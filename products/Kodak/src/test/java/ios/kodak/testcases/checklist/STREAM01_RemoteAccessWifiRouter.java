package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class STREAM01_RemoteAccessWifiRouter extends TestBaseIOS{

	ApiHelper api;
	Device device;
	Terminal terminal;
	
	@BeforeClass
	public void setup() throws SerialPortException {
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		
		terminal = new Terminal(c_comport);
	}
	
	@Test(priority = 32, description = "Remote access with WiFi Router")
	public void remoteWifiAccess() {
		String camLog;
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.selectDeviceToView(device.getName());
		
		String streamMode = PageStreamView.getStreamMode(5);
		if (streamMode.contains("PL")) {
			// Kill app and open app again
			DriverManager.getDriver().closeApp();
			DriverManager.createWebDriver(driverSetting);
			PageDashboard.selectDeviceToView(device.getName());
			streamMode = PageStreamView.getStreamMode(5);
			if (streamMode.contains("PL"))
				Assert.fail("Stream mode still stay in local");
		}else {
			// Verify talkback work well
			PageStreamView.clickMoreMenuIcon();
			terminal.clearTeratermLog();
			PageStreamView.enableTalkBack(true);	
			camLog = terminal.getTeratermLog();
			Assert.assertEquals(camLog.contains("P2P Talkback state 1"), true);
			Assert.assertTrue(PageStreamView.verifyTalkbackEnable(true), "Talk back should enable");
			
			// Disable talkback and verify
			terminal.clearTeratermLog();
			PageStreamView.enableTalkBack(false);
			camLog = terminal.getTeratermLog();
			Assert.assertEquals(camLog.contains("P2P Talkback state 0"), true);
			Assert.assertTrue(PageStreamView.verifyTalkbackEnable(false), "Talk back should disable");
			PageStreamView.clickMoreMenuIcon();
			
			// Verify melody
			PageStreamView.openMelody();
			terminal.clearTeratermLog();
			PageStreamView.playMelody(1);
			camLog = terminal.getTeratermLog();
			Assert.assertTrue(camLog.contains("/melody/melody1.wav"));
			
			// Stop melody
			terminal.clearTeratermLog();
			PageStreamView.clickMelodyIconOn();
			PageStreamView.clickStopMelody();
			camLog = terminal.getTeratermLog();
			Assert.assertTrue(camLog.contains("req=melodystop"));
			
		}
	}
	
	@AfterClass
	public void cleanUp() {
		try {
			terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
