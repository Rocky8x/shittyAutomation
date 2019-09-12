package com.ebn.ecomm.tests;

import static org.testng.Assert.assertEquals;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.ebn.automation.core.WbDriverManager;
import com.ebn.ecomm.dashboard.PageDashboard;
import com.ebn.ecomm.data.AggregatedData;
import com.ebn.ecomm.data.SalesReportData;

/**
 * Hello world!
 *
 */
public class DataComparision extends TestBase {

	String			AmazonCaSaleReportDataFile	= "test-data/saleReportSample.data";
	PageDashboard	dashboard;

	@DataProvider
	public Object[][] dateToCompare() {

		return new Object[][] { { "2019-06-28" }, };
// { "2019-06-29" },
// { "2019-06-30" },
// { "2019-07-01" } };
	}

// @Test(dataProvider = "dateToCompare")
	public void compareByDate_SaleReportVsAggregatedData(String date) throws Exception {

		SalesReportData data = new SalesReportData();
		data.readFromTextFile(AmazonCaSaleReportDataFile);
		data.filterDate("purchase-date", date);
		data.filterMatch("sales-channel", "^Amazon\\.ca$")
// .filterMatch("order-status", "Shipped")
		;
		data.prettyPrint();

		AggregatedData aggregatedData = new AggregatedData();
		aggregatedData.getRawDataFromAmazonCa(date);
		aggregatedData.prettyPrint();
		int		qtyFromSaleSite	= data.count();
		int		qtyFromDb		= aggregatedData.sum("total_orders");
		String	msg				= String.format(
				"Total sale from sale report is %d while from database is %d.", qtyFromSaleSite,
				qtyFromDb);
		assertEquals(qtyFromSaleSite, qtyFromDb, msg);
	}

// @Test(dataProvider = "dateToCompare")
	public void compareByDate_SaleReportVsDashboard(String date) throws Exception {

		dashboard = new PageDashboard();
		SalesReportData amazonCaSaleData = new SalesReportData();
		amazonCaSaleData.readFromTextFile(AmazonCaSaleReportDataFile);
		amazonCaSaleData.filterDate("purchase-date", date);
		amazonCaSaleData.filterMatch("sales-channel", "^Amazon\\.ca$");
		int qtyFromSaleSite = amazonCaSaleData.count();

		dashboard.filterDate(date, date);
		Thread.sleep(1000);
		dashboard.filterChanelOnly("Amazon CA");

		int qtyFromDashboard = Integer.parseInt(dashboard.getSumaryTable("Country")
				.getGrandTotalNumber().getText().replace(",", ""));

		String msg = String.format("Total sale from sale report is %d while from dashboard is %d.",
				qtyFromSaleSite, qtyFromDashboard);

		assertEquals(qtyFromSaleSite, qtyFromDashboard, msg);

	}

// @Test(dataProvider = "dateToCompare")
	public void validate_AggregatedData(String date) throws Exception {

		AggregatedData aggregatedData = new AggregatedData();
		aggregatedData.getRawDataFromAmazonCa(date);
		int	totalOrder	= aggregatedData.sum("total_orders");
		int	sold		= aggregatedData.sum("qty_sold");
		int	shipped		= aggregatedData.sum("qty_shipped");
		int	refund		= aggregatedData.sum("qty_refunded");
		int	canceled	= aggregatedData.sum("qty_canceled");

		aggregatedData.prettyPrint();

		int	sum1	= totalOrder - canceled; // must equal total sold
		int	sum2	= shipped + refund; // must equal total sold

		String message1 = String.format("Total order - cancel = %s - %s = %s must equal Total sold"
				+ "\n        while Total sold = %s", totalOrder, canceled, sum1, sold);
		assertEquals(sum1, sold, message1);

		String message2 = String.format("Total shipped + refund = %s + %s =%s must equal Total sold"
				+ "\n        while Total sold = %s ", shipped, refund, sum2, sold);
		assertEquals(sum2, sold, message2);

	}

	@Test(dataProvider = "dateToCompare")
	public void compareOrderStatus_SaleReportVsAggregatedData(String date) throws Exception {

		SalesReportData amazonCaSalesReport = new SalesReportData();
		amazonCaSalesReport.readFromTextFile(AmazonCaSaleReportDataFile);
		amazonCaSalesReport.filterDatePurchase(date);
		amazonCaSalesReport.filterMatch("sales-channel", "^Amazon\\.ca$");
		amazonCaSalesReport.prettyPrint();

		int	saleReportShipped	= amazonCaSalesReport.countOrderStatus("Shipped");
		int	saleReportCancelled	= amazonCaSalesReport.countOrderStatus("Cancelled");
// int saleReportPending = amazonCaSalesReport.countOrderStatus("Pending");

		AggregatedData amazonCaAggregatedData = new AggregatedData();
		amazonCaAggregatedData.getRawDataFromAmazonCa(date);
		amazonCaAggregatedData.prettyPrint();
		int	aggDataShipped		= amazonCaAggregatedData.sum("qty_shipped");
		int	aggDataCancelled	= amazonCaAggregatedData.sum("qty_canceled");
// int aggDataPending = amazonCaAggregatedData.sum("");

		assertEquals(aggDataShipped, saleReportShipped,
				"Total shipped order of sale report and aggregated data should be equal, but no!");
		assertEquals(aggDataCancelled, saleReportCancelled,
				"Total cancelled order of sale report and aggregated data should be equal, but no!");

	}

	public void closeBrowser() {

		try {
			dashboard.quit();
		} catch (Exception e) {}
	}

	@AfterMethod
	public void screenShot(ITestResult result) {

		if (result.getName().toString().equals("compareByDate_SaleReportVsAggregatedData")) {
			String param = "";
			for (Object p : result.getParameters()) { param += (String) p; }
			param += ".png";
			String screenShotFile = StringHelper.getSystemPath() + "/" + result.getName().toString()
					+ "_" + param;
			WbDriverManager.takeScreenShot(screenShotFile);
		}
		closeBrowser();
	}
}
