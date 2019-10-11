package com.ebn.server.tests;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.ApiHelperConfig;
import com.cinatic.TimeHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.ebn.automation.core.TestHelper;

public class RequestCameraLog {

	Class<?> tenant = TenantObjects.Kodak.class;

	Map<String, String>	testParam;
	ApiHelper			api;
	Device				device;
	int					requestCount	= 0;
	long				elappedTime		= 0;

	@BeforeSuite
	public void prepare(ITestContext context) throws Exception {

		testParam = TestHelper.loadTestParams(context);
		ApiHelperConfig.configServer(tenant, testParam.get("server"), testParam.get("username"));
		api = new ApiHelper();
		api.userLogin(testParam.get("username"), testParam.get("password"));

		api.getDevices();
		device = api.getDeviceByDeviceId(testParam.get("cameraId"));
	}

	@Test
	public void getCameraLogInDuration() throws Exception {

		long	startTime	= System.currentTimeMillis();
		long	currentTime	= 0;
		long	runDuration	= Long.parseLong(testParam.get("testTime")) * 60 * 1000;
		if (device.isOnline()) {
			while (true) {
				currentTime	= System.currentTimeMillis();
				elappedTime	= currentTime - startTime;
				try {
					api.helper().downloadCloudLogFile(device.getDevice_id());
					requestCount++;
				} catch (Exception e) {
					Log.info(e.getMessage());
					api.getDevices();
					Device d = api.getDeviceByDeviceId(device.getDevice_id());
					if (!d.isOnline())
						throw new Exception("Camera is offline after running for: "
								+ TimeHelper.milisec2String((int) elappedTime));
					else
						throw new Exception("No response from device");
				}
				if (elappedTime >= runDuration)
					return;
				TimeHelper.sleep(Integer.parseInt(testParam.get("gap")));
			}
		} else {
			throw new Exception("Camera is offline");
		}
	}

	@AfterSuite
	public void echoJenkinsDescription() {

		// this is for jenkins set build description plugin
		Log.info("+ Summary:", requestCount + "", "request(s) in",
				TimeHelper.milisec2String((int) elappedTime));
	}
}
