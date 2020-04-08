package edu.cmu.cs.cs214.hw5.plugin;

import com.google.gson.Gson;
import edu.cmu.cs.cs214.hw5.framework.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.framework.core.DataSet;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The plugin for getting currency exchange rate. The user can get the data of several currencies in a time range.
 * The default currency base is USD.
 */
public class CurrencyDataPlugin implements DataPlugin {
    //all type of currencies the API support, according to the official documentation
    private static String ALL_CURRENCY = "CAD,HKD,ISK,PHP,DKK,HUF,CZK,GBP,RON,SEK,IDR,INR,BRL,RUB,HRK,JPY," +
            "THB,CHF,EUR,MYR,BGN,TRY,CNY,NOK,NZD,ZAR,USD,MXN,SGD,AUD,ILS,KRW,PLN";
    //the map to store all possible params the user can set
    private Map<String, List<String>> paramOptions = new HashMap<>();
    //the map to indicate if the parameter can have multiple values (e.g., several different currencies)
    private Map<String, Boolean> isParamsMultiple = new HashMap<>();
    private String startDate = "";
    private String endDate;
    private String base = "USD"; // default base currency
    private String symbols = ""; // the currencies to be calculated

    public CurrencyDataPlugin() {
        //set params options for this plugin
        List<String> options = Arrays.asList(ALL_CURRENCY.split(","));
        paramOptions.put("base", options);
        isParamsMultiple.put("base", false);
        paramOptions.put("symbols", options);
        isParamsMultiple.put("symbols", true);
    }

    @Override
    public Map<String, Boolean> areParamsMultiple() {
        return isParamsMultiple;
    }


    @Override
    public String name() {
        return "Currency Data plugin (JSON)";
    }

    @Override
    public boolean isDataPlugin() {
        return true;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        return paramOptions;
    }

    @Override
    public boolean addParam(String param, String option) {
        if (param.equals("base")) {
            //set the base currency, if valid.
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
            //set or append the symbol list string
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

    //validate the symbol
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
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public DataSet getData() {
        if (startDate.equals("")) {
            //date is necessary for this api
            return null;
        } else {
            try {
                //create the url
                //the base url of the api
                String API_URL = "https://api.exchangeratesapi.io/history?";
                URL url = new URL(API_URL + "start_at=" + startDate + "&end_at=" + endDate + "&base=" + base + "&symbols=" + symbols);
                System.out.println(url);
                String content;
                try (Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8)) {
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

    //parse the json to into dataset for the framework
    private DataSet parseJSON(String s) throws ParseException {
        Gson gson = new Gson();
        CurrencyJSONReader.JSONRates JSONData = gson.fromJson(s, CurrencyJSONReader.JSONRates.class);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(JSONData.start_at);
        Date end = format.parse(JSONData.end_at);

        //set the array to represent dates
        int days = DateUtil.dateInterval(start, end);
        Date[] dates = DateUtil.getDateArray(start, end);

        Map<String, double[]> data = new HashMap<>();
        for (Map<String, Double> e : JSONData.rates.values()) {
            for (String s1 : e.keySet()) {
                data.put(s1, new double[days]);
            }
            break;
        }

        //add the detailed data to the map
        for (Map.Entry<String, Map<String, Double>> jsr : JSONData.rates.entrySet()) {
            Date date = format.parse(jsr.getKey());
            int i;
            for (i = 0; i < dates.length; i++) {
                if (date.equals(dates[i])) {
                    break;
                }
            }
            for (Map.Entry<String, Double> e : jsr.getValue().entrySet()) {
                data.get(e.getKey())[i] = e.getValue();
            }
        }

        //on weekends, no data will be provided. We will use the data of last Friday or the following Monday
        //as the value of the weekend.
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
