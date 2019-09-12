package ios.kodak.testcases.checklist;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
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

public class PAIR02_SetupCameraWithWEP extends TestBaseIOS{

private Terminal terminal;
	String deviceid1, devicessid1, comport1, wepwifiname, wepwifipassword;
	
	@Parameters({"deviceid1", "devicessid1", "comport1", "wepwifiname", "wepwifipassword"})
	
	@BeforeClass
	public void setup(String deviceid1, String devicessid1, String comport1, String wepwifiname, String wepwifipassword) throws SerialPortException{
		this.deviceid1 = deviceid1;
		this.devicessid1 = devicessid1;
		this.comport1 = comport1;
		this.wepwifiname = wepwifiname;
		this.wepwifipassword = wepwifipassword;
		terminal = new Terminal(comport1);
	}
	
	
	@Test(priority = 74, description = "Verify that user can setup camera with wep router successful")
	public void setupCameraWithWEPRouter() throws SerialPortException{
		String secureType = "wep";
		
		PageGetStart.checkAndSignin(c_username, c_password);
		terminal.sendCommand("pair", "start_pairing_mode", 10);
		PageDashboard.clickAddNewCamera();
		PageSetup.selectCameraImageByModelName(Device.getModelNameByUuid(deviceid1));
		PageSetup.clickProceedAnyway();
		PageSetup.clickOnContinueSettupButton();
		PageSetup.clickOnContinueSettupButton();
		PageSetup.clickGotoSettingsButton();
		PageIOSSetting.clickWifiSettings();
		TimeHelper.sleep(20000);
		PageIOSSetting.clickOnWifiName(devicessid1);
		TimeHelper.sleep(20000);
		PageIOSSetting.backToKodakApp();
		TimeHelper.sleep(10000);
		PageSetup.clickSelectAnotherWifi();
		TimeHelper.sleep(20000);
		PageSetup.selectWifitoConnectRouter(wepwifiname, wepwifipassword, secureType);
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
