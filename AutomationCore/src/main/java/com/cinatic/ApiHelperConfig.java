package com.cinatic;

import static com.jayway.restassured.RestAssured.given;

import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class ApiHelperConfig {

	private static boolean		isStaging;
	private static Response		serverInfo;
	public static TenantObjects	currentTenant;

	public static void configServer(Class<?> tenantName, String server, String username)
			throws Exception {

		Log.info("Get api url base on server and username");
		String body = "";

		isStaging = server.equals("production") ? false : true;

		Class<?> tenantClass = Class.forName(tenantName.getName());
		currentTenant = (TenantObjects) tenantClass.newInstance();

		RestAssured.baseURI = isStaging ? currentTenant.STAGING : currentTenant.PRODUCTION;

		if (username != "") { body += "?username=" + username; }
		serverInfo = given().auth().preemptive()
				.basic(currentTenant.BASIC_AUTH_USER, currentTenant.BASIC_AUTH_PASSWD).when().log()
				.all().get(body).then().log().all().extract().response();
		Log.info("Set Base URI");
		RestAssured.baseURI = getApiServer();
		Log.info(RestAssured.baseURI);
	}

	public static String getApiServer() {

		return "https://" + serverInfo.path("data.master.uapi");
	}

	public static String getMqttServer() throws Exception{

		if (isStaging)
			return "ssl://" + serverInfo.path("data.master.mqtt") + ":"
					+ TenantObjects.getInUsedTenantOjbect().mqttPortSt;
		return "ssl://" + serverInfo.path("data.master.mqtt") + ":"
				+ TenantObjects.getInUsedTenantOjbect().mqttPort;
	}
}
