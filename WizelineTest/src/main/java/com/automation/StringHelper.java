package com.automation;

public class StringHelper {

	public static String arrayToString(String... strings) {

		String x = "";
		for (String string : strings) {
			x += string;
			if (!string.endsWith(" "))
				x += " ";
		}
		return x;
	}
}
