package com.ebn.ecomm.dashboard;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

public class TableDashboard {

	
	/**
	 * Get Dashboard table
	 */
	public TableDashboard() {

	}
	public class Cell extends WbElement {

		private static final String	xpathNum			= ".//div[contains(@class,'value')]";
		private static final String	xpathGrowthPercent	= ".//span[@ng-style]";

		public String getNumber() {

			return findSubElement(By.xpath(xpathNum), "value").getText();
		}

		public String getGrowthPercent() {

			return findSubElement(By.xpath(xpathGrowthPercent), "Grow percentage").getText();
		}
		
		public Cell(By by, String desc) {
			super(by, desc);
		}
	}
	public class Row {

		int	colTOTAL	= 5;
		int	colYDAY		= 1;
		int	colWTD		= 2;
		int	colMTD		= 3;
		int	colYTD		= 4;

		private final static String	xpathCells	= "(//div[@class='scorecard-component ng-scope']//div[contains(text(),'%s')]/../../..)[%s]";
		private String				row;

		public Row(String row) {

			this.row = row;
			if ("Blended ACoS".contains(row)) {
				colTOTAL	= 1;
				colYDAY		= 2;
				colWTD		= 3;
				colMTD		= 4;
				colYTD		= 5;
			}
		}

		private Cell getCell(int col) {

			String xpath = String.format(xpathCells, row, col);
			return new Cell(By.xpath(xpath), "cell" + row);
		}

		public Cell getCellTotal() {

			return getCell(colTOTAL);
		}

		public Cell getCellYesterday() {

			return getCell(colYDAY);
		}

		public Cell getCellWeekToDate() {

			return getCell(colWTD);
		}

		public Cell getCellMonthToDate() {

			return getCell(colMTD);
		}

		public Cell getCellYearToDate() {

			return getCell(colYTD);
		}
	}

	public Row	rowUnitsSold	= new Row("Units Sold");
	public Row	rowRevenue		= new Row("Revenue");
	public Row	rowTotalAdSpend	= new Row("Total Ad");
	public Row	rowACoS			= new Row("ACoS");
}
