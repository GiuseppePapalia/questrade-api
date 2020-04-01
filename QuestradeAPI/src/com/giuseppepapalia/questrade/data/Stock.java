package com.giuseppepapalia.questrade.data;

public final class Stock extends Underlying {

	public Stock(String ticker, int id) {
		super(ticker, id);
	}

	public static final String SUFFIX = "v1/symbols/search?prefix=";

	@Override
	public String toString() {
		return ticker;
	}
}
