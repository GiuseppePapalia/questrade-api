package com.giuseppepapalia.questrade.constants;

public enum OptionType {

	CALL, PUT;

	@Override
	public String toString() {
		switch (this) {
		case PUT:
			return "Put";
		case CALL:
			return "Call";
		}
		return null;
	}

	public String toLetter() {
		switch (this) {
		case PUT:
			return "p";
		case CALL:
			return "c";
		}
		return null;
	}

	public static OptionType parse(String toParse) {
		switch (toParse.toLowerCase()) {
		case "put":
		case "p":
			return PUT;
		case "call":
		case "c":
			return CALL;
		}
		return null;
	}
}
