package com.ebn.ecomm.dashboard;

import org.openqa.selenium.By;

import com.ebn.automation.core.WbElement;

/**
 * @author cobblestone
 *
 */
public class TableSummary extends WbElement {

	/**
	 * @param tbName : must be 'Country' | 'Channel' | 'Model' only
	 */
	public TableSummary(String tbName) {

		String		xpath	= "//div[@class='table']//div[text()='" + tbName + "']/../../../..";
		WbElement	e		= new WbElement(By.xpath(xpath), "Sumary table: " + tbName);
		this.element		= e.element;
		this.description	= e.description;
	}

	public WbElement getGrandTotalNumber() {

		String xpath = "div[@class='totalsRow']/div[2]/div[@class='totalsContent']/span";
		return this.findSubElement(By.xpath(xpath), "Grand total number");
	}

	public WbElement getGrandTotalRevenue() {

		String xpath = "div[@class='totalsRow']/div[3]/div[@class='totalsContent']/span";
		return new WbElement(this.findElement((By.xpath(xpath))), "Grand total number");
	}

	public WbElement getGrandTotalDiscount() {

		String xpath = "div[@class='totalsRow']/div[4]/div[@class='totalsContent']/span";
		return new WbElement(this.findElement((By.xpath(xpath))), "Grand total number");
	}

	public class Row extends WbElement {

		private String rowName;

		public Row(String rowName) {

			this.rowName = rowName;
			String		xpath	= ".//div[@class='row']/div[text()='" + rowName + "']/..";
			WbElement	e		= TableSummary.this.findSubElement(By.xpath(xpath),
					"Row: " + rowName);
			this.element		= e.element;
			this.description	= e.description;
		}

		public WbElement getCellUnitsSold() {

			String xpath = "./div[2]";
			return findSubElement(By.xpath(xpath), "Row : " + rowName + " : Units sold");
		}

		public WbElement getCellRevenue() {

			String xpath = "./div[3]";
			return findSubElement(By.xpath(xpath), "Row : " + rowName + " : Revenue");
		}

		public WbElement getCellDiscounts() {

			String xpath = "./div[4]";
			return findSubElement(By.xpath(xpath), "Row : " + rowName + " : Discounts");
		}
	}

	
	/**
	 * @param name : first colum of the table is the name
	 * @return : WbElement of entire row
	 */
	public Row getRowByName(String name) {

		return new Row(name);
	}
}
