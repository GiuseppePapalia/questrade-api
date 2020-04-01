package com.giuseppepapalia.questrade.data.chart;

import java.util.Date;
import java.util.List;

public abstract class AbstractChart {

	protected List<Candle> candles;
	protected List<Double> minima;
	protected List<Double> maxima;
	protected double highPrice;
	protected double lowPrice;
	protected int volume;

	public AbstractChart(List<Candle> candles) {
		this.candles = candles;
		setHighPrice();
		setLowPrice();
		setVolume();
		setMaxima();
		setMinima();
	}

	protected abstract List<Double> setMaxima();

	protected abstract List<Double> setMinima();

	protected abstract void setVolume();

	protected abstract void setHighPrice();

	protected abstract void setLowPrice();

	public List<Candle> getCandles() {
		return candles;
	}

	public int getVolume() {
		return volume;
	}

	public Date getStartDate() {
		return candles.get(0).getStartDate();
	}

	public Date getEndDate() {
		return candles.get(candles.size() - 1).getEndDate();
	}

	public double getHighPrice() {
		return lowPrice;
	}

	public double getLowPrice() {
		return highPrice;
	}

	public List<Double> getMaxima() {
		return maxima;
	}

	public List<Double> getMinima() {
		return minima;
	}

	@Override
	public String toString() {
		String s = "";
		for (Candle candle : candles) {
			s += "\n" + candle.toString() + "\n";
		}
		return s;
	}

}
