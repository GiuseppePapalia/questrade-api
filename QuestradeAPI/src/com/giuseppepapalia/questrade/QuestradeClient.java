package com.giuseppepapalia.questrade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import com.giuseppepapalia.questrade.constants.Interval;
import com.giuseppepapalia.questrade.data.Position;
import com.giuseppepapalia.questrade.data.Quote;
import com.giuseppepapalia.questrade.data.Stock;
import com.giuseppepapalia.questrade.data.chart.Candle;
import com.giuseppepapalia.questrade.util.DateFormatter;
import com.giuseppepapalia.questrade.util.DateUtilities;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class QuestradeClient {

	private QuestradeAPI api;

	public QuestradeClient(QuestradeAPI api) {
		this.api = api;
	}

	private JsonObject get(String suffix) {
		try {
			URL url = new URL(api.getServer() + suffix);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", api.getAuthorization());

			if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			JsonObject obj = new Gson().fromJson(br.readLine(), JsonObject.class);
			conn.disconnect();
			return obj;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Stock getStock(String symbol) {
		JsonObject obj = get(Stock.SUFFIX + symbol);
		int id = obj.get("symbols").getAsJsonArray().get(0).getAsJsonObject().get("symbolId").getAsInt();
		return new Stock(symbol, id);
	}

	public Quote getQuote(Stock stock) {
		return getQuote(stock.getID());
	}

	public Quote getQuote(int id) {
		String suffix = "v1/markets/quotes?ids=" + id;
		JsonObject obj = get(suffix).get("quotes").getAsJsonArray().get(0).getAsJsonObject();

		Double bidPrice = 0.00;
		if (!obj.get("bidPrice").isJsonNull()) {
			bidPrice = obj.get("bidPrice").getAsDouble();
		}
		Double askPrice = 0.00;
		if (!obj.get("askPrice").isJsonNull()) {
			askPrice = obj.get("askPrice").getAsDouble();
		}

		Integer bidSize = 0;
		if (!obj.get("bidSize").isJsonNull()) {
			bidSize = obj.get("bidSize").getAsInt();
		}

		Integer askSize = 0;
		if (!obj.get("askSize").isJsonNull()) {
			askSize = obj.get("askSize").getAsInt();
		}

		Double highPrice = 0.00;
		if (!obj.get("highPrice").isJsonNull()) {
			highPrice = obj.get("highPrice").getAsDouble();
		}

		Double lowPrice = 0.00;
		if (!obj.get("lowPrice").isJsonNull()) {
			lowPrice = obj.get("lowPrice").getAsDouble();
		}

		Double openPrice = 0.00;
		if (!obj.get("openPrice").isJsonNull()) {
			openPrice = obj.get("openPrice").getAsDouble();
		}

		Double lastTradePrice = 0.00;
		if (!obj.get("lastTradePrice").isJsonNull()) {
			lastTradePrice = obj.get("lastTradePrice").getAsDouble();
		}

		Integer volume = 0;
		if (!obj.get("volume").isJsonNull()) {
			volume = obj.get("volume").getAsInt();
		}

		Boolean isHalted = false;
		if (!obj.get("isHalted").isJsonNull()) {
			isHalted = obj.get("isHalted").getAsBoolean();
		}

		return new Quote(bidPrice, askPrice, bidSize, askSize, highPrice, lowPrice, openPrice, lastTradePrice, volume, isHalted);
	}

	public List<Position> getPositions(QuestradeAccount account) {
		List<Position> positions = new ArrayList<Position>();
		String suffix = "v1/accounts/" + account.getNumber() + "/positions";
		JsonArray arr = get(suffix).get("positions").getAsJsonArray();
		Iterator<JsonElement> i = arr.iterator();
		while (i.hasNext()) {
			JsonObject obj = i.next().getAsJsonObject();
			int openQuantity = obj.get("openQuantity").getAsInt();
			if (openQuantity != 0) {
				String symbol = obj.get("symbol").getAsString().replaceAll("\"", "");
				int id = obj.get("symbolId").getAsInt();
				double avgEntryPrice = obj.get("averageEntryPrice").getAsDouble();
				positions.add(new Position(symbol, id, openQuantity, avgEntryPrice));
			}
		}
		return positions;
	}

	public QuestradeAccount getAccount() {
		String s = get(QuestradeAccount.SUFFIX).get("accounts").getAsJsonArray().get(0).getAsJsonObject().get("number").getAsString().replaceAll("\"", "");
		return new QuestradeAccount(Long.parseLong(s));
	}

	public Candle getCandle(Stock stock, Date startDate, Interval interval) {
		Date endDate = interval.addInterval(startDate);
		String start = DateTimeFormatter.ISO_INSTANT.format(startDate.toInstant());
		String end = DateTimeFormatter.ISO_INSTANT.format(endDate.toInstant());

		String suffix = "v1/markets/candles/" + stock.getID() + "?startTime=" + start + "&endTime=" + end + "&interval=" + interval;
		JsonObject obj = get(suffix).get("candles").getAsJsonArray().get(0).getAsJsonObject();

		double lowPrice = obj.get("low").getAsDouble();
		double highPrice = obj.get("high").getAsDouble();
		double openPrice = obj.get("open").getAsDouble();
		double closePrice = obj.get("close").getAsDouble();
		int volume = obj.get("volume").getAsInt();

		return new Candle(startDate, endDate, highPrice, lowPrice, openPrice, closePrice, volume);
	}

	public List<Candle> getCandles(Stock stock, Date startDate, Date endDate, Interval interval) {

		/*
		 * Adjust times to start and end of trading day
		 */
		startDate = DateUtilities.getNextBusinessDay(startDate);
		endDate = DateUtilities.getLastBusinessDay(endDate);

		String start = DateTimeFormatter.ISO_INSTANT.format(startDate.toInstant());
		String end = DateTimeFormatter.ISO_INSTANT.format(endDate.toInstant());

		String suffix = "v1/markets/candles/" + stock.getID() + "?startTime=" + start + "&endTime=" + end + "&interval=" + interval;
		Iterator<JsonElement> i = get(suffix).get("candles").getAsJsonArray().iterator();
		List<Candle> candles = new ArrayList<Candle>();
		while (i.hasNext()) {
			JsonObject obj = i.next().getAsJsonObject();

			double lowPrice = obj.get("low").getAsDouble();
			double highPrice = obj.get("high").getAsDouble();
			double openPrice = obj.get("open").getAsDouble();
			double closePrice = obj.get("close").getAsDouble();
			int volume = obj.get("volume").getAsInt();
			Date candleStart = DateFormatter.parseCandleDate(obj.get("start").getAsString().split(Pattern.quote("."))[0]);
			Date candleEnd = DateFormatter.parseCandleDate(obj.get("end").getAsString().split(Pattern.quote("."))[0]);
			candles.add(new Candle(candleStart, candleEnd, highPrice, lowPrice, openPrice, closePrice, volume));
		}
		return candles;
	}

}
