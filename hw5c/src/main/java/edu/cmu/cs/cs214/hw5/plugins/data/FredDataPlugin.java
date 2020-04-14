package edu.cmu.cs.cs214.hw5.plugins.data;

import edu.cmu.cs.cs214.hw5.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.net.ssl.SSLHandshakeException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FredDataPlugin implements DataPlugin {
    public static final String SOURCE_NAME = "Federal Reserve Historical Economic Data";
    public static final String PROMPT = "Input your Quandl.com API key here!";

    @Override
    public List<String> getAvailableDataSetNames() {
        return new ArrayList<>();
    }

    @Override
    public DataSet getDataSet(String apiKey) {
        TimeSeries[] tss = new TimeSeries[5];
        tss[0] = new TimeSeries("Gross Domestic Product");
        tss[1] = new TimeSeries("Consumer Price Index");
        tss[2] = new TimeSeries("Adjusted Monetary Base");
        tss[3] = new TimeSeries("Effective Federal Funds Rate");
        tss[4] = new TimeSeries("Civilian Unemployment Rate");
        String[] apiSymbols = new String[5];
        apiSymbols[0] = "GDP";
        apiSymbols[1] = "CPIAUCSL";
        apiSymbols[2] = "BASE";
        apiSymbols[3] = "DFF";
        apiSymbols[4] = "UNRATE";
        JOptionPane.showMessageDialog(new JFrame(),
                "Downloading...This may take up to 20 seconds, depending on the bandwidth.");
        apiKey = apiKey.trim();
        for (int i = 0; i < 5; i++) {
            String urlString = "https://www.quandl.com/api/v3/datasets/FRED/" + apiSymbols[i] + ".csv?api_key=" + apiKey;
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(),
                        "Fetching data error!\nDid you use a valid API Key?");
                return DataSet.EMPTY_DATASET;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                //remove the first line
                String s = reader.readLine();
                while ((s = reader.readLine()) != null) {
                    String[] details = s.split(",");
                    LocalDate date = LocalDate.parse(details[0]);
                    tss[i].insert(date, Double.parseDouble(details[1]));
                }

            } catch (SSLHandshakeException e) {
                e.printStackTrace();
                if (e.getMessage().contains("handshake_failure")) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "SSL Handshake Exception.\nMaybe you request data too frequent or something is wrong with" +
                                    "the server.\nRestarting the framework may solve it.");
                } else {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Fetching data error!\nDid you use a valid API Key?");
                }
                return DataSet.EMPTY_DATASET;

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(),
                        "IOException. Something is wrong.");
                return DataSet.EMPTY_DATASET;
            }
        }
        return new DataSet(Arrays.asList(tss), new ArrayList<>(), SOURCE_NAME);
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
