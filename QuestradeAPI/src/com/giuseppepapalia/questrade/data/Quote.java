package com.giuseppepapalia.questrade.data;

public final class Quote {

	private final Double bidPrice;
	private final Double askPrice;
	private final Integer bidSize;
	private final Integer askSize;
	private final Double highPrice;
	private final Double lowPrice;
	private final Double openPrice;
	private final Double lastTradePrice;
	private final Integer volume;
	private final Boolean isHalted;

	public Quote(Double bidPrice, Double askPrice, Integer bidSize, Integer askSize, Double highPrice, Double lowPrice, Double openPrice, Double lastTradePrice, Integer volume, Boolean isHalted) {
		this.bidPrice = bidPrice;
		this.askPrice = askPrice;
		this.bidSize = bidSize;
		this.askSize = askSize;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.openPrice = openPrice;
		this.lastTradePrice = lastTradePrice;
		this.volume = volume;
		this.isHalted = isHalted;
	}

	public Double getLastTradePrice() {
		return lastTradePrice;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public Integer getBidSize() {
		return bidSize;
	}

	public Double getAskPrice() {
		return askPrice;
	}

	public Integer getAskSize() {
		return askSize;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public Integer getVolume() {
		return volume;
	}

	public Boolean isHalted() {
		return isHalted;
	}

	@Override
	public int hashCode() {
		return bidPrice.hashCode() + volume.hashCode() + bidSize.hashCode() + volume.hashCode() * 7;
	}
}
