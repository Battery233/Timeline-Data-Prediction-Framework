package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.List;
import java.util.Map;

public interface Plugin {
    /**
     * The name function can be used to identify a plugin
     *
     * @return a String to indicate the name of the plugin.
     */
    String name();

    /**
     * A function to indicate the type of the plugin.
     *
     * @return true if the plug is a data plug, false, if the plugin is a display plugin.
     */
    boolean isDataPlugin();

    /**
     * Gives a map which contains all possible options for the user to config the plugin.
     * The Key string is the name of the parameter users can config, and the List contains all
     * possible choices the user can choose for the specific parameter.
     * for example, <key = "base currency", List<String> = "GBP", "USD", "EUR"> indicates the "base currency"
     * parameter of the plugin have 3 possible options.
     *
     * @return a map contains all possible options for the parameters of the plugin
     */
    Map<String, List<String>> getParamOptions();

    /**
     * Gives a map to indicate if the parameter of the plugin can intake multiply vales.
     * for example, <key = "base currency", value = true> means that the "base currency" can have
     * multiply values as configuration.
     *
     * @return a map indicates whether the parameter can take more than 1 value.
     */
    Map<String, Boolean> areParamsMultiple();

    /**
     * Function for set a plugin parameter to a specific value or add a value to the parameter.
     *
     * @param param  the parameter to set value
     * @param option the value of the configuration
     * @return whether the operation of setting parameter is successful'
     */
    boolean addParam(String param, String option);
}
