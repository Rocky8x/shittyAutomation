package com.auto.core.config;

import java.io.File;

public class TestConstant {
	public static final int longTime = 30;
	public static final int mediumTime = 15;
	public static final int shortTime = 5;
	public static String logLevel = "error";
	public static String resourcePath = System.getenv("CINATIC_AUTOMATION_RES");
	public static String appFolder = resourcePath + File.separator + "app";
	public static String appPackage;
	public static String appVersion;
}
