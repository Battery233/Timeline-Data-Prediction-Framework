package edu.cmu.cs.cs214.team24.framework.core;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface DataPlugin extends Plugin {

    Map<String, List<String>> getParamOptions();

    boolean addParam(String param, String option);

    boolean setTimePeriod(Calendar start, Calendar end);

    DataSet getData();
}
