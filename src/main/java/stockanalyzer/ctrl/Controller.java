package stockanalyzer.ctrl;

import stockanalyzer.downloader.Downloader;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


public class Controller {

    public ArrayList<String> process(String ticker) throws IOException {
        System.out.println("Start process");

        ArrayList<String> data = new ArrayList<>(getData(ticker));

        Stock stock = null;

        Calendar from = Calendar.getInstance();
        from.add(Calendar.WEEK_OF_MONTH, -2);

        stock = yahoofinance.YahooFinance.get(ticker);
        List<HistoricalQuote> h = stock.getHistory(from, Interval.DAILY);

        data.add("Number of records " + getRecords(h));
        data.add("MAX: " + getMax(h));
        data.add("AVERAGE: " + getAverage(h));

        return data;
    }

    public ArrayList<String> getData(String searchString) throws IOException {

        ArrayList<String> data = new ArrayList<>();
        YahooFinance yahooFinance = new YahooFinance();
        ArrayList<String> tickers = new ArrayList<>();
        tickers.add(searchString);
        YahooResponse yahooResponse = yahooFinance.getCurrentData(tickers);
        QuoteResponse quotes = yahooResponse.getQuoteResponse();

        quotes.getResult()
                .forEach(q -> data.add("Name: " + q.getLongName()));
        quotes.getResult()
                .forEach(q -> data.add("Current: " + q.getAsk().toString()));

        return data;
    }


    public double getRecords(List<HistoricalQuote> history) {

        return (int) history.stream()
                .mapToDouble(q -> q.getClose().intValue())
                .count();


    }

    public double getMax(List<HistoricalQuote> history) {


        return history.stream()
                .mapToDouble(q -> q.getClose().doubleValue())
                .max()
                .orElse(0.0);

    }

    public double getAverage(List<HistoricalQuote> history) {

        return history.stream()
                .mapToDouble(q -> q.getClose().doubleValue())
                .average()
                .orElse(0.0);

    }

    public void downloadTickers(Downloader down, ArrayList<String> ticker){
        down.process(ticker);
    }

}
