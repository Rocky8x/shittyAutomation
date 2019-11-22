package com.cinatic.config;

import java.io.File;

import com.cinatic.StringHelper;

public class TestConstant {

	public static enum directioName {
		Left, Right, Up, Down
	}

	public static enum motionLevel {
		Low, Medium, High
	}

	public static enum soundLevel {
		Low, Medium, High
	}

	public static String logtime = StringHelper.getCurrentDateTime("MM-dd HH:mm:ss.SSS");
	public static String filecreatetime = StringHelper.getCurrentDateTime("dd_MM_YY_HHmmss");
	public static String logLevel = "error";

	public static String formatDate = "hh:mm a, dd MMM";
	public static String durationformat = "MM-dd HH:mm:ss.SSS";
	public static String systemUser = System.getProperty("user.name");

	public static String emailHost = "@mailinator.com";
	public static String baseURI = "https://api.hubble.in";
	public static String devURI = "https://dev-h2o.hubble.in";
	public static String btURI = "https://bt-r1-cs.hubble.in";
	public static String mqttURI = "https://dev-api.hubble.in";
	public static String stagingURI = "https://staging-h2o.hubble.in";

	public static String resourcePath = System.getenv("CINATIC_AUTOMATION_RES");
	public static String appFolder = resourcePath + File.separator + "app";

	public static String forgetpassword_username = "qaforgotpasstest";
	public static String forgetpassword_email = "qaforgotpasstest@gmail.com";
	public static String forgetpassword_emailPassword = "Aaaa1111";
	public static String forgetpassword_password = "Aaaa1111";

	public static String appPackage;
	public static String appVersion;
}
