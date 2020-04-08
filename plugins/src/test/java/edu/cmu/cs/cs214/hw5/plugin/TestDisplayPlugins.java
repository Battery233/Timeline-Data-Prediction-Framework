package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.framework.core.DataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayDataSet;
import edu.cmu.cs.cs214.hw5.framework.core.DisplayPlugin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestDisplayPlugins {

    DataSet data;
    DisplayDataSet displayData;

    @Before
    public void setUp() throws ParseException {
        String str = "2019-06-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(str);
        Date[] dates = new Date[6];
        for (int i = 0; i < 6; i++) {
            dates[i] = new Date(start.getTime() + i * 86400000L);
        }
        double[] arr1 = new double[]{1, 2, 3, 4, 5, 6};
        double[] arr2 = new double[]{1.83, 2.77, 3.46, 4.54, 5.12, 3.58};
        Map<String, double[]> map = new HashMap<>();
        map.put("RMB", arr1);
        map.put("USD", arr2);
        data = new DataSet(dates, map);

        Map<String, Double> pred = new HashMap<>();
        pred.put("RMB", 6.9999999);
        pred.put("USD", 3.6677777);
        Date latestDate = dates[dates.length - 1];
        Date newDate = new Date(latestDate.getTime() + 86400000L);
        displayData = new DisplayDataSet(data, newDate, pred);
    }

    @Test
    public void testStaticDisplay() {
        DisplayPlugin plugin = new StatisticDisplayPlugin();
        plugin.setDataSet(data);
        plugin.setDisplayDataSet(displayData);
        String name = plugin.name();
        Assert.assertEquals(name, "Statistic Display Plugin");
        Assert.assertEquals(plugin.isDataPlugin(), false);
        Map<String, List<String>> act1 = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("RMB");
        list.add("USD");
        act1.put("option1", list);
        act1.put("option2", list);
        Map<String, List<String>> expected1 = plugin.getParamOptions();
        Assert.assertEquals(expected1, act1);

        Map<String, Boolean> act2 = new HashMap<>();
        act2.put("option1", false);
        act2.put("option2", false);
        Map<String, Boolean> expected2 = plugin.areParamsMultiple();
        Assert.assertEquals(expected2, act2);

        boolean expected3 = plugin.addParam("option1", "RMB");
        boolean expected4 = plugin.addParam("option2", "USD");
        boolean expected5 = plugin.addParam("option3", "EUR");
        Assert.assertEquals(expected3, true);
        Assert.assertEquals(expected4, true);
        Assert.assertEquals(expected5, false);

        JPanel expected6 = plugin.display();
        Assert.assertTrue(expected6 != null);
    }

    @Test
    public void testLineChartDisplay() {
        DisplayPlugin plugin = new LineChartDisplayPlugin();
        plugin.setDataSet(data);
        plugin.setDisplayDataSet(displayData);
        String name = plugin.name();
        Assert.assertEquals(name, "Line Chart Display Plugin");
        Assert.assertEquals(plugin.isDataPlugin(), false);
        Map<String, List<String>> act1 = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("RMB");
        list.add("USD");
        act1.put("toAdd", list);
        Map<String, List<String>> expected1 = plugin.getParamOptions();
        Assert.assertEquals(expected1, act1);

        Map<String, Boolean> act2 = new HashMap<>();
        act2.put("toAdd", true);
        Map<String, Boolean> expected2 = plugin.areParamsMultiple();
        Assert.assertEquals(expected2, act2);

        boolean expected3 = plugin.addParam("toAdd", "RMB");
        boolean expected4 = plugin.addParam("toAdd", "USD");
        boolean expected5 = plugin.addParam("Wrong", "EUR");
        Assert.assertEquals(expected3, true);
        Assert.assertEquals(expected4, true);
        Assert.assertEquals(expected5, false);

        JPanel expected6 = plugin.display();
        Assert.assertTrue(expected6 != null);

        plugin.clearToDisplay();
        LineChartDisplayPlugin lineChart = (LineChartDisplayPlugin) plugin;
        boolean expected7 = lineChart.isToDisplayEmpty();
        Assert.assertTrue(expected7);
    }

    @Test
    public void testBarChartDisplay() {
        DisplayPlugin plugin = new BarChartDisplayPlugin();
        plugin.setDataSet(data);
        plugin.setDisplayDataSet(displayData);
        String name = plugin.name();
        Assert.assertEquals(name, "Bar Chart Display Plugin");
        Assert.assertEquals(plugin.isDataPlugin(), false);
        Map<String, List<String>> act1 = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("RMB");
        list.add("USD");
        act1.put("categories", list);
        Map<String, List<String>> expected1 = plugin.getParamOptions();
        Assert.assertEquals(expected1, act1);

        Map<String, Boolean> act2 = new HashMap<>();
        act2.put("categories", true);
        Map<String, Boolean> expected2 = plugin.areParamsMultiple();
        Assert.assertEquals(expected2, act2);

        boolean expected3 = plugin.addParam("categories", "RMB");
        boolean expected4 = plugin.addParam("categories", "USD");
        boolean expected5 = plugin.addParam("Error", "EUR");
        Assert.assertEquals(expected3, true);
        Assert.assertEquals(expected4, true);
        Assert.assertEquals(expected5, false);

        JPanel expected6 = plugin.display();
        Assert.assertTrue(expected6 != null);

        plugin.clearToDisplay();
        BarChartDisplayPlugin lineChart = (BarChartDisplayPlugin) plugin;
        boolean expected7 = lineChart.isToDisplayEmpty();
        Assert.assertTrue(expected7);
    }
}
