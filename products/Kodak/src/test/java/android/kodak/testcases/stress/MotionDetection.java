package android.kodak.testcases.stress;

import java.text.ParseException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.DatabaseHelper;
import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.driver.WebDriverHelper;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class MotionDetection extends TestBaseAndroid{
	Device device;
	String videoUrl = "https://www.youtube.com/watch?v=94LNZwX4gQI";
	private SerialPort serialPort;
	String response, startTime, eventTime, teratermLog;
	int countEvent = 0;
	DatabaseHelper db;
	ApiHelper api;
	@BeforeMethod
	public void Precondition() throws SerialPortException {
		serialPort = new SerialPort(c_comport);
		db = new DatabaseHelper();
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 1, description = "Verify camera can trigger motion events continuously")
	public void verifyCameraCanTriggerEventsContinuously() throws SerialPortException {
		int timePlayVideo = 7;
		long threshold = 3*60*1000;
		PageGetStart.checkAndSignin(c_username, c_password);
		// Enable motion detection
		PageDashboard.openDeviceSetting(device.getName());
		PageCameraSetting.enableMotionDetection();
		PageCameraSetting.changeMotionSensitivityLevel(TestConstant.motionLevel.High);
		PageBase.exitPage();
		
		// Open Youtube and get event motion detection
		WebDriverHelper webHelper = new WebDriverHelper();
		webHelper.openBrowserAndNavigatetoUrl(videoUrl);
		// Open with full screen mode
		webHelper.findElement(By.xpath("//button[contains(@class, 'fullscreen')]")).click();
		startTime = StringHelper.getCurrentDateTime();
		serialPort.openPort();
		serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		// Listen event in comport
		serialPort.addEventListener(new SerialPortEventListener() {
			@Override
			public void serialEvent(SerialPortEvent serialPortEvent) {
				try {
					response = StringHelper.cleanInvalidCharacters(serialPort.readString(serialPortEvent.getEventValue()));
					teratermLog += response;
					if(teratermLog.contains("'MD' -->")) {
						countEvent ++;
						eventTime = StringHelper.getCurrentDateTime();
						long event_duration = StringHelper.getDuration(startTime, eventTime);
						startTime = eventTime;
						String result = event_duration < threshold ? "PASSED" : "FAILED";
						String fileLog = StringHelper.getStringByString(teratermLog, "clips_md", ".flv", true) + ".flv";
						teratermLog = "";
						// Insert data into database
						db.doInsertMotionDetectionData(device, event_duration, eventTime, driverSetting.getDeviceName(), c_username + "/" + c_password, fileLog, result, null, "MOTION DETECTION PERFORMANCE");
					}
				} catch (SerialPortException e) {
					Log.error(e.getMessage());
				} catch (ParseException ex) {
					// TODO Auto-generated catch block
					Log.error(ex.getMessage());
				}
			}
		});
		
		TimeHelper.sleep(timePlayVideo*60*1000);
		webHelper.closeBrowser();
		serialPort.closePort();
//		// Motion detection each 3mins
		Assert.assertTrue(countEvent >= (timePlayVideo/3));
	}
	
	@Test(priority = 1, description = "Verify motion detection sync between firmware and app", invocationCount = 1)
	public void verifyMotionEventSyncBetweenFirmwareAndApp() throws SerialPortException, ParseException {
		PageGetStart.checkAndSignin(c_username, c_password);
		
		// Enable motion detection
		PageDashboard.openDeviceSetting(device.getName());
		PageCameraSetting.enableMotionDetection();
		PageCameraSetting.changeMotionSensitivityLevel(TestConstant.motionLevel.High);
		PageBase.exitPage();
		// Open Youtube and get event motion detection
		WebDriverHelper webHelper = new WebDriverHelper();
		webHelper.openBrowserAndNavigatetoUrl(videoUrl);
		// Open with full screen mode
		webHelper.findElement(By.xpath("//button[contains(@class, 'fullscreen')]")).click();
		
		startTime = StringHelper.getCurrentDateTime();
		// Send trash command to check motion detection in 3mins
		Terminal terminal = new Terminal(c_comport);
		boolean rs = terminal.sendCommand("", "'MD' -->", 180);
		String result = "FAILED";
		String fileLog;
		String detectionTime;
		if(rs) {
			// Prepare data
			eventTime = StringHelper.getCurrentDateTime();
			long event_duration = StringHelper.getDuration(startTime, eventTime);
			teratermLog = terminal.getTeratermLog();
			fileLog = StringHelper.getStringByString(teratermLog, "clips_md", ".flv", true) + ".flv";
			// get latest event in stream page
			PageDashboard.selectDeviceToView(device.getName());
			detectionTime = PageStreamView.getMotionEventTime();
			if (StringHelper.getTimeStamp("hh:mm a", eventTime).equalsIgnoreCase(detectionTime)) {
				result = "PASSED";
			}
			
			db.doInsertMotionDetectionData(device, event_duration, eventTime, driverSetting.getDeviceName(), c_username + "/" + c_password, fileLog, result, detectionTime, "CHECK MOTION DETECTION IN FIRMWARE AND APP");
		}
		terminal.closePort();
		webHelper.closeBrowser();
	}
}
