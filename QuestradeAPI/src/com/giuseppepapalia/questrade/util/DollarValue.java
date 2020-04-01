package com.giuseppepapalia.questrade.util;

import java.text.NumberFormat;

public final class DollarValue {

	double value;

	public DollarValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return NumberFormat.getCurrencyInstance().format(value);
	}

}
