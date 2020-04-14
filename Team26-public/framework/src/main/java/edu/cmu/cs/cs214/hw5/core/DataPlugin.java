package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;

import java.util.List;

/**
 * The data plugin interface for extracting data sets from one type of data source.
 */
public interface DataPlugin {

    /**
     * Gets the names of all data sets that the data plugin provides.
     * (These names will be displayed on the GUI). Return empty list
     * if the data plugin requires user input (e.g. file path, data
     * name, etc.) to fetch data set
     *
     * @return the list containing the names of all data sets that the
     * data plugin provides
     */
    List<String> getAvailableDataSetNames();

    /**
     * Get a data set from the data plugin according to the string representation
     * of the data set. For example, the string could be file path, data name, etc.
     *
     * @param dataSetName the string needed to fetch the data set
     * @return the data set
     */
    DataSet getDataSet(String dataSetName);

    /**
     * Gets the name of the data source that that data plugin uses. For example,
     * a CSV data plugin should return "CSV". Yahoo Finance Web API data plugin
     * should return "Yahoo Finance Web API". This name will be displayed in GUI
     * when the user chooses data plugin
     *
     * @return the name of the data source that the data plugin uses
     */
    String getDataSourceName();

    /**
     * Gets the user input prompt for fetching data set from the data plugin. For example,
     * a CSV data plugin should have the prompt "Enter File Path". Yahoo Finance Web
     * API data plugin should have the prompt "Enter Stock Symbol". If the data plugin
     * DOES NOT require user input to fetch data, simply return the empty string "".
     *
     * @return the user input prompt for fetching data set from the data plugin
     */
    String getPrompt();
}
