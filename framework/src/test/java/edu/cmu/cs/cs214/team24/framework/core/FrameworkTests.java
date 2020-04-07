package edu.cmu.cs.cs214.team24.framework.core;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameworkTests {
    @Test
    public void testPredict() throws ParseException {
        FrameworkImpl impl = new FrameworkImpl();
        String str = "2019-06-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(str);
        Date[] dates = new Date[6];
        for (int i = 0; i < 6; i++) {
            dates[i] = new Date(start.getTime() + i * 86400000);
        }
        double[] arr1 = new double[]{1, 2, 3, 4, 5, 6};
        double[] arr2 = new double[]{1.83, 2.77, 3.46, 4.54, 5.12, 3.58};
        Map<String, double[]> map = new HashMap<>();
        map.put("RMB", arr1);
        map.put("USD", arr2);
        DataSet data = new DataSet(dates, map);
        Map<String, List<Double>> res = impl.predict(data, 2, 3);
        //System.out.println(res);
        double expect1 = res.get("RMB").get(6);
        double expect2 = res.get("USD").get(7);
        Assert.assertTrue(Math.abs(expect1 - 7) < 1);
        Assert.assertTrue(Math.abs(expect2 - 1) < 1);
    }
}
