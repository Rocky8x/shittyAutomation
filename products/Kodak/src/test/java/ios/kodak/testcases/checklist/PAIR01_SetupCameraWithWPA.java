package ios.kodak.testcases.checklist;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageIOSSetting;
import ios.kodak.object.PageSetup;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class PAIR01_SetupCameraWithWPA extends TestBaseIOS{
	private Terminal terminal;
	
	@BeforeClass
	public void setup() throws SerialPortException{
		terminal = new Terminal(c_comport);
	}
	
	@Test(priority = 73, description = "Verify that user can setup camera successful")
	public void setupCameraWithWPARouter() throws SerialPortException{
		String secureType = "wpa";
		
		PageGetStart.checkAndSignin(c_username, c_password);
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.clickAddNewCamera();
		PageSetup.selectCameraImageByModelName(Device.getModelNameByUuid(c_deviceid));
		PageSetup.clickProceedAnyway();
		PageSetup.clickOnContinueSettupButton();
		PageSetup.clickOnContinueSettupButton();
		PageSetup.clickGotoSettingsButton();
		PageIOSSetting.clickWifiSettings();
		PageIOSSetting.clickOnWifiName(c_devicessid);
		TimeHelper.sleep(10000);
		PageIOSSetting.backToKodakApp();
		TimeHelper.sleep(10000);
		PageSetup.clickSelectAnotherWifi();
		TimeHelper.sleep(20000);
		PageSetup.selectWifitoConnectRouter(c_wifiname, c_wifipassword, secureType);
		PageSetup.clickCustomButton();
		PageBase.clickContinueButton();
		PageBase.clickDoneButton();
	}
	
	@AfterClass
	public void cleanUp() {
		try {
			terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
