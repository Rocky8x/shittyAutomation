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


public class SETTING11_LightSourceFreqChangeManualy extends TestBaseAndroid {
	Device device;

	@BeforeMethod
	public void Precondition() throws Exception{
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);

	}
		
	@Test(priority = 12, description = "Light Source Frequency manual set via camera setting page")
	public void verifyUserCanChangeLightSourceFrequency() {
		float timeZone = 10000;
		String currentFrequency;
		String newFrequency;
		try {
			PageGetStart.checkAndSignin(c_username, c_password);			
			PageDashboard.openDeviceSetting(device.getName());
			timeZone = PageCameraSetting.getTimeZoneSystem();
			currentFrequency = PageCameraSetting.getLightSourceFrequencyValue();
			PageCameraSetting.changeLightSourceFrequency();
			newFrequency = PageCameraSetting.getLightSourceFrequencyValue();
			Assert.assertNotEquals(currentFrequency, newFrequency, 
					String.format("Frequency should change. Before: %s. After change frequency: %s",
							currentFrequency, newFrequency));
		} finally {
			String freq = (timeZone > -4 && timeZone < 9) ? "50Hz" : "60Hz";
			if(!PageCameraSetting.getLightSourceFrequencyValue().equals(freq)) {
				PageCameraSetting.changeLightSourceFrequency();
			}
		}
	}
}
