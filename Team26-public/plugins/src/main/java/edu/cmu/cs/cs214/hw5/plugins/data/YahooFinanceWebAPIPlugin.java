package edu.cmu.cs.cs214.hw5.plugins.data;

import edu.cmu.cs.cs214.hw5.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YahooFinanceWebAPIPlugin implements DataPlugin {
    public static final String SOURCE_NAME = "Yahoo Finance Stock Open Price";
    public static final String PROMPT = "Enter stock symbol";

    /** {@inheritDoc} */
    @Override
    public List<String> getAvailableDataSetNames() {
        return new ArrayList<>();
    }

    private static LocalDate calendarToLocalDate(Calendar d) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
        return dateTime.toLocalDate();
    }

    /** {@inheritDoc} */
    @Override
    public DataSet getDataSet(String stockSymbol) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1);
        Stock stock;
        List<HistoricalQuote> quotes;
        try {
            stock = YahooFinance.get(stockSymbol.toUpperCase(), from, to, Interval.DAILY);
            if (stock == null) {
                JOptionPane.showMessageDialog(new JFrame(),
                        getClass().getSimpleName() + ": stock symbol does not exist");
                return DataSet.EMPTY_DATASET;
            }
            quotes = stock.getHistory();
        } catch (IOException e) {
            System.err.println(getClass().getSimpleName() + ": error fetching history for stock symbol " + stockSymbol);
            return DataSet.EMPTY_DATASET;
        }
        String name = stock.getName() + " History Open Price";
        TimeSeries ts = new TimeSeries(name);
        for (HistoricalQuote q : quotes) {
            LocalDate date = calendarToLocalDate(q.getDate());
            double openPrice = q.getOpen().doubleValue();
            ts.insert(date, openPrice);
        }
        List<TimeSeries> tsList = new ArrayList<>();
        tsList.add(ts);

        return new DataSet(tsList, new ArrayList<>(), name);
    }

    /** {@inheritDoc} */
    @Override
    public String getDataSourceName() {
        return SOURCE_NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getPrompt() {
        return PROMPT;
    }
}
