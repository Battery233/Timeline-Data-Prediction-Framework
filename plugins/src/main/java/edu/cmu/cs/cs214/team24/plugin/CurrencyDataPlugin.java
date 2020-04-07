package edu.cmu.cs.cs214.team24.plugin;

import com.google.gson.Gson;
import edu.cmu.cs.cs214.team24.framework.core.DataPlugin;
import edu.cmu.cs.cs214.team24.framework.core.DataSet;
import edu.cmu.cs.cs214.team24.framework.core.Framework;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CurrencyDataPlugin implements DataPlugin {
    private static String ALL_CURRENCY = "CAD,HKD,ISK,PHP,DKK,HUF,CZK,GBP,RON,SEK,IDR,INR,BRL,RUB,HRK,JPY,THB,CHF,EUR,MYR,BGN,TRY,CNY,NOK,NZD,ZAR,USD,MXN,SGD,AUD,ILS,KRW,PLN";
    private static String API_URL = "https://api.exchangeratesapi.io/history?";
    private static long SECONDS_PER_DAY = 86400000;
    private Map<String, List<String>> paramOptions = new HashMap<>();
    private Map<String, Boolean> isParamsMultiple = new HashMap<>();
    private String startDate = "";
    private String endDate;
    private String base = "USD"; // default base
    private String symbols = "";
    private Framework framework;

    public CurrencyDataPlugin() {
        List<String> options = Arrays.asList(ALL_CURRENCY.split(","));
        paramOptions.put("base", options);
        isParamsMultiple.put("base", false);
        paramOptions.put("symbols", options);
        isParamsMultiple.put("symbols", true);
    }

    @Override
    public Map<String, Boolean> isParamsMultiple() {
        return isParamsMultiple;
    }


    @Override
    public String name() {
        return "Currency Data plugin";
    }

    @Override
    public boolean isDataPlugin() {
        return true;
    }

    @Override
    public boolean isDisplayPlugin() {
        return false;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        return paramOptions;
    }

    @Override
    public boolean addParam(String param, String option) {
        if (param.equals("base")) {
            if (validSymbol(option)) {
                base = option;
                return true;
            } else {
                return false;
            }
        } else if (param.equals("symbols")) {
            if (!validSymbol(option)) {
                return false;
            }
            if (symbols.equals("")) {
                symbols = option;
            } else {
                symbols = symbols + "," + option;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRegister(Framework framework) {
        this.framework = framework;
    }

    private boolean validSymbol(String symbol) {
        for (String s : ALL_CURRENCY.split(",")) {
            if (s.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setTimePeriod(Date start, Date end) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            startDate = format.format(start.getTime());
            endDate = format.format(end.getTime());
            if (!start.before(end)) {
                throw new IllegalArgumentException("start date should be earlier than end date!");
            }
            System.out.println("Start date = " + startDate);
            System.out.println("End date = " + endDate);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    @Override
    public DataSet getData() {
        if (startDate.equals("")) {
            return null;
        } else {
            try {
                URL url = new URL(API_URL + "start_at=" + startDate + "&end_at=" + endDate + "&base=" + base + "&symbols=" + symbols);
                System.out.println(url);
                String content;
                try (Scanner scanner = new Scanner(url.openStream())) {
                    content = scanner.useDelimiter("\\A").next();
                }
                System.out.println(content);
                return parseJSON(content);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private DataSet parseJSON(String s) throws ParseException {
        Gson gson = new Gson();
        CurrencyJSONReader.JSONRates JSONData = gson.fromJson(s, CurrencyJSONReader.JSONRates.class);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(JSONData.start_at);
        Date end = format.parse(JSONData.end_at);

        int days = (int) ((end.getTime() - start.getTime()) / SECONDS_PER_DAY + 1);
        Date[] dates = new Date[days];
        for (int i = 0; i < days; i++) {
            dates[i] = new Date(start.getTime() + i * SECONDS_PER_DAY);
        }

        Map<String, double[]> data = new HashMap<>();
        for (Map<String, Double> e : JSONData.rates.values()) {
            for (String s1 : e.keySet()) {
                data.put(s1, new double[days]);
            }
            break;
        }

        int i = 0;
        for (Map.Entry<String, Map<String, Double>> jsr : JSONData.rates.entrySet()) {
            Date date = format.parse(jsr.getKey());
            if (i == dates.length) break;
            while (!dates[i].equals(date)) {
                i++;
            }
            if (dates[i].equals(date)) {
                for (Map.Entry<String, Double> e : jsr.getValue().entrySet()) {
                    data.get(e.getKey())[i] = e.getValue();
                }
            }
            i++;
        }

        for (double[] d : data.values()) {
            for (int j = 0; j < d.length; j++) {
                if (d[j] == 0) {
                    if (j == 0 && d[j + 1] != 0) {
                        d[j] = d[j + 1];
                    } else if (j == 0 && j + 2 < d.length) {
                        d[j] = d[j + 2];
                    } else if (j > 0 && d[j - 1] != 0) {
                        d[j] = d[j - 1];
                    }
                }
            }
        }
        return new DataSet(dates, data);
    }
}