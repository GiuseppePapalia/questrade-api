package com.giuseppepapalia.questrade.data;

import java.util.Date;

import com.giuseppepapalia.questrade.constants.OptionType;
import com.giuseppepapalia.questrade.util.DateFormatter;

public final class Option extends Underlying {

	private final double strikePrice;
	private final OptionType optionType;
	private final Date expiryDate;

	public Option(String ticker, int id, Date expiryDate, OptionType optionType, double strikePrice) {
		super(ticker, id);
		this.expiryDate = expiryDate;
		this.optionType = optionType;
		this.strikePrice = strikePrice;
	}

//	/**
//	 * Should be in format:
//	 * 
//	 * ID#,MSFT,tickerID#,03/20/2020,Call,strikePrice#
//	 * 
//	 * @param toParse
//	 */
//	public QuestradeOption(String toParse) {
//		String[] arr = toParse.split(",");
//		this.id = Integer.parseInt(arr[0]);
//		this.ticker = new QuestradeTicker(arr[1], Integer.parseInt(arr[2]));
//		this.expiryDate = DateFormatter.parseFormattedOptionDate(arr[3]);
//		this.optionType = OptionType.parse(arr[4]);
//		this.strikePrice = Double.parseDouble(arr[5]);
//	}

	public double getStrikePrice() {
		return strikePrice;
	}

	public OptionType getOptionType() {
		return optionType;
	}

	@Override
	public String getTicker() {
		return ticker;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	@Override
	public int hashCode() {
		return expiryDate.hashCode() * 7 * (int) strikePrice;
	}

	@Override
	public String toString() {
		return ticker + " " + DateFormatter.formatOptionDate(expiryDate) + " " + strikePrice + optionType.toLetter();
	}
}
