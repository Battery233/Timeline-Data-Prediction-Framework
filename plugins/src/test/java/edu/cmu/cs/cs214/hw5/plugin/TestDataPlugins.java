package edu.cmu.cs.cs214.hw5.plugin;

import edu.cmu.cs.cs214.hw5.framework.core.DataPlugin;
import edu.cmu.cs.cs214.hw5.framework.core.Framework;
import edu.cmu.cs.cs214.hw5.framework.core.FrameworkImpl;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestDataPlugins {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testCurrencyDataPlugin() throws ParseException {
        DataPlugin cdp = new CurrencyDataPlugin();
        assertEquals("Currency Data plugin (JSON)", cdp.name());
        assertTrue(cdp.isDataPlugin());
        Date bt = format.parse("2020-03-20");
        Date et = format.parse("2020-03-25");
        assertFalse(cdp.setTimePeriod(bt, bt));
        assertTrue(cdp.setTimePeriod(bt, et));
        assertTrue(cdp.addParam("base", "GBP"));
        assertTrue(cdp.addParam("symbols", "CNY"));
        assertTrue(cdp.addParam("symbols", "USD"));
        assertFalse(cdp.addParam("symbol", "JPY"));
        assertEquals(2, cdp.getParamOptions().size());
        assertEquals(2, cdp.areParamsMultiple().size());
        assertNotNull(cdp.getData().toString());
        assertEquals(6, cdp.getData().getTimeRange().length);
    }

    @Test(expected = ParseException.class)
    public void testCurrencyPluginExceptions() throws ParseException {
        DataPlugin cdp = new CurrencyDataPlugin();
        assertFalse(cdp.addParam("error", "foo"));
        Date bt = format.parse("2020-03-25");
        Date et = format.parse("2020-03-20");
        assertFalse(cdp.setTimePeriod(bt, et));
        Date error = format.parse("201a-131-02");
        assertFalse(cdp.setTimePeriod(bt, error));
    }

    @Test
    public void testAppleStockDataPlugin() throws ParseException {
        DataPlugin stockPriceDataPlugin = new StockPriceDataPlugin();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-02-13");
        Date et = format.parse("2020-03-17");
        assertEquals("Stock price data of Apple (CSV)", stockPriceDataPlugin.name());
        assertTrue(stockPriceDataPlugin.isDataPlugin());
        assertFalse(stockPriceDataPlugin.setTimePeriod(et, bt));
        assertTrue(stockPriceDataPlugin.setTimePeriod(bt, et));
        assertNotNull(stockPriceDataPlugin.getData());
        assertEquals(0, stockPriceDataPlugin.getParamOptions().size());
        assertEquals(0, stockPriceDataPlugin.areParamsMultiple().size());
        assertFalse(stockPriceDataPlugin.addParam("foo", "bar"));
        assertEquals(34, stockPriceDataPlugin.getData().getTimeRange().length);
    }

    @Test
    public void testFredDataPlugin() throws ParseException {
        Framework framework = new FrameworkImpl();
        DataPlugin plugin = new FederalReserveDataPlugin();
        framework.setCurrentDataPlugin(plugin);
        assertEquals("{Data Type=[Treasury Bill Secondary Market Rate, Treasury Constant Maturity Rate, " +
                "Effective Federal Funds Rate, Forward Inflation Expectation Rate, " +
                "Bank Prime Loan Rate, Breakeven Inflation Rate]}", plugin.getParamOptions().toString());
        assertEquals("(Before Mar. 2020) Federal Reserve Interest Rates Data", plugin.name());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-03-03");
        Date et = format.parse("2020-04-02");
        Map<String, List<String>> params = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("Treasury Constant Maturity Rate");
        list.add("Bank Prime Loan Rate");
        params.put("Data Type", list);
        assertFalse(plugin.addParam("Data", "Interest"));
        assertTrue(plugin.areParamsMultiple().get("Data Type"));
        assertEquals(1, plugin.getParamOptions().size());
        assertTrue(framework.setPluginParameters(true, params, bt, et));
        assertTrue(plugin.isDataPlugin());
        assertNotNull(plugin.getData());
        assertEquals(29, plugin.getData().getTimeRange().length);
    }

    @Test
    public void testUtil() throws ParseException {
        Date bt = format.parse("2020-03-29");
        Date et = format.parse("2020-04-02");
        assertEquals(5, DateUtil.dateInterval(bt, et));
        assertEquals(5, DateUtil.getDateArray(bt, et).length);
    }

    @Test
    public void testPluginFromFramework() throws ParseException {
        Framework framework = new FrameworkImpl();
        DataPlugin cdp = new CurrencyDataPlugin();
        framework.setCurrentDataPlugin(cdp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-02-13");
        Date et = format.parse("2020-03-17");
        Map<String, List<String>> params = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("CNY");
        list.add("USD");
        params.put("symbols", list);
        List<String> list2 = new ArrayList<>();
        list2.add("GBP");
        params.put("base", list2);
        assertTrue(framework.setPluginParameters(true, params, bt, et));
        assertTrue(framework.getData());
        System.out.println(cdp.getData());
    }
}
