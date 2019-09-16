package com.automation;

import org.apache.log4j.Logger;

public class Log {

	public static void info(String... message) {

		String	trace	= Thread.currentThread().getStackTrace()[2].getClassName();
		Logger	logger	= Logger.getLogger(trace);
		logger.info(StringHelper.arrayToString(message));
	}

	public static void warn(String... message) {

		String	trace	= Thread.currentThread().getStackTrace()[2].getClassName();
		Logger	logger	= Logger.getLogger(trace);
		logger.warn(StringHelper.arrayToString(message));
	}

	public static void error(String... message) {

		String	trace	= Thread.currentThread().getStackTrace()[2].getClassName();
		Logger	logger	= Logger.getLogger(trace);
		logger.error(StringHelper.arrayToString(message));
	}

	public static void fatal(String... message) {

		String	trace	= Thread.currentThread().getStackTrace()[2].getClassName();
		Logger	logger	= Logger.getLogger(trace);
		logger.fatal(StringHelper.arrayToString(message));
	}

	public static void debug(String... message) {

		String	trace	= Thread.currentThread().getStackTrace()[2].getClassName();
		Logger	logger	= Logger.getLogger(trace);
		logger.debug(StringHelper.arrayToString(message));
	}
}
