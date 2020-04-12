package edu.cmu.cs.cs214.hw5.plugins.data;

import edu.cmu.cs.cs214.hw5.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class CurrencyRateWebAPIPlugin implements DataPlugin {
    public static final String SOURCE_NAME = "Foreign Exchange Rates API with currency conversion";
    public static final String PROMPT = "Enter base currency";
    private final String API_URL_FORMAT =
            "https://api.exchangeratesapi.io/history?start_at=%s&end_at=%s&base=%s";
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public List<String> getAvailableDataSetNames() {
        return new ArrayList<>();
    }

    @Override
    public DataSet getDataSet(String baseCurrency) {
        Map<String, TimeSeries> tsMap = new HashMap<>();
        String dsName = "Currency Conversion Based on " + baseCurrency;

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        String attempUrl = String.format(API_URL_FORMAT,
                sdf.format(from.getTime()), sdf.format(to.getTime()), baseCurrency.toUpperCase());
        try {
            URL url = new URL(attempUrl);
            Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8.name());
            String content = scanner.next();

            // parse the obtained json file
            JSONParser parser = new JSONParser();
            JSONObject jobj = (JSONObject) parser.parse(content);
            JSONObject rates = (JSONObject) jobj.get("rates");

            // parse rates, keys are dates
            for (Object key1: rates.keySet()) {
                String date = (String) key1;
                LocalDate localDate = LocalDate.parse(date);
                JSONObject dateCurrencies = (JSONObject) rates.get(date);

                // parse a date, keys are currencies
                for (Object key2: dateCurrencies.keySet()) {
                    String currency = (String) key2;
                    double val = (Double) dateCurrencies.get(currency);
                    if (tsMap.containsKey(currency)) {
                        tsMap.get(currency).insert(localDate, val);
                    }
                    else {
                        TimeSeries ts = new TimeSeries(currency);
                        ts.insert(localDate, val);
                        tsMap.put(currency, ts);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        List<TimeSeries> tsList = new ArrayList<>(tsMap.values());
        return new DataSet(tsList, new ArrayList<>(), dsName);
    }

    @Override
    public String getDataSourceName() {
        return SOURCE_NAME;
    }

    @Override
    public String getPrompt() {
        return PROMPT;
    }

}
