package com.ebn.ecomm.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cinatic.log.Log;

public class AggregatedData {

	String				DB_SERVER	= "datawarehouse.c36vlfoezvt0.us-east-1.rds.amazonaws.com";
	String				DB_NAME		= "dwh_rawdata";
	String				DB_USER		= "readonly";
	String				DB_PASSWD	= "Pvndva_reF2QQWcx";
	String				url			= "jdbc:mysql://" + DB_SERVER + "/" + DB_NAME;
	Connection			conn;
	ResultSet			data;
	ResultSetMetaData	metaData;

	public ResultSet getData() {

		return data;
	}

	public AggregatedData() throws Exception {

		conn = DriverManager.getConnection(url, DB_USER, DB_PASSWD);
	}

	protected ResultSet executeQuery(String sql) {

		try {
			Log.info(sql);
			Statement statement = conn.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
			return null;
		}
	}

	public AggregatedData getRawDataFromAmazonCa(String date) throws Exception {

		getRawDataFromAmazonCa(date, date);
		return this;
	}

	public AggregatedData getRawDataFromAmazonCa(String dateFrom, String dateTo) throws Exception {

		Log.info("Getting aggregated data of Amazon CA from " + dateFrom + " to " + dateTo);
		String sqlStatement = "select soi.*, p.brand, "
				+ "c.name as channel_name, w.name as warehouse_name "
				+ "from opa_sales_order_item_aggregate soi "
				+ "inner join opa_warehouse w on w.id=soi.warehouse_id "
				+ "inner join opa_channel c on c.id=soi.channel_id "
				+ "left join opa_product p on p.sku=soi.sku " + "where `date` >= \"" + dateFrom
				+ "\" and `date` <= \"" + dateTo + "\" and channel_id = 508";
		data		= executeQuery(sqlStatement);
		metaData	= data.getMetaData();
		return this;
	}

	public AggregatedData filterByDate(Date start, Date end) throws Exception {

		while (data.next()) {
			Date dataDate = data.getDate("date");
			if (dataDate.after(start) & dataDate.before(end)) {
				System.out.println(data.getString(1));
			}
		}
		return this;
	}

	/**
	 * The name says it all
	 * @throws Exception
	 */
	public void prettyPrint() throws Exception {

		int columnsNumber = metaData.getColumnCount();

		List<Integer> colWidth = new ArrayList<Integer>();
		for (int i = 0; i < columnsNumber; i++) { colWidth.add(0); }

		Log.info("---------------- Printing data ----------------");

		// finding collum width
		for (int i = 0; i < columnsNumber; i++) { // for header
			int width = metaData.getColumnName(i + 1).length() + 3;
			if (width > colWidth.get(i))
				colWidth.set(i, width);
		}
		while (data.next()) { // for data
			for (int i = 0; i < columnsNumber; i++) {
				String	columnValue	= data.getString(i + 1) == null ? "null"
						: data.getString(i + 1);
				int		width		= columnValue.length() + 3;
				if (width > colWidth.get(i))
					colWidth.set(i, width);
			}
		}
		data.beforeFirst();

		for (int i = 0; i < columnsNumber; i++) {
			printCellWithWidth(metaData.getColumnName(i + 1), colWidth.get(i));
		}
		System.out.println();

		while (data.next()) {
			for (int i = 0; i < columnsNumber; i++) {
				String columnValue = data.getString(i + 1) == null ? "null" : data.getString(i + 1);
				printCellWithWidth(columnValue, colWidth.get(i));
			}
			System.out.println();
		}
		data.beforeFirst();
	}

	private void printCellWithWidth(String data, int width) {

		System.out.print(data);
		for (int i = 0; i < width - data.length(); i++) { System.out.print(" "); }
	}

	/**
	 * @param colName column name to get sum
	 * @return int, sum of the @colName which data is number
	 * @throws Exception
	 */
	public int sum(String colName) throws Exception {

		int					colNum	= 0;
		ResultSetMetaData	header	= data.getMetaData();
		for (int i = 1; i <= header.getColumnCount(); i++) {
			if (header.getColumnName(i).equals(colName)) {
				colNum = i;
				break;
			}
		}
		if (colNum ==0) {
			return 0;
		}

		int count = 0;
		while (data.next()) {

			count += data.getInt(colNum);
		}
		data.beforeFirst();
		return count;
	}
}
