package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageIOSPhotos;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class MEDIA01_TakePhotos extends TestBaseIOS{
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void setup() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}

	@Test(priority = 22, description = "Verify that user can take photos")
	public void takePhotos() {
		final String PHOTOS_BUNDLE_ID = "com.apple.mobileslideshow";
		final String KODAK_BUNDLE_ID = "com.perimetersafe.kodaksmarthome";
		
		// Switch to Photos app and delete all today's image
		PageBase.switchApp(PHOTOS_BUNDLE_ID, DriverManager.getDriver().getAppiumDriver());
		TimeHelper.sleep(5000);
		
		if(PageIOSPhotos.verifyTodayImageExisted()) {
			PageIOSPhotos.deleteAllImageOnToday();
		}
		
		// Switch to Kodak app and capture image
		PageBase.switchApp(KODAK_BUNDLE_ID, DriverManager.getDriver().getAppiumDriver());
		TimeHelper.sleep(5000);
		
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.selectDeviceToView(device.getName());
		
		// Take a photo
		PageStreamView.clickMoreMenuIcon();
		PageStreamView.clickTakePhotoButton();
		TimeHelper.sleep(10000);
		
		// Switch to Photos app and check image
		PageBase.switchApp(PHOTOS_BUNDLE_ID, DriverManager.getDriver().getAppiumDriver());
		TimeHelper.sleep(10000);
		Assert.assertTrue(PageIOSPhotos.verifyTodayImageExisted());
	}
}
