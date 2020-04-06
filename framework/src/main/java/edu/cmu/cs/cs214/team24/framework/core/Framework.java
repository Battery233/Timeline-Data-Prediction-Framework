package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Framework {

    List<String> getDataPluginNames();

    List<String> getDisplayPluginNames();

    void setCurrentDataPlugin(Plugin plugin);

    void setCurrentDisplayPlugin(Plugin plugin);

    Map<String, List<String>> getParamOptions(boolean isDataPlugin);

    boolean setPluginParameters(boolean isDataPlugin, Map<String, List<String>> params, Date startDate, Date endDate);

    DataSet getData();

    JPanel processAndDisplay();
}
