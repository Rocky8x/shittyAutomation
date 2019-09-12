package android.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelperConfig;
import com.cinatic.ApiHelper;
import com.cinatic.MQTTHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.object.Device;
import com.cinatic.object.MqttCommandResponse;
import com.cinatic.object.MqttObject;
import com.cinatic.object.Terminal;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import mobile.kodak.base.TestBaseAndroid;

public class DETECT02_SoundDetection extends TestBaseAndroid {
	Device device;
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
	
	@Test(priority = 22, description = "TC003: Verify that user can change Sound Detection setting successfully")
	public void TC003_SoundDetection() throws Exception{
		PageDashboard.openDeviceSetting(device.getName());
		
		PageCameraSetting.enableSoundDetection();
		TimeHelper.sleep(3 *1000);
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		String value = mqttCommandResponse.getAttribute("sd");
		Assert.assertEquals(value.matches("1:2:\\d:0"), true, "Error: actual result is " + value);

		PageCameraSetting.changeSoundSensitivityLevel(TestConstant.soundLevel.Low);
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("sd");
		Assert.assertEquals(value, "1:2:1:0", "Error: actual result is " + value);
		
		PageCameraSetting.changeSoundSensitivityLevel(TestConstant.soundLevel.High);
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("sd");
		Assert.assertEquals(value, "1:2:5:0", "Error: actual result is " + value);
		
		PageCameraSetting.changeSoundSensitivityLevel(TestConstant.soundLevel.Medium);
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("sd");
		Assert.assertEquals(value, "1:2:3:0", "Error: actual result is " + value);
		
		PageCameraSetting.disableSoundDetection();
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("sd");
		Assert.assertEquals(value, "0:2:3:0", "Error: actual result is " + value);
	}
	
	@AfterMethod
	public void closeComPort() {
		try {
			terminal.closePort();
		} catch (Exception e) {	}
	}
}
