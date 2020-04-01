package com.giuseppepapalia.questrade.constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum Interval {

	ONE_MINUTE, TWO_MINUTES, FIVE_MINUTES, TEN_MINUTES, FIFTEEN_MINUTES, TWENTY_MINUTES, HALF_HOUR, ONE_HOUR, TWO_HOURS, FOUR_HOURS, ONE_DAY, ONE_WEEK, ONE_MONTH, ONE_YEAR;

	@Override
	public String toString() {
		switch (this) {
		case FIFTEEN_MINUTES:
			return "FifteenMinutes";
		case FIVE_MINUTES:
			return "FiveMinutes";
		case FOUR_HOURS:
			return "FourHours";
		case HALF_HOUR:
			return "HalfHour";
		case ONE_DAY:
			return "OneDay";
		case ONE_HOUR:
			return "OneHour";
		case ONE_MINUTE:
			return "OneMinute";
		case ONE_MONTH:
			return "OneMonth";
		case ONE_WEEK:
			return "OneWeek";
		case ONE_YEAR:
			return "OneYear";
		case TEN_MINUTES:
			return "TenMinutes";
		case TWENTY_MINUTES:
			return "TwentyMinutes";
		case TWO_HOURS:
			return "TwoHours";
		case TWO_MINUTES:
			return "TwoMinutes";
		default:
			return null;

		}
	}

	public Date addInterval(Date startDate) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		switch (this) {
		case FIFTEEN_MINUTES:
			cal.add(Calendar.MINUTE, 15);
			return cal.getTime();
		case FIVE_MINUTES:
			cal.add(Calendar.MINUTE, 5);
			return cal.getTime();
		case FOUR_HOURS:
			cal.add(Calendar.HOUR, 4);
			return cal.getTime();
		case HALF_HOUR:
			cal.add(Calendar.MINUTE, 30);
			return cal.getTime();
		case ONE_DAY:
			cal.add(Calendar.DAY_OF_YEAR, 1);
			return cal.getTime();
		case ONE_HOUR:
			cal.add(Calendar.HOUR, 1);
			return cal.getTime();
		case ONE_MINUTE:
			cal.add(Calendar.MINUTE, 1);
			return cal.getTime();
		case ONE_MONTH:
			cal.add(Calendar.MONTH, 1);
			return cal.getTime();
		case ONE_WEEK:
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			return cal.getTime();
		case ONE_YEAR:
			cal.add(Calendar.YEAR, 1);
			return cal.getTime();
		case TEN_MINUTES:
			cal.add(Calendar.MINUTE, 10);
			return cal.getTime();
		case TWENTY_MINUTES:
			cal.add(Calendar.MINUTE, 20);
			return cal.getTime();
		case TWO_HOURS:
			cal.add(Calendar.HOUR, 2);
			return cal.getTime();
		case TWO_MINUTES:
			cal.add(Calendar.MINUTE, 2);
			return cal.getTime();
		default:
			return null;

		}
	}

}
