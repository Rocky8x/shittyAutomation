package com.ebn.mqtt.airpurifier.tests;

import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.ebn.mqtt.SendMqttCommandStressTestBase;

/**
 * This test is designed to measure  
 */
public class AirPurifierMqttStressTestFullCmd extends SendMqttCommandStressTestBase {

	private int loopCount = 10;

	public AirPurifierMqttStressTestFullCmd() {

		tenantClass = TenantObjects.AirPurifier.class;
	}

	@Test
	public void sendCommandViaMQTT() throws Exception {

		Log.info("======Begin execute command======");
		for (String strLine : testData) {
			for (int i = 0; i < loopCount; i++) {

				String	strCommand	= strLine.split(",")[0].trim();
				String	strExpected	= strLine.split(",")[1].trim();

				// Send the command
				String starttime = StringHelper.getCurrentDateTime();
				mqttHelper.sendCommand(strCommand);
				String	mqttMessage	= mqttHelper.output;
				String	endtime		= StringHelper.getCurrentDateTime();
				Long	duration	= StringHelper.getDuration(starttime, endtime);
				TimeHelper.sleep(Integer.parseInt(testParams.get("sleepTime")));

				String result;
				if (!mqttMessage.toString().isEmpty() && duration <= 1500) {
					result = "passed";
				} else {
					result = "failed";
				}

				String mcuAck = "-1";
				if (!mqttMessage.isEmpty()) {
					for (String res : mqttMessage.split("&")) {
						if (res.contains("mcu_ack=")) {
							mcuAck = res.split("=")[1].trim();
							break;
						}
					}
				}

				Log.info("Command sent:                         ", strCommand);
				Log.info("Message recieved:                     ", mqttMessage);
				Log.info("Message expected:                     ", strExpected);
				Log.info("MCU ACK:                              ", mcuAck);
				Log.info("Result:                               ", result);
				Log.info("Time sent:                            ", starttime);
				Log.info("Time to received message from server: ", duration + "\n");

				dbReporter.doInsertMqttTestData(testEnvInfo, strCommand, strExpected, mqttMessage,
						starttime, duration, result, mcuAck);
			}
		}
		Log.info("======End execute command======");
	}
}
