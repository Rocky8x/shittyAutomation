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

import android.kodak.object.PageBase;
import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import mobile.kodak.base.TestBaseAndroid;

public class DETECT03_MotionDetection extends TestBaseAndroid {
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
	
	@Test(priority = 21, description = "TC002: Verify that user can change Motion Detection setting successfully")
	public void uiTest_MotionDetection() throws Exception{
		PageDashboard.openDeviceSetting(device.getName());
		
		PageCameraSetting.enableMotionDetection();
		PageBase.closeCouldUpgradeTipIfAsked();
		TimeHelper.sleep(3 *1000);
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		String value = mqttCommandResponse.getAttribute("md");
		Assert.assertEquals(value, "1:1:3:0", "Error: actual result is " + value);

		PageCameraSetting.changeMotionSensitivityLevel(TestConstant.motionLevel.Low);
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("md");
		Assert.assertEquals(value, "1:1:1:0", "Error: actual result is " + value);
		
		PageCameraSetting.changeMotionSensitivityLevel(TestConstant.motionLevel.High);
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("md");
		Assert.assertEquals(value, "1:1:5:0", "Error: actual result is " + value);
		
		PageCameraSetting.changeMotionSensitivityLevel(TestConstant.motionLevel.Medium);

		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("md");
		Assert.assertEquals(value, "1:1:3:0", "Error: actual result is " + value);
		
		PageCameraSetting.disableMotionDetection();
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);
		value = mqttCommandResponse.getAttribute("md");
		Assert.assertEquals(value, "0:0:3:0", "Error: actual result is " + value);
		PageCameraSetting.enableMotionDetection();
	}
	
	@AfterMethod
	public void closeComPort() {
		try {
			terminal.closePort();
		} catch (Exception e) {	}
	}
	
}
