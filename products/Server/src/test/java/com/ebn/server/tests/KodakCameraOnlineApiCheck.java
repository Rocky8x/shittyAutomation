package com.ebn.server.tests;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.ApiHelperConfig;
import com.cinatic.DatabaseHelper;
import com.cinatic.StringHelper;
import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.jayway.restassured.RestAssured;

public class KodakCameraOnlineApiCheck {

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
	public void checkOnlineByApi3Server(String... data) throws Exception {

		String	user	= data[0];
		String	passwd	= data[1];
		ApiHelperConfig.configServer(tenant, server, user);
		ApiHelper api = new ApiHelper();
		api.userLogin(user, passwd);
		api.getDevices();
		ArrayList<Device> devices = api.helper().getDevices();

		DatabaseHelper db = new DatabaseHelper();
		for (Device device : devices) {

			String	cameraId	= device.getDevice_id();
			String	time		= StringHelper.getCurrentDateTime();
			byte	status		= device.isOnline() ? (byte) 1 : 0;

			Log.info("Device       : ", cameraId);
			Log.info("Time         : ", time);
			Log.info("Is online    ? ", device.isOnline() + "");
			db.doInsertDeviceStatusCheck(cameraId, RestAssured.baseURI, time, status + "");
		}
	}
}
