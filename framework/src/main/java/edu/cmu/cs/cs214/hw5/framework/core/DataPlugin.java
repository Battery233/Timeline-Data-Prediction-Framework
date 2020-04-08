package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.Date;

public interface DataPlugin extends Plugin {
    boolean setTimePeriod(Date start, Date end);

    DataSet getData();
}
