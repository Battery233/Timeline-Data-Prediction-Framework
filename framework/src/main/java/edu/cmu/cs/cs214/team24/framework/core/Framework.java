package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Framework {

    void registerPlugin(Plugin plugin);

    void setStatusChangeListener(StatusChangeListener listener);

    void setCurrentDataPlugin(Plugin plugin);

    void setCurrentDisplayPlugin(Plugin plugin);

    Map<String, List<String>> getParamOptions(boolean isDataPlugin);

    Map<String, Boolean> getAreDataParamsMultiple(boolean isDataPlugin);

    boolean setPluginParameters(boolean isDataPlugin, Map<String, List<String>> params, Date startDate, Date endDate);

    DataSet getData();

    JPanel processAndDisplay();
}
