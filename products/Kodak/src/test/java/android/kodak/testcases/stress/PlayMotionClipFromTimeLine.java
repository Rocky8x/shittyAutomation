package android.kodak.testcases.stress;

import java.io.IOException;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.DatabaseHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageTimeline;
import android.kodak.object.PageVideoPlayer;
import mobile.kodak.base.StressTestBaseAndroid;

public class PlayMotionClipFromTimeLine extends StressTestBaseAndroid{
	
	private int numOfData = 20;
	
	private Device cam1, // for sdcard playback
				   cam2; // for cloud playback
	private String tc_name;
	private String deviceStatus, timeClickPlaybutton ,timeReadyVideo, timeCompleteVideoPlay, streamingMode;

	private String playSuccess;
	DatabaseHelper db = new DatabaseHelper();

	@BeforeSuite
	public void prepare() throws Exception {
		Terminal terminalCam2 = new Terminal(testParams.get("comport1"));
		cam1 = c_device;
		cam2 = c_api.getDeviceByDeviceId(terminalCam2.getCameraUdid());
	}
	
	@DataProvider(name = "PlayVideoData")
	public Object[][] createData() {
		Object data[][] = new Object[numOfData][1];
		for (int i=0; i<numOfData/2;){
			data[i][0] = "sdCard" + ++i;
		}
		
		for (int i=0; i<numOfData/2;){
			data[i+numOfData/2][0] = "cloud" + ++i;
		}
		return data;
	}

	@Test(priority = 1, dataProvider = "PlayVideoData")
	public void testPlayMdClip(String test) throws InterruptedException {
		this.tc_name = test;
		Log.info(test);
		Log.info(c_device.getName());

		playSuccess = "Play video clip failed";
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		deviceStatus = PageDashboard.waitForDeviceOnline(c_device.getName(), 30);
		if (!deviceStatus.equals("Online"))
			return;

		PageDashboard.gotoToTimelinePage();
		if (test.contains("cloud")) {
			c_device = cam2;
			streamingMode = "Cloud";
			PageTimeline.findVideoStorageInCloud();
			PageTimeline.getPlayVideoIconStorageInCloud().click();
		} else { // sdcard
			streamingMode = "sdCards";
			c_device = cam1;
			PageTimeline.findVideoStorageInSDCard();
			PageTimeline.getPlayVideoIconStorageInSDCard().click();
		}

		timeClickPlaybutton = StringHelper.getCurrentDateTime();

		PageVideoPlayer.getVideoLoading().waitForDisappear(15);
		timeReadyVideo = StringHelper.getCurrentDateTime();

		Element e = PageVideoPlayer.getVideoPlayFailTextView();
		if (e.getWebElement() != null) {
			playSuccess = e.getText().replace("'", "");
		} else {
			playSuccess = "Play video clip passed";
		}
		PageVideoPlayer.getReplayButton().waitForAppear(60);
		timeCompleteVideoPlay = StringHelper.getCurrentDateTime();

	}

	@AfterMethod
	public void AfterMethod(ITestResult result) throws ParseException, InterruptedException, IOException {
		DriverManager.captureScreen(tc_name);
		long durationReadyVideo = StringHelper.getDuration(timeClickPlaybutton, timeReadyVideo);
		long durationVideoPlay = StringHelper.getDuration(timeReadyVideo, timeCompleteVideoPlay);
		String systemPath = StringHelper.getSystemPath();
		String fileName = (tc_name + "_" + timeClickPlaybutton.replace(":", "-")).toLowerCase() + ".txt";
		String fullPath = systemPath + "/html/" + fileName;
		String fileUrl = StringHelper.getBuildPath() + fileName;
		System.out.println(fullPath);


		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		String logMessage = "";
		if (!deviceStatus.equals("Online")) {
			logMessage = deviceStatus + " " + logMessage;
		} else {
			logMessage = playSuccess;
		}
		
		Log.info("----------Information "+ tc_name + "----------");
		Log.info("Start play video: " + timeClickPlaybutton);
		Log.info("End play video: " + timeReadyVideo);
		Log.info("Playable? "+ playSuccess);
		Log.info("Video ready duration: " + durationReadyVideo);
		Log.info("Video play duration: " + durationVideoPlay);
		Log.info("----------End Information------");
		
		db.doInsertVideoData(c_device, streamingMode, durationVideoPlay, timeClickPlaybutton, TestConstant.appVersion, driverSetting.getDeviceName(), logMessage, fileUrl, c_username + "/" + c_password,
				"PLAY MOTION CLIP PERFORMANCE");

		resetInfo();
		closeApp();

		TimeHelper.sleep(2 * 60 * 1000);
	}
	
	public void resetInfo() {
		timeClickPlaybutton = "";
		timeReadyVideo = "";
	}
}
