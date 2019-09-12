package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING10_LightSourceFreqAutoSetup extends TestBaseAndroid {
	Device device;
	
	@BeforeMethod
	public void Precondition() throws Exception{
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 11, description = "Light Source Frequency auto set during setup")
	public void verifyLightSourceFrequencyAutoSetup() {
		PageGetStart.checkAndSignin(c_username, c_password);		
		PageDashboard.openDeviceSetting(device.getName());
		float timeZone = PageCameraSetting.getTimeZoneSystem();
		String lightSource = PageCameraSetting.getLightSourceFrequencyValue();
		Assert.assertTrue(PageCameraSetting.verifyLightSourceFrequencyByTimeZone(timeZone, lightSource),
				String.format("Light source frequency display wrong. Time zone is: %s, expected %s, actual: %s", 
						timeZone, (timeZone > -4 && timeZone < 9 ? "50Hz" : "60Hz") , lightSource));
	}
}
