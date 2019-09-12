package com.ebn.mqtt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import com.cinatic.ApiHelper;
import com.cinatic.ApiHelperConfig;
import com.cinatic.MQTTHelper;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.MqttObject;
import com.ebn.automation.core.TestHelper;

/**
 * @author Rocky
 *         Very basic class for MQTT test which will:
 *         .. -load pram from xml file
 *         .. -login to API server, register new mqtt app to server and get
 *         device info
 *         .. -create mqtt connection to the device.
 *
 */
public class MqttTestBase {

	protected static String		reportFileName			= "mqtt_command_report";
	protected MQTTHelper		mqttHelper;
	protected Device			device;
	protected ArrayList<String>	testData				= new ArrayList<>();
	protected Class<?>			tenantClass;
	protected String			DsuiteBuildUrl;
	protected ArrayList<String>	testEnvInfo				= new ArrayList<>();
	boolean						appendPreviousReport	= false;
	protected Map<String, String>			testParams;

	@BeforeClass
	public void configAndLoadParam(ITestContext context) throws Exception {

		DsuiteBuildUrl = System.getProperty("suiteBuildUrl");

		testParams = TestHelper.loadTestParam(context);

		// Config api server connection
		ApiHelperConfig.configServer(tenantClass, testParams.get("region"),
				testParams.get("username"));

		// login and get MQTT topic first
		ApiHelper apiHelper = new ApiHelper();
		apiHelper.userLogin(testParams.get("username"), testParams.get("password"));
		apiHelper.getDevices();
		try {
			device = apiHelper.getDeviceByDeviceId(testParams.get("deviceId"));
		} catch (Exception e) {
			device = apiHelper.getDeviceByDeviceIdv2(testParams.get("deviceId"));
		}
		MqttObject mqttObj = apiHelper.registerApp();

		// connect to api server and get MQTT channel
		// store environment info
		testEnvInfo.add(device.getDevice_id());	// 0
		testEnvInfo.add(device.getFirmware_version()); // 1
		testEnvInfo.add(testParams.get("username") + "/" + testParams.get("password")); // 2
		testEnvInfo.add(ApiHelperConfig.getMqttServer()); // 3
		testEnvInfo.add(testParams.get("commandFile")); // 4
		mqttHelper = new MQTTHelper(testEnvInfo.get(3), mqttObj, device);

		// Load test data
		if (!testParams.containsKey("testData")) {
			/* if there's no data from command line then use data file */
			FileReader		fileReader		= new FileReader("test-data" + File.separator +testParams.get("commandFile"));
			BufferedReader	bufferedReader	= new BufferedReader(fileReader);
			String line = bufferedReader.readLine();
			while (line != null) {
				// ignore empty lines and lines start with #
				if (!line.trim().isEmpty() && line.trim().charAt(0) != '#')
					testData.add(line);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} else {
			String testDataFromCmd = testParams.get("testData");
			/* use test data from command line */
			String[] testDataParse = testDataFromCmd.split("\n");
			for (String string : testDataParse) {
				if (!string.trim().isEmpty() && string.trim().charAt(0) != '#') {
					testData.add(string);
				}
			}
		}
		Log.info("Test data: " + testData.size() + " entries");

	}
}
