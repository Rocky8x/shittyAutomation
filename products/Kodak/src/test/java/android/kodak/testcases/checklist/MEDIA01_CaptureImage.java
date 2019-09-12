package android.kodak.testcases.checklist;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class MEDIA01_CaptureImage extends TestBaseAndroid {
	private String username;
	private String password;
	private Device device;	

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		this.username = c_username;
		this.password = c_password;

		ApiHelper api;
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 11, description = "TC001: Verify that user can capture image successfully")
	public void TC001_CaptureImage() {

		PageGetStart.checkAndSignin(username, password);		
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.getStreamMode(60);
				
		PageStreamView.captureImage();		
	}
}
