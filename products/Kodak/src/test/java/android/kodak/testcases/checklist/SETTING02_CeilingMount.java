package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelperConfig;
import com.cinatic.ApiHelper;
import com.cinatic.MQTTHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.MqttCommandResponse;
import com.cinatic.object.MqttObject;
import com.cinatic.object.Terminal;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import mobile.kodak.base.TestBaseAndroid;

public class SETTING02_CeilingMount extends TestBaseAndroid {

	private Device device;
	ApiHelper api;
	Terminal terminal;
	MQTTHelper mqttHelperl;
	MqttCommandResponse mqttCommandResponse;

	@BeforeMethod
	public void Precondition() throws Exception{
		api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		MqttObject o = api.registerApp();
		
		mqttHelperl = new MQTTHelper(
				ApiHelperConfig.getMqttServer()
				, o
				, device);
		terminal = new Terminal(c_comport);
	}

	@Test(priority = 20, description = "TC001: Verify that user can change Ceiling Mount setting successfully")
	public void verifyCeilingMountByMqtt() throws Exception {
		PageGetStart.checkAndSignin(c_username, c_password);
		PageDashboard.openDeviceSetting(device.getName());
		
		PageCameraSetting.enableCeilingMount();
		TimeHelper.sleep(3 *1000);		
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		String value = mqttCommandResponse.getAttribute("flipup");
		Assert.assertEquals(value, "1", "Error: actual result is " + value);

		PageCameraSetting.disableCeilingMount();
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("flipup");
		Assert.assertEquals(value, "0", "Error: actual result is " + value);
	}
	
	@AfterMethod
	public void closeComPort() {
		try {
			terminal.closePort();
		} catch (Exception e) {	}
	}
}
