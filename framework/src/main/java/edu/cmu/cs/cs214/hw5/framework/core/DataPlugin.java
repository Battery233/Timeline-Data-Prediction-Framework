package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.Date;

/**
 *  The interface of data plugin
 */
public interface DataPlugin extends Plugin {
    /**
     * Set the time period for a data plugin and determine if it succeed.
     * @param start   the start date
     * @param end     the end date
     * @return       true or false
     */
    boolean setTimePeriod(Date start, Date end);

    /**
     * Get the data of the data plugin
     * @return   a dataset instance which represents the data
     */
    DataSet getData();
}
