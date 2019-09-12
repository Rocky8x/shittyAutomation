package com.ebn.mqtt;

import org.testng.annotations.BeforeClass;

import com.cinatic.DatabaseHelper;
import com.cinatic.log.Log;

/**
 * for stress test, performance test, the test result will be pushed to data
 * base but NOT generated to html file.
 */
public class SendMqttCommandStressTestBase extends MqttTestBase {

	protected DatabaseHelper	dbReporter;

	@BeforeClass
	public void prepare() throws Exception {
		
		for (String string : testEnvInfo) { Log.info(string); }

		// open connection to db server
		dbReporter = new DatabaseHelper();
	}
}
