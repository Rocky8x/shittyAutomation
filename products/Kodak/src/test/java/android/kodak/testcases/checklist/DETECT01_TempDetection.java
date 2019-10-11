package android.kodak.testcases.checklist;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.ApiHelperConfig;
import com.cinatic.MQTTHelper;
import com.cinatic.TimeHelper;
import com.cinatic.object.Device;
import com.cinatic.object.MqttCommandResponse;
import com.cinatic.object.MqttObject;
import com.cinatic.object.Terminal;

import android.kodak.object.PageCameraSetting;
import android.kodak.object.PageDashboard;
import mobile.kodak.base.TestBaseAndroid;

public class DETECT01_TempDetection extends TestBaseAndroid {

	Device				device;
	Terminal			terminal;	
	MQTTHelper			mqttHelperl;
	MqttCommandResponse	mqttCommandResponse;

	@BeforeMethod
	public void Precondition() throws Exception {

		terminal = new Terminal(c_comport);
		ApiHelper api = new ApiHelper();
		api.userLogin(c_username, c_password);
		api.getDevices();
		device = api.getDeviceByDeviceId(c_deviceid);
		MqttObject o = api.registerApp();

		mqttHelperl = new MQTTHelper(ApiHelperConfig.getMqttServer(), o, device);
	}

	@Test(priority = 23,
			description = "TC004: Verify that user can change Temperature setting successfully")
	public void TC004_TemperatureDetectionUiTest() throws Exception {

		PageDashboard.openDeviceSetting(device.getName());

		// enable temp detection
		PageCameraSetting.enableTemperature();

		// get temp detection setting via MQTT command
		mqttHelperl.sendCommand("get_caminfo");
		mqttCommandResponse = new MqttCommandResponse(mqttHelperl.output);

		TimeHelper.sleep(3 * 1000);
		PageCameraSetting.disableTemperature();

	}

	@AfterMethod
	public void closeComPort() {

		try {
			terminal.closePort();
		} catch (Exception e) {}
	}
}
