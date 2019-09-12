package com.ebn.server.tests;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.ApiHelperConfig;
import com.cinatic.config.TenantObjects;
import com.cinatic.object.Device;

public class RequestCameraLog {

	Class<?>	tenant	= TenantObjects.Kodak.class;
	String		server	= "production";

	@DataProvider
	public Object[][] testData() {

		return new Object[][] {	{	"support.hk",
									"12345678Rr" },
								{	"support.eu",
									"12345678Rr" },
								{	"support.us",
									"12345678Rr" } };
	}

	@Test(dataProvider = "testData")
	public void getCameraLog(String... data) throws Exception {

		String	user	= data[0];
		String	passwd	= data[1];
		ApiHelperConfig.configServer(tenant, server, user);
		ApiHelper api = new ApiHelper();
		api.userLogin(user, passwd);
		api.getDevices();
		ArrayList<Device> devices = api.helper().getDevices();

		for (Device device : devices) {

			if (device.isOnline())
				api.helper().downloadCloudLogFile(device.getDevice_id());
		}
	}
}
