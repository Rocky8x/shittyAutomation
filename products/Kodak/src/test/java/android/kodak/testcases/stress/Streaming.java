package android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.DatabaseHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.driver.DriverManager;
import com.cinatic.log.Log;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.StressTestBaseAndroid;

public class Streaming extends StressTestBaseAndroid {

	private int numOfData = 20;
	private String testType = "Streaming"; // sdCardClip or cloudClip
	
	private String tc_name;
	private String device_status, startStreaming, endStreaming, device_mode;

	private boolean hasStream;

	DatabaseHelper db = new DatabaseHelper();

	@DataProvider(name = "Data")
	public Object[][] createData() {
		Object data[][] = new Object[numOfData][1];
		for (int i=0; i<numOfData;){
			data[i][0] = testType + ++i;
		}		
		return data;
	}

	@Test(priority = 1, dataProvider = "Data")
	public void TC_View_Stream(String tc_name) throws InterruptedException {
		this.tc_name = tc_name;
		Log.info(tc_name);
		Log.info(c_device.getName());

		device_mode = "Drop internet"; 
		hasStream = false;
		PageDashboard.isDisplayed();
		startStreaming = StringHelper.getCurrentDateTime();
		device_status = PageDashboard.waitForDeviceOnline(c_device.getName(), 30);
		
		if (device_status.equals("Online")) {
			device_mode = "";
			PageDashboard.getCameraByName(c_device.getName()).click();
			hasStream = PageStreamView.getStreamLoadingImg().waitForDisappear(90);
			device_mode = PageStreamView.getStreamMode(60);
			endStreaming = StringHelper.getCurrentDateTime();

			Assert.assertEquals(device_mode.contains("OK"), true, "Error: actual mode is " + device_mode);
			Assert.assertTrue(hasStream, "Error: Load streaming is " + hasStream);
		} else {
			device_mode = "";
			Assert.assertTrue(false, "Error: Camera is not online");
		}
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) throws ParseException, InterruptedException, IOException {
		DriverManager.captureScreen(tc_name);

		Log.info("----------Information----------");

		Log.info(tc_name + "_device_mode: " + device_mode);
		Log.info(tc_name + "_start_device: " + startStreaming);
		Log.info(tc_name + "_end_device: " + endStreaming);

		Log.info("----------End Information------");

		long streaming_duration = StringHelper.getDuration(startStreaming, endStreaming);
		Log.info(tc_name + "_duration_cam: " + StringHelper.getDuration(startStreaming, endStreaming));

		String systemPath = StringHelper.getSystemPath();
		String fileName = (tc_name + "_" + startStreaming.replace(":", "-")).toLowerCase() + ".txt";
		String fullPath = systemPath + "/html/" + fileName;
		String fileUrl = StringHelper.getBuildPath() + fileName;
		System.out.println(fullPath);

		String errorCode = "";
		String deviceLog = "";

		// deviceLog = TerminalHelper.getLogCat(driverSetting.getDeviceUDID(),
		// "errorCode", c_device.getDevice_id());
		// Pattern p = Pattern.compile("errorCode = -?\\d+");
		// java.util.regex.Matcher m = p.matcher(deviceLog);
		// String strLastLine = "";
		// while (m.find()) {
		// if (!errorCode.contains(m.group() + ","))
		// errorCode += m.group() + ",";
		// }
		// if (errorCode.length() > 0)
		// errorCode = errorCode.substring(0, errorCode.length() - 1);
		//
		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		if (!device_status.equals("Online")) {
			errorCode = device_status + " " + errorCode;
		} else {
			if (device_mode.equals("")) {
				errorCode = "Tap failed";
			} else if (hasStream == false) {
				errorCode = "Stream failed";
			} else {
				errorCode = device_mode;
			}
		}

		db.doInsertStreamData(c_device, device_mode, streaming_duration, startStreaming, TestConstant.appVersion, c_platform, errorCode, fileUrl, c_username + "/" + c_password,
				"STREAMING PERFORMANCE");

		device_mode = "";
		startStreaming = "";
		endStreaming = "";
		closeApp();

		TimeHelper.sleep(90 * 1000);
	}
}
