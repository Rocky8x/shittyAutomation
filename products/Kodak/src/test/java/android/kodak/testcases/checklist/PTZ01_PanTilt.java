package android.kodak.testcases.checklist;

import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.object.Device;
import com.cinatic.object.Terminal;

import android.kodak.object.PageBase;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageHomeSetting;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class PTZ01_PanTilt extends TestBaseAndroid {
	ApiHelper api;
	Device device;
	Terminal com;
	
	@BeforeClass
	public void precondition() throws Exception {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		com = new Terminal(c_comport);
	}
	
	@Test(priority = 26, description = "Pan tilt with natural PTZ off")
	public void PanTiltWithNaturalPtzOffTest() {

		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		String naturalPtzOption = PageHomeSetting.getNaturalPtzSwitch().getAttribute("checked");

		if (naturalPtzOption.equals("true")) {
			PageHomeSetting.getNaturalPtzSwitch().click();
		}

		PageBase.exitPage();
		PageDashboard.selectDeviceToView(device.getName());

		// Pan tilt test
		PageStreamView.getStreamMode(60);
		boolean naturalPtz = false;
		PageStreamView.checkPanTilt(naturalPtz,com);
	}

	@Test(priority = 27, description = "Pan tilt with natural PTZ on")
	public void PanTiltWithNaturalPtzOnTest() {

		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		String naturalPtzOption = PageHomeSetting.getNaturalPtzSwitch().getAttribute("checked");

		if (naturalPtzOption.equals("false")) {
			PageHomeSetting.getNaturalPtzSwitch().click();
		}
		PageBase.exitPage();

		PageDashboard.selectDeviceToView(device.getName());

		// Pan tilt test
		PageStreamView.getStreamMode(60);
		boolean naturalPtz = true;
		PageStreamView.checkPanTilt(naturalPtz,com);
	}

	@Test(priority=29, description="Pan tilt in landscape with natural PTZ on and off")
	public void PanTiltInLandscape() {

		try {
			PageDashboard.gotoHomeMenuPage();
			PageHomeMenu.gotoHomeSetingPage();
			boolean naturalPtz;
			PageHomeSetting.getNaturalPtzSwitch().getAttribute("checked");

			// natural PTZ on
			PageHomeSetting.getNaturalPtzSwitch().setValue(true);
			PageBase.exitPage();

			PageDashboard.selectDeviceToView(device.getName());
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.LANDSCAPE);
			PageStreamView.getStreamingScreen();
			PageStreamView.getStreamMode(60);
			naturalPtz = true;
			PageStreamView.checkPanTilt(naturalPtz,com);
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.PORTRAIT);
			PageStreamView.getStreamMode(60);
			PageStreamView.exitPage();			

			// natural PTZ off
			PageDashboard.gotoHomeMenuPage();
			PageHomeMenu.gotoHomeSetingPage();
			PageHomeSetting.getNaturalPtzSwitch().setValue(false);
			
			PageBase.exitPage();
			
			PageDashboard.selectDeviceToView(device.getName());
			DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.LANDSCAPE);
			PageStreamView.getStreamingScreen();
			naturalPtz = false;
			PageStreamView.checkPanTilt(naturalPtz,com);
			
		} finally {
			try {
				DriverManager.getDriver().getAppiumDriver().rotate(ScreenOrientation.PORTRAIT);
			} catch (Exception e) {
			}
		}
	}
	
	@AfterMethod
	public void closeComPort() {
		try {
			com.closePort();
		} catch (Exception e) {	}
	}
}
