package com.cinatic.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.ISuite;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;

import com.cinatic.StringHelper;
import com.cinatic.config.TestConstant;
import com.cinatic.log.Log;

public class KodakCustomizedHTMLReporter extends HTMLReporter {

	String htmlpath;

	File				screenshotsFolder;
	ArrayList<String>	screenshots;
	Map<String, String>	resultMap;

	/*
	 * main
	 */
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectoryName) {

		super.generateReport(xmlSuites, suites, outputDirectoryName);

		try {
			prepareReportData();
			modifyOverviewReport();
			modifySuitesReport();
		} catch (Exception e) {
			Log.info(String.format("catch exception: "));
			e.printStackTrace();
		}

	}

	private Map<String, String> getHtmlResult() {

		Map<String, String>	res		= new HashMap<String, String>();
		File				files[]	= new File(htmlpath).listFiles();
		if (files != null) {
			for (File file : files) {
				// suite1_test1_results
				if (file.toString()
						.matches(".*" + File.separatorChar + "suite\\d+_test\\d+_results.html")) {
					String modFile = file.toString().split("\\.")[0] + "_mod.html";
					res.put(file.toString(), modFile);
				}
			}
		}
		return res;
	}

	private ArrayList<String> getPictures() {

		ArrayList<String>	pictures			= new ArrayList<String>();
		File				screenshotFile[]	= screenshotsFolder.listFiles();
		if (screenshotFile != null) {
			for (File file : screenshotFile) {
				if (file.getName().contains(".png")) { pictures.add(file.getName()); }
			}
		}
		return pictures;
	}

	private String trimFileExt(String filename) {

		return filename.substring(0, filename.length() - 4);
	}

	private String timeConvert(String duration) {

		float	totalSecs	= Float.parseFloat(duration);
		int		hours		= (int) totalSecs / 3600;
		int		minutes		= (int) (totalSecs % 3600) / 60;
		int		seconds		= (int) totalSecs % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	private void prepareReportData() {

		htmlpath = StringHelper.getSystemPath() + File.separatorChar + "html";
		Log.info("++++++++++++ ", htmlpath, " ++++++++++++");

		screenshotsFolder	= new File(htmlpath);
		screenshots			= getPictures();
		resultMap			= getHtmlResult();
		for (String string : screenshots) { Log.info(string); }
		for (String key : resultMap.keySet()) { Log.info(resultMap.get(key)); }

	}

	private void modifyOverviewReport() throws Exception {

		/*
		 * edit overview.html file converting time unit from second to H:m:s
		 */
		String	overviewReportOld	= htmlpath + File.separatorChar + "overview.html";
		String	overviewReportNew	= htmlpath + File.separatorChar + "overview_new.html";

		BufferedReader	bufferedReader	= new BufferedReader(new FileReader(overviewReportOld));
		BufferedWriter	bufferedWriter	= new BufferedWriter(new FileWriter(overviewReportNew));
		String			duration;
		String			line;
		float			totalDuration	= 0;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.contains("<table class=\"overviewTable\">")) {
				bufferedWriter
						.write("<h2>" + " App version = " + TestConstant.appVersion + "</h2>");
			} else if (line.contains("<td class=\"duration\">")) {
				bufferedWriter.write(line);
				bufferedWriter.newLine();
				line			= bufferedReader.readLine().trim();
				duration		= line.substring(0, line.length() - 1);
				totalDuration	+= Float.parseFloat(duration);

				line = timeConvert(duration);
			} else if (line.contains("class=\"totalLabel\">Total<")) {
				line = "    <td class=\"totalLabel\">Total</td>\n";
				bufferedWriter.write(line);
				bufferedWriter.newLine();
				line = bufferedReader.readLine();

				bufferedWriter.write(String.format("        <td class=\"duration\">%s</td>\n",
						timeConvert(String.valueOf(totalDuration))));
				bufferedWriter.newLine();

				line = bufferedReader.readLine();
			}
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}
		bufferedReader.close();
		bufferedWriter.close();
		File oldOverview = new File(overviewReportOld);
		oldOverview.renameTo(new File(overviewReportOld + ".backup"));
		new File(overviewReportNew).renameTo(oldOverview);
	}

	private void modifySuitesReport() throws Exception {

		/*
		 * edit suite files one by one,
		 * convert exec time from second to h:m:s,
		 * insert hyperlink to screenshot.
		 */
		Set<String> oldResults = resultMap.keySet();
		for (String oldResultFile : oldResults) {
			boolean			flagDurationConverted	= false;
			String			newResultFile			= resultMap.get(oldResultFile);
			BufferedReader	bufferedReader			= new BufferedReader(
					new FileReader(oldResultFile));
			BufferedWriter	bufferedWriter			= new BufferedWriter(
					new FileWriter(newResultFile));
			String			lineRead;
			String			lineWrite				= "";
			String			duration;

			while ((lineRead = bufferedReader.readLine()) != null) {

				// ignore blank line, do not copy to new file
				if (lineRead.trim().equals("")) { continue; }

				// convert exec time at the beginning of the file from second to Hour:Min:Sec
				if (!flagDurationConverted) {
					if (lineRead.contains("Test duration:")) {
						/*
						 * line sample: Test duration: 274.091s
						 */

						duration	= lineRead.split(":")[1];
						duration	= duration.substring(0, duration.length() - 1);

						String timeString = timeConvert(duration);
						lineWrite = "  Test duration: " + timeString;

						bufferedWriter.write(lineWrite);
						bufferedWriter.newLine();
						flagDurationConverted = true;
						continue;
					} else {
						bufferedWriter.write(lineRead);
						bufferedWriter.newLine();
						continue;
					}
				}

				// insert hyperlink to image file
				if (screenshots.size() > 0) {
					lineWrite = lineRead;
					if (lineRead.contains("<td class=\"method\">")) {
						bufferedWriter.write(lineRead);
						bufferedWriter.newLine();
						lineRead = bufferedReader.readLine();
						for (String picture : screenshots) {
							String	tcName			= trimFileExt(picture);
							String	htmlHyperLink	= String.format("<a href=\"%s\">%s</a>",
									picture, tcName);
							lineWrite = lineRead.replace(tcName, htmlHyperLink);
							if (!lineRead.equals(lineWrite)) {
								screenshots.remove(picture);
								break;
							}
						}
					}
					bufferedWriter.write(lineWrite);
					bufferedWriter.newLine();
				} else {
					bufferedWriter.write(lineRead);
					bufferedWriter.newLine();
				}
			}

			// write a note to tell this is the modified html report and
			// close file.
			bufferedWriter.write("Modified version of HTMLReporter");
			bufferedReader.close();
			bufferedWriter.close();

			// Once everything is complete, backup old file..
			File oldFile = new File(oldResultFile);
			oldFile.renameTo(
					new File(htmlpath + File.separatorChar + oldFile.getName() + ".backup"));

			// And rename tmp file's name to old file name
			File newFile = new File(newResultFile);
			newFile.renameTo(oldFile);
		}
	}
}
