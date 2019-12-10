package com.cinatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.cinatic.log.Log;
import com.cinatic.object.Device;

public class DatabaseHelper {

	// MSSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String	DB_DRIVER	= "com.mysql.cj.jdbc.Driver";
	private static final String	DB_SERVER	= "192.168.1.101";
	private static final String	DB_NAME		= "QADB";
	private static final String	DB_user		= "reporter";
	private static final String	DB_passwd	= "123123Aa";

	Connection conn;

	public DatabaseHelper() {

		connect();
	}

	public void connect() {

		try {
			Class.forName(DB_DRIVER);
			String url = "";

			switch (System.getProperty("os.name").toLowerCase()) {
			case "windows":
			case "linux":
// url = "jdbc:sqlserver://" + DB_SERVER + ";databaseName=" + DB_NAME;
				url = "jdbc:mysql://" + DB_SERVER + "/" + DB_NAME;
				break;
			case "mac os x":
// url = "jdbc:sqlserver://" + DB_SERVER + ";databaseName=" + DB_NAME;
				url = "jdbc:mysql://" + DB_SERVER + "/" + DB_NAME;
				break;
			}

			conn = DriverManager.getConnection(url, DB_user, DB_passwd);
			Log.info("connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected int executeUpdate(String sql) {

		try {
			Log.info(sql);
			Statement statement = conn.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
			return -1;
		}
	}

	public int doInsertStreamData(Device c_device, String streaming_mode, long streamingDuration,
			String startStream, String appVersion, String platform, String devicelog,
			String jenkins_url, String account, String test_type) {

		String fields =  
				"           device_name,\r\n" +
				"           device_id,\r\n" +
				"           firmware_version,\r\n" +
				"           streaming_mode,\r\n" +
				"           streaming_duration,\r\n" +
				"           start_time_mode,\r\n" +
				"           appVer,\r\n" +
				"           platform,\r\n" +
				"           devicelog,\r\n" +
				"           jenkins_url,\r\n" +
				"           account,\r\n" +
				"           test_type\r\n" ;

		String[] data = {	c_device.getName(),
							c_device.getDevice_id(),
							c_device.getFirmware_version(),
							streaming_mode,
							streamingDuration + "",
							startStream,
							appVersion,
							platform,
							devicelog,
							jenkins_url,
							account,
							test_type };
		return doInsertDatabase("Streaming",fields, data);
	}

	public int doInsertVideoData(Device c_device, String streaming_mode, long videoPlayDuration,
			String videoPlayStartTime, String appVersion, String platform, String devicelog,
			String jenkins_url, String account, String test_type) {
		String fields = 
				"           device_name,\r\n" + 
				"           device_id,\r\n" + 
				"           firmware_version,\r\n" + 
				"           streaming_mode,\r\n" + 
				"           videoPlayDuration,\r\n" + 
				"           videoPlayStartTime,\r\n" + 
				"           appVersion,\r\n" + 
				"           platform,\r\n" + 
				"           devicelog,\r\n" + 
				"           jenkins_url,\r\n" + 
				"           account,\r\n" + 
				"           test_type\r\n";
		
		
		String[] data = {	c_device.getName(),
							c_device.getDevice_id(),
							c_device.getFirmware_version(),
							streaming_mode,
							videoPlayDuration + "",
							videoPlayStartTime,
							appVersion,
							platform,
							devicelog,
							jenkins_url,
							account,
							test_type };
		return doInsertDatabase("Video",fields, data);
	}

	public int doInsertNotificationData(Device c_device, long event_duration,
			String start_time_event, String end_time_event, String platform, String devicelog,
			String jenkins_url, String account, String test_type) {

		String data[] = {	c_device.getName(),
							c_device.getDevice_id(),
							c_device.getFirmware_version(),
							event_duration + "",
							start_time_event,
							end_time_event,
							platform,
							devicelog,
							jenkins_url,
							account,
							test_type };
		return doInsertDatabase("Notification", data);
	}

	public int doInsertSetupData(Device c_device, long event_duration, String start_time_event,
			String end_time_event, String platform, String devicelog, String jenkins_url,
			String account, String test_type) {

		String[] data = {	c_device.getName(),
							c_device.getDevice_id(),
							c_device.getFirmware_version(),
							event_duration + "",
							start_time_event,
							end_time_event,
							platform,
							devicelog,
							jenkins_url,
							account,
							test_type };
		return doInsertDatabase("Setup", data);
	}

	/**
	 * @param c_device
	 * @param event_duration
	 * @param start_time_event
	 * @param platform
	 * @param account
	 * @param motion_clip
	 * @param result
	 * @param app_event_time
	 * @param test_type
	 * @return
	 */
	public int doInsertMotionDetectionData(Device c_device, long event_duration,
			String start_time_event, String platform, String account, String motion_clip,
			String result, String app_event_time, String test_type) {

		String data[] = {	c_device.getName(),
							c_device.getDevice_id(),
							c_device.getFirmware_version(),
							event_duration + "",
							start_time_event,
							platform,
							account,
							motion_clip,
							result,
							app_event_time,
							test_type };
		return doInsertDatabase("MotionDetection", data);
	}

	public int doInsertMqttTestData(ArrayList<String> testEnv, String cmdSend, String expect,
			String actual, String timeSent, long timeDelta, String result, String mcuAck) {

		String[] data = {	testEnv.get(0),
							testEnv.get(1),
							testEnv.get(2),
							testEnv.get(3),
							testEnv.get(4),
							cmdSend,
							expect,
							actual,
							timeSent,
							timeDelta + "",
							result,
							mcuAck };

		return doInsertDatabase("MQTT", data);
	}

	/**
	 * @param data must be strictly follow the order:
	 *             deviceId, server, time, status
	 * @return
	 */
	public int doInsertDeviceStatusCheck(String... data) {

		return doInsertDatabase("OnlineCheck", data);
	}

	/**
	 * @param data must be strictly follow the order:
	 *             deviceId
	 *             testSite
	 *             timeCheckFwUpg
	 *             currentFw
	 *             forceOtaResponse
	 *             wifiFw
	 *             otaStatusCode
	 *             mcuVersion
	 * @return
	 */
	public int doInsertApOtaTest(String... data) {

		return doInsertDatabase("AP_OTA", data);
	}

	/**
	 * @param dataTable
	 * @param data
	 * @return
	 */
	public int doInsertDatabase(String dataTable, String... data) {

		String dataToInsert = "";
		for (String datum : data) {
			dataToInsert += String.format("'%s', ", datum);

		}
		dataToInsert = dataToInsert.substring(0, dataToInsert.length() - 2); // remove the last {,
																			 // '}
		String sql = String.format("INSERT INTO %s\r\n VALUES (%s)", dataTable, dataToInsert);
		return executeUpdate(sql);
	}

	public int doInsertDatabase(String dataTable, String fields, String... data) {

		String dataToInsert = "";
		for (String datum : data) {
			dataToInsert += String.format("'%s', ", datum);

		}
		dataToInsert = dataToInsert.substring(0, dataToInsert.length() - 2); // remove the last {,

		String sql = String.format("INSERT INTO %s\r\n (%s) VALUES (%s)", dataTable, fields,
				dataToInsert);
		return executeUpdate(sql);
	}

}
