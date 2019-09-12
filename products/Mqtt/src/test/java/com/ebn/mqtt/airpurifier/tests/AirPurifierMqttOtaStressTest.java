package com.ebn.mqtt.airpurifier.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.config.TenantObjects;
import com.ebn.mqtt.SendMqttCommandStressTestBase;

public class AirPurifierMqttOtaStressTest extends SendMqttCommandStressTestBase {

	String startTime, forceOtaReponse, wifiFw, otaStatusCode, mcuVersion, isOnline;

	public AirPurifierMqttOtaStressTest() {

		tenantClass	= TenantObjects.AirPurifier.class;
		startTime	= StringHelper.getCurrentDateTime();
		forceOtaReponse = wifiFw = otaStatusCode = mcuVersion = isOnline = "";
	}

	@Test
	public void test() throws Exception {

		if (device.isOnline()) {
			isOnline	= "1";
			startTime	= StringHelper.getCurrentDateTime();
			mqttHelper.sendCommand("check_fw_upgrade&force=1");
			forceOtaReponse = mqttHelper.output;
			TimeHelper.sleep(15 * 60 * 1000);

			/*
			 * NOTE:
			 * OTA status:
			 * 0: no data. Haven't ota
			 * 1: Last ota connect server fail
			 * 2: Download mcu package fail
			 * 3: Upgrade mcu package fail
			 * 4: download wifi fail
			 * 5: upgrade wifi fail
			 * 6: success
			 */
			mqttHelper.sendCommand("get_ota_status");
			String[] response = mqttHelper.output.split("&");
			wifiFw			= response[2].split("=")[1].trim();
			mcuVersion		= response[3].split("=")[1].trim();
			otaStatusCode	= response[4].split("=")[1].trim();
		}
	}

	@AfterMethod
	public void pushDatabase() {

		dbReporter.doInsertApOtaTest(testEnvInfo.get(0), testEnvInfo.get(3), startTime,
				device.getFirmware_version(), forceOtaReponse, wifiFw, otaStatusCode, mcuVersion,
				isOnline);
	}
}
