package com.cinatic.driver;

public class DriverSetting {

	private String	app;
	private String	platformName;
	private String	deviceName;
	private String	deviceVersion;
	private String	deviceUDID;
	private String	remoteURL;
	private String	appId; // bundleId in iOS | packageName in Android
	private String	appActivity;

	public String appActivity() {

		return appActivity;
	}

	public void setAppActivity(String appActivity) {

		this.appActivity = appActivity;
	}

	public void setAppId(String packageName) {

		this.appId = packageName;
	}

	public String getAppId() {

		return appId;
	}

	public String getPlatformName() {

		return platformName;
	}

	public void setPlatformName(String platformName) {

		this.platformName = platformName;
	}

	public String getDeviceUDID() {

		return deviceUDID;
	}

	public void setDeviceUDID(String deviceUDID) {

		this.deviceUDID = deviceUDID;
	}

	public String getDeviceName() {

		return deviceName;
	}

	public void setDeviceName(String deviceName) {

		this.deviceName = deviceName;
	}

	public String getDeviceVersion() {

		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {

		this.deviceVersion = deviceVersion;
	}

	public String getRemoteURL() {

		return remoteURL;
	}

	public void setRemoteURL(String remoteURL) {

		this.remoteURL = remoteURL;
	}

	public String getApp() {

		return app;
	}

	public void setApp(String apk) {

		this.app = apk;
	}

}
