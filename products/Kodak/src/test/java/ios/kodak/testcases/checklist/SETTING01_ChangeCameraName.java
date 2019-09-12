package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseIOS;

public class SETTING01_ChangeCameraName extends TestBaseIOS{

	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
				
	}
	
	@Test(priority = 26, description = "Verify that user can change camera name")
	public void verifyUserCanChangeCameraName() {
		String newCameraName = "KodakCam_12345";
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.openDeviceSetting(device.getName());
		PageDeviceSettings.gotoDetailsCamera();
		String cameraName = PageDeviceSettings.getCameraName();
		
		// Verify cancel change camera name
		PageDeviceSettings.clickOnDeviceName();
		PageDeviceSettings.inputCameraName(newCameraName);
		PageBase.clickCancelButton();
		Assert.assertEquals(PageDeviceSettings.getCameraName(), cameraName);
		
		// Verify change camera name with short name
		PageDeviceSettings.changeCameraName("Cam");
		Assert.assertEquals(PageDeviceSettings.getChangeCameraNameWarningMessage(), PageDeviceSettings.CHANGE_DEVICE_NAME_ERROR_MSG);
		
		// Verify change camera name with long name
		PageDeviceSettings.changeCameraName("Change camenra name with too long name");
		Assert.assertEquals(PageDeviceSettings.getChangeCameraNameWarningMessage(), PageDeviceSettings.CHANGE_DEVICE_NAME_ERROR_MSG);
		
		// Verify change camera name successful with valid name
		PageDeviceSettings.changeCameraName(newCameraName);
		Assert.assertEquals(PageDeviceSettings.getCameraName(), newCameraName);
		
		// Back to dashboard and verify new camera name updated
		PageBase.clickBackButton();
		Assert.assertTrue(PageDashboard.getListDeviceNameInDashboard().contains(newCameraName), "New camera name should display in dashboard.");
		
		// Revert to old camera name
		PageDashboard.openDeviceSetting(newCameraName);
		PageDeviceSettings.gotoDetailsCamera();
		PageDeviceSettings.changeCameraName(cameraName);
		Assert.assertEquals(PageDeviceSettings.getCameraName(), cameraName);
		
		// update api and device
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
}
