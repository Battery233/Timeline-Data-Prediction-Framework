package edu.cmu.cs.cs214.hw5.plugins.data;

import edu.cmu.cs.cs214.hw5.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedDailyStockDataPlugin implements DataPlugin {
    public static final String SOURCE_NAME = "Advanced Daily Stock Historical Data";
    public static final String PROMPT = "Enter Stock Code";

    @Override
    public List<String> getAvailableDataSetNames() {
        return new ArrayList<>();
    }

    @Override
    public DataSet getDataSet(String dataSetName) {
        TimeSeries ts1 = new TimeSeries(dataSetName + " open price");
        TimeSeries ts2 = new TimeSeries(dataSetName + " daily highest price");
        TimeSeries ts3 = new TimeSeries(dataSetName + " daily lowest price");
        TimeSeries ts4 = new TimeSeries(dataSetName + " close price");
        String urlString = "https://eodhistoricaldata.com/api/eod/" + dataSetName + "?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX";
        URL url;
        try {
            url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String s = reader.readLine();
            boolean exist = false;
            int counter = 0;
            while ((s = reader.readLine()) != null) {
                try {
                    String[] details = s.split(",");
                    LocalDate date = LocalDate.parse(details[0]);
                    exist = true;
                    ts1.insert(date, Double.parseDouble(details[1]));
                    ts2.insert(date, Double.parseDouble(details[2]));
                    ts3.insert(date, Double.parseDouble(details[3]));
                    ts4.insert(date, Double.parseDouble(details[4]));
                    counter++;
                } catch (DateTimeParseException e) {
                    if (!exist) {
                        return DataSet.EMPTY_DATASET;
                    } else {
                        break;
                    }
                }
            }
            reader.close();
            JOptionPane.showMessageDialog(new JFrame(),
                    counter + " days of data loaded!");

            List<TimeSeries> tsList = new ArrayList<>();
            tsList.add(ts1);
            tsList.add(ts2);
            tsList.add(ts3);
            tsList.add(ts4);
            return new DataSet(tsList, new ArrayList<>(), dataSetName + " detailed stock price");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    dataSetName + ": stock data does not exist!");
            return DataSet.EMPTY_DATASET;
        }
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
