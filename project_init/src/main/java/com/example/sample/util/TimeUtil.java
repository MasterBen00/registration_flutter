package com.example.sample.util;

import java.time.ZonedDateTime;
import java.util.Date;

public class TimeUtil {

	private TimeUtil() {
	}

	public static ZonedDateTime timeNowInZonedDateTime() {
		return ZonedDateTime.now();
	}

	public static Double timeNowInDouble() {
		return (double) timeNowToMilli() /1000;
	}

	public static long timeNowToMilli() {
		return timeNowInZonedDateTime().toInstant().toEpochMilli();
	}

	public static long timeNowToSec() {
		return timeNowInZonedDateTime().toInstant().toEpochMilli() / 1000;
	}

	public static Long getZoneDateTime(ZonedDateTime zonedDateTime) {
		if (zonedDateTime == null) {
			return null;
		}

		return zonedDateTime.toInstant().toEpochMilli() / 1000;
	}

	public static Date getDateFromZonedDateTime(ZonedDateTime zonedDateTime) {
		return Date.from(zonedDateTime.toInstant());
	}
}
