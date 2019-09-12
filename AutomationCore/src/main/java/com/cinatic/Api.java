package com.cinatic;

import static com.jayway.restassured.RestAssured.given;

import com.cinatic.config.TenantObjects;
import com.cinatic.log.Log;
import com.cinatic.object.Device;
import com.cinatic.object.Event;
import com.cinatic.object.MqttObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

/*
 * I'm planing to split ApiHelper class into 2 classes: Api and ApiHelper
 * Api class contains only basic method to communicate with backend server via
 * . provided REST API
 * ApiHelper class will contains much higher level actions which involve calling
 * . multiple Api method, extract, query info .... from server
 */

public class Api {

	private String				c_accessToken;
	private Response			c_response;
	private String				region;
	private static int			tandc_id	= -1;
	private TenantObjects		tenant;
	private static boolean		isStaging;
	private static Response		serverInfo;
	public static TenantObjects	currentTenant;

	public Api() {

		try {

			Class<?> classTenant = Class.forName(TenantObjects.tenantInUsed.getName());
			tenant = (TenantObjects) classTenant.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		RestAssured.useRelaxedHTTPSValidation();
		getTandC();
	}

	// Utils
	public void getTandC() {

		if (tandc_id != -1) { return; }

		Log.info("Get term and condition");
		Response response = given().contentType("application/json").log().all().when()
				.get("/v1/utils/t_and_c").then().log().all().extract().response();
		c_response	= response;
		tandc_id	= response.path("data.tandc_id");
	}

	// Users
	public Response registerUserAccount(String email, String username, String password,
			String confirmPassword) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"secure_code\": \"\", \"user_name\":\"" + username + "\", \"email\":\""
						+ email + "\", \"password\":\"" + password + "\", \"confirm_password\":\""
						+ confirmPassword + "\", \"tandc_id\": " + tandc_id + " }")
				.when().post("/v1/users/account").then().log().all().extract().response();
		return response;
	}

	public Response verifyUserAccountRegister(String verificationToken) {

		Response response = given().contentType("application/json").log().all().when()
				.get("/v1/users/account/verify?token=" + verificationToken).then().log().all()
				.extract().response();
		return response;
	}

	/**
	 * Thach Nguyen login and get access token using POST /v1/oauth/token
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Response postOauth(String username, String password) {

		Response response = given().auth().preemptive()
				.basic(ApiHelperConfig.currentTenant.BASIC_AUTH_USER,
						ApiHelperConfig.currentTenant.BASIC_AUTH_PASSWD)
				.contentType("application/x-www-form-urlencoded").log().all()
				.body("grant_type=password&" + "model=java&" + "username=" + username + "&password="
						+ password + "&undefined=")
				.when().post("/v1/oauth/token").then().log().all().extract().response();

		c_accessToken	= response.path("access_token");
		tandc_id		= response.path("account_info.tandc_id");
		return response;
	}

	public Response changePassword(String oldPassword, String newPassword, String confirmPassword) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"current_password\":\"" + oldPassword + "\", \"password\":\"" + newPassword
						+ "\", \"confirm_password\":\"" + confirmPassword + "\"}")
				.when().put("/v1/users/account/change_password?access_token=" + c_accessToken)
				.then().log().all().extract().response();
		return response;
	}

	public Response resetPassword(String resetPassToken, String newPassword) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"password\":\"" + newPassword + "\", \"confirm_password\":\"" + newPassword
						+ "\"}")
				.when().put("/v1/users/account/reset_password?token=" + resetPassToken).then().log()
				.all().extract().response();
		return response;
	}

	public Response recoverPassword(String email) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"email\":\"" + email + "\"}").when()
				.post("/v1/users/account/recover_password?access_token=" + c_accessToken).then()
				.log().all().extract().response();
		return response;
	}

	public Response getUserLogInAccessToken(String refreshToken) {

		Response response = given().contentType("application/json").log().all().when()
				.get("/v1/users/authenticate?refresh_token=" + refreshToken).then().log().all()
				.extract().response();
		return response;
	}

	public Response getUserLogInLogs() {

		return given().contentType("application/json").log().all().when()
				.get("/v1/users/access_log?access_token=" + c_accessToken).then().log().all()
				.extract().response();
	}

	public Response getCurrentLoggedInUser() {

		Response response = given().contentType("application/json").log().all().when()
				.get("/v1/users/account?access_token=" + c_accessToken).then().log().all().extract()
				.response();
		return response;
	}

	// Audit
	public Response subscribe(String planId) {

		Response response = given().contentType("application/json").log().all()
				.body("\"plan_id\":\"" + planId + "\"").when()
				.post("/v1/audit/fake_subscribe?access_token=" + c_accessToken).then().log().all()
				.extract().response();
		return response;
	}

	/**
	 * @param deviceId
	 * @return since feb 2019, server requires secret key, which is not
	 *         available for anybody, to use this api so mark as deprecated
	 */
	@Deprecated
	public Response getCamInfo(String deviceId) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"access_token\":\"" + c_accessToken
						+ "\",\"is_ssl\":true,\"command\":\"get_caminfo\"}")
				.when().post("/v1/audit/" + deviceId + "/send_cmd").then().log().all().extract()
				.response();
		return response;
	}

	// Devices
	public Response getDevices() {

		String		req			= tenant.apiGetDevices + "?access_token=" + c_accessToken;
		Response	response	= given().contentType("application/json").log().all().when()
				.get(req).then().log().all().extract().response();
		c_response = response;
		return response;
	}

	public Response getShareDevices() {

		Response response = given().contentType("application/json").log().all().when()
				.get("/v1/users/share_devices?access_token=" + c_accessToken).then().log().all()
				.extract().response();
		c_response = response;
		return response;
	}

	public Device getDeviceByDeviceId(String deviceId) {

		Device o = new Device();

		try {
			o.setName(
					c_response.path("data.find{ it.device_id == '%s' }.name", deviceId).toString());
			o.setDevice_id(deviceId);
			o.setIs_online(c_response.path("data.find{ it.device_id == '%s' }.is_online", deviceId)
					.toString());
			o.setMqtt_topic(c_response
					.path("data.find{ it.device_id == '%s' }.mqtt_topic", deviceId).toString());
			o.setLocal_ip(c_response.path("data.find{ it.device_id == '%s' }.local_ip", deviceId)
					.toString());
			o.setFirmware_version(
					c_response.path("data.find{ it.device_id == '%s' }.firmware.version", deviceId)
							.toString());
		} catch (IllegalArgumentException e) {
			throw e;
		}

		return o;
	}

	public Device getDeviceByDeviceIdv2(String deviceId) {

		Device	o				= new Device();
		String	jsonPathShare	= "data.shared_devices.find";
		String	jsonPathOwn		= "data.own_devices.find";
		String	jsonPath;

		try {
			c_response.path("%s{ it.device_id == '%s' }.name", jsonPathOwn, deviceId);
			jsonPath = jsonPathOwn;
		} catch (Exception e) {
			jsonPath = jsonPathShare;
		}

		try {
			o.setName(c_response.path("%s{ it.device_id == '%s' }.name", jsonPath, deviceId)
					.toString());
			o.setDevice_id(deviceId);
			o.setIs_online(c_response
					.path("%s{ it.device_id == '%s' }.is_online", jsonPath, deviceId).toString());
			o.setMqtt_topic(c_response
					.path("%s{ it.device_id == '%s' }.mqtt_topic", jsonPath, deviceId).toString());
			o.setLocal_ip(c_response.path("%s{ it.device_id == '%s' }.local_ip", jsonPath, deviceId)
					.toString());
			o.setFirmware_version(c_response
					.path("%s{ it.device_id == '%s' }.firmware.version", jsonPath, deviceId)
					.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw e;
		}

		return o;
	}

	public Response registerDevice(String deviceId, String version, String timeZone, String localIp,
			String routerSsid, String routerName) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"name\":\"camtest\", \"device_id\":\"" + deviceId + "\"version\":\""
						+ version + "\", \"time_zone\":\"" + timeZone + "\", \"local_ip\":\""
						+ localIp + "\", \"router_ssid\":\"" + routerSsid + "\", \"router_name\":\""
						+ routerName + "\"}")
				.when().post("/v1/devices/" + deviceId + "/register?access_token=" + c_accessToken)
				.then().log().all().extract().response();
		return response;
	}

	public Response addSubscriptionForDevice(String deviceId) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"device_ids\":\"" + deviceId + "\"}").when()
				.post("/v1/users/sub_plan/update_devices?access_token=" + c_accessToken).then()
				.log().all().extract().response();
		return response;
	}

	// Events
	public Response addNewEvent(String deviceId, String device_token, int event_type) {

		Response response = given().contentType("application/json").log().all()
				.body("{\"device_id\":\"" + deviceId + "\",\"event_type\":" + event_type
						+ ",\"device_token\":\"" + device_token + "\"}")
				.when().post("/v1/devices/events?access_token=" + c_accessToken).then().log().all()
				.extract().response();
		return response;
	}

	public Response getEventsByDeviceId(String deviceId) {

		Response response = given().contentType("application/json").log().all().when()
				.get("/v1/devices/events?access_token=" + c_accessToken + "&device_ids=" + deviceId)
				.then().log().all().extract().response();
		c_response = response;
		return response;
	}

	public Event getEventByEventId(String eventId) {

		try {
			String	event_id			= c_response
					.path("data.events.find{ it.id == '" + eventId + "' }.id") != null
							? c_response.path("data.events.find{ it.id == '" + eventId + "' }.id")
									.toString()
							: "";
			String	device_id			= c_response.path(
					"data.events.find{ it.id == '" + eventId + "' }.device_id") != null ? c_response
							.path("data.events.find{ it.id == '" + eventId + "' }.device_id")
							.toString() : "";
			String	event_type			= c_response
					.path("data.events.find{ it.id == '" + eventId + "' }.event_type") != null
							? c_response.path(
									"data.events.find{ it.id == '" + eventId + "' }.event_type")
									.toString()
							: "";
			String	created_date		= c_response
					.path("data.events.find{ it.id == '" + eventId + "' }.created_date") != null
							? c_response.path(
									"data.events.find{ it.id == '" + eventId + "' }.created_date")
									.toString()
							: "";
			String	snapshot			= c_response
					.path("data.events.find{ it.id == '" + eventId + "' }.snapshot") != null
							? c_response
									.path("data.events.find{ it.id == '" + eventId + "' }.snapshot")
									.toString()
							: "";
			String	file				= c_response.path(
					"data.events.find{ it.id == '" + eventId + "' }.data.file") != null ? c_response
							.path("data.events.find{ it.id == '" + eventId + "' }.data.file")
							.toString() : "";
			String	file_type			= c_response
					.path("data.events.find{ it.id == '" + eventId + "' }.data.file_type") != null
							? c_response.path(
									"data.events.find{ it.id == '" + eventId + "' }.data.file_type")
									.toString()
							: "";
			String	file_created_date	= c_response.path(
					"data.events.find{ it.id == '" + eventId + "' }.data.created_date") != null
							? c_response.path("data.events.find{ it.id == '" + eventId
									+ "' }.data.created_date").toString()
							: "";
			String	file_size			= c_response
					.path("data.events.find{ it.id == '" + eventId + "' }.data.file_size") != null
							? c_response.path(
									"data.events.find{ it.id == '" + eventId + "' }.data.file_size")
									.toString()
							: "";

			Event o = new Event();
			o.setEvent_id(event_id);
			o.setDevice_id(device_id);
			o.setEvent_type(event_type);
			o.setCreated_date(created_date);
			o.setSnapshot(snapshot);
			o.setFile(file);
			o.setFile_type(file_type);
			o.setFile_created_date(file_created_date);
			o.setFile_size(file_size);

			return o;
		} catch (Exception e) {
			return null;
		}
	}

	public MqttObject registerApp() {

		Log.info("Registering an application to server for mqtt connecting");
		String		app_code		= StringHelper.randomString("", 15);
		Response	res				= given().contentType("application/json").log().all()
				.body("{\"secure_code\":\"\",\"name\":\"" + TenantObjects.tenantInUsed.getName()
						+ "\",\"app_code\":\"" + app_code + "\",\"version\":\"1.0(55)\"" + "}")
				.when().post("/v1/users/apps?access_token=" + c_accessToken).then().log().all()
				.extract().response();
		String		subscribeTopic	= res.path("data.mqtt.subscribe_topic");
		String		clientId		= res.path("data.mqtt.client_id");
		String		accessKey		= res.path("data.mqtt.access_key");
		String		secretKey		= res.path("data.mqtt.secret_key");
		MqttObject	mqttObject		= new MqttObject(subscribeTopic, clientId, accessKey,
				secretKey);
		return mqttObject;
	}

	public String getC_accessToken() {

		return c_accessToken;
	}

	public Response postRequestUploadLog(String deviceId) {

		Log.info("Request log from: ", deviceId);
		c_response = given().contentType("application/json").log().all().when()
				.post("/v1/users/devices/{device_id}/request_upload_log?access_token={token}",
						deviceId, c_accessToken)
				.then().log().all().extract().response();
		return c_response;
	}

	public Response getCloudLogs(String deviceId) {

		c_response = given().contentType("application/json").log().all().when()
				.get("/v1/users/devices/{device_id}/cloud_logs?access_token={token}", deviceId,
						c_accessToken)
				.then().log().all().extract().response();
		return c_response;
	}

	/// config

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

	public static String getMqttServer() throws Exception {

		return "ssl://" + serverInfo.path("data.master.mqtt") + ":"
				+ TenantObjects.getInUsedTenantOjbect().mqttPort;
	}
}
