package com.giuseppepapalia.questrade.data;

public abstract class Underlying {

	protected final String ticker;
	protected final int id;

	public Underlying(String ticker, int id) {
		this.ticker = ticker;
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public String getTicker() {
		return ticker;
	}

	@Override
	public abstract String toString();
}
