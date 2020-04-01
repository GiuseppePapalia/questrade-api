package com.giuseppepapalia.questrade.data.chart;

import java.util.ArrayList;
import java.util.List;

public class HistoricalChart extends AbstractChart {

	public HistoricalChart(List<Candle> candles) {
		super(candles);
	}

	@Override
	protected List<Double> setMaxima() {
		maxima = new ArrayList<Double>();

		/*
		 * Skip over the first and last candle because we don't know their neighbors and therefore cannot declare it a maxima or minima
		 */
		for (int i = 1; i < candles.size() - 1; i++) {
			if (candles.get(i - 1).getHighPrice() < candles.get(i).getHighPrice() && candles.get(i).getHighPrice() > candles.get(i + 1).getHighPrice()) {
				maxima.add(candles.get(i).getHighPrice());
			}
		}

		return maxima;
	}

	@Override
	protected List<Double> setMinima() {
		minima = new ArrayList<Double>();

		/*
		 * Skip over the first and last candle because we don't know their neighbors and therefore cannot declare it a maxima or minima
		 */
		for (int i = 1; i < candles.size() - 1; i++) {
			if (candles.get(i - 1).getLowPrice() > candles.get(i).getLowPrice() && candles.get(i).getLowPrice() < candles.get(i + 1).getLowPrice()) {
				minima.add(candles.get(i).getLowPrice());
			}
		}

		return minima;
	}

	@Override
	protected void setVolume() {
		for (Candle candle : candles) {
			volume += candle.getVolume();
		}
	}

	@Override
	protected void setHighPrice() {
		highPrice = 0;
		for (Candle candle : candles) {
			if (highPrice < candle.getHighPrice()) {
				highPrice = candle.getHighPrice();
			}
		}
	}

	@Override
	protected void setLowPrice() {
		lowPrice = 500000000;
		for (Candle candle : candles) {
			if (lowPrice > candle.getLowPrice()) {
				lowPrice = candle.getLowPrice();
			}
		}
	}

}
