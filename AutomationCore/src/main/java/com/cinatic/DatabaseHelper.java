package com.cinatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.cinatic.log.Log;
import com.cinatic.object.Camera;
import com.cinatic.object.Device;

public class DatabaseHelper {

	private static final String	DB_DRIVER_SQL	= "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String	DB_SERVER		= "172.16.100.197";
	private static final String	DB_NAME			= "QADB";

	private static final String	DB_user		= "sa";
	private static final String	DB_passwd	= "sa";

	Connection conn;

	public DatabaseHelper() {

		connect();
	}

	public DatabaseHelper(String os) {

		connect();
	}

	public void connect() {

		try {
			Class.forName(DB_DRIVER_SQL);
			String url = "";

			switch (System.getProperty("os.name").toLowerCase()) {
			case "windows":
			case "linux":
				url = "jdbc:sqlserver://" + DB_SERVER + ";databaseName=" + DB_NAME;
				break;
			case "mac os x":
				url = "jdbc:sqlserver://" + DB_SERVER + ";databaseName=" + DB_NAME;
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

	public int doInsertStreamData(Camera c_camera, String cameraMode, long streaming_duration,
			long status_duration, String startTimeStatus, String endTimeStatus,
			String startTimeMode, String endTimeMode, String note, String device_os,
			String account) {

		if (!startTimeStatus.equals(""))
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, status_duration, start_time_status, end_time_status, start_time_mode, end_time_mode, note, device_os, account) values('%s', '%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getUdid(), c_camera.getFirmware_version(),
					cameraMode, streaming_duration, status_duration, startTimeStatus, endTimeStatus,
					startTimeMode, endTimeMode, note, device_os, account));
		else
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, start_time_mode, end_time_mode, note, device_os, account) values('%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getUdid(), c_camera.getFirmware_version(),
					cameraMode, streaming_duration, startTimeMode, endTimeMode, note, device_os,
					account));

	}

	public int doInsertStreamData(Camera c_camera, String cameraMode, long streaming_duration,
			long status_duration, String startTimeStatus, String endTimeStatus,
			String startTimeMode, String endTimeMode, String note, String deviceLog, String fileUrl,
			String device_os, String account) {

		if (!startTimeStatus.equals(""))
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, status_duration, start_time_status, end_time_status, start_time_mode, end_time_mode, note, device_log, file_url, device_os, account) values('%s', '%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getUdid(), c_camera.getFirmware_version(),
					cameraMode, streaming_duration, status_duration, startTimeStatus, endTimeStatus,
					startTimeMode, endTimeMode, note, deviceLog, fileUrl, device_os, account));
		else
			return executeUpdate(String.format(
					"insert into Streaming(camera_name, camera_udid, firmware_version, streaming_mode, streaming_duration, start_time_mode, end_time_mode, note, device_log, file_url, device_os, account) values('%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					c_camera.getName(), c_camera.getUdid(), c_camera.getFirmware_version(),
					cameraMode, streaming_duration, startTimeMode, endTimeMode, note, deviceLog,
					fileUrl, device_os, account));

	}

	public int doInsertStreamData(Device c_device, String streaming_mode, long streamingDuration,
			String startStream, String endStream, String platform, String devicelog,
			String jenkins_url, String account, String test_type) {

		//@formatter:off
		String sql = String.format("INSERT INTO [dbo].[Streaming]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[streaming_mode]\r\n" + 
				"           ,[streaming_duration]\r\n" + 
				"           ,[start_time_mode]\r\n" + 
				"           ,[end_time_mode]\r\n" + 
				"           ,[platform]\r\n" + 
				"           ,[devicelog]\r\n" + 
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%d\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), streaming_mode, streamingDuration, startStream, endStream, platform, devicelog, jenkins_url, account, test_type);
		//@formatter:on
		return executeUpdate(sql);
	}

	public int doInsertVideoData(Device c_device, String streaming_mode, long videoPlayDuration,
			String videoPlayStartTime, String videoPlayEndTime, String platform, String devicelog,
			String jenkins_url, String account, String test_type) {

		//@formatter:off
		String sql = String.format("INSERT INTO [dbo].[Video]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[streaming_mode]\r\n" + 
				"           ,[videoPlayDuration]\r\n" + 
				"           ,[videoPlayStartTime]\r\n" + 
				"           ,[videoPlayEndTime]\r\n" + 
				"           ,[platform]\r\n" + 
				"           ,[devicelog]\r\n" + 
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%d\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), streaming_mode, videoPlayDuration, videoPlayStartTime, videoPlayEndTime, platform, devicelog, jenkins_url, account, test_type);
		//@formatter:on
		return executeUpdate(sql);
	}

	public int doInsertNotificationData(Device c_device, long event_duration,
			String start_time_event, String end_time_event, String platform, String devicelog,
			String jenkins_url, String account, String test_type) {

		//@formatter:off
		String sql = String.format("INSERT INTO [dbo].[Notification]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[event_duration]\r\n" + 
				"           ,[start_time_event]\r\n" + 
				"           ,[end_time_event]\r\n" + 
				"           ,[platform]\r\n" +
				"           ,[devicelog]\r\n" +
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 				
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%s\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), event_duration, start_time_event, end_time_event, platform, devicelog, jenkins_url, account, test_type);		
		//@formatter:on
		return executeUpdate(sql);
	}

	public int doInsertSetupData(Device c_device, long event_duration, String start_time_event,
			String end_time_event, String platform, String devicelog, String jenkins_url,
			String account, String test_type) {

		//@formatter:off
		String sql = String.format("INSERT INTO [dbo].[Setup]\r\n" + 
				"           ([device_name]\r\n" + 
				"           ,[device_id]\r\n" + 
				"           ,[firmware_version]\r\n" + 
				"           ,[event_duration]\r\n" + 
				"           ,[start_time_event]\r\n" + 
				"           ,[end_time_event]\r\n" + 
				"           ,[platform]\r\n" +
				"           ,[devicelog]\r\n" +
				"           ,[jenkins_url]\r\n" + 
				"           ,[account]\r\n" + 
				"           ,[test_type])\r\n" + 				
				"     VALUES\r\n" + 
				"           ('%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,%s\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" +
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s'\r\n" + 
				"           ,'%s')", c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(), event_duration, start_time_event, end_time_event, platform, devicelog, jenkins_url, account, test_type);		
		//@formatter:on
		return executeUpdate(sql);
	}

	public int doInsertAccount(String username, String password, String server) {

		// @formatter:off
		String sql = String.format("INSERT INTO [dbo].[Account]\r\n" 
				+ "           ([username]\r\n"
				+ "           ,[password]\r\n" 
				+ "           ,[server])\r\n" 
				+ "     VALUES\r\n"
				+ "           ('%s'\r\n" 
				+ "           ,'%s'\r\n" 
				+ "           ,'%s')", username,
				password, server);
		//@formatter:on
		return executeUpdate(sql);
	}

	public int doInsertMotionDetectionData(Device c_device, long event_duration,
			String start_time_event, String platform, String account, String motion_clip,
			String result, String app_event_time, String test_type) {

		// @formatter:off
		String sql = String.format(
				"INSERT INTO [dbo].[MotionDetection]\r\n" 
				+ "           ([device_name]\r\n"
				+ "           ,[device_id]\r\n" 
				+ "           ,[firmware_version]\r\n"
				+ "           ,[delta_time]\r\n" 
				+ "           ,[start_time_event]\r\n"
				+ "           ,[platform]\r\n" 
				+ "           ,[account]\r\n"
				+ "           ,[motion_clip]\r\n" 
				+ "           ,[result]\r\n"
				+ "           ,[app_event_time]\r\n" 
				+ "           ,[test_type])\r\n"
				+ "     VALUES\r\n" 
				+ "           ('%s'\r\n" 
				+ "           ,'%s'\r\n"
				+ "           ,'%s'\r\n"
				+ "           ,'%s'\r\n" 
				+ "           ,'%s'\r\n"
				+ "           ,'%s'\r\n" 
				+ "           ,'%s'\r\n" 
				+ "           ,'%s'\r\n"
				+ "           ,'%s'\r\n" 
				+ "           ,'%s'\r\n" 
				+ "           ,'%s')",
				c_device.getName(), c_device.getDevice_id(), c_device.getFirmware_version(),
				event_duration, start_time_event, platform, account, motion_clip, result,
				app_event_time, test_type);
		//@formatter:on
		return executeUpdate(sql);
	}

	public int doInsertMqttTestData(ArrayList<String> testEnv, String cmdSend, String expect,
			String actual, String timeSent, long timeDelta, String result, String mcuAck) {

		//@formatter:off
		String sql = String.format("INSERT INTO [dbo].[MQTT]\r\n" + 
				"([deviceId] " +
				",[fwVersion] " +
				",[user] " +
				",[testSite] " +
				",[testDataFile] " +
				",[cmdSend] " +
				",[cmdExpect] " +
				",[cmdActual] " +
				",[timeSent] " +
				",[timeToReceive] " +
				",[result] " +
				",[mcuAck]) " +

				"VALUES " +
				"('%s' " +
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s' " + 
				",'%s')",
				testEnv.get(0),testEnv.get(1),testEnv.get(2),testEnv.get(3),
				testEnv.get(4),cmdSend,expect,actual,timeSent,timeDelta,result,mcuAck);
		//@formatter:on
		return executeUpdate(sql);
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

		//@formatter:off
		String dataToInsert = "";
		for (String datum : data) {
			dataToInsert += String.format("'%s', ", datum);
			
		}
		dataToInsert = dataToInsert.substring(0,dataToInsert.length()-2); // remove the last {, '}
		String sql = String.format(
				"INSERT INTO [dbo].[%s]\r\n VALUES (%s)",
				dataTable,
				dataToInsert);
		//@formatter:on
		return executeUpdate(sql);

	}
}
