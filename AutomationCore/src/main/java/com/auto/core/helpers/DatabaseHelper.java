package com.auto.core.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.auto.core.utils.Log;

public class DatabaseHelper {

	// MSSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_SERVER = "192.168.1.101";
	private static final String DB_NAME = "QADB";
	private static final String DB_user = "reporter";
	private static final String DB_passwd = "123123Aa";

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

		String sql = String.format("INSERT INTO %s\r\n (%s) VALUES (%s)", dataTable, fields, dataToInsert);
		return executeUpdate(sql);
	}

}
