package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.framework.core.DataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayDataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayPlugin;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TTest;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StatisticDisplayPlugin implements DisplayPlugin {

    private String option1;
    private String option2;
    private DisplayDataSet data;
    private Set<String> options;
    private TTest testEnvir;

    public StatisticDisplayPlugin() {
        testEnvir = new TTest();
    }

    @Override
    public void setDataSet(DataSet metaData){
        this.options = metaData.getData().keySet();
    }

    @Override
    public void setDisplayDataSet(DisplayDataSet data){
        this.data = data;
    }

    @Override
    public JPanel display() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = new String[] {"Categories", option1, option2};
        Map<String, Double> map1 = getStatisticData(option1);
        Map<String, Double> map2 = getStatisticData(option2);
        Object[][] rowData = {
                {"Sample Number", map1.get("N"), map2.get("N")},
                {"Mean", map1.get("Mean"), map2.get("Mean")},
                {"Median", map1.get("Median"), map2.get("Median")},
                {"StandardDev", map1.get("SD"), map2.get("SD")}
        };
        JTable table = new JTable(rowData, columnNames);
        table.setForeground(Color.BLACK);
        table.setFont(new Font(null, Font.PLAIN, 14));
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setSelectionBackground(Color.LIGHT_GRAY);

        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(30);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);

        JScrollPane scrollPane = new JScrollPane(table);

        double pValue = tTest();
        JLabel tTest = new JLabel("P-value is " + pValue);
        JLabel res = new JLabel();
        if (pValue < 0.05) {
            res.setText("The difference is considered statistically significant.");
        } else {
            res.setText("The difference is not considered statistically significant.");
        }
        scrollPane.add(tTest);
        scrollPane.add(res);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(tTest, BorderLayout.EAST);
        panel.add(res, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public String name() {
        return "Statistic Display Plugin";
    }

    @Override
    public boolean isDataPlugin() {
        return false;
    }

    @Override
    public Map<String, List<String>> getParamOptions() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> optionsList = new ArrayList<>(options);
        map.put("option1", optionsList);
        map.put("option2", optionsList);
        return map;
    }

    @Override
    public Map<String, Boolean> areParamsMultiple() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("option1", false);
        map.put("option2", false);
        return map;
    }

    @Override
    public boolean addParam(String param, String option) {
        switch (param) {
            case "option1":
                option1 = option;
                return true;
            case "option2":
                option2 = option;
                return true;
            default:
                return false;
        }
    }

    public double tTest() {
        Map<String, double[]> map = data.getOriginalData().getData();
        double res = testEnvir.pairedTTest(map.get(option1), map.get((option2)));
        return res;
    }

    public Map<String, Double> getStatisticData(String input) {
        Map<String, Double> res = new HashMap<>();
        Map<String, double[]> map = data.getOriginalData().getData();
        double[] testData = map.get(input);
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(testData);
        res.put("N", (double) testData.length);
        res.put("Mean", descriptiveStatistics.getMean());
        res.put("Median", descriptiveStatistics.getPercentile(50));
        res.put("SD", descriptiveStatistics.getStandardDeviation());

        return res;
    }

}
