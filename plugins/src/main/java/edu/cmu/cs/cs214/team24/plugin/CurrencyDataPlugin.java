package edu.cmu.cs.cs214.team24.plugin;

import edu.cmu.cs.cs214.team24.framework.core.DataPlugin;
import edu.cmu.cs.cs214.team24.framework.core.DataSet;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CurrencyDataPlugin implements DataPlugin {
    private static String ALL_CURRENCY = "CAD,HKD,ISK,PHP,DKK,HUF,CZK,GBP,RON,SEK,IDR,INR,BRL,RUB,HRK,JPY,THB,CHF,EUR,MYR,BGN,TRY,CNY,NOK,NZD,ZAR,USD,MXN,SGD,AUD,ILS,KRW,PLN";
    private static String API_URL = "https://api.exchangeratesapi.io/history?";
    private Map<String, List<String>> paramOptions = new HashMap<>();
    private String startDate = "";
    private String endDate;
    private String base = "USD"; // default base
    private String symbols = "";

    public CurrencyDataPlugin() {
        List<String> options = Arrays.asList(ALL_CURRENCY.split(","));
        paramOptions.put("base", options);
        paramOptions.put("symbols", options);
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

    private boolean validSymbol(String symbol) {
        for (String s : ALL_CURRENCY.split(",")) {
            if (s.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setTimePeriod(Calendar start, Calendar end) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            startDate = format.format(start.getTime());
            endDate = format.format(end.getTime());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bt = sdf.parse(startDate);
            Date et = sdf.parse(endDate);
            if (!bt.before(et)) {
                throw new IllegalArgumentException("start date should be earlier than end date!");
            }
            System.out.println("Start date = " + startDate);
            System.out.println("End date = " + endDate);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
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
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private DataSet parseJSON(String s) {
        //todo
        return null;
    }
}