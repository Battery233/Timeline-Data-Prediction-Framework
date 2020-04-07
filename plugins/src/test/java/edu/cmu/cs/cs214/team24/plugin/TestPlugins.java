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
import static org.junit.Assert.assertTrue;

public class TestPlugins {

    @Test
    public void testCurrencyData() throws ParseException {
        DataPlugin cdp = new CurrencyDataPlugin();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-03-20");
        Date et = format.parse("2020-03-25");
        assertFalse(cdp.setTimePeriod(bt, bt));
        assertTrue(cdp.setTimePeriod(bt, et));
        assertTrue(cdp.addParam("base", "GBP"));
        assertTrue(cdp.addParam("symbols", "CNY"));
        assertTrue(cdp.addParam("symbols", "USD"));
        assertEquals("timeRange=[Fri Mar 20 00:00:00 EDT 2020, Sat Mar 21 00:00:00 EDT 2020, " +
                "Sun Mar 22 00:00:00 EDT 2020, Mon Mar 23 00:00:00 EDT 2020, Tue Mar 24 00:00:00 EDT 2020, " +
                "Wed Mar 25 00:00:00 EDT 2020]\nUSD: 1.1762314892, 1.1762314892, 1.1762314892, 1.1598365064, " +
                "1.1773072747, 1.1832398938, \nCNY: 8.3401810432, 8.3401810432, 8.3401810432, 8.2160912122," +
                " 8.3115092291, 8.3979760226, \n", cdp.getData().toString());
    }

    @Test
    public void testPluginFromFramework() throws ParseException {
        Framework framework = new FrameworkImpl();
        DataPlugin cdp = new CurrencyDataPlugin();
        framework.setCurrentDataPlugin(cdp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = format.parse("2020-03-20");
        Date et = format.parse("2020-03-25");
        Map<String, List<String>> params = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("CNY");
        list.add("USD");
        params.put("symbols", list);
        List<String> list2 = new ArrayList<>();
        list2.add("GBP");
        params.put("base", list2);
        assertTrue(framework.setPluginParameters(true, params, bt, et));
        assertEquals("timeRange=[Fri Mar 20 00:00:00 EDT 2020, Sat Mar 21 00:00:00 EDT 2020, " +
                "Sun Mar 22 00:00:00 EDT 2020, Mon Mar 23 00:00:00 EDT 2020, Tue Mar 24 00:00:00 EDT 2020, " +
                "Wed Mar 25 00:00:00 EDT 2020]\nUSD: 1.1762314892, 1.1762314892, 1.1762314892, 1.1598365064, " +
                "1.1773072747, 1.1832398938, \nCNY: 8.3401810432, 8.3401810432, 8.3401810432, 8.2160912122," +
                " 8.3115092291, 8.3979760226, \n", cdp.getData().toString());
    }
}
