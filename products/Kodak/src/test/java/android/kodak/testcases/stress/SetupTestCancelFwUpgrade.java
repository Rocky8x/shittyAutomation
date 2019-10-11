package android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.DatabaseHelper;
import com.cinatic.FileHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.driver.DriverSetting;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import jssc.SerialPortException;

public class SetupTestCancelFwUpgrade {
	private static ApplicationContext context;

	private String c_platform, c_server, c_username, c_password, c_deviceid, c_devicessid, c_comport,
			c_wifipassword, tc_name;
	private String start_setup, end_setup;
	private Device c_device;

	private DriverSetting driverSetting;

	//MySqlDbHelper db = new MySqlDbHelper();
	DatabaseHelper db = new DatabaseHelper();
	Random ran = new Random();
	ApiHelper api;
	Terminal terminal;

	@Parameters({ "platform", "server", "username", "password", "deviceid", "devicessid", "comport", "wifiname",
			"wifipassword" })
	@BeforeSuite
	public void berforeSuite(String platform, String server, String username, String password, String deviceid,
			String devicessid, String comport, String wifiname, String wifipassword)
			throws IOException, InterruptedException {
		
		context = new ClassPathXmlApplicationContext("Bean.xml");
		String systemPath = StringHelper.getSystemPath();
		try {
			FileHelper.clearFolder(systemPath + "/html/");
			FileHelper.clearFolder(systemPath + "/allure-results/");
		} catch (Exception e) {
			
		}
	

		this.c_server = server;
		this.c_platform = platform;
		this.c_username = username;
		this.c_password = password;
		this.c_deviceid = deviceid;
		this.c_devicessid = devicessid;
		this.c_comport = comport;
		this.c_wifipassword = wifipassword;

		if (System.getProperty("suitePlatform") != null) {
			this.c_platform = System.getProperty("suitePlatform");
		}

		if (System.getProperty("suiteUsername") != null) {
			this.c_username = System.getProperty("suiteUsername");
		}

		if (System.getProperty("suitePassword") != null) {
			this.c_password = System.getProperty("suitePassword");
		}

		if (System.getProperty("suiteUdid") != null) {
			this.c_deviceid = System.getProperty("suiteUdid");
		}

		if (this.c_server.equals("production")) {
			api = new ApiHelper();
		} else if (this.c_server.equals("staging")) {
			api = new ApiHelper();
		}

		launchApp();
		PageGetStart.checkAndSignin(c_username, c_password);	
		closeApp();

	}

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
		
		PageDashboard.getProceedUpgradeButton();
		end_setup = StringHelper.getCurrentDateTime();

		closeApp();
		launchApp();
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

		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		db.doInsertSetupData(c_device, setup_duration, start_setup, end_setup, c_platform, errorCode, fileUrl,
				c_username + "/" + c_password, "SETUP PERFORMANCE");
		start_setup="";
		end_setup="";
		closeApp();
		
		terminal.sendCommand("reboot");
		terminal.closePort();
		TimeHelper.sleep(60 * 1000);
	}
	
	@BeforeMethod
	private void launchApp() {
		driverSetting = (DriverSetting) context.getBean(c_platform);
		DriverManager.createWebDriver(driverSetting);
	}

	private void closeApp() {
		DriverManager.getDriver().quit();
	}
}
