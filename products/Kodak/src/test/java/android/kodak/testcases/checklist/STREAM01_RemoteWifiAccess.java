package android.kodak.testcases.checklist;

import static org.testng.Assert.assertTrue;

import com.cinatic.ApiHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.object.Terminal;

import android.kodak.object.PageAndroidSetting;
import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

// not stable
//Temporary disable this test,
public class STREAM01_RemoteWifiAccess extends TestBaseAndroid{
	String cameraName;

	String secondWifi;
	String secondwifiPw;
	String firstWifiname;
	Terminal com;
//	@BeforeMethod
//	@Parameters({"wepwifiname", "wepwifipassword" })
	public void prepare(String wepwifiname, String wepwifipassword) throws Exception{
		ApiHelper  apiHelper = new ApiHelper();
		apiHelper.userLogin(c_username, c_password);
		apiHelper.getDevices();
		cameraName = apiHelper.getDeviceByDeviceId(c_deviceid).getName();
		firstWifiname = c_wifiname;
		secondWifi = wepwifiname;
		secondwifiPw = wepwifipassword;
		
		com = new Terminal(c_comport);
		
	}
	
//	@Test (priority=50, description = "Remote access to camera, basic function test: PTZ, sound, melody")
	public void psStreaming() {
		try {		
		PageBase.openSystemWifiSetting();
		PageAndroidSetting.connectWifi(secondWifi,secondwifiPw);
		DriverManager.getDriver().switchRecentApp();
		
		PageDashboard.selectDeviceToView(cameraName);
		
		String streamMod = PageStreamView.getStreamMode(30);
		PageStreamView.checkPanTilt(true, com);
	
		assertTrue(streamMod.contains("PS"));		
		} finally {
			PageBase.openSystemWifiSetting();
			PageAndroidSetting.connectWifi(firstWifiname);
			PageAndroidSetting.forgetOtherWifi(secondWifi);
			DriverManager.getDriver().switchRecentApp();
			try {
				com.closePort();
			} catch (Exception e) {
			}
		}
	}
}
