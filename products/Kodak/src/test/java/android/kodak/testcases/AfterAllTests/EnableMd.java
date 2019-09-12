package android.kodak.testcases.AfterAllTests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseAndroid;

public class EnableMd extends TestBaseAndroid{
	
	String nameCamera1="";
	String nameCamera2="";
	String deviceid1="";
	String deviceid2="";
	
	@Parameters({"comport1"})
	@BeforeMethod
	public void getCamerasNames(String comport1) throws Exception{
		Terminal cam1Term = new Terminal(c_comport);
		Terminal cam2Term = new Terminal(comport1);

		cam1Term.unlockCameraShell();		
		cam2Term.unlockCameraShell();		
		deviceid1 = cam1Term.getCameraUdid();
		deviceid2 = cam2Term.getCameraUdid();
		
		ApiHelper apiHelper = new ApiHelper();
		apiHelper.userLogin(c_username, c_password);
		apiHelper.getDevices();
		nameCamera1 = apiHelper.getDeviceByDeviceId(deviceid1).getName();
		nameCamera2 = apiHelper.getDeviceByDeviceId(deviceid2).getName();
		
		cam1Term.closePort();
		cam2Term.closePort();
	}
	
	
	@Test (description = "Enable motion detection, cloud storage, on camera 1 and SD card storage, on camera 2 for test run next day")
	public void enableCloudMdOnCam1() {
		PageGetStart.checkAndSignin(c_username, c_password);
		
//		Enable motion detection, cloud storage, on camera 1
		PageDashboard.openDeviceSetting(nameCamera1);
		PageCameraSetting.getMotionDetectionOnButton().click();
		PageCameraSetting.getMdCloudStorageBtn().click();
		PageBase.exitPage();
		try {
			PageBase.getCloudUpgradeTipCloseBtn().click();
		} catch (Exception e) {
		}
		
//		Enable motion detection, sdcard storage, on camera 2
		PageDashboard.openDeviceSetting(nameCamera2);
		PageCameraSetting.getMotionDetectionOnButton().click();
		PageCameraSetting.getMdSdCardStorageBtn().click();
		PageBase.exitPage();
		
	}
	
	@AfterMethod
	public void rebootPhone() throws Exception{
		String cmd = String.format("adb -s %s shell reboot",driverSetting.getDeviceUDID());
		TerminalHelper.exeBashCommand(cmd);
	}
}
