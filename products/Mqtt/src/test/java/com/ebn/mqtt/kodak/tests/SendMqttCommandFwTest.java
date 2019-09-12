package com.ebn.mqtt.kodak.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.ebn.mqtt.SendMqttCommandTestBase;

/**
 * This test is use for verifying MQTT command for Kodak camera
 * It will send all commands in the test data one by one, and expect the return
 * ...message from server to be valid and correct
 */
public class SendMqttCommandFwTest extends SendMqttCommandTestBase {

	private int loopCount = 1;

	public SendMqttCommandFwTest() {

		tenantClass = TenantObjects.Kodak.class;
	}

	@Test
	public void sendCommandViaMQTT() throws Exception {

		Log.info("======Begin execute command======");

		for (int i = 0; i < loopCount; i++) {
			for (String strLine : testData) {

				String	strCommand	= strLine.split(",")[0].trim();
				String	strExpected	= strLine.split(",")[1].trim();

				// Send the command
				String starttime = StringHelper.getCurrentDateTime();
				mqttHelper.sendCommand(strCommand);
				String	mqttMessage	= mqttHelper.output;
				String	endtime		= StringHelper.getCurrentDateTime();
				Long	duration	= StringHelper.getDuration(starttime, endtime);
				TimeHelper.sleep(3000);

				String result;
				if (mqttMessage.toString().contains(strExpected)) {
					result = "Passed";
				} else {
					result = "Failed";
				}

				Log.info("Command sent:                         ", strCommand);
				Log.info("Message recieved:                     ", mqttMessage);
				Log.info("Message expected:                     ", strExpected);
				Log.info("Result:                               ", result);
				Log.info("Time sent:                            ", starttime);
				Log.info("Time to received message from server: ", duration + "\n");

				appendResult(strCommand, mqttMessage, result, starttime, duration, strExpected,
						DsuiteBuildUrl);
			}
		}
		Log.info("======End execute command======");
	}

	@BeforeMethod
	public void logInfo() {

		Log.info(String.format("Test env info: %s : %s", device.getDevice_id(),
				device.getFirmware_version()));
	}
}
