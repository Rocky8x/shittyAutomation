package com.ebn.mqtt.kodak.tests;

import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.ebn.mqtt.SendMqttCommandTestBase;

/**
 * This test is use for verifying MQTT server stress endurance
 * It does not verify server's response, any non-empty response will be accepted
 * ... as passed
 * For each time this test class is call, it will run twice
 * For each run, get a random command from the test data to send to server
 * ... and repeat it for a random time (from 1-5)
 * 
 */
public class SendMqttCommandSvrStressTest extends SendMqttCommandTestBase {

	protected int loopCount = new Random().nextInt(5) + 1;

	public SendMqttCommandSvrStressTest() {

		tenantClass = TenantObjects.Kodak.class;
	}

	@DataProvider(name = "TestRun")
	public Object[][] runTime() {

		return new Object[][] {	{ "1" },
								{ "2" } };
	}

	@Test(dataProvider = "TestRun")
	public void sendCommandViaMQTT(String input) throws Exception {

		Log.info("======Begin execute command======");

		for (int i = 0; i < loopCount; i++) {
			int randomLine = new Random().nextInt(testData.size());

			String	strLine	= "";
			String	strCommand;
			try {
				strLine		= testData.get(randomLine);
				strCommand	= strLine.split(",")[0].trim();
			} catch (Exception e) {
				Exception b = new Exception(String.format(" error at line %s: data size: %s line",
						randomLine, testData.size()));
				b.setStackTrace(e.getStackTrace());
				throw b;
			}
			String strExpected = "";
			strExpected = strLine.split(",")[1].trim();

			// Send the command
			String starttime = StringHelper.getCurrentDateTime();
			mqttHelper.sendCommand(strCommand);
			String	mqttMessage	= mqttHelper.output;
			String	endtime		= StringHelper.getCurrentDateTime();
			Long	duration	= StringHelper.getDuration(starttime, endtime);
			TimeHelper.sleep(3000);

			String result;
			if (!mqttMessage.toString().isEmpty()) {
				result = "passed";
			} else {
				result = "failed";
			}

			appendResult(strCommand, mqttMessage, result, starttime, duration, strExpected,
					DsuiteBuildUrl);
		}
		Log.info("======End execute command======");
	}

	@AfterMethod
	public void sleep() {

		TimeHelper.sleep(30000);
	}
}
