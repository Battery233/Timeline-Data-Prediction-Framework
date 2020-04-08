package edu.cmu.cs.cs214.hw5.framework.core;

import java.util.List;
import java.util.Map;

public interface Plugin {
    String name();

    boolean isDataPlugin();

    Map<String, List<String>> getParamOptions();

    Map<String, Boolean> areParamsMultiple();

    boolean addParam(String param, String option);

}
