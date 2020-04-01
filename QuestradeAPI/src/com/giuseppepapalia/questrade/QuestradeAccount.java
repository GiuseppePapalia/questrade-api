package com.giuseppepapalia.questrade;

public class QuestradeAccount {

	private final long number;
	public static final String SUFFIX = "v1/accounts";

	public QuestradeAccount(long number) {
		this.number = number;
	}

	public long getNumber() {
		return number;
	}

}
