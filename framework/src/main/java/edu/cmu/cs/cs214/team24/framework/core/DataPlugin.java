package edu.cmu.cs.cs214.team24.framework.core;

import java.util.Calendar;

public interface DataPlugin extends Plugin {
    boolean setTimePeriod(Calendar start, Calendar end);

    DataSet getData();
}
