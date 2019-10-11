package com.ebn.mqtt;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.cinatic.FileHelper;
import com.cinatic.StringHelper;
import com.cinatic.log.Log;

/**
 * for normal test, the test report will be generate into a html file
 *
 */
public class SendMqttCommandTestBase extends MqttTestBase {

	protected String	reportFolder						= StringHelper.getSystemPath()
			+ File.separatorChar + "mqtt_command_test_result";
	protected String	txtMqttCommandSendResult			= reportFolder + File.separatorChar
			+ reportFileName + ".tmp";
	protected String	htmlMqttCommandSendReportTemplate	= "../Docs" + File.separatorChar
			+ "Templates" + File.separatorChar + "mqtt_command_result_template.html";

	@BeforeClass
	public void prepare() throws Exception {

		// get environment info to write to text file. @formatter:off
		String textEnvInfo = 
			StringHelper.getCurrentDateTime() + "\n" + 
			testEnvInfo.get(4) + "\n" + 
			device.getDevice_id() + "\n" + 
			device.getFirmware_version() + "\n" +
			testEnvInfo.get(3) + " | " + testEnvInfo.get(2) + "\n";
		Log.info("Test environment: \n", textEnvInfo);
		// @formatter:on
		
		// prepare report folder
		FileHelper.createFolder(reportFolder);
		if (Boolean.parseBoolean(testParams.get("cleanReport"))) {
			Log.info("Cleaning report folder");
			try {
				FileHelper.clearFolder(reportFolder);
			} catch (Exception e) {}
			testParams.put("cleanReport", "false");

			FileHelper.writeFile(txtMqttCommandSendResult, textEnvInfo);
		} else {
			try {
				FileHelper.deleteFiles(reportFolder, "html");
			} catch (Exception e) {}
			if (!FileHelper.isFileExist(txtMqttCommandSendResult)) {
				FileHelper.writeFile(txtMqttCommandSendResult, textEnvInfo);
			}
		}
	}

	@AfterClass
	public void exportHtmlResult() {

		mqttHelper.disconnect();
		try {
			String		textReport			= FileHelper.readFile(txtMqttCommandSendResult);
			String		htmlReportTemplate	= FileHelper
					.readFile(htmlMqttCommandSendReportTemplate);
			String[]	textResultArr		= textReport.split("\n");

			String htmlReportEnvironment = "";
			// building htmlReportContent data: Environment part
			// (first 4 line of textResultArr)
			htmlReportEnvironment	+= String.format("var exeDate = \"%s\";\n", textResultArr[0]);
			htmlReportEnvironment	+= String.format("var cmdFile = \"%s\";\n", textResultArr[1]);
			htmlReportEnvironment	+= String.format("var camModel = \"%s\";\n", textResultArr[2]);
			htmlReportEnvironment	+= String.format("var fwVer = \"%s\";\n", textResultArr[3]);
			htmlReportEnvironment	+= String.format("var testSite = \"%s\";\n", textResultArr[4]);

			// building htmlReportContent data: Detail part (from the 5th line
			// of textResultArr)
			String htmlReportDetail = "";
			for (int i = 5; i < textResultArr.length; i++) {
				// first 5 lines are environment info
				String result = textResultArr[i].trim();
				if (!result.equals("")) {
					String[]	resultArr		= result.split("###");
					int			count			= i - 4;
					String		tcommand		= StringHelper.stringToHTMLString(resultArr[0]);
					String		tActualResult	= StringHelper.stringToHTMLString(resultArr[1]);
					String		tresult			= StringHelper.stringToHTMLString(resultArr[2]);
					String		tStartTime		= StringHelper.stringToHTMLString(resultArr[3]);
					String		tDuration		= StringHelper.stringToHTMLString(resultArr[4]);
					String		tExpected		= StringHelper.stringToHTMLString(resultArr[5]);
					String		tClipboard		= "<a href='#' onclick='setClipboard(this)'>Copy response to clipboard</a>";
					String		tLinkToLog		= "-";
					try {
						tLinkToLog = resultArr[6].equals("empty") ? tLinkToLog
								: String.format("<a href='%s'>To console log</a>",
										resultArr[6] + "consoleFull");
					} catch (Exception ex) {

					}

					htmlReportDetail	+= "<tr>\n";
					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= count;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tcommand;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tresult;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tStartTime;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tDuration;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tExpected;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tActualResult;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tClipboard;
					htmlReportDetail	+= "</td>\n";

					htmlReportDetail	+= "<td>";
					htmlReportDetail	+= tLinkToLog;
					htmlReportDetail	+= "</td>\n";
					htmlReportDetail	+= "</tr>\n";
				}
			}
			/*** updating report template and write to new file ***/
			// getting first part of report template
			int	indexOfEnvironment	= htmlReportTemplate.indexOf("<!--Environment-->");
			int	indexOfDetailPart	= htmlReportTemplate.indexOf("<!--Details-->");

			String htmlReportReady = "";
			htmlReportReady = htmlReportTemplate.substring(0, indexOfEnvironment)
					+ htmlReportEnvironment
					+ htmlReportTemplate.substring(indexOfEnvironment, indexOfDetailPart)
					+ htmlReportDetail
					+ htmlReportTemplate.substring(indexOfDetailPart, htmlReportTemplate.length());

			String url = reportFolder + File.separatorChar + reportFileName + ".html";
			FileHelper.writeFile(url, htmlReportReady);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void appendResult(String action, String actual_result, String passed_failed,
			String starttime, long duration, String expected, String reason) {

		Log.info("Command sent:                         ", action);
		Log.info("Message recieved:                     ", actual_result);
		Log.info("Message expected:                     ", expected);
		Log.info("Result:                               ", passed_failed);
		Log.info("Time sent:                            ", starttime);
		Log.info("Time to received message from server: ", duration + "\n");

		String resultEntry = String.format("%s###%s###%s###%s###%s###%s###%s\n", action,
				actual_result, passed_failed, starttime, duration, expected, reason);
		try {
			FileHelper.writeFile(txtMqttCommandSendResult, resultEntry);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
