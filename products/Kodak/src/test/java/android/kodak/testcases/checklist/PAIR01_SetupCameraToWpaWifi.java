package android.kodak.testcases.checklist;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class PAIR01_SetupCameraToWpaWifi extends TestBaseAndroid {
	private Terminal terminalCamera1;

	@BeforeMethod
	public void prepare()  throws Exception{
		terminalCamera1 = new Terminal(c_comport);
	}
	
	@Test(retryAnalyzer = com.cinatic.RetryIfFail.class, priority = 10, description = "TC001: Verify that user can setup camera successfully")
	public void setupFirstCamWithWpaWifi() throws SerialPortException {
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.getAddDeviceBigBtn().click();		
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
					
		PageDashboard.getBabySeriesImage().click();
		
		try {
			PageDashboard.getSameSsidProcessAnywayButton().click();
		} catch (Exception e) {	}
		
		PageDashboard.getDeviceModelLabel("520").click();
		terminalCamera1.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.getContinueButton().click();
		PageDashboard.getContinueButton().click();
		if (PageDashboard.getSSIDLabel(c_devicessid).getWebElement() != null) {
			PageDashboard.getSSIDLabel(c_devicessid).click();
		} else {
			PageDashboard.getSSIDRefreshImage().click();
			PageDashboard.getSSIDLabel(c_devicessid).click();
		}
		PageDashboard.getTextWifiPasswordTextbox().sendKeys(c_wifipassword);		
		PageDashboard.getContinueButton().click();
		if(PageDashboard.getMobileDataContinueButton().getWebElement()!=null) {
			PageDashboard.getMobileDataContinueButton().click();
		}
		PageDashboard.getCustomButton().click();
		PageDashboard.getContinueButton().click();
		PageDashboard.getDoneButton().click();
	}
			
	// pair the second camera in the test case above to the main wifi 
	@Test (priority=11, description="Re-setup the same camera to another SSID")
	public void reSetupToAnotherWifi() throws SerialPortException {
		
		String deviceId1 = terminalCamera1.getCameraUdid();
		String devicessid1 = Device.convertSsidByUuid(deviceId1);
		
		PageGetStart.checkAndSignin(c_username, c_password);
				
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_MEDIA);		
		PageDashboard.getAddDeviceBigBtn().click();
		
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
					
		PageDashboard.getHomeSeriesImage().click();
		
		PageDashboard.getDeviceModelLabel("680").click();
		terminalCamera1.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.getContinueButton().click();
		PageDashboard.getContinueButton().click();
		if (PageDashboard.getSSIDLabel(devicessid1).getWebElement() != null) {
			PageDashboard.getSSIDLabel(devicessid1).click();
		} else {
			PageDashboard.getSSIDRefreshImage().click();
			PageDashboard.getSSIDLabel(devicessid1).click();
		}
		PageDashboard.getTextWifiPasswordTextbox().sendKeys(c_wifipassword);		
		PageDashboard.getContinueButton().click();
		if(PageDashboard.getMobileDataContinueButton().getWebElement()!=null) {
			PageDashboard.getMobileDataContinueButton().click();
		}
		PageDashboard.getCustomButton().click();
		PageDashboard.getContinueButton().click();
		PageDashboard.getDoneButton().click();
		
	}
	
	@AfterMethod
	public void cleanup(ITestResult result){
		int testStatus = result.getStatus();
		if (testStatus == ITestResult.FAILURE) {
			try {
				terminalCamera1.sendCommand("reboot");
			} catch (Exception e) {
			}
		}
		try {
			terminalCamera1.closePort();
		} catch (Exception e) {
		}
	}
}
