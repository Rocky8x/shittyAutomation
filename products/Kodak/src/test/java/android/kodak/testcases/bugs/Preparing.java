package android.kodak.testcases.bugs;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.cinatic.log.Log;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageHomeSetting;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class Preparing extends TestBaseAndroid {
	private Terminal terminal;

	
	@Test(priority = -10, description = "Preparing for bug verification")
	public void preparingForBugVerification() throws SerialPortException {
		
		// unlock camera shell
		terminal = new Terminal(c_comport);
		try {
			Log.info("Unlock camera shell");
			terminal.sendCommand("shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN");
		} catch (SerialPortException e) {
			Log.fatal(e.getMessage());
			throw e;
		}
		
		// pair camera
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.getAddDeviceBigBtn().click();		
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
					
		PageDashboard.getBabySeriesImage().click();
		
		try {
			PageDashboard.getSameSsidProcessAnywayButton().click();
		} catch (Exception e) {	}
		
		PageDashboard.getDeviceModelLabel("520").click();
		terminal.sendCommand("pair", "start_pairing_mode", 10);
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
		
		// enable debug and show debug info
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.enableDebug();
		PageHomeMenu.gotoHomeSetingPage();
		PageHomeSetting.enableShowDebugInfo();		
		PageBase.exitPage();
		
	}

	@AfterMethod
	public void cleanup(ITestResult result) {
		int testStatus = result.getStatus();
		if (testStatus == ITestResult.FAILURE) {
			try {
				terminal.sendCommand("reboot");
			} catch (Exception e) { } 
		}
		try {
			terminal.closePort();
		} catch (Exception e) {	}
	}
}
