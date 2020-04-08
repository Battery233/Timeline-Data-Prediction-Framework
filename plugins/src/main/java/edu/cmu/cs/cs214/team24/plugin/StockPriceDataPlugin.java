package edu.cmu.cs.cs214.team24.plugin;

import edu.cmu.cs.cs214.team24.framework.core.DataPlugin;
import edu.cmu.cs.cs214.team24.framework.core.DataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockPriceDataPlugin implements DataPlugin {
    //free sample API key only works on AAPL
    private static String API_URL = "https://eodhistoricaldata.com/api/eod/AAPL.US?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private String startDate = "";
    private String endDate = "";

    @Override
    public boolean setTimePeriod(Date start, Date end) {
        try {
            if (!start.before(end)) {
                throw new IllegalArgumentException("start date should be earlier than end date!");
            }
            //the api will return all values if the date is missing, so we don't need to check data range here
            startDate = format.format(start.getTime());
            endDate = format.format(end.getTime());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public DataSet getData() {
        try {
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);

            int days = dateUtil.dateInterval(start, end);
            Date[] dates = dateUtil.getDateArray(start, end);

            Map<String, double[]> data = new HashMap<>();
            data.put("open", new double[days]);
            data.put("high", new double[days]);
            data.put("low", new double[days]);
            data.put("close", new double[days]);

            String urlString = API_URL + "&from=" + startDate + "&to=" + endDate;
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            int i = 0;
            String s;
            reader.readLine();
            while ((s = reader.readLine()) != null) {
                String[] details = s.split(",");
                Date date;
                try {
                    date = format.parse(details[0]);
                    while (i != dates.length && !date.equals(dates[i])) {
                        i++;
                    }
                    if (i == dates.length) {
                        break;
                    }
                    data.get("open")[i] = Double.parseDouble(details[1]);
                    data.get("high")[i] = Double.parseDouble(details[2]);
                    data.get("low")[i] = Double.parseDouble(details[3]);
                    data.get("close")[i] = Double.parseDouble(details[4]);
                } catch (ParseException e) {
                    break;
                }
            }
            reader.close();

            for (double[] d : data.values()) {
                for (int j = 0; j < d.length; j++) {
                    if (d[j] == 0) {
                        if (j == 0 && d.length > 1 && d[j + 1] != 0) {
                            d[j] = d[j + 1];
                        } else if (j == 0 && d.length > 2) {
                            d[j] = d[j + 2];
                        } else if (j > 0 && d[j - 1] != 0) {
                            d[j] = d[j - 1];
                        }
                    }
                }
            }
            return new DataSet(dates, data);
        } catch (IOException | ParseException e) {
            return null;
        }
    }

    @Override
    public String name() {
        return "Stock price data of Apple (CSV)";
    }

    @Override
    public boolean isDataPlugin() {
        return true;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        return new HashMap<>();
    }

    @Override
    public Map<String, Boolean> areParamsMultiple() {
        return new HashMap<>();
    }

    @Override
    public boolean addParam(String param, String option) {
        return false;
    }
}
