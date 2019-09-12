package ios.kodak.testcases.AfterAllTests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.TimeHelper;
import com.cinatic.object.Terminal;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageIOSSetting;
import ios.kodak.object.PageSetup;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseIOS;

public class PrepareEnvironment extends TestBaseIOS{

	Terminal terminal;
	
	@BeforeClass
	public void setup() throws SerialPortException {
		 terminal = new Terminal(c_comport);
	}

	@Test(priority = 101)
	public void prepareTestEnvironment() throws SerialPortException {
		String cameraModel = "C520";
		String secureType = "wpa";
		//Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		String deviceStt = "";
		try {
			deviceStt = PageDashboard.getCameraStatusByName(c_devicessid);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		if (!deviceStt.contains("Online")) {
			terminal.sendCommand("shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN");
			terminal.sendCommand("pair", "start_pairing_mode", 10);
			PageDashboard.clickAddNewCamera();
			if(cameraModel.startsWith("C")) {
				PageSetup.clickBabySeriesModel();
				PageSetup.clickOnDeviceModel(cameraModel);	
			}else if(cameraModel.startsWith("F")){
				PageSetup.clickHomeSeriesModel();
				PageSetup.clickOnDeviceModel(cameraModel);
//				PageSetup.clickOnContinueSettupButton();
			}
			if(PageSetup.getContinueButton().getWebElement() != null) {
				PageSetup.clickOnContinueSettupButton();
			}
			PageSetup.clickProceedAnyway();
			PageSetup.clickOnContinueSettupButton();
			PageSetup.clickOnContinueSettupButton();
			PageSetup.clickGotoSettingsButton();
			PageIOSSetting.clickWifiSettings();
			TimeHelper.sleep(10000);
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
