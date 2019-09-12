package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class MEL02_TalkBackAndMelody extends TestBaseIOS{
	ApiHelper api;
	Device device;
	Terminal terminal;
	
	@BeforeClass
	public void setUp(){
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@BeforeMethod
	public void config() throws SerialPortException {
		terminal = new Terminal(c_comport);
	}
	
	@Test(priority = 18, description = "Enable talkback while melody is ON.")
	public void enableTalkBackWhenMelodyOn() {
		String camLog;
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to stream view
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.openMelody();
		
		// Play melody 1
		terminal.clearTeratermLog();
		PageStreamView.playMelody(1);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("/melody/melody1.wav"));
		
		// Enable Talkback and verify
		PageStreamView.swipeToTalkBack();
		terminal.clearTeratermLog();
		PageStreamView.enableTalkBack(true);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("P2P Talkback state 1"));
		
		// Verify melody icon off
		PageStreamView.swipeToMelody();
		Assert.assertTrue(PageStreamView.getMelodyIconOff().getWebElement() != null);
		
		TimeHelper.sleep(10000);
	}
	
	@Test(priority = 19, description = "Play melody when talkback is ON.")
	public void openMelodyWhenTalkBackOn() {
		String camLog;
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Go to stream view
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.clickMoreMenuIcon();

		// Enable talkback
		terminal.clearTeratermLog();
		PageStreamView.enableTalkBack(true);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("P2P Talkback state 1"));
		
		// Try to play melody
		PageStreamView.swipeToMelody();
		PageStreamView.clickOnMelodyIconOff();
		TimeHelper.sleep(5000);
		
		// Verify melody icon still off
		Assert.assertTrue(PageStreamView.getMelodyIconOff().getWebElement() != null);
	}
	
	@AfterMethod
	public void cleanUp(){
		try {
			terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
