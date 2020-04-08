package edu.cmu.cs.cs214.hw5.framework.core;

import javax.swing.JPanel;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The interface of Framework.
 */
public interface Framework {

    /**
     * To register the current plugin in the framework.
     *
     * @param plugin The current plugin.
     */
    void registerPlugin(Plugin plugin);

    /**
     * Set a change listener to listen to any changes.
     *
     * @param listener The listener to listen to all the changes.
     */
    void setStatusChangeListener(StatusChangeListener listener);

    /**
     * Set the current data plugin to the input data plugin.
     *
     * @param plugin The input data plugin.
     */
    void setCurrentDataPlugin(Plugin plugin);

    /**
     * Set the current display plugin to the input display plugin.
     *
     * @param plugin The input display plugin.
     */
    void setCurrentDisplayPlugin(Plugin plugin);

    /**
     * To get the map whose key is the configuration parameter for the plugin and the value is the list of
     * possible options which can be configured.
     *
     * @param isDataPlugin true if we are getting parameter from data plug,
     *                     false if we are getting parameter from display plug,
     * @return A map which mapping parameter and the list of options.
     */
    Map<String, List<String>> getParamOptions(boolean isDataPlugin);

    /**
     * To get the map whose key is the configuration parameter for the plugin and
     * the value represents whether the parameter can have multiple values.
     *
     * @param isDataPlugin true if we are getting it from the data plugin,
     *                     false if we are getting it from the display plugin,
     * @return A map which mapping parameter and whether it can be multiple checked.
     */
    Map<String, Boolean> getAreDataParamsMultiple(boolean isDataPlugin);

    /**
     * Set the parameters for a data or display plugin.
     *
     * @param isDataPlugin true if we are setting the data plugin,
     *                     false if we are setting the display plugin,
     * @param params       A map which mapping parameter and the list of options.
     * @param startDate    The start date for the plugin
     * @param endDate      The end date for the plugin.
     * @return True if set the parameters successfully.
     */
    boolean setPluginParameters(boolean isDataPlugin, Map<String, List<String>> params, Date startDate, Date endDate);

    /**
     * Get the data from the data plugin.
     *
     * @return True if get the data successfully.
     */
    boolean getData();

    /**
     * Set the Options of the display plugin. The goal is to extracted options from data set which
     * extracted from data plugin and set it in the display plugin.
     */
    void setDisplayPluginOptions();

    /**
     * Process the data extracted from the data plugin and generate a JPanel to display.
     *
     * @return A Jpanel for displaying in GUI.
     */
    JPanel processAndDisplay();
}
