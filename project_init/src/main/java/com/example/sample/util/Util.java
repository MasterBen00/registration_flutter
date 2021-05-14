package com.example.sample.util;

import com.github.f4b6a3.uuid.UuidCreator;

public class Util {

	private Util() {
	}

	public static String getUniqueTimeBasedUUID() {

		return UuidCreator.getTimeBased().toString();
	}

	public static Boolean booleanTrueIfNull(Boolean b) {
		return b == null || b;
	}

	public static Boolean booleanFalseIfNull(Boolean b) {
		return b != null && b;
	}

	public static int booleanToInteger(boolean value) {
		return value ? 1 : 0;
	}
}
