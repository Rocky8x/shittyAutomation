package com.auto.core.helpers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.auto.core.config.TestConstant;
import com.auto.core.utils.Log;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumHelper {
	private String Appium_Node_IOS_Path = "/usr/local/bin/node";
	private String Appium_JS_IOS_Path = "/Applications/Appium.app/Contents/Resources/app/node_modules/appium/build/lib/main.js";
	private AppiumDriverLocalService service;
	private int port;
	private String platform;

	public AppiumHelper() {
		port = 4723;
	}

	public AppiumHelper(String remoteUrl, String platform) {
		Pattern p = Pattern.compile(":\\d+");
		java.util.regex.Matcher m = p.matcher(remoteUrl);
		String output = "";
		if (m.find()) {
			output += m.group();
		}
		port = Integer.parseInt(output.replace(":", ""));
		this.platform = platform;
	}

	private void startAppiumServer(int port, String platform) {
		if (platform.toLowerCase().equals("android")) {
			Log.info("START APPIUM SERVER");
//			service = AppiumDriverLocalService.buildDefaultService();
			AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, TestConstant.logLevel);
			service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
		} else if (platform.toLowerCase().equals("ios")) {
			Map<String, String> env = new HashMap<>(System.getenv());
			env.put("PATH", "/usr/local/bin:" + env.get("PATH"));
			AppiumServiceBuilder builder = new AppiumServiceBuilder().withIPAddress("0.0.0.0").usingPort(port)
					.withEnvironment(env).usingDriverExecutable(new File(Appium_Node_IOS_Path))
					.withAppiumJS(new File(Appium_JS_IOS_Path));

			service = AppiumDriverLocalService.buildService(builder);
		}
		service.start();
	}

	public void startAppiumServer() {
		startAppiumServer(port, platform);
	}

	public void stopAppiumServer() {
		Log.info("STOP APPIUM SERVER");
		service.stop();
	}
}
