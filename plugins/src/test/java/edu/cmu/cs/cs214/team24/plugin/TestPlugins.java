package edu.cmu.cs.cs214.team24.plugin;

import edu.cmu.cs.cs214.team24.framework.core.DataPlugin;
import edu.cmu.cs.cs214.team24.framework.core.FrameworkImpl;
import edu.cmu.cs.cs214.team24.framework.core.PluginLoader;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestPlugins {

    @Test
    public void testFrameWorkInit() {
        FrameworkImpl framework = new FrameworkImpl();
        assertEquals(1, framework.getDataPluginNumber());
    }

    @Test
    public void testLoading() {
        assertEquals(1, PluginLoader.listPlugins());
    }

    @Test
    public void testCurrencyData() {
        DataPlugin cdp = new CurrencyDataPlugin();
        Calendar start = Calendar.getInstance();
        start.set(2020, Calendar.MARCH, 20);
        assertFalse(cdp.setTimePeriod(start, start));
        Calendar end = Calendar.getInstance();
        end.set(2020, Calendar.MARCH, 25);
        cdp.setTimePeriod(start, end);
        cdp.addParam("base","GBP");
        cdp.addParam("symbols","CNY");
        cdp.addParam("symbols","USD");
        cdp.getData();
    }
}
