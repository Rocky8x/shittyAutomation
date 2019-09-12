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

public class MEL01_MelodyPlay extends TestBaseIOS{
	Terminal terminal;
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() throws SerialPortException {
		terminal = new Terminal(c_comport);
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}
	
	@Test(priority = 17, description = "Melody play (1-5)")
	public void playMelody() {
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
		
		// Play melody 2
		terminal.clearTeratermLog();
		PageStreamView.clickMelodyIconOn();
		PageStreamView.playMelody(2);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("/melody/melody2.wav"));
		
		// Play melody 3
		terminal.clearTeratermLog();
		PageStreamView.clickMelodyIconOn();
		PageStreamView.playMelody(3);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("/melody/melody3.wav"));
				
		// Play melody 4	
		terminal.clearTeratermLog();
		PageStreamView.clickMelodyIconOn();
		PageStreamView.playMelody(4);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("/melody/melody4.wav"));
		
		// Play melody 5
		terminal.clearTeratermLog();
		PageStreamView.clickMelodyIconOn();
		PageStreamView.playMelody(5);
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("/melody/melody5.wav"));
		
		// Stop melody
		terminal.clearTeratermLog();
		PageStreamView.clickMelodyIconOn();
		PageStreamView.clickStopMelody();
		camLog = terminal.getTeratermLog();
		Assert.assertTrue(camLog.contains("req=melodystop"));
		
	}
	
	@AfterMethod
	public void cleanUp() {
		try {
			terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
