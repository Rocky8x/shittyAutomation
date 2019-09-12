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

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseIOS;

public class PlayMotionClip extends TestBaseIOS{
	
	private String device_status = "", device_mode = "", clipType = "";
	private String startPlayVideo = "", endPlayVideo = "", readyPlayVideo = "";
	private Device c_device;

	private boolean hasStream;
	private String playSuccess;
	
	DatabaseHelper db = new DatabaseHelper();
	ApiHelper api;
	String tc_name;
	
	@BeforeClass
	public void setUp() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		c_device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 1, dataProvider = "sdCardMDClip")
	public void testPlayMdClip(String tc_name) throws InterruptedException {
		this.tc_name = tc_name;
		Log.info(tc_name);
		Log.info(c_device.getName());
		clipType = tc_name.substring(0, tc_name.length() - 8);
		Log.info(clipType);

		device_mode = "Drop internet";
		hasStream = false;
		playSuccess = "Play video clip failed";
		device_status = PageDashboard.getCameraStatusByName(c_device.getName());

		if (device_status.equals("Online")) {
			device_mode = "";
			PageDashboard.getCameraByName(c_device.getName()).click();;
			PageStreamView.closeWarningCloudStorageUpdate();
			device_mode = PageStreamView.getStreamMode(60);
			
			boolean rs = PageStreamView.getMotionVideo(clipType);
			if(!rs) {
				playSuccess = "There is no motion clip storage in " + clipType;
				Assert.fail(playSuccess);
			}
			if(clipType.equalsIgnoreCase(PageStreamView.SDCARD)) {
				PageStreamView.playSDcardMotionClip();
			}else if(clipType.equalsIgnoreCase(PageStreamView.CLOUD)){
				PageStreamView.playCloudMotionClip();
			}else {
				return;
			}
			
			startPlayVideo = StringHelper.getCurrentDateTime();
			if(PageStreamView.getDownloadButton().waitForAppear(30)) {
				readyPlayVideo = StringHelper.getCurrentDateTime();
			}
			
			PageStreamView.getReplayIcon().waitForAppear(60);
			endPlayVideo = StringHelper.getCurrentDateTime();
			String message = PageStreamView.verifyPlayMotionClipSuccessfull();
			if (message.equals(""))
				playSuccess = "Play video clip passed";
			else
				playSuccess = message;
			
			if (device_mode.contains("OK"))
				hasStream = true;
			
			Assert.assertEquals(device_mode.contains("OK"), true, "Error: actual mode is " + device_mode);
			Assert.assertTrue(hasStream, "Error: Load streaming is " + hasStream);
		} else {
			device_mode = "";
			Assert.assertTrue(false, "Error: Camera is not online");
		}
	}
	
	@AfterMethod
	@Override
	public void afterMethod(ITestResult result) throws InterruptedException {
		try {
			DriverManager.captureScreen(tc_name);
			Log.info("----------Information----------");

			Log.info(tc_name + " Device status" + device_status);
			Log.info(tc_name + " Streaming status: " + device_mode);
			Log.info(tc_name + " Start video: " + startPlayVideo);
			Log.info(tc_name + " Time video ready " + readyPlayVideo);
			Log.info(tc_name + " End video: " + endPlayVideo);

			Log.info("----------End Information------");

			long playDuration = 0;
			long clipReadyDuration = 0;
			try {
				playDuration = StringHelper.getDuration(startPlayVideo, endPlayVideo);
				clipReadyDuration = StringHelper.getDuration(startPlayVideo, readyPlayVideo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.info(tc_name + "_duration_play_video: " + playDuration);

			String systemPath = StringHelper.getSystemPath();
			String fileName = tc_name + ("_" + startPlayVideo.replace(":", "-")).toLowerCase() + ".txt";
			String fileUrl = StringHelper.getBuildPath() + fileName;

			String errorCode = "";

			if (!device_status.equals("Online")) {
				errorCode = device_status + " " + errorCode;
			} else {
				if (device_mode.equals("")) {
					errorCode = "Tap failed";
				} else if (hasStream == false) {
					errorCode = "Stream failed";
				} else {
					errorCode = device_mode + " - " + playSuccess;
				}
			}

			db.doInsertVideoData(c_device, clipType, playDuration, startPlayVideo, clipReadyDuration + "", c_platform, errorCode, fileUrl, c_username + "/" + c_password,
					"PLAY MOTION CLIP PERFORMANCE");

			device_mode = "";
			startPlayVideo = "";
			
			endPlayVideo = "";
			clipType = "";
			
			DriverManager.getDriver().getAppiumDriver().quit();
			TimeHelper.sleep(10 * 1000);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	@DataProvider(name = "sdCardMDClip")
	public Object[][] sdCardMDClip() {
		return new Object[][] { 
				{ "sdCardMDClip01" }, { "sdCardMDClip02" }, { "sdCardMDClip03" }, { "sdCardMDClip04" },
				{ "sdCardMDClip05" }, { "sdCardMDClip06" }, { "sdCardMDClip07" }, { "sdCardMDClip08" },
				{ "sdCardMDClip09" }, { "sdCardMDClip10" }, { "sdCardMDClip11" }, { "sdCardMDClip12" },
				{ "sdCardMDClip13" }, { "sdCardMDClip14" },	{ "sdCardMDClip15" }, { "sdCardMDClip16" },
				{ "sdCardMDClip17" }, { "sdCardMDClip18" }, { "sdCardMDClip19" }, { "sdCardMDClip20" },
		};
	}
	
	@DataProvider(name = "cloudMDClip")
	public Object[][] cloudMDClip() {
		return new Object[][] { 
				{ "cloudMDClip01" }, { "cloudMDClip02" }, { "cloudMDClip03" }, { "cloudMDClip04" },
				{ "cloudMDClip05" }, { "cloudMDClip06" }, { "cloudMDClip07" }, { "cloudMDClip08" },
				{ "cloudMDClip09" }, { "cloudMDClip10" }, { "cloudMDClip11" }, { "cloudMDClip12" },
				{ "cloudMDClip13" }, { "cloudMDClip14" }, { "cloudMDClip15" }, { "cloudMDClip16" },
				{ "cloudMDClip17" }, { "cloudMDClip18" }, { "cloudMDClip19" }, { "cloudMDClip20" }, 
		};
	}
}
