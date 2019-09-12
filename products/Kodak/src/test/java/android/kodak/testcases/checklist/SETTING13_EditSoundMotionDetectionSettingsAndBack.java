package android.kodak.testcases.checklist;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING13_EditSoundMotionDetectionSettingsAndBack extends TestBaseAndroid{
	String cameraName="";
	
	@BeforeMethod
	public void prepare() {
		ApiHelper apiHelper = new ApiHelper();
		apiHelper.userLogin(c_username, c_password);
		apiHelper.getDevices();
		cameraName = apiHelper.getDeviceByDeviceId(c_deviceid).getName();
	}
	
	@Test (description = "Everything must works when hit \"Edit Sound/Motion Detection Settings\" and back from stream view",
			priority = 41)
	public void editSoundMotionDetectionSettingsAndBack() {
		PageDashboard.openDeviceSetting(cameraName);
		
		PageCameraSetting.deleteAllEvents();
		PageBase.exitPage();
		
		PageDashboard.selectDeviceToView(cameraName);
		PageStreamView.clickEditSoundMotionSetting();
		PageCameraSetting.getCameraNameValue().getText();
		PageBase.exitPage();
		PageStreamView.panTilt("up");
		PageStreamView.panTilt("down");
		PageStreamView.getMenuImage().click();
		PageStreamView.getMelodyImage().click();
		PageStreamView.getMelodyByName(3).click();
		PageStreamView.getStopMelody().click();
		PageStreamView.getCancelMelodyButton().click();
		PageStreamView.getSoundImageBtn().click();
		PageStreamView.getSoundImageBtn().click();
		PageStreamView.exitPage();

	}
}
