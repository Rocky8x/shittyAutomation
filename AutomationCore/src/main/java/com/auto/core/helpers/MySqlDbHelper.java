package com.auto.core.helpers;

import java.sql.DriverManager;
import java.sql.Statement;

import com.auto.core.utils.Log;

public class MySqlDbHelper extends DatabaseHelper {
	private static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String DB_SERVER = "localhost";
	private static String DB_NAME = "QADB";

	private static String DBO_Setup = "Setup";
	private static String DBO_Notificaion = "Notification";
	private static String DBO_Stream = "Streaming";
	private static String DBO_Account = "Account";
	private static String DBO_Video = "Video";

	private static String DB_user = "sa";
	private static String DB_passwd = "sa";

	public MySqlDbHelper() {
		connect();
	}

	@Override
	public void connect() {
		try {
			Class.forName(DB_DRIVER);
			String url = "";

			switch (System.getProperty("os.name").toLowerCase()) {
			case "windows":
			case "linux":
				url = "jdbc:mysql://" + DB_SERVER + "/" + DB_NAME;
				break;
			case "mac":
				url = "jdbc:mysql://qa-server.local" + ";databaseName=" + DB_NAME;
				break;
			}

			conn = DriverManager.getConnection(url, DB_user, DB_passwd);
			Log.info("connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doIn(String username, String password, String server) {
		String sql = String.format("INSERT INTO " + DBO_Account + "\r\n" + "(username, password, server)\r\n"
				+ "     VALUES ('%s', '%s', '%s')", username, password, server);
		try {
			Log.info(sql);
			Statement statement = conn.createStatement();
			int status = statement.executeUpdate(sql);
			System.out.println(String.format("### SQL return %d", status));
		} catch (Exception ex) {

		}
	}

}
