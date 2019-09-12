package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class SOUND02_TalkBack extends TestBaseAndroid {
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

	@Test(priority = 11, description = "TC001: Verify that user can talkback successfully")
	public void TC001_TalkBack() {
		TimeHelper.sleep(10000);
		PageGetStart.checkAndSignin(username, password);
		
		
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.getStreamMode(60);
		
		terminal.clearTeratermLog();
		PageStreamView.enableTalkBack();		
		String camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 1"), true);		
		
		terminal.clearTeratermLog();
		PageStreamView.disableTalkBack();
		camLog = terminal.getTeratermLog();
		Assert.assertEquals(camLog.contains("P2P Talkback state 0"), true);
	}
	
	@AfterMethod
	public void cleanup() throws SerialPortException{
		terminal.closePort();
	}
}
