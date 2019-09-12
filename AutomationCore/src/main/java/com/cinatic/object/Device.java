package com.cinatic.object;

import java.util.Random;

public class Device {
	private String name;
	private String device_id;	
	private String is_online;
	private String mqtt_topic;
	private String local_ip;
	private String firmware_version;	

	public Device() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public boolean isOnline() {
		return Boolean.parseBoolean(is_online);
	}

	public void setIs_online(String is_online) {
		this.is_online = is_online;
	}

	public String getMqtt_topic() {
		return mqtt_topic;
	}

	public void setMqtt_topic(String mqtt_topic) {
		this.mqtt_topic = mqtt_topic;
	}

	public String getLocal_ip() {
		return local_ip;
	}

	public void setLocal_ip(String local_ip) {
		this.local_ip = local_ip;
	}

	public String getFirmware_version() {
		return firmware_version;
	}

	public void setFirmware_version(String firmware_version) {
		this.firmware_version = firmware_version;
	}	
	
	public static String convertSsidByUuid(String uuid) {
		String ssid = "KodakCam-";
		int model = Integer.parseInt(uuid.substring(3,6));
		if (model < 7 || model == 18) {
			ssid += "C";
		} else {
			ssid += "F";
		}
		ssid += String.format("%s",uuid.substring(3,6));
		ssid += uuid.substring(12,18);
		return ssid;
	}
	
	public static String getModelNameByUuid(String uuid) {
		String C_modelList[] = {"520", "220", "525", "225", "120", "125", "200", "500"};
		String F_modelList[] = {"670", "680", "685"};
		
		Random r = new Random();
		String ssid = convertSsidByUuid(uuid);
		if (ssid.contains("KodakCam-C")) {
			return "C"+ C_modelList[r.nextInt(C_modelList.length)];
		}
		return "F"+ F_modelList[r.nextInt(F_modelList.length)];
	}
}
