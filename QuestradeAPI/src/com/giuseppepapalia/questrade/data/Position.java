package com.giuseppepapalia.questrade.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.giuseppepapalia.questrade.constants.OptionType;

public class Position {

	private Underlying underlying;
	private int openQuantity;
	private double avgEntryPrice;

	public Position(String symbol, int id, int openQuantity, double avgEntryPrice) {
		setUnderlying(symbol, id);
		this.openQuantity = openQuantity;
		this.avgEntryPrice = avgEntryPrice;
	}

	/*
	 * EWI17Apr20P16.00
	 */
	private void setUnderlying(String symbol, int id) {
		// TODO: rework this terrible regex logic if the String is greater than 4 letters (ie an option)
		if (symbol.matches(".*\\d.*")) {
			String ticker = null;
			for (int i = 0; i < symbol.length(); i++) {
				if (Character.isDigit(symbol.charAt(i))) {
					ticker = symbol.substring(0, i);
					symbol = symbol.replace(ticker, "");
					break;
				}
			}

			// Need to add a zero in front of single digit days because questrade is retarded and formats their dates as 3/Apr/20
			for (int i = 0; i < symbol.length(); i++) {
				if (!Character.isDigit(symbol.charAt(i))) {
					if (i < 2) {
						symbol = String.join("0", symbol);
					}
					break;
				}
			}

			SimpleDateFormat f = new SimpleDateFormat("ddMMMyy");
			Date expiryDate = null;
			try {
				expiryDate = f.parse(symbol);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			symbol = symbol.replace(f.format(expiryDate), "");

			OptionType optionType = OptionType.parse(symbol.charAt(0) + "");
			symbol = symbol.substring(1);

			double strikePrice = Double.parseDouble(symbol);
			underlying = new Option(ticker, id, expiryDate, optionType, strikePrice);
		} else {
			underlying = new Stock(symbol, id);
		}
	}

	public static void main(String args[]) {
		SimpleDateFormat f = new SimpleDateFormat("ddMMMyy");
		try {
			String symbol = "03Apr20P244.00";
			Date d = f.parse(symbol);
			System.out.println(symbol.replace(f.format(d), ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Underlying getUnderlying() {
		return underlying;
	}

	public int getID() {
		return underlying.getID();
	}

	public double getAvgEntryPrice() {
		return avgEntryPrice;
	}

	public int getOpenQuantity() {
		return openQuantity;
	}

	public void setOpenQuantity(int openQuantity) {
		this.openQuantity = openQuantity;
	}

	public void setAvgEntryPrice(double avgEntryPrice) {
		this.avgEntryPrice = avgEntryPrice;
	}

	@Override
	public String toString() {
		return underlying.toString();
	}

}
