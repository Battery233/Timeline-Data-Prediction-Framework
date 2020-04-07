package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;
import java.util.Set;

public interface DisplayPlugin extends Plugin{

    void setDataSet(DataSet metaData);

    void setDisplayDataSet(DisplayDataSet data);

    JPanel display();
}
