package com.giuseppepapalia.questrade.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	public static final SimpleDateFormat OPTION_FORMATTED = new SimpleDateFormat("MM/dd/yyyy");
	public static final SimpleDateFormat OPTION_UNFORMATTED = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat CANDLE = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	public static final SimpleDateFormat SIMPLE = new SimpleDateFormat("MM/dd/yyyy-HH:mm");

	public static String formatSimpleDateTime(Date date) {
		return SIMPLE.format(date);
	}

	public static Date parseCandleDate(String candleDate) {
		try {
			return CANDLE.parse(candleDate.replace("T", "-"));
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatOptionDate(Date date) {
		return OPTION_FORMATTED.format(date);
	}

	public static Date parseFormattedOptionDate(String date) {
		try {
			return OPTION_FORMATTED.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parseUnformattedOptionDate(String date) {
		try {
			return OPTION_UNFORMATTED.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
}
