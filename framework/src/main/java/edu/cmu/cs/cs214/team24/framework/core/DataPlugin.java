package edu.cmu.cs.cs214.team24.framework.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DataPlugin extends Plugin {

    Map<String, List<String>> getParamOptions();

    boolean addParam(String param, String option);

    boolean setTimePeriod(Date start, Date end);

    boolean downloadData();

    DataSet getData();

    void trimData();

}
