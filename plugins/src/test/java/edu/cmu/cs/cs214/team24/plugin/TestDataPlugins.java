package edu.cmu.cs.cs214.team24.plugin;

import edu.cmu.cs.cs214.team24.framework.core.DataPlugin;
import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.FrameworkImpl;
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

    @Test
    public void testCurrencyDataPlugin() throws ParseException {
        DataPlugin cdp = new CurrencyDataPlugin();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-03-20");
        Date et = format.parse("2020-03-25");
        assertFalse(cdp.setTimePeriod(bt, bt));
        assertTrue(cdp.setTimePeriod(bt, et));
        assertTrue(cdp.addParam("base", "GBP"));
        assertTrue(cdp.addParam("symbols", "CNY"));
        assertTrue(cdp.addParam("symbols", "USD"));
        assertNotNull(cdp.getData().toString());
    }

    @Test
    public void testAppleStockDataPlugin() throws ParseException {
        DataPlugin stockPriceDataPlugin = new StockPriceDataPlugin();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-02-13");
        Date et = format.parse("2020-03-17");
        stockPriceDataPlugin.setTimePeriod(bt, et);
        System.out.println(stockPriceDataPlugin.getData());
    }

    @Test
    public void testFredDataPlugin() throws ParseException {
        Framework framework = new FrameworkImpl();
        DataPlugin plugin = new FederalReserveDataPlugin();
        framework.setCurrentDataPlugin(plugin);
        assertEquals("{Data Type=[Treasury Bill Secondary Market Rate, Treasury Constant Maturity Rate, " +
                "Effective Federal Funds Rate, Forward Inflation Expectation Rate, " +
                "Bank Prime Loan Rate, Breakeven Inflation Rate]}",plugin.getParamOptions().toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-03-12");
        Date et = format.parse("2020-03-16");
        Map<String, List<String>> params = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("Treasury Constant Maturity Rate");
        list.add("Bank Prime Loan Rate");
        params.put("Data Type", list);
        assertTrue(framework.setPluginParameters(true, params, bt, et));
        System.out.println(plugin.getData());
    }
}
