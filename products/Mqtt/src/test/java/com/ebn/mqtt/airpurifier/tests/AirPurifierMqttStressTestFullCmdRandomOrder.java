package com.ebn.mqtt.airpurifier.tests;

import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.ebn.mqtt.SendMqttCommandStressTestBase;

/**
 * This test is designed to measure
 */
public class AirPurifierMqttStressTestFullCmdRandomOrder extends SendMqttCommandStressTestBase {

	String	dummyCommand	= "fan_mode_ctrl&value=1";
	String	previousCommand	= "";

	public AirPurifierMqttStressTestFullCmdRandomOrder() {

		tenantClass = TenantObjects.AirPurifier.class;
	}

	@Test()
	public void sendCommandViaMQTT() throws Exception {

		Log.info("====== Begin sending command ======");
		while (testData.size() > 0) {

			int			randomTestDataIndex	= new Random().nextInt(testData.size());
			String[]	testDataEntry		= testData.get(randomTestDataIndex).split(",");
			Log.fatal("-=-=-=-=- Test data entry -=-=-=-=-");
			Log.fatal(testData.get(randomTestDataIndex));
			String strCommand = testDataEntry[0].trim();
			if (strCommand.equals(previousCommand)) {
				if (testData.size() > 1) { // avoid dead loop when last sent command is the only command left in the test data
					Log.info("Command has just been sent recently, skip!!!");
					continue;
				}
			}

			String	strExpected	= testDataEntry[1].trim();
			int		loop		= Integer.parseInt(testDataEntry[2].trim());
			setTestDataLoop(randomTestDataIndex, loop - 1);

			// Send the command
			String starttime = StringHelper.getCurrentDateTime();
			mqttHelper.sendCommand(strCommand);
			String	mqttMessage	= mqttHelper.output;
			String	endtime		= StringHelper.getCurrentDateTime();
			Long	duration	= StringHelper.getDuration(starttime, endtime);
			TimeHelper.sleep(Integer.parseInt(testParams.get("sleepTime")));

			if (strCommand.equals("power_state_set&value=0")) {

				Log.debug(
						"power_state_set&value=0 has just been sent, sending dummy command now to wake the AP up");
				mqttHelper.sendCommand(dummyCommand);
				TimeHelper.sleep(5000);
			}

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

			dbReporter.doInsertMqttTestData(testEnvInfo, strCommand, strExpected, mqttMessage,
					starttime, duration, result, mcuAck);

			Log.info("Command sent:                         ", strCommand);
			Log.info("Message recieved:                     ", mqttMessage);
			Log.info("Message expected:                     ", strExpected);
			Log.info("MCU ACK:                              ", mcuAck);
			Log.info("Result:                               ", result);
			Log.info("Time sent:                            ", starttime);
			Log.info("Time to received message from server: ", duration + "\n");

			previousCommand = strCommand;
		}
		Log.info("====== End test ======");
	}

	@BeforeMethod
	public void prepareTestDataLoopTime() {

		int	dataSize	= testData.size();
		int	minLoop		= Integer.parseInt(testParams.get("minLoop"));
		int	maxLoop		= Integer.parseInt(testParams.get("maxLoop"));
		for (int i = 0; i < dataSize; i++) {
			int		loop	= new Random().nextInt(maxLoop - minLoop + 1) + minLoop;
			String	newData	= testData.get(i) + "," + loop;
			testData.set(i, newData);
		}
	}

	public void setTestDataLoop(int testDataIndex, int newLoop) {

		if (newLoop < 1) {
			testData.remove(testDataIndex);
			return;
		}
		String	oldData[]	= testData.get(testDataIndex).split(",");
		String	newData		= "";
		for (int i = 0; i < oldData.length - 1; i++) { newData += oldData[i] + ","; }
		newData += newLoop;
		testData.set(testDataIndex, newData);
	}
}
