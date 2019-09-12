package com.ebn.ecomm.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cinatic.log.Log;

public class SalesReportData {

	ArrayList<ArrayList<String>> data = new ArrayList<>();

	public void readFromTextFile(String pathToFile) throws Exception {

		Log.info("Reading sale report data from text file: " + pathToFile);
		InputStream		in		= new FileInputStream(new File(pathToFile));
		BufferedReader	fileIn	= new BufferedReader(new InputStreamReader(in));

		String line = fileIn.readLine();

		while (line != null) {
			appendDataRow(line, "\t");
			line = fileIn.readLine();
		}
		fileIn.close();
		in.close();
	}

	@Deprecated()
	public void readFromExcel(String excelFile) throws IOException {

		FileInputStream	fileInStream	= new FileInputStream(new File(excelFile));
		XSSFWorkbook	workbook		= new XSSFWorkbook(fileInStream);
		XSSFSheet		sheet			= workbook.getSheet("saleReport");

		Iterator<Row> rowIterator = sheet.rowIterator();

		while (rowIterator.hasNext()) {
			Row				row				= rowIterator.next();
			Iterator<Cell>	cellIterator	= row.cellIterator();

			ArrayList<String> dataRow = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell	cell	= cellIterator.next();
				String	value;
				try {
					value = cell.getStringCellValue();
				} catch (Exception e) {
					value = Double.toString(cell.getNumericCellValue());
				}
				dataRow.add(value);
			}
			appendDataRow(dataRow);
		}
		workbook.close();

	}

	public void appendDataRow(ArrayList<String> row) {

		data.add(row);
	}

	public void appendDataRow(String[] row) {

		ArrayList<String> rowAdd = new ArrayList<>(Arrays.asList(row));
		data.add(rowAdd);
	}

	public void appendDataRow(String row, String delimiter) {

		appendDataRow(row.split(delimiter));
	}

	/**
	 * filter the column @colName which data match with @match
	 * 
	 * @param colName
	 * @param match
	 * @return this ref
	 */
	public SalesReportData filterMatch(String colName, String match) {

		int colNum = data.get(0).indexOf(colName);

		Iterator<ArrayList<String>> itr = data.iterator();
		itr.next();
		while (itr.hasNext()) {
			ArrayList<String> rowInData = itr.next();
			if (!rowInData.get(colNum).matches(match))
				itr.remove();
		}
		return this;
	}

	/**
	 * @param      colName: name of column which data is date
	 * @param date must be in yyyy-mm-dd format
	 * @return
	 */
	public SalesReportData filterDate(String colName, String date) {

		filterDate(colName, date, date);
		return this;
	}

	/**
	 * @param date must be in yyyy-mm-dd format
	 * @return
	 */
	public SalesReportData filterDatePurchase(String date) {

		filterDate("purchase-date", date, date);
		return this;
	}

	/**
	 * @param      colName: name of column which data is date
	 * @param from date, must be in yyyy-mm-dd format
	 * @param to   date, must be in yyyy-mm-dd format
	 * @return
	 */
	public SalesReportData filterDate(String colName, String from, String to) {

		Log.info("Filter out all " + colName + " that is not from " + from + " to " + to);
		int colNum = data.get(0).indexOf(colName);

		Date	fromDate	= Date.valueOf(from);
		Date	toDate		= Date.valueOf(to);

		Iterator<ArrayList<String>> itr = data.iterator();
		itr.next(); // skip header
		while (itr.hasNext()) {
			ArrayList<String>	rowInData	= itr.next();
			Date				curDate		= Date.valueOf(rowInData.get(colNum).split("T")[0]);

			if (curDate.before(fromDate) || curDate.after(toDate))
				itr.remove();
		}
		return this;
	}

	/**
	 * The name says it all
	 */
	public SalesReportData prettyPrint() {

		int				numberOfCol	= getHeader().size();
		List<Integer>	colWidth	= new ArrayList<Integer>();
		for (int i = 0; i < numberOfCol; i++) { colWidth.add(0); }

		Log.info("---------------- Printing data ----------------");

		// finding width for each column
		for (ArrayList<String> row : data) {
			int i = 0;
			for (String cell : row) {
				int width = cell.length() + 3;
				if (width > colWidth.get(i))
					colWidth.set(i, width);
				i++;
			}
		}

		// printing data
		int printedLineCount = 0;
		for (ArrayList<String> row : data) {
			int i = 0;
			for (String cell : row) {
				printCellWithWidth(cell, colWidth.get(i));
				i++;
			}
			System.out.println();
			printedLineCount++;
		}
		Log.info("----------------- Line printed/Total: " + printedLineCount + "/" + data.size()
				+ " (including header) ------------------");
		return this;
	}

	public ArrayList<String> getHeader() {

		return data.get(0);
	}

	private void printCellWithWidth(String data, int width) {

		System.out.print(data);
		for (int i = 0; i < width - data.length(); i++) { System.out.print(" "); }
	}

	public void readFromResultSet(ResultSet res) throws Exception {

		ResultSetMetaData	header		= res.getMetaData();
		int					numOfCol	= header.getColumnCount();
		ArrayList<String>	headerRow	= new ArrayList<>();
		for (int i = 1; i <= numOfCol; i++) { headerRow.add(header.getColumnName(i)); }
		appendDataRow(headerRow);

		while (res.next()) {
			ArrayList<String> rowToAdd = new ArrayList<>();
			for (int i = 1; i <= numOfCol; i++) {
				String columnValue = res.getString(i) == null ? "null" : res.getString(i);
				rowToAdd.add(columnValue);
			}
			appendDataRow(rowToAdd);
		}
	}

	/**
	 * @param statuses : Pending, Cancelled, Shipped, Shipping
	 * @return number of the order which has status = @status
	 */
	public int countOrderStatus(String... statuses) {

		int	count			= 0;
		int	orderStatCol	= getColumnIndex("order-status");

		for (String status : statuses) {
			for (ArrayList<String> arrayList : data) {
				if (arrayList.get(orderStatCol).equals(status)) { count++; }
			}
		}
		return count;
	}

	public int count() {

		// minus 1 for the header line;
		return data.size() - 1;
	}

	public int getColumnIndex(String columnName) {

		return getHeader().indexOf(columnName);
	}
}
