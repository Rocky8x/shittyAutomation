package android.kodak.testcases.checklist;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class MEL01_PlayMelody extends TestBaseAndroid {
	private String username;
	private String password;
	private Device device;	
	private Terminal terminal;
//	private PageGetStart getStartPage;
//	private PageLogin loginPage;
//	private PageDashboard dashboarPage;
//	private PageStreamView streamViewPage; 

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		this.username = c_username;
		this.password = c_password;

		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		terminal = new Terminal(c_comport);
	}

	@Test(priority = 11, description = "TC001: Verify that user can play melody successfully")
	public void TC001_playMelody1to5() throws ParseException {
		PageGetStart.checkAndSignin(username, password);
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.getStreamMode(60);
		PageStreamView.openMelody();
		terminal.clearTeratermLog();
		PageStreamView.playMelody(1);
		String camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody1.wav"), true);
		
		terminal.clearTeratermLog();
		PageStreamView.playMelody(2);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody2.wav"), true);
		
		terminal.clearTeratermLog();
		PageStreamView.playMelody(3);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody3.wav"), true);
		
		terminal.clearTeratermLog();
		PageStreamView.playMelody(4);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody4.wav"), true);
		
		terminal.clearTeratermLog();
		PageStreamView.playMelody(5);
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("/melody/melody5.wav"), true);
		
		terminal.clearTeratermLog();
		PageStreamView.playStopMelody();
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("req=melodystop"), true);		
	}
		
	@AfterMethod
	public void Cleanup() throws SerialPortException{
		terminal.closePort();
	}
}
