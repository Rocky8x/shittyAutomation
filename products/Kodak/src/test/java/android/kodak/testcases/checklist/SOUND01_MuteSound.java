package android.kodak.testcases.checklist;

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

public class SOUND01_MuteSound extends TestBaseAndroid {
	private String username;
	private String password;
	private Device device;
	private Terminal terminal;

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

	@Test(priority = 11, description = "TC001: Verify that user can mute sound successfully")
	public void TC001_MuteSound() {
		PageGetStart.checkAndSignin(username, password);
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.getStreamMode(60);
				
		PageStreamView.unMuteSound();
		PageStreamView.muteSound();
		PageStreamView.unMuteSound();
	}
	
	@AfterMethod
	public void cleanup() throws SerialPortException{
		terminal.closePort();
	}
}
