package edu.cmu.cs.cs214.hw5.plugins.data;

import edu.cmu.cs.cs214.hw5.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;


public class CSVDataPlugin implements DataPlugin {
    public static final String SOURCE_NAME = "CSVData";
    public static final String PROMPT = "Enter .csv file path";
    public static final String DATE_PATTERN = "yyyy[[-][.][/]MM[[-][.][/]dd]]";

    /** {@inheritDoc} */
    @Override
    public List<String> getAvailableDataSetNames() {
        return new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public DataSet getDataSet(String path) {
        String[] pathComponents = path.split("/");
        String dsName = pathComponents[pathComponents.length - 1];
        List<TimeSeries> timeSeriesList = new ArrayList<>();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(path))) {
            String row;
            boolean firstRow = true;
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(DATE_PATTERN)
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                if (firstRow) {
                    for (int i = 1; i < data.length; i++) {
                        TimeSeries ts = new TimeSeries(data[i]);
                        timeSeriesList.add(ts);
                    }
                    firstRow = false;
                    continue;
                }
                for (int i = 1; i < data.length; i++) {
                    LocalDate date;
                    try {
                        date = LocalDate.parse(data[0], formatter);
                    } catch (DateTimeParseException e1) {
                        System.err.println("CSVDataPlugin: unable to parse " + data[0]);
                        continue;
                    }
                    double val = 0;
                    try {
                        val = Double.parseDouble(data[i]);
                    } catch (NumberFormatException e) {
                        System.err.println("CSVDatPlugin: unable to parse " + data[i] + ", set to 0");
                    }
//                    System.out.println(date+":"+val);
                    timeSeriesList.get(i - 1).insert(date, val);
                }
            }
        } catch (IOException e) {
            System.err.println("CSVDataPlugin: error while loading data.");
            JOptionPane.showMessageDialog(new JFrame(),
                    getClass().getSimpleName() + ": error reading file");
        }
        return new DataSet(timeSeriesList, new ArrayList<>(), dsName);
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
