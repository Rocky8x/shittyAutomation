package com.ebn.mqtt.airpurifier.tests;

import java.io.File;

import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TenantObjects;
import com.ebn.mqtt.SendMqttCommandTestBase;

public class AirPurifierMqttStressTestPowerState extends SendMqttCommandTestBase {

	String	commands[]	= {	"power_state_set&value=0",
							"power_state_set&value=1" };
	int		loop		= 5;

	public AirPurifierMqttStressTestPowerState() {

		tenantClass							= TenantObjects.AirPurifier.class;
		htmlMqttCommandSendReportTemplate	= "../Docs" + File.separatorChar + "Templates"
				+ File.separatorChar + "ap_mqtt_command_result_template.html";
	}

	@Test
	public void test() throws Exception {

		for (int i = 0; i < loop; i++) {
			for (String string : commands) {
				String starttime = StringHelper.getCurrentDateTime();
				mqttHelper.sendCommand(string);
				String	mqttMessage	= mqttHelper.output;
				String	endtime		= StringHelper.getCurrentDateTime();
				Long	duration	= StringHelper.getDuration(starttime, endtime);
				TimeHelper.sleep(Integer.parseInt(testParams.get("timeGap")) * 1000);

				String	result;
				String	expectedMsg	= "&power_state_set: 0";
				if (mqttMessage.toString().contains(expectedMsg)) {
					result = "passed";
				} else {
					result = "failed";
				}

				appendResult(string, mqttMessage, result, starttime, duration, expectedMsg,
						DsuiteBuildUrl);
			}
		}
	}
}
