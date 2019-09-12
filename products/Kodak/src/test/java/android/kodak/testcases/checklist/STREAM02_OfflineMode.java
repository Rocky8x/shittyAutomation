package android.kodak.testcases.checklist;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.object.LocalPC;
import com.cinatic.object.Terminal;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class STREAM02_OfflineMode extends TestBaseAndroid {
	
	String cameraName;
	Terminal com;
	
	@BeforeMethod
	public void prepare() throws Exception{
		
		ApiHelper apiHelper = new ApiHelper();
		apiHelper.userLogin(c_username, c_password);
		apiHelper.getDevices();
		cameraName = apiHelper.getDeviceByDeviceId(c_deviceid).getName();
		
		LocalPC.switchHotspotNoInternet();
		DriverManager.getDriver().quit();
		DriverManager.removeWebDriver();
		adbRefreshWifi();
		
		DriverManager.createWebDriver(driverSetting);
		
		com = new Terminal(c_comport);
	}
	
	@Test (description = "Check streaming, PTZ, melody, talkback, audio in offline mode",
			priority = 100)
	public void offlineModeFunctionTest() {
		PageGetStart.OfflineModeDialog.verifyDialogTitleAndMessage();
		PageGetStart.OfflineModeDialog.getGoOfflineBtn().click();
		
		PageDashboard.isCameraOnline(cameraName);
		PageDashboard.selectDeviceToView(cameraName);
		
		PageStreamView.getStreamMode(60);
		
		PageStreamView.getMenuImage().click();
		PageStreamView.getMelodyImage().click();
		for (int i=1; i<=5; i++) {
			PageStreamView.getMelodyByName(i).click();
			TimeHelper.sleep(3000);		
		}
		PageStreamView.getStopMelody().click();
		PageStreamView.getCancelMelodyButton().click();
		PageStreamView.swipeUpMiniMenu();

		for(int i=1; i<=4; i++) { PageStreamView.getZoomInButton().click();}
		PageStreamView.getZoomOutButton().click();
		PageStreamView.getMenuImage().click();
		
		PageStreamView.checkPanTilt(true, com);
	}
	@AfterMethod
	public void cleanup() throws Exception{
		com.closePort();
		LocalPC.switchHotspotInternet();
		adbRefreshWifi();
	}
}
