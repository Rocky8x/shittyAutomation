package android.kodak.testcases.bugs;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.element.Element;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageHomeSetting;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class Bug6353_PtzAfterBackFromSetting extends TestBaseAndroid{

	private Device device;
	private Terminal tty;
	
	@BeforeClass
	public void prepare() throws Exception {
		ApiHelper apiHelper = new ApiHelper();
		apiHelper.userLogin(c_username, c_password);
		apiHelper.getDevices();
		device = apiHelper.getDeviceByDeviceId(c_deviceid);
		tty = new Terminal(c_comport);
	}
	
	@Test (description="[Kodak] Cannot pan tilt after going to camera setting from streaming view and back")
	public void panTiltAfterOpenCameraSettingAndBackToStreaming() {
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		Element naturalPtzSw = PageHomeSetting.getNaturalPtzSwitch();
		
		if (naturalPtzSw.getAttribute("checked").equals("true")) {
			naturalPtzSw.click();
		};		
		PageBase.exitPage();		
		PageDashboard.selectDeviceToView(device.getName());
		
		String cameraExpectCommand="";
		
		tty.clearTeratermLog();
		PageStreamView.panTilt("left");
		assertTrue(tty.getTeratermLog().contains("req=motor_left"),"Camera did not receive req=motor_left");
		assertFalse(tty.getTeratermLog().contains("req=motor_right"));
		
		tty.clearTeratermLog();
		PageStreamView.panTilt("right");
		assertTrue(tty.getTeratermLog().contains("req=motor_right"),"Camera did not receive req=motor_right");
		assertFalse(tty.getTeratermLog().contains("req=motor_left"));

		tty.clearTeratermLog();
		PageStreamView.panTilt("up");
		assertTrue(tty.getTeratermLog().contains("req=motor_up"),"Camera did not receive req=motor_up");
		assertFalse(tty.getTeratermLog().contains("req=motor_down"));
		
		tty.clearTeratermLog();
		PageStreamView.panTilt("down");
		assertTrue(tty.getTeratermLog().contains("req=motor_down"),"Camera did not receive req=motor_down");
		assertFalse(tty.getTeratermLog().contains("req=motor_up"));
	
		// go to camera setting then go back to stream view
		PageStreamView.getCameraSettingBtn().click();
		PageCameraSetting.getBatteryLevelValue().getWebElement();
		PageBase.exitPage();
		
		//check if PTZ still works
		tty.clearTeratermLog();
		PageStreamView.panTilt("left");
		assertTrue(tty.getTeratermLog().contains("req=motor_left"),"Camera did not receive req=motor_left");
		assertFalse(tty.getTeratermLog().contains("req=motor_right"));

		tty.clearTeratermLog();
		PageStreamView.panTilt("right");
		assertTrue(tty.getTeratermLog().contains("req=motor_right"),"Camera did not receive req=motor_right");
		assertFalse(tty.getTeratermLog().contains("req=motor_left"));
		
		tty.clearTeratermLog();
		PageStreamView.panTilt("up");
		assertTrue(tty.getTeratermLog().contains("req=motor_up"),"Camera did not receive req=motor_up");
		assertFalse(tty.getTeratermLog().contains("req=motor_down"));

		tty.clearTeratermLog();
		PageStreamView.panTilt("down");
		assertTrue(tty.getTeratermLog().contains("req=motor_down"),"Camera did not receive req=motor_down");
		assertFalse(tty.getTeratermLog().contains("req=motor_up"));

	}
	
	@AfterClass
	public void cleanup() throws Exception{
		tty.closePort();
	}
}
