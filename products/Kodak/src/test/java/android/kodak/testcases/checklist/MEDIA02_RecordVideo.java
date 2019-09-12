package android.kodak.testcases.checklist;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.TerminalHelper;
import com.cinatic.log.Log;
import com.cinatic.object.Device;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageStreamView;
import jssc.SerialPortException;
import mobile.kodak.base.TestBaseAndroid;

public class MEDIA02_RecordVideo extends TestBaseAndroid {
	private String username;
	private String password;
	private Device device;	

	@BeforeMethod
	public void Precondition() throws SerialPortException {
		this.username = c_username;
		this.password = c_password;

		ApiHelper api;
		if (c_server.equals("production")) {
			api = new ApiHelper();
			api.userLogin(c_username, c_password);
			api.getDevices();
			device = api.getDeviceByDeviceId(c_deviceid);
		}
	}

	@Test(priority = 11, description = "TC001: Verify that user can record video successfully")
	public void TC001_RecordVideo() throws Exception {
		PageGetStart.checkAndSignin(username, password);
		PageDashboard.selectDeviceToView(device.getName());
		PageStreamView.getStreamMode(60);				
		PageStreamView.startRecordVideo(30);
		PageStreamView.stopRecordVideo();		
		String timeAfterRecord = TerminalHelper.exeBashCommand("adb shell date +\"%B %d %Y %I:%M:%S %p\"");
		PageStreamView.exitToDashboard();
		PageDashboard.openVideoGalleryTab();		
		String videoGalleryTime = PageDashboard.getFirstVieoGalleryInfo();		
		long timeGap = StringHelper.getDuration("MMM dd yyyy hh:mm:ss a", timeAfterRecord, videoGalleryTime);
		Log.info("Time: \n"
				+ "     Before record:    " + timeAfterRecord
				+ "     Video stop time:  " + videoGalleryTime + "\n"
				+ "     Gap:              " + timeGap );
		
		// time gap between record video start time and before tapping start record button must < 10s
		assertTrue(Math.abs(timeGap) < 5000, "Gap: " + timeGap ); 
	}
}
