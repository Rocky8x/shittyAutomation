package com.auto.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.auto.core.helpers.StringHelper;
import com.auto.core.helpers.TimeHelper;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Terminal {

	private String		response	= "";
	private String		teratermLog;
	private SerialPort	serialPort;

	public Terminal(String portName) throws SerialPortException {

		clearTeratermLog();
		serialPort = new SerialPort(portName);
		serialPort.openPort();
		serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialPort.addEventListener(new SerialPortEventListener() {

			@Override
			public void serialEvent(SerialPortEvent serialPortEvent) {

				try {
					response	= StringHelper.cleanInvalidCharacters(
							serialPort.readString(serialPortEvent.getEventValue()));
					teratermLog	+= response;
				} catch (SerialPortException e) {
					Log.error(e.getMessage());
				}
			}
		});
	}

	public Terminal(String portName, boolean printLog) throws SerialPortException {

		clearTeratermLog();
		serialPort = new SerialPort(portName);
		serialPort.openPort();
		serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialPort.addEventListener(new SerialPortEventListener() {

			@Override
			public void serialEvent(SerialPortEvent serialPortEvent) {

				try {
					response	= StringHelper.cleanInvalidCharacters(
							serialPort.readString(serialPortEvent.getEventValue()));
					teratermLog	+= response;
					if (printLog) {
						if (!response.trim().equals(""))
							System.out.println("Camera_Log ==> " + response);
					}
				} catch (SerialPortException e) {
					Log.error(e.getMessage());
				}
			}
		});
	}

	public void sendCommand(String command) throws SerialPortException {

		Log.info(String.format("Sending \'%s\' to \'%s\'", command, serialPort.getPortName()));
		serialPort.writeString("\r " + command + " \r");

	}

	public boolean sendCommand(String command, String expectedMessage, int timeout)
			throws SerialPortException {

		clearTeratermLog();
		serialPort.writeString("\r " + command + " \r");
		for (int i = 0; i < timeout; i++) {
			TimeHelper.sleep(1000);
			if (getTeratermLog().matches(".*" + expectedMessage + ".*")) {
				Log.info(String.format("Sending \'%s\' to \'%s\' sucess", command,
						serialPort.getPortName()));
				return true;
			}
		}
		Log.info(String.format(("Sending \'%s\' to \'%s\' fail: response does not match %s"),
				command, serialPort.getPortName(), expectedMessage));
		return false;
	}

	public void closePort() throws SerialPortException {

		Log.info("Close serial port :", serialPort.getPortName());
		serialPort.closePort();
	}

	public String getTeratermLog() {

		TimeHelper.sleep(10000);
		return teratermLog;
	}

	public void clearTeratermLog() {

		teratermLog = "";
	}

	public String getCameraUdid() throws SerialPortException {

		clearTeratermLog();
		unlockCameraShell();
		sendCommand("config");
		TimeHelper.sleep(2000);

		Pattern	pattern	= Pattern.compile("UDID : \'(.*?)\'");
		Matcher	matcher	= pattern.matcher(teratermLog);
		if (matcher.find()) { return (matcher.group(1)); }
		return "";
	}

	public void unlockCameraShell() throws SerialPortException {

		sendCommand("shell 0 LAnXh7fr7yB3JJEtKqkFBxN3jrEPS4sN");
	}
}
