package edu.cmu.cs.cs214.hw5.plugins.data;

import edu.cmu.cs.cs214.hw5.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedDailyStockDataPlugin implements DataPlugin {
    public static final String SOURCE_NAME = "Advanced Daily Stock Historical Data";
    public static final String PROMPT = "Enter Stock Code (e.g. AAPL for Apple)";

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
        String urlString;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/API-KEY/AdvancedDailyStockDataPlugin.txt"));) {
            urlString = "https://eodhistoricaldata.com/api/eod/" + dataSetName + "?api_token=" + br.readLine().trim();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Read API key file error!");
            return DataSet.EMPTY_DATASET;
        }
        URL url;
        JOptionPane.showMessageDialog(new JFrame(),
                "Downloading...This may take up to 10 seconds, depending on the bandwidth.");
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Load URL error!");
            return DataSet.EMPTY_DATASET;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String s = reader.readLine();
            boolean exist = false;
            int counter = 0;
            while ((s = reader.readLine()) != null) {
                try {
                    //read data and put data into the object
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
                    dataSetName + ": stock data does not exist!\n" +
                            "Did you input the right symbol?\n" +
                            "Did you set up the API key correctly?");
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
