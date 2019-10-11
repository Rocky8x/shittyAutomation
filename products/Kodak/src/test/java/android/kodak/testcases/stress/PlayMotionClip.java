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
import com.cinatic.element.Element;
import com.cinatic.log.Log;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageStreamView;
import android.kodak.object.PageVideoPlayer;
import mobile.kodak.base.StressTestBaseAndroid;

public class PlayMotionClip extends StressTestBaseAndroid{
	
	private int numOfData = 20;
	private String testType = "Streaming";
	
	private String tc_name;
	private String deviceStatus, timeClickPlaybutton ,timeReadyVideo, timeCompleteVideoPlay , streamingStatus;

	private boolean hasStream;
	private String playSuccess;
	DatabaseHelper db = new DatabaseHelper();

	@DataProvider(name = "PlayVideoData")
	public Object[][] createData() {
		Object data[][] = new Object[numOfData][1];
		for (int i=0; i<numOfData;){
			data[i][0] = testType + ++i;
		}		
		return data;
	}

	@Test(priority = 1, dataProvider = "PlayVideoData")
	public void testPlayMdClip(String tc_name) throws InterruptedException {
		this.tc_name = tc_name;
		Log.info(tc_name);
		Log.info(c_device.getName());

		streamingStatus = "Drop internet";
		hasStream = false;
		playSuccess = "Play video clip failed";
		PageDashboard.handlePermissionsAndHintsWhenPageOpen();
		deviceStatus = PageDashboard.waitForDeviceOnline(c_device.getName(), 30);

		if (deviceStatus.equals("Online")) {
			streamingStatus = "";
			PageDashboard.selectDeviceToView(c_device.getName());
			try {
				PageStreamView.closeTutorial();
				PageStreamView.SdcardNearFullWarning.getOkButton().click();
			} catch (Exception e) {
			}
			
			hasStream = PageStreamView.getStreamLoadingImg().waitForDisappear(90);
			streamingStatus = PageStreamView.getStreamMode(60);
			
			PageStreamView.get1stMDClipPlayButton().click();
			timeClickPlaybutton = StringHelper.getCurrentDateTime();
	
			PageStreamView.getVideoLoading().waitForDisappear(15);
			timeReadyVideo = StringHelper.getCurrentDateTime();


			Element e = PageVideoPlayer.getVideoPlayFailTextView();
			if (e.getWebElement() != null) {
				playSuccess = e.getText().replace("'", "");
			} else {
				playSuccess = "Play video clip passed";
			}
			
			PageVideoPlayer.getReplayButton().waitForAppear(60);
			timeCompleteVideoPlay = StringHelper.getCurrentDateTime();

			Assert.assertEquals(streamingStatus.contains("OK"), true, "Error: actual mode is " + streamingStatus);
			Assert.assertTrue(hasStream, "Error: Load streaming is " + hasStream);
		} else {
			streamingStatus = "";
			Assert.assertTrue(false, "Error: Camera is not online");
		}
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

		String logMessage = "";

		TerminalHelper.exportLogCatLucy(driverSetting.getDeviceUDID(), fullPath);
		TerminalHelper.clearLogCat(driverSetting.getDeviceUDID());

		if (!deviceStatus.equals("Online")) {
			logMessage = deviceStatus + " " + logMessage;
		} else {
			if (streamingStatus.equals("")) {
				logMessage = "Tap failed";
			} else if (hasStream == false) {
				logMessage = "Stream failed";
			} else {
				logMessage = playSuccess;
			}
		}
		
		Log.info("----------Information "+ tc_name + "----------");
		Log.info("Streaming: " + streamingStatus);
		Log.info("Start play video: " + timeClickPlaybutton);
		Log.info("End play video: " + timeReadyVideo);
		Log.info("Playable? "+ playSuccess);
		Log.info("Video ready duration: " + durationReadyVideo);
		Log.info("Video play duration: " + durationVideoPlay);
		Log.info("----------End Information------");
		
		db.doInsertVideoData(c_device, streamingStatus, durationVideoPlay, timeClickPlaybutton, TestConstant.appVersion, driverSetting.getDeviceName(), logMessage, fileUrl, c_username + "/" + c_password,
				"PLAY MOTION CLIP PERFORMANCE");

		resetInfo();
		closeApp();

		TimeHelper.sleep(2 * 60 * 1000);
	}
	
	public void resetInfo() {
		streamingStatus = "";
		timeClickPlaybutton = "";
		timeReadyVideo = "";
	}
}
