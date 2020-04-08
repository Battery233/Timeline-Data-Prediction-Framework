package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.framework.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.framework.core.DataSet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The plugin to get economy data from the FRED (local database). The user can get up
 * to 6 types of data from 2009 to March 2020.
 */
public final class FederalReserveDataPlugin implements DataPlugin {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Date start;
    private Date end;
    //types the user can choose
    private Map<String, Integer> optionIndexes;
    //types the user chose
    private Map<Integer, String> outputIndexed;


    public FederalReserveDataPlugin() {
        optionIndexes = new HashMap<>();
        outputIndexed = new HashMap<>();
        optionIndexes.put("Effective Federal Funds Rate", 1);
        optionIndexes.put("Treasury Bill Secondary Market Rate", 2);
        optionIndexes.put("Treasury Constant Maturity Rate", 3);
        optionIndexes.put("Breakeven Inflation Rate", 4);
        optionIndexes.put("Forward Inflation Expectation Rate", 5);
        optionIndexes.put("Bank Prime Loan Rate", 6);
    }

    @Override
    public boolean setTimePeriod(Date start, Date end) {
        try {
            if (!start.before(end)) {
                throw new IllegalArgumentException("start date should be earlier than end date!");
            }
            //make sure the dates do not exceed the limit
            Date earliest = format.parse("2009-01-01");
            Date latest = format.parse("2020-03-31");
            if (start.before(earliest)) {
                start = earliest;
            }
            if (latest.before(end)) {
                end = latest;
            }
            this.start = start;
            this.end = end;
        } catch (IllegalArgumentException | ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public DataSet getData() {
        try {
            //make sure the period is valid
            if (format.parse("2020-03-31").before(start)) {
                return null;
            }

            //read data from local csv
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader("src/main/resources/fred_data.csv", StandardCharsets.UTF_8));
            } catch (FileNotFoundException e2) {
                br = new BufferedReader(new FileReader("plugins/src/main/resources/fred_data.csv", StandardCharsets.UTF_8));
            }
            br.readLine();

            //set up the data structure to store data.
            int days = DateUtil.dateInterval(start, end);
            Date[] dates = DateUtil.getDateArray(start, end);
            Map<String, double[]> data = new HashMap<>();
            for (String s : outputIndexed.values()) {
                data.put(s, new double[days]);
            }
            String line;
            int dataIndex = 0;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                Date currentDate = format.parse(details[0]);
                //parse and store the data
                if (currentDate.after(end)) {
                    break;
                } else if (!start.after(currentDate)) {
                    for (Map.Entry<Integer, String> e : outputIndexed.entrySet()) {
                        int i = e.getKey();
                        data.get(outputIndexed.get(i))[dataIndex] = Double.parseDouble(details[i]);
                    }
                    dataIndex++;
                }
            }
            return new DataSet(dates, data);
        } catch (FileNotFoundException e) {
            System.out.println("Local data not found!");
            return null;
        } catch (IOException e) {
            System.out.println("Io exception!");
            return null;
        } catch (ParseException e) {
            System.out.println("Illegal date format!");
            return null;
        }
    }

    @Override
    public String name() {
        return "(Before Mar. 2020) Federal Reserve Interest Rates Data";
    }

    @Override
    public boolean isDataPlugin() {
        return true;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        Map<String, List<String>> result = new HashMap<>();
        result.put("Data Type", new ArrayList<>(optionIndexes.keySet()));
        return result;
    }

    @Override
    public Map<String, Boolean> areParamsMultiple() {
        Map<String, Boolean> result = new HashMap<>();
        result.put("Data Type", true);
        return result;
    }

    @Override
    public boolean addParam(String param, String option) {
        if (!param.equals("Data Type")) {
            return false;
        } else {
            if (optionIndexes.containsKey(option)) {
                //add param if valid
                outputIndexed.put(optionIndexes.get(option), option);
                return true;
            } else {
                return false;
            }
        }
    }
}