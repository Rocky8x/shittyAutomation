package android.kodak.testcases.stress;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;
import com.cinatic.object.Device;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageDoorbellCall;
import android.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseAndroid;

public class DoorbellPickupACall extends TestBaseAndroid{
	private String deviceLog;
	private Device c_device;
	
	ApiHelper api;
	PageDashboard pageDashboard = new PageDashboard();

	@BeforeTest
	public void Pecondition() throws IOException {		
		
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		c_device = api.getDeviceByDeviceId(c_deviceid); 
	}

	@Test(priority = 1, description = "TC001: Verify that user can make call from doorbell and pickup from Android phone")
	public void TC001_Pickup_a_call() throws IOException, InterruptedException {
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		Log.info(c_device.getName());


//		1. Call from DBell
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());
		TerminalHelper.sendCommand(c_comport, "y", "");
		TerminalHelper.sendCommand(c_comport, "atecmd ver", "");
		TerminalHelper.sendCommand(c_comport, "atecmd calling", "OK");
		int count = 0;
		boolean hasEvent = false;
		while (count < 30) {
			deviceLog = TerminalHelper.getLogCat(driverSetting.getDeviceUDID(), "MqttPushService",
					c_device.getDevice_id());
			if (deviceLog.contains("Doorbell pressed detected")) {
				hasEvent = true;
				break;
			}
			TimeHelper.sleep(1000);
			count++;
		}
		Assert.assertTrue(hasEvent, "Error: Unable to make a call");

//		2. Pickup the call on Android phone
		TimeHelper.sleep(3500);
		String context = DriverManager.getDriver().getContext();
		DriverManager.getDriver().context(context);
				
		
		PageDoorbellCall.getAcceptButton().click();

//		3. View streaming for 10s
		TimeHelper.sleep(9000);

//		4. end , back to dash board.

	}
}
