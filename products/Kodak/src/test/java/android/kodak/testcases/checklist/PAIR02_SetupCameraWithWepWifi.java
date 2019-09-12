package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class PAIR02_SetupCameraWithWepWifi extends TestBaseAndroid {
	private Terminal terminalCamera2;
	private String cameraSsid2;

	@Parameters({"comport1"})
	@BeforeMethod
	public void openComPort(String comport1) throws Exception{
		terminalCamera2= new Terminal(comport1);
		terminalCamera2.sendCommand("shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN");
		if (cameraSsid2==null) {
			String cameraId2 = terminalCamera2.getCameraUdid();
			cameraSsid2 = Device.convertSsidByUuid(cameraId2);
		}
	}
	
	@Parameters({ "comport1", "wepwifiname", "wepwifipassword"})
	@Test(priority = 10, description = "Verify that user can setup camera with special char SSID")
	public void setupCameraWithWepWifi(String comport1, String wepwifiname, String wepwifipassword) throws SerialPortException {
	
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();		
		PageDashboard.getAddDeviceBigBtn().click();
		
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
		
		PageDashboard.getHomeSeriesImage().click();
		PageDashboard.proceedAnyway5GWifiIfAsk();
		PageDashboard.getDeviceModelLabel("680").click();
		terminalCamera2.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.getContinueButton().click();
		PageDashboard.getContinueButton().click();
		// Click on device name
		if (PageDashboard.getSSIDLabel(cameraSsid2).getWebElement() != null) {
			PageDashboard.getSSIDLabel(cameraSsid2).click();
		} else {
			PageDashboard.getSSIDRefreshImage().click();
			PageDashboard.getSSIDLabel(cameraSsid2).click();
		}
		// Config wifi for camera
		PageDashboard.configWifiForCamera(wepwifiname, "WEP", wepwifipassword);

		if(PageDashboard.getMobileDataContinueButton().getWebElement()!=null) {
			PageDashboard.getMobileDataContinueButton().click();
		}
		PageDashboard.getCustomButton().click();
		PageDashboard.getContinueButton().click();
		PageDashboard.getDoneButton().click();
		// Verify camera in dashboard after settup
		Assert.assertTrue(PageDashboard.getListCameraNameInDashBoard().contains(cameraSsid2),
				String.format("Camera: %s should display in dashboard.", cameraSsid2));
	}
	
	@AfterMethod
	public void closeComPort(ITestResult result) {
		int testStatus = result.getStatus();
		if (testStatus == ITestResult.FAILURE) {
			try {
				terminalCamera2.sendCommand("reboot");
			} catch (Exception e) {
			}
		}
		try {
			terminalCamera2.closePort();
		} catch (Exception e) {	}
	}
}
