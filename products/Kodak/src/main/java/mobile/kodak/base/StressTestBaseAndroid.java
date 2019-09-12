package mobile.kodak.base;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.cinatic.ApiHelper;
import com.cinatic.log.Log;
import com.cinatic.object.Device;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageStreamView;

public class StressTestBaseAndroid extends TestBaseAndroid{
	protected static Device	c_device;
	protected static ApiHelper c_api;

	@BeforeSuite
	public void berforeSuiteStress() throws Exception {
		
		Log.info("check if the device is paired");
		c_api = new ApiHelper();
		c_api.userLogin(c_username, c_password);
		c_api.getDevices();
		launchApp();
		try {
			 c_device = c_api.getDeviceByDeviceId(c_deviceid);			
		} catch (IllegalArgumentException e) {
			Log.info("Devices "+ c_deviceid +" is not paired to account " + c_username + " yet.");
			Log.info("Start pairing " + c_deviceid + " now");			
			PageDashboard.setupNewCamera(c_wifiname, "WPA", c_wifipassword, c_deviceid, c_comport);
		}
		if(!c_device.isOnline()) {
			Log.info("Camera is paired to this account but offline, suspect that it was re-pair to another server");
			Log.info("Re-pair the cam to current user to make sure");
			PageDashboard.setupNewCamera(c_wifiname, "WPA", c_wifipassword, c_deviceid, c_comport);
		}
		
		// enable debug
		// try closing all auto-hints
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		PageDashboard.selectDeviceToView(c_device.getName());
		PageStreamView.closeCouldUpgradeTipIfAsked();
		closeApp();

	}
	
	@AfterMethod
	@Override
	public void afterMethodAndroid(ITestResult result) {
		// override parent method and leave it empty to not let them run
	}
	
	@AfterMethod
	@Override
	public void afterMethod(ITestResult result) {
		// override parent method and leave it empty to not let them run
	}
}
