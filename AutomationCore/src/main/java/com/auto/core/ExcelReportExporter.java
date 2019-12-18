package com.auto.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.auto.core.config.TestConstant;
import com.auto.core.driver.DriverManager;
import com.auto.core.helpers.StringHelper;
import com.auto.core.utils.Log;

public class ExcelReportExporter implements ITestListener {

	private int resultColumn = 2; // 2 for Android, 3 for iOS
	private int testIdColumn = 5; // 5 for Android, 6 for iOS

	private String excelTemplateFile = "../Docs/Templates/EBN_CameraTestPlan_Master.xlsx";
	private String excelOutPutFile = StringHelper.getSystemPath() + "/KODAK App Release Note "
			+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xlsx";
	private final String sheetName = "Core APP-FW checklist";
	private final String rowStartFind = "APP & Functionals checklist";
	private final String rowEndFind = "Remove camera";
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private int startRowIndex;
	private int endRowIndex;
	private boolean flagPlatformSet = false;
	private boolean flagVersionSet = false;
	private String currentTestID="";

	public void setTemplateFile(String templateFilePath) {
		excelTemplateFile = templateFilePath;
	}

	public void setOutPutFile(String reportOutputFilePath) {
		excelOutPutFile = reportOutputFilePath;
	}

	private Row findRow(String cellContent) {
		Iterator<Row> rowIterator = sheet.rowIterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getCellType() == CellType.STRING)
					if (cell.getRichStringCellValue().getString().trim().equals(cellContent))
						return row;
			}
		}
		return null;
	}

	@Override
	public void onTestStart(ITestResult result) {
		currentTestID = prepareReportEntry(result);
		if(!flagVersionSet) {
			XSSFRow verInfoRow = (XSSFRow) findRow("App version");
			XSSFCell verInfoCell = verInfoRow.getCell(resultColumn);
			verInfoCell.setCellValue(TestConstant.appVersion);
			flagPlatformSet = true;
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// fill the report

		for (int iRow = startRowIndex; iRow <= endRowIndex; iRow++) {
			Row row = sheet.getRow(iRow);
			if (currentTestID.equals(row.getCell(testIdColumn).getStringCellValue())) {
				Cell cell = row.getCell(resultColumn);
				if (cell.getStringCellValue() == "") {
					cell.setCellValue("PASS");
					break;
				}
			}
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {

		for (int iRow = startRowIndex; iRow <= endRowIndex; iRow++) {
			Row row = sheet.getRow(iRow);
			if (currentTestID.equals(row.getCell(testIdColumn).getStringCellValue())) {
				Cell cell = row.getCell(resultColumn);
				cell.setCellValue("FAIL");
				break;
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {

		try {
			FileInputStream file = new FileInputStream(new File(excelTemplateFile));
			excelTemplateFile = excelOutPutFile;
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			Log.fatal(" !!! Got error while opening report template");
			e.printStackTrace();
		}

		startRowIndex = findRow(rowStartFind).getRowNum() + 1;
		endRowIndex = findRow(rowEndFind).getRowNum();
	}

	@Override
	public void onFinish(ITestContext context) {
		// write report file
		try {
			FileOutputStream out = new FileOutputStream(new File(excelOutPutFile));
			workbook.write(out);
			workbook.close();
			Log.info("Updated test result to: " + excelOutPutFile);
		} catch (Exception e) {
			Log.fatal(" !!!! Got error while writing report file");
			e.printStackTrace();
		}
	}

	public String prepareReportEntry(ITestResult result) {
		String[] testClassNameFull = result.getTestClass().getName().toString().split("\\.");
		String testClassName = testClassNameFull[testClassNameFull.length - 1];
		String testID = testClassName.split("_")[0];
		if (flagPlatformSet)
			return testID;
		try {
			String platform = (DriverManager.getDriver().getDriverSetting().getPlatformName());
			switch (platform.toLowerCase()) {
			case "ios":
				resultColumn = 3;
				testIdColumn = 6;
				break;
			case "android":
				resultColumn = 2;
				testIdColumn = 5;
				break;
			default:
				break;
			}
			flagPlatformSet = true;
			return testID;
		} catch (Exception e) {
			Log.fatal(" !!! Cannot get running platform");
			e.printStackTrace();
		}
		return null;
	}
}