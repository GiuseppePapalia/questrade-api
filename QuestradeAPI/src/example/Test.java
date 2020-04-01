package example;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.giuseppepapalia.questrade.QuestradeAPI;
import com.giuseppepapalia.questrade.QuestradeClient;
import com.giuseppepapalia.questrade.constants.Interval;
import com.giuseppepapalia.questrade.data.Stock;
import com.giuseppepapalia.questrade.data.chart.HistoricalChart;

public class Test {

	public static void main(String args[]) throws Exception {

		QuestradeAPI api = new QuestradeAPI("access token");
		QuestradeClient client = new QuestradeClient(api);
		Stock stock = client.getStock("SPY");
		Date startDate = new SimpleDateFormat("dd:MM:yyyy hh:mm").parse("01:01:2019 09:30");
		Date endDate = new SimpleDateFormat("dd:MM:yyyy hh:mm").parse("31:12:2019 4:00");
		HistoricalChart chart = new HistoricalChart(client.getCandles(stock, startDate, endDate, Interval.ONE_DAY));
		System.out.println(chart);
	}

}
