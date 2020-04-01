package com.giuseppepapalia.questrade.data.chart;

import java.util.Date;

import com.giuseppepapalia.questrade.util.DateFormatter;
import com.giuseppepapalia.questrade.util.DollarValue;

public final class Candle {

	private final Date startDate;
	private final Date endDate;
	private final double highPrice;
	private final double lowPrice;
	private final double openPrice;
	private final double closePrice;
	private final int volume;

	public Candle(Date startDate, Date endDate, double highPrice, double lowPrice, double openPrice, double closePrice, int volume) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.volume = volume;
	}

	public double getPrice() {
		return (highPrice + closePrice + lowPrice) / 3;
	}

	public boolean isGreen() {
		return closePrice >= openPrice;
	}

	public boolean isRed() {
		return !isGreen();
	}

	public int getVolume() {
		return volume;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public Date getEndDate() {
		return endDate;
	}

	public double getHighPrice() {
		return highPrice;
	}

	public double getLowPrice() {
		return lowPrice;
	}

	public double getOpenPrice() {
		return openPrice;
	}

	public Date getStartDate() {
		return startDate;
	}

	@Override
	public String toString() {
		return DateFormatter.formatSimpleDateTime(startDate) + " -> " + DateFormatter.formatSimpleDateTime(endDate) + "\nOpen: " + new DollarValue(openPrice) + "\nHigh: " + new DollarValue(highPrice) + "\nLow: " + new DollarValue(lowPrice) + "\nCLose: " + new DollarValue(closePrice);
	}

}
