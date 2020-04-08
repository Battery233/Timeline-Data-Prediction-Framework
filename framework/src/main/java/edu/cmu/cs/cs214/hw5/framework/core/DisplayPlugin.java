package edu.cmu.cs.cs214.hw5.framework.core;

import javax.swing.JPanel;

/**
 * The interface for display plugin
 */
public interface DisplayPlugin extends Plugin{

    /**
     * Set the meta data for the display plugin
     * @param metaData   the data originally extracted from data plugin
     */
    void setDataSet(DataSet metaData);

    /**
     * Set the display data for the display plugin
     * @param data   the display data which has been processed by framework
     */
    void setDisplayDataSet(DisplayDataSet data);

    /**
     * Clear the history in display plugin record
     */
    void clearToDisplay();

    /**
     * Generate a Jpanel for displaying
     * @return    a Jpanel instance which will be added in GUI frame
     */
    JPanel display();
}
