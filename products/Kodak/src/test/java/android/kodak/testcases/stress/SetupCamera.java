package android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.DatabaseHelper;
import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import jssc.SerialPortException;
import mobile.kodak.base.StressTestBaseAndroid;

public class SetupCamera extends StressTestBaseAndroid{

	private String tc_name;
	private String start_setup, end_setup;
	private Device c_device;

	//MySqlDbHelper db = new MySqlDbHelper();
	DatabaseHelper db = new DatabaseHelper();
	Random ran = new Random();
	ApiHelper api;
	Terminal terminal;

	@DataProvider(name = "Data")
	public Object[][] createData() {
		return new Object[][] { { "Setup_01" }, { "Setup_02" }, { "Setup_03" }, { "Setup_04" }, { "Setup_05" },
				{ "Setup_06" }, { "Setup_07" }, { "Setup_08" }, { "Setup_09" }, { "Setup_10" }, { "Setup_11" },
				{ "Setup_12" }, { "Setup_13" }, { "Setup_14" }, { "Setup_15" }, { "Setup_16" }, { "Setup_17" },
				{ "Setup_18" }, { "Setup_19" }, { "Setup_20" }, };
	}
	@Test(priority = 1, dataProvider = "Data")
	public void TC_Setup(String tc_name) throws SerialPortException, InterruptedException {
		this.tc_name = tc_name;
		terminal = new Terminal(c_comport);
		Log.info("-------------------------------" + tc_name + "-------------------------------");
		
		start_setup = StringHelper.getCurrentDateTime();
		PageDashboard.getAddDeviceBigBtn().click();		
		PageBase.allowAndroidPermissionIfAsked(PageBase.ANDROID_PERMISSION_LOCATION);
		
		PageDashboard.getHomeSeriesImage().click();
		PageDashboard.getDeviceModelLabel("680").click();
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
		
		end_setup = StringHelper.getCurrentDateTime();
		api.userLogin(c_username, c_password);
		api.getDevices();
		c_device = api.getDeviceByDeviceId(c_deviceid);		
		PageDashboard.getCameraByName(c_device.getName()).getWebElement();
		
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) throws SerialPortException, ParseException, InterruptedException, IOException {
		DriverManager.captureScreen(tc_name);

		Log.info("----------Information----------");

		Log.info(tc_name + "_start_setup: " + start_setup);
		Log.info(tc_name + "_end_setup: " + end_setup);

		Log.info("----------End Information------");

		long setup_duration = StringHelper.getDuration(start_setup, end_setup);
		Log.info(tc_name + "_duration_setup: " + setup_duration);

		if (!result.isSuccess()) {
			c_device = new Device();
			c_device.setDevice_id(c_deviceid);
			c_device.setName("");
			c_device.setFirmware_version("");
		}
		String systemPath = StringHelper.getSystemPath();
		String fileName = (tc_name + "_" + start_setup.replace(":", "-")).toLowerCase() + ".txt";
		String fullPath = systemPath + "/html/" + fileName;
		String fileUrl = StringHelper.getBuildPath() + fileName;
		System.out.println(fullPath);

		String errorCode = "";
		String deviceLog = "";

		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		int code = db.doInsertSetupData(c_device, setup_duration, start_setup, end_setup, c_platform, errorCode, fileUrl,
				c_username + "/" + c_password, "SETUP PERFORMANCE");
		start_setup="";
		end_setup="";
		closeApp();
		
		terminal.sendCommand("reboot");
		terminal.closePort();
		TimeHelper.sleep(30 * 1000);
	}
}
