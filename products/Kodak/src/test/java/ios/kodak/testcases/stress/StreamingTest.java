package ios.kodak.testcases.stress;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.DatabaseHelper;
import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;
import com.cinatic.object.Device;

import ios.kodak.object.PageBase;
import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageDeviceSettings;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class StreamingTest extends TestBaseIOS{
	ApiHelper api;
	Device device;
	private String deviceStatus = "", startStream = "", endStream = "", streamMode = "";
	private boolean hasStream;
	DatabaseHelper db = new DatabaseHelper();
	boolean deleteEventFlag = false;
	String tc_name;
	String appVersion;
	
	@BeforeClass
	public void setup() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	
	@Test(priority = 1, dataProvider = "Data")
	public void viewStreamingTest(String tc_name) {
		this.tc_name = tc_name;
		Log.info(tc_name);
		Log.info(device.getName());

		streamMode = "Drop internet";
		hasStream = false;
		
		if(deleteEventFlag) {
			boolean dashBoardDisplay = PageDashboard.isDisplayed();
			if (dashBoardDisplay)
				startStream = StringHelper.getCurrentDateTime();
			else {
				return;
			}
		}
		deviceStatus = PageDashboard.getCameraStatusByName(device.getName());
		
		if (deviceStatus.equals("Online")) {
			// Delete all event
			if(!deleteEventFlag) {
				PageGetStart.checkAndSignin(c_username, c_password);
				appVersion = PageDashboard.getCurrentAppVersion();
				PageDashboard.clickHomeButton();
				PageDashboard.openDeviceSetting(device.getName());
				PageDeviceSettings.gotoDetailsCamera();
				PageDeviceSettings.deleteAllEvent();
				deleteEventFlag = true;
				PageBase.clickBackButton();
				startStream = StringHelper.getCurrentDateTime();
			}
			
			streamMode = "";
			hasStream = true;
			PageDashboard.getCameraByName(device.getName()).click();
			streamMode = PageStreamView.getStreamMode(10);
			endStream = StringHelper.getCurrentDateTime();
			
			if(streamMode.contains("OK"))
				hasStream = true;

//			Assert.assertEquals(streamMode.contains("OK"), true, "Error: actual mode is " + streamMode);
//			Assert.assertTrue(hasStream, "Error: Load streaming is " + hasStream);
		} else {
			streamMode = "";
			startStream = "";
			Assert.assertTrue(false, "Error: Camera is not online");
		}
		
	}
	
	@DataProvider(name = "Data")
	public Object[][] createData() {
		return new Object[][] { { "Streaming_01" }, { "Streaming_02" }, { "Streaming_03" }, { "Streaming_04" },
				{ "Streaming_05" }, { "Streaming_06" }, { "Streaming_07" }, { "Streaming_08" }, { "Streaming_09" },
				{ "Streaming_10" }, { "Streaming_11" }, { "Streaming_12" }, { "Streaming_13" }, { "Streaming_14" },
				{ "Streaming_15" }, { "Streaming_16" }, { "Streaming_17" }, { "Streaming_18" }, { "Streaming_19" },
				{ "Streaming_20" }};
	}
	
	@AfterMethod
	@Override
	public void afterMethod(ITestResult result) throws InterruptedException{
		try {
			DriverManager.captureScreen(tc_name);

			Log.info("----------Information----------");

			Log.info(tc_name + " Device status" + deviceStatus);
			Log.info(tc_name + " Streaming status: " + streamMode);
			Log.info(tc_name + " Start stream: " + startStream);
			Log.info(tc_name + " End stream: " + endStream);
			
			Log.info("----------End Information------");

			long streamingDuration = 0;
			try {
				streamingDuration = StringHelper.getDuration(startStream, endStream);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.info("Stream duration: " + streamingDuration);

			String systemPath = StringHelper.getSystemPath();
			// Currently i havent found any method to get iphone log but just prepare the file name in case  i can get the log from the phone in the future
			String logFileName = tc_name + ("_" + startStream.replace(":", "-")).toLowerCase() + ".txt";
			String fileUrl = StringHelper.getBuildPath() + logFileName;

			String errorCode = "";
			String deviceLog = "";

			if (!deviceStatus.equals("Online")) {
				errorCode = deviceStatus + " " + errorCode;
			} else {
				if (streamMode.equals("")) {
					errorCode = "Tap failed";
				} else if (hasStream == false) {
					errorCode = "Stream failed";
				} else {
					errorCode = streamMode;
				}
			}

			db.doInsertStreamData(device, streamMode, streamingDuration, startStream, appVersion, c_platform, errorCode, fileUrl, c_username + "/" + c_password,
					"STREAMING PERFORMANCE");

			streamMode = "";
			startStream = "";
			endStream = "";
			DriverManager.getDriver().getAppiumDriver().quit();
			TimeHelper.sleep(90 * 1000);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
