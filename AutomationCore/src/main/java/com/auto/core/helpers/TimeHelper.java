package com.auto.core.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.auto.core.utils.Log;

public class TimeHelper {

	public static void sleep(int duration) {

		try {
			Log.debug("Wait for " + milisec2String(duration));
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getRandomTime(String format) {

		Random				random	= new Random();
		Date				date	= new Date(random.nextLong());
		SimpleDateFormat	sdf		= new SimpleDateFormat(format);
		String				rs		= sdf.format(date);
		Log.info("Random time: " + rs);
		return rs;
	}

	public static String milisec2String(int miliseconds) {

		int hour = 0, min = 0, sec = 0, mili = miliseconds;
		if (miliseconds >= 1000) {
			sec		= miliseconds / 1000;
			mili	= miliseconds % 1000;

			if (sec > 60) {
				min	= sec / 60;
				sec	= sec % 60;

				if (min > 60) {
					hour	= min / 60;
					min		= min % 60;
				}
			}
		}
		String[]	label	= {	"h",
								"m",
								"s",
								"ms" };
		String[]	num		= {	hour + "",
								min + "",
								sec + "",
								mili + "" };
		String		ret		= "";
		for (int i = 0; i < 4; i++) {
			if (!num[i].equals("0"))
				ret += num[i] + label[i] + ' ';
		}
		return ret;
	}
}
