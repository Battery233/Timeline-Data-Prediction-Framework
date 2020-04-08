package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.Date;

/**
 * The interface of the plugins which can get data from data sources.
 */
public interface DataPlugin extends Plugin {
    /**
     * Set the time period for a data plugin to fetch the data
     *
     * @param start the start date of the period (included)
     * @param end   the end date of the period (included)
     * @return true if the operation is successful, false if failed.
     */
    boolean setTimePeriod(Date start, Date end);

    /**
     * Get the data from the data plugin, after the plugin is configured properly.
     *
     * @return a dataset instance which represents the data from data source
     */
    DataSet getData();
}
