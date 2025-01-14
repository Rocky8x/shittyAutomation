package com.auto.core.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

import org.springframework.util.FileSystemUtils;

import com.auto.core.config.TestConstant;
import com.auto.core.utils.Log;

public class FileHelper {

	public static void writeText(String fileName, String text) {

		File			logFile	= new File(fileName);
		BufferedWriter	writer	= null;
		try {
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(text);
			writer.close();
		} catch (IOException ex) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String[] createImageFromBase(String udid) throws IOException {

		String	filePath	= TestConstant.resourcePath + "\\images\\";
		String	timeStamp	= StringHelper.getCurrentTimeStamp();
		String	fileName	= udid.substring(6, 18) + "_04_" + timeStamp + ".jpg";
		org.apache.commons.io.FileUtils.copyFile(
				new File(TestConstant.resourcePath + "\\push_image.jpg"),
				new File(filePath + fileName));
		return new String[] {	filePath,
								fileName,
								timeStamp };
	}

	public static String readFile(String filename) throws IOException {

		String		content	= null;
		File		file	= new File(filename);
		FileReader	reader	= null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) { reader.close(); }
		}
		return content;
	}

	public static void writeFile(String filename, String input) throws IOException {

		FileOutputStream	out	= new FileOutputStream(filename, true);
		Writer				w	= new OutputStreamWriter(out, "UTF-8");
		w.write(input);
		w.close();
		out.close();
	}

	public static String readIni(String fileName, String key) {

		Properties p = new Properties();
		try {
			p.load(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(String.format("Load Ini: %s=%s", key, p.getProperty(key)));
		return p.getProperty(key);
	}

	public static String formatMQTT(String command, String jsonFormat) {

		String[] arrJson = jsonFormat.split("\n");

		String[]	arrInput		= command.split("&");
		String		strCommand		= arrInput[0].toUpperCase();
		String		strAttributes	= "";

		for (int j = 1; j < arrInput.length; j++) {
			String strInput = arrInput[j];
			strInput = String.format("%s:%s", strInput.split("=")[0], strInput.split("=")[1]);
			for (int i = 0; i < arrJson.length; i++) {
				if (arrJson[i].contains(strInput.split(":")[0])) {
					arrJson[i] = arrJson[i].replace("string", strInput.split(":")[1]);
				}
			}
		}

		for (int i = 0; i < arrJson.length; i++) {
			if (arrJson[i].contains("string")) { arrJson[i] = ""; }
		}
		for (int i = 0; i < arrJson.length; i++) { strAttributes += arrJson[i]; }
		return String.format("{ \"command\": \"%s\",\r%s", strCommand, strAttributes);
	}

	public static void copyDirectory(File source, File target) throws IOException {

		if (!target.exists()) { target.mkdir(); }

		for (String f : source.list()) { copy(new File(source, f), new File(target, f)); }
	}

	public static void copy(File sourceLocation, File targetLocation) throws IOException {

		if (sourceLocation.isDirectory()) {
			copyDirectory(sourceLocation, targetLocation);
		} else {
			copyFile(sourceLocation, targetLocation);
		}
	}

	public static void copyFile(File source, File target) throws IOException {

		try (InputStream in = new FileInputStream(source);
				OutputStream out = new FileOutputStream(target)) {
			byte[]	buf	= new byte[1024];
			int		length;
			while ((length = in.read(buf)) > 0) { out.write(buf, 0, length); }
		}
	}

	public static void clearFolder(String path) throws IOException {

		Log.info("Clean up folder: ", path);
		File f = new File(path);
		FileSystemUtils.deleteRecursively(f);
		f.mkdir();
	}

	public static void deleteFiles(String folder, final String ext) {

		Log.info("Delete all '.", ext, "' file in: ", folder);
		File dir = new File(folder);

		// list out all the file name with .txt extension
		File[] list = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {

				return file.isFile() && file.getName().endsWith(ext);
			}
		});

		if (list.length == 0)
			return;

		for (File file : list) { file.delete(); }
	}

	public static boolean createFolder(String folder) throws Exception {

		Log.info("Create folder", folder, " if not yet existed");
		File dir = new File(folder);
		return dir.mkdirs();
	}

	public static boolean isFileExist(String filePath) {

		File	file	= new File(filePath);
		boolean	e		= file.exists();
		if (e) {
			if (!file.isFile()) {
				Log.fatal(filePath, "is not a file");
				return false;
			}
		}
		Log.info(filePath, "is exist?", e + "");
		return e;
	}
}
