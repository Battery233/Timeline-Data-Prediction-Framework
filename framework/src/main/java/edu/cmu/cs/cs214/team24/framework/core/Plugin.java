package edu.cmu.cs.cs214.team24.framework.core;

import java.util.List;
import java.util.Map;

public interface Plugin {
    String name();

    boolean isDataPlugin();

    boolean isDisplayPlugin();

    Map<String, List<String>> getParamOptions();

    Map<String, Boolean> isParamsMultiple();

    boolean addParam(String param, String option);

}
