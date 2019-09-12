package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Device;

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageHomeMenu;
import android.kodak.object.PageHomeSetting;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class HUMTMP02_TempUnitSetting  extends TestBaseAndroid {
	ApiHelper api;
	Device device;
	
	@BeforeMethod
	public void Precondition() {
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
	}
	
	@Test(priority = 11, description = "Verify temperature (C and F) will show according phone setting")
	public void verifyTemperatureUnitSetting() {
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		// Set Celsius temperature unit
		PageHomeSetting.clickCelsiusUnit();
		PageBase.exitPage();
		
		// Go to camera setting page and verify Celsius temperature unit
		PageDashboard.openDeviceSetting(device.getName());
		PageCameraSetting.enableTemperature();
		// verify temperature unit
		boolean rs = PageCameraSetting.verifyTemperatureUnitInDeviceSetting("℃");
		Assert.assertTrue(rs, "Temperature unit should display with Celsius in camera setting.");
		PageBase.exitPage();
		
		// Go to stream view page and verify Celsius temperature unit
		PageDashboard.selectDeviceToView(device.getName());
		rs = PageStreamView.verifyTemperatureUnitInStreamPage("℃");
		Assert.assertTrue(rs, "Temperature unit should display with Celsius in stream view.");
		PageStreamView.exitPage();
		
		PageDashboard.gotoHomeMenuPage();
		PageHomeMenu.gotoHomeSetingPage();
		// Set Fahrenheit temperature unit
		PageHomeSetting.clickFahrenheitUnit();
		PageBase.exitPage();
		
		// Go to camera setting page and verify Fahrenheit temperature unit
		PageDashboard.tryCloseGrantAccessAndPreviewTip();
		PageDashboard.openDeviceSetting(device.getName());
		rs = PageCameraSetting.verifyTemperatureUnitInDeviceSetting("℉");
		Assert.assertTrue(rs, "Temperature unit should display with Fahrenheit in camera setting.");
		PageBase.exitPage();
		
		// Go to stream view page and verify Fahrenheit temperature unit
		PageDashboard.selectDeviceToView(device.getName());
		rs = PageStreamView.verifyTemperatureUnitInStreamPage("℉");
		Assert.assertTrue(rs, "Temperature unit should display with Fahrenheit in strean view.");
		PageStreamView.exitPage();
	}
}
