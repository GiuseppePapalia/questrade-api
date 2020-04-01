package com.giuseppepapalia.questrade.util;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtilities {

	public static boolean areSameDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	public static Date createDate(int day, int month, int year) {
		return new GregorianCalendar(year, month - 1, day).getTime();
	}

	public static Date addBusinessDays(Date date, int days) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		while (days != 0) {

			boolean isWeekend = isWeekend(cal.getTime());
			if (days > 0) {
				days = isWeekend ? days - 1 : days;
				cal.add(Calendar.DAY_OF_YEAR, 1);
			} else {
				days = isWeekend ? days + 1 : days;
				cal.add(Calendar.DAY_OF_YEAR, -1);
			}
		}
		return cal.getTime();
	}

	public static Date getNextBusinessDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		while (isWeekend(cal.getTime())) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}

		if (cal.get(Calendar.HOUR_OF_DAY) < 9 || cal.get(Calendar.HOUR_OF_DAY) == 9 && cal.get(Calendar.MINUTE) < 30) {
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 30);
		}

		return cal.getTime();
	}

	public static Date getLastBusinessDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		while (isWeekend(cal.getTime())) {
			cal.add(Calendar.DAY_OF_YEAR, -1);
		}

		if (cal.get(Calendar.HOUR_OF_DAY) >= 16) {
			cal.set(Calendar.HOUR_OF_DAY, 16);
			cal.set(Calendar.MINUTE, 0);
		} else if (cal.get(Calendar.HOUR_OF_DAY) < 9 || cal.get(Calendar.HOUR_OF_DAY) == 9 && cal.get(Calendar.MINUTE) < 30) {
			cal.add(Calendar.DAY_OF_YEAR, -1);
			while (isWeekend(cal.getTime())) {
				cal.add(Calendar.DAY_OF_YEAR, -1);
			}
			cal.set(Calendar.HOUR_OF_DAY, 16);
			cal.set(Calendar.MINUTE, 0);
		}

		return cal.getTime();
	}

	public static boolean isWeekend(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return DayOfWeek.SATURDAY.getValue() == dayOfWeek || DayOfWeek.SUNDAY.getValue() == dayOfWeek;
	}

	public static Date setTimeToMarketOpen(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 30);
		return cal.getTime();
	}

	public static Date setTimeToMarketClose(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 16);
		cal.set(Calendar.MINUTE, 0);
		return cal.getTime();
	}

}
